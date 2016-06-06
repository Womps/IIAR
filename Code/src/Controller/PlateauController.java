package Controller;


import java.util.ArrayList;

import Memoire.PointRetour;
import Model.*;
import Model.Pion;
import View.*;

public class PlateauController
{
	private boolean tourNoir;
	private int[] pionSelect; //[x, y]
	private Plateaux game;
	private static PlateauController instance = null;
	private ArrayList<Pion[][]> saveGame;
	private FilesDatas filesData;
	
	private PlateauController()
	{
		this.initGame();
		this.filesData = new FilesDatas();
		PointRetour.getInstance().attributionPointNonRetour();
	}
	
	public void initGame()
	{
		this.game = new Plateaux();
		this.tourNoir = false;
		this.pionSelect = new int[]{-1, -1};
		this.saveGame = new ArrayList<Pion[][]>();
		System.out.println("SaveCoup pour memoire");
		this.addSaveGame(this.game.getGamePlateau());
	}
	
	public static PlateauController getInstance()
	{
		if(instance == null)
			instance = new PlateauController();
		
		return instance;
	}
	
	public Plateaux getGame()
	{
		return this.game;
	}
	
	public void setGame(Plateaux ga)
	{
		this.game = ga;
	}
	
	public boolean possibleSaute()
	{
		boolean saute = false;
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				saute = isSauteOne(x, y, saute);
			}
		}
		return saute;
	}
	
	public boolean isSauteOne(int x, int y, boolean saute)
	{
		int[] tmp = this.pionSelect;
		
		if(this.game.getGamePlateau()[x][y].getCouleur() == 0 && this.tourNoir && !saute)
		{
			this.pionSelect = new int[]{x, y};
			if(this.game.getGamePlateau()[x][y].getDeplaceTop())
			{
				if(x-2<10 && x-2>=0 && y-2<10 && y-2>=0)
					saute = isSaute(x-2, y-2);
				if(x-2<10 && x-2>=0 && y+2<10 && y+2>=0 && !saute)
					saute = isSaute(x-2, y+2);
			}
			else
			{
				if(x+2<10 && x+2>=0 && y+2<10 && y+2>=0)
					saute = isSaute(x+2, y+2);
				if(x+2<10 && x+2>=0 && y-2<10 && y-2>=0 && !saute)
					saute = isSaute(x+2, y-2);
			}
		}
		else if(this.game.getGamePlateau()[x][y].getCouleur() == 1 && !this.tourNoir && !saute)
		{
			this.pionSelect = new int[]{x, y};
			if(this.game.getGamePlateau()[x][y].getDeplaceTop())
			{
				if(x-2<10 && x-2>=0 && y-2<10 && y-2>=0)
					saute = isSaute(x-2, y-2);
				if(x-2<10 && x-2>=0 && y+2<10 && y+2>=0 && !saute)
					saute = isSaute(x-2, y+2);
			}
			else
			{
				if(x+2<10 && x+2>=0 && y+2<10 && y+2>=0)
					saute = isSaute(x+2, y+2);
				if(x+2<10 && x+2>=0 && y-2<10 && y-2>=0 && !saute)
					saute = isSaute(x+2, y-2);
			}
		}
		this.pionSelect = tmp;
		return saute;
	}
	
	
	
	public boolean isPossible(int xSelect, int ySelect)
	{
		if(this.pionSelect[0] != -1 && this.game.getGamePlateau()[xSelect][ySelect] == this.game.getEmpty())
		{
			Model.Pion pion = this.game.getGamePlateau()[this.pionSelect[0]][this.pionSelect[1]];
			if(pion.getDeplaceTop())
			{
				if((this.pionSelect[0]-1) == xSelect && (this.pionSelect[1]-1)==ySelect
						&& this.game.getGamePlateau()[xSelect][ySelect].getCouleur() == this.game.getEmpty().getCouleur())
				{
					return true;
				}
				else if((this.pionSelect[0]-1) == xSelect && (this.pionSelect[1]+1)==ySelect
						&& this.game.getGamePlateau()[xSelect][ySelect].getCouleur() == this.game.getEmpty().getCouleur())
				{
					return true;
				}
			}
			else
			{
				if((this.pionSelect[0]+1) == xSelect && (this.pionSelect[1]+1)==ySelect
						&& this.game.getGamePlateau()[xSelect][ySelect].getCouleur() == this.game.getEmpty().getCouleur())
				{
					return true;
				}
				else if((this.pionSelect[0]+1) == xSelect && (this.pionSelect[1]-1)==ySelect
						&& this.game.getGamePlateau()[xSelect][ySelect].getCouleur() == this.game.getEmpty().getCouleur())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isSaute(int xSelect, int ySelect)
	{
		if(this.pionSelect[0] != -1 && this.game.getGamePlateau()[xSelect][ySelect].equals(this.game.getEmpty()))
		{
			Model.Pion pion = this.game.getGamePlateau()[this.pionSelect[0]][this.pionSelect[1]];
			if(pion.getDeplaceTop())
			{
				
				if((this.pionSelect[0]-2) == xSelect && (this.pionSelect[1]-2)==ySelect
						&& !this.game.getGamePlateau()[(this.pionSelect[0]-1)][(this.pionSelect[1]-1)].equals(this.game.getEmpty()) 
						&& this.game.getGamePlateau()[(this.pionSelect[0]-1)][(this.pionSelect[1]-1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
				else if((this.pionSelect[0]-2) == xSelect && (this.pionSelect[1]+2)==ySelect
						&& !this.game.getGamePlateau()[(this.pionSelect[0]-1)][(this.pionSelect[1]+1)].equals(this.game.getEmpty())
						&& this.game.getGamePlateau()[(this.pionSelect[0]-1)][(this.pionSelect[1]+1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
			}
			else
			{
				if((this.pionSelect[0]+2) == xSelect && (this.pionSelect[1]+2)==ySelect
						&& !this.game.getGamePlateau()[(this.pionSelect[0]+1)][(this.pionSelect[1]+1)].equals(this.game.getEmpty()) 
						&& this.game.getGamePlateau()[(this.pionSelect[0]+1)][(this.pionSelect[1]+1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
				else if((this.pionSelect[0]+2) == xSelect && (this.pionSelect[1]-2)==ySelect
						&& !this.game.getGamePlateau()[(this.pionSelect[0]+1)][(this.pionSelect[1]-1)].equals(this.game.getEmpty())
						&& this.game.getGamePlateau()[(this.pionSelect[0]+1)][(this.pionSelect[1]-1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean pionExistant(int couleur)
	{
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(this.game.getGamePlateau()[x][y].getCouleur() == couleur)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isPossibleDeplacement(int couleur)
	{
		boolean deplacement = false;
		int[] tmp = this.pionSelect;
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(!deplacement && this.game.getGamePlateau()[x][y].getCouleur() == couleur)
				{
					this.pionSelect = new int[]{x, y};
					if(this.game.getGamePlateau()[x][y].getDeplaceTop())
					{
						if(x-1<10 && x-1>=0 && y-1<10 && y-1>=0)
							deplacement = isPossible(x-1, y-1);
						if(x-1<10 && x-1>=0 && y+1<10 && y+1>=0 && !deplacement)
							deplacement = isPossible(x-1, y+1);
					}
					else
					{
						if(x+1<10 && x+1>=0 && y+1<10 && y+1>=0)
							deplacement = isPossible(x+1, y+1);
						if(x+1<10 && x+1>=0 && y-1<10 && y-1>=0 && !deplacement)
							deplacement = isPossible(x+1, y-1);
					}
				}
			}
		}
		this.pionSelect = tmp;
		return deplacement;
	}
	
	public void setPionActif(int xPionActif, int yPionActif)
	{
		if((this.tourNoir && this.game.getGamePlateau()[xPionActif][yPionActif].getCouleur() == 0) 
				|| (!this.tourNoir && this.game.getGamePlateau()[xPionActif][yPionActif].getCouleur() == 1))
		{
			this.pionSelect[0] = xPionActif;
			this.pionSelect[1] = yPionActif;
		}	
	}
	
	public int isTerminer()
	{
		if(!isPossibleDeplacement(0))
			return 1;
		else if(!isPossibleDeplacement(1))
			return 0;
		
		if(!pionExistant(0))
			return 1;
		else if(!pionExistant(1))
			return 0;
		
		return -1;
	}
	
	/*
	 * return true s'il y a un autre pion a prendre sinon false
	 */
	public boolean validSelection(boolean saute, int x, int y)
	{
		if(saute)
		{
			if(this.game.getGamePlateau()[this.pionSelect[0]][this.pionSelect[1]].getDeplaceTop())
			{
				if((this.pionSelect[0]-2) == x && (this.pionSelect[1]-2)==y)
				{
					this.game.delPion(this.pionSelect[0]-1, this.pionSelect[1]-1);
				}
				else if((this.pionSelect[0]-2) == x && (this.pionSelect[1]+2)==y)
				{
					this.game.delPion(this.pionSelect[0]-1, this.pionSelect[1]+1);
				}
			}
			else
			{
				if((this.pionSelect[0]+2) == x && (this.pionSelect[1]+2)==y)
				{
					this.game.delPion(this.pionSelect[0]+1, this.pionSelect[1]+1);
				}
				else if((this.pionSelect[0]+2) == x && (this.pionSelect[1]-2)==y)
				{
					this.game.delPion(this.pionSelect[0]+1, this.pionSelect[1]-1);
				}
			}
			
			this.game.deplacePion(this.pionSelect[0], this.pionSelect[1], x, y);
			if(!isSauteOne(x, y, false))
			{
				this.tourNoir = !this.tourNoir;
				this.addSaveGame(this.game.getGamePlateau());
				System.out.println("SaveCoup pour memoire");
			}
			else
			{
				return true;
			}
		}
		else
		{
			this.game.deplacePion(this.pionSelect[0], this.pionSelect[1], x, y);
			this.tourNoir = !this.tourNoir;
			this.addSaveGame(this.game.getGamePlateau());
			System.out.println("SaveCoup pour memoire");
		}
		int tmpTeminer = isTerminer();
		if(tmpTeminer != -1)
		{
			savePartieInMemoire(tmpTeminer);
			new FinFrame(tmpTeminer);
		}
		
		return false;
	}
	
	
	public boolean getTourNoir()
	{
		return this.tourNoir;
	}
	
	public void setTourNoir()
	{
		this.tourNoir = !this.tourNoir;
	}

	public ArrayList<Pion[][]> getSaveGame() {
		return saveGame;
	}
	
	public void addSaveGame(Pion[][] p)
	{
		Pion[][] fini = new Pion[10][10];
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				fini[i][j] = new Pion(p[i][j].getCouleur(), p[i][j].getDeplaceTop());
			}
		}
		this.saveGame.add(fini);
	}

 	public void setSaveGame(ArrayList<Pion[][]> saveGame) {
		this.saveGame = saveGame;
	}
	
	public void savePartieInMemoire(int gagnant)
	{
		Pion[][] fini = new Pion[10][10];
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				fini[i][j] = new Pion(gagnant, false);
			}
		}
		this.saveGame.add(fini);
		this.filesData.sauvegarder(this.saveGame);
	}
	
}
