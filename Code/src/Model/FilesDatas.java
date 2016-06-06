package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class FilesDatas
{
	private File directory;
	private Plateaux mesPlateaux;
	
	public FilesDatas()
	{
		this.directory = new File(".//");
		this.mesPlateaux = new Plateaux();
	}
	
	public ArrayList<ArrayList<Pion[][]>> charger()
	{
		ArrayList<ArrayList<Pion[][]>> myGames = new ArrayList<ArrayList<Pion[][]>>();
		String[] myFiles = this.directory.list();
		
		for(int i = 0; i < myFiles.length; i++)
		{
			String fileName = this.directory + "\\" + myFiles[i]; 
		    if(myFiles[i].endsWith(".txt"))
		    {
		    	ArrayList<Pion[][]> unePartie = new ArrayList<Pion[][]>();
				try
			    {
					BufferedReader in  = new BufferedReader(new FileReader(fileName));
					String line = "";
					while ((line = in.readLine()) != null)
					{
						Pion[][] plateau = new Pion[this.mesPlateaux.getX()][this.mesPlateaux.getY()];
						int j = 0;
						int x = 0;
						int y = 0;	  
						while(j < line.length())
						{
							if(line.charAt(j) != ' ')
							{
								if(line.charAt(j) == '-')
								{
									j = j + 3;
									
									plateau[x][y] = new Pion(-1, ((line.charAt(j) == '1') ? true:false)); 
								}
								else if (line.charAt(j) == '1')
								{
									j = j + 2;
									plateau[x][y] = new Pion(1, ((line.charAt(j) == '1') ? true:false));
								}
								else if (line.charAt(j) == '0')
								{
									j = j + 2;
									plateau[x][y] = new Pion(0, ((line.charAt(j) == '1') ? true:false));
								}
								y++;
								if(y == this.mesPlateaux.getY())
								{
									y = 0;
									x++;
								}
								if(x == this.mesPlateaux.getX())
								{
									x = 0;
									unePartie.add(plateau);							
								}
								j++;
							}
							else
							{
								j++;
							}
						}

					}
					in.close();
			    }
			    catch(Exception e)
				{
			      e.printStackTrace();
			    }
				myGames.add(unePartie);
		    }
		}
		return myGames;
	}
	
	public void sauvegarder(ArrayList<Pion[][]> etat)
	{
		Date creationTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		Iterator<Pion[][]> ite = etat.iterator();
		
		try
		{
			FileWriter fw = new FileWriter ((this.directory + "\\" + dateFormat.format(creationTime) + ".txt"));
			while(ite.hasNext())
			{
				Pion[][] monPlateau = ite.next();
				
				for(int i = 0; i < this.mesPlateaux.getX(); i++)
				{
					for(int j = 0; j < this.mesPlateaux.getY(); j++)
					{
						fw.write(String.valueOf(monPlateau[i][j].getCouleur()) + ":" + ((monPlateau[i][j].getDeplaceTop() == true)? '1':'0') + " ");
					}
				}
				fw.write("\r\n");
			}
			fw.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
