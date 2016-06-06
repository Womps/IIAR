package Memoire;

import java.util.ArrayList;

import Model.Pion;

public class PointRetour 
{
	private Tree arbreDePartie;
	
	private static PointRetour instance = null;
	
	private PointRetour()
	{
		this.arbreDePartie = new Tree();
	}
	
	public static PointRetour getInstance()
	{
		if(instance == null)
			instance = new PointRetour();
		
		return instance;
	}
	
	public void attributionPointNonRetour()
	{
		ArrayList<Integer> antiBoucle = new ArrayList<Integer>();
		final int nbStart = this.arbreDePartie.getTableHash().getTableStart().size();
		int compt = 0;
		
		while(compt < nbStart)
		{
			antiBoucle.add(this.arbreDePartie.getTableHash().getTableStart().get(compt).getID_TableHash());
			int res = parcourtNoeud(this.arbreDePartie.getTableHash().getTableStart().get(compt), antiBoucle);
			if(res == 0)
			{
				this.arbreDePartie.getTableHash().addPerdant(this.arbreDePartie.getTableHash().getTableStart().get(compt), 0);
				System.out.println();
				System.out.println();
				System.out.println("noir");
				for(int x = 0; x < 10; x++)
				{
					for(int y = 0; y < 10; y++)
					{
						System.out.print((this.arbreDePartie.getTableHash().getTableStart().get(compt).getTableau()[x][y].getCouleur()==-1 ? "-" : (this.arbreDePartie.getTableHash().getTableStart().get(compt).getTableau()[x][y].getCouleur()))+" ");
					}
					System.out.println();
				}
			}
			else if(res == 1)
			{
				this.arbreDePartie.getTableHash().addPerdant(this.arbreDePartie.getTableHash().getTableStart().get(compt), 1);
				System.out.println();
				System.out.println();
				System.out.println("blanc");
				for(int x = 0; x < 10; x++)
				{
					for(int y = 0; y < 10; y++)
					{
						System.out.print((this.arbreDePartie.getTableHash().getTableStart().get(compt).getTableau()[x][y].getCouleur()==-1 ? "-" : (this.arbreDePartie.getTableHash().getTableStart().get(compt).getTableau()[x][y].getCouleur()))+" ");
					}
					System.out.println();
				}
			}
			
			compt++;
		}
		
		System.out.println("Nombre de partie finie : " + this.arbreDePartie.getTableHash().getTableFin().size());
		System.out.println("Nombre de partie Commencé : " + this.arbreDePartie.getTableHash().getTableStart().size());
		int c = 0;
		for(int i = 0; i < this.arbreDePartie.getTableHash().getNoeudNoirPerdant().length; i++)
		{
			c =c+ this.arbreDePartie.getTableHash().getNoeudNoirPerdant()[i].size();
		}
		System.out.println("Nombre de point Noir : " + c);
		c = 0;
		for(int i = 0; i < this.arbreDePartie.getTableHash().getNoeudBlancPerdant().length; i++)
		{
			c = c+ this.arbreDePartie.getTableHash().getNoeudBlancPerdant()[i].size();
		}
		System.out.println("Nombre de point Blanc : " + c);
	}
	
	/*
	 * Retourne :
	 * 	0 pour noir
	 *  1 pour blanc
	 *  -1 pour null
	 */
	public int parcourtNoeud(Noeud noeudPere, ArrayList<Integer> antiBoucle)
	{
		final int nbNoeudFils = noeudPere.getNoeudSuiv().size();
		int compt = 0;
		ArrayList<NoeudPointNonRetour> resCompare = new ArrayList<NoeudPointNonRetour>();
		
		if(nbNoeudFils == 0)
		{
			if(noeudPere.getTableau()[0][0].getCouleur() == 0){
				return 1;
			}
			else if(noeudPere.getTableau()[0][0].getCouleur() == 1){
				return 0;
			}
			else
				return -1;
		}
		
		antiBoucle.add(noeudPere.getID_TableHash());
		while(compt < nbNoeudFils)
		{
			boolean addPass = true;
			for(int i = 0; i < antiBoucle.size(); i++)
			{
				if(antiBoucle.get(i) == noeudPere.getNoeudSuiv().get(compt).getID_TableHash())
				{
					addPass = false;
				}
			}
			if(!addPass)
			{
				compt++;
				continue;
			}
			
			int res = parcourtNoeud(noeudPere.getNoeudSuiv().get(compt), antiBoucle);
			if(resCompare.size() == 0 && res != -1)
			{
				resCompare.add(new NoeudPointNonRetour(res, noeudPere.getNoeudSuiv().get(compt)));
			}
			else if(res != -1)
			{
				boolean add = true;
				for(int i = 0; i < resCompare.size(); i++)
				{
					if(resCompare.get(i).getCouleurPerdante() == res)
					{
						add = false;
					}
				}
				
				if(add)
					resCompare.add(new NoeudPointNonRetour(res, noeudPere.getNoeudSuiv().get(compt)));
			}
			compt++;
		}
		
		if(resCompare.size() > 1)
		{
			for(int i = 0; i < resCompare.size(); i++)
			{
				if(resCompare.get(i).getCouleurPerdante() == 0)
				{
					this.arbreDePartie.getTableHash().addPerdant(resCompare.get(i).getNoeudFilsNonRetour(), 0);
					System.out.println();
					System.out.println();
					System.out.println("noir");
					for(int x = 0; x < 10; x++)
					{
						for(int y = 0; y < 10; y++)
						{
							System.out.print((resCompare.get(i).getNoeudFilsNonRetour().getTableau()[x][y].getCouleur()==-1 ? "-" : (resCompare.get(i).getNoeudFilsNonRetour().getTableau()[x][y].getCouleur()))+" ");
						}
						System.out.println();
					}
				}
				else if(resCompare.get(i).getCouleurPerdante() == 1)
				{
					this.arbreDePartie.getTableHash().addPerdant(resCompare.get(i).getNoeudFilsNonRetour(), 1);
					System.out.println();
					System.out.println();
					System.out.println("blanc");
					for(int x = 0; x < 10; x++)
					{
						for(int y = 0; y < 10; y++)
						{
							System.out.print((resCompare.get(i).getNoeudFilsNonRetour().getTableau()[x][y].getCouleur()==-1 ? "-" : (resCompare.get(i).getNoeudFilsNonRetour().getTableau()[x][y].getCouleur()))+" ");
						}
						System.out.println();
					}
				}
			}
		}
		return ((resCompare.size() == 1)? resCompare.get(0).getCouleurPerdante():-1);
	}

	/*
	 * 0 pour noir
	 * 1 pour blanc
	 */
	public boolean searchCoupPerdant(int couleur, Pion[][] plateau)
	{
		if(this.arbreDePartie.getTableHash().searchPerdant(plateau, couleur) != null)
			return true;
		
		return false;
	}

}
