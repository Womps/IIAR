package Memoire;

import java.util.ArrayList;

import Model.*;

public class TableNoeud 
{
	private ArrayList<Noeud> tableHash[];
	private ArrayList<Noeud> tableStart;
	private ArrayList<Noeud> tableFin;
	private ArrayList<Noeud> noeudNoirPerdant[];
	private ArrayList<Noeud> noeudBlancPerdant[];
	private final int tailleTableHash = 100;
	
	@SuppressWarnings("unchecked")
	public TableNoeud()
	{
		this.tableStart = new ArrayList<Noeud>();
		this.tableFin = new ArrayList<Noeud>();
		this.tableHash = new ArrayList[this.tailleTableHash];
		for(int i = 0; i < this.tailleTableHash; i++)
		{
			this.tableHash[i] = new ArrayList<Noeud>();
		}
		
		this.noeudNoirPerdant = new ArrayList[this.tailleTableHash];
		for(int i = 0; i < this.tailleTableHash; i++)
		{
			this.noeudNoirPerdant[i] = new ArrayList<Noeud>();
		}
		
		this.noeudBlancPerdant = new ArrayList[this.tailleTableHash];
		for(int i = 0; i < this.tailleTableHash; i++)
		{
			this.noeudBlancPerdant[i] = new ArrayList<Noeud>();
		}
	}
	
	/*
	 * retourne un tableau d'entier avec : [0] => id et [1] => case de la table
	 */
	public int[] getHash(Pion[][] jeux)
	{
		int coef = 1;
		int id = 0;
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				int base = ((jeux[x][y].getCouleur() == -1) ? 0 : ((jeux[x][y].getCouleur() == 1) ? 2 : 3));
				id += Math.pow(coef, base);
				coef++;
			}
		}
		return new int[]{id, id%this.tailleTableHash};
	}
	
	public void addElement(Noeud pere, Pion[][] jeux, boolean fini, boolean start)
	{
		int id[] = this.getHash(jeux);
		Noeud element = searshElement(id[1], id[0]);
		
		if(element == null)
		{
			Noeud fils = new Noeud(id[0], id[1], jeux, fini, start);
			this.tableHash[id[1]].add(fils);
			if(pere != null)
				pere.getNoeudSuiv().add(fils);
			
			if(start)
			{
				this.tableStart.add(fils);
			}
			
			if(fini)
			{
				this.tableFin.add(fils);
			}
		}
		else if(pere != null)
		{
			pere.getNoeudSuiv().add(element);
			if(fini)
			{
				this.tableFin.add(element);
			}
		}
	}
	
	public Noeud searshElement(int idCase, int id)
	{
		if(idCase < 0 && idCase >= 100)
			return null;
		
		final int max = this.tableHash[idCase].size();
		int compt = 0;
		while(compt < max)
		{
			if(this.tableHash[idCase].get(compt).getID_TableHash() == id)
			{
				return this.tableHash[idCase].get(compt);
			}
			compt++;
		}
		return null;
	}
	
	public Noeud searshElement(Pion[][] pl)
	{
		int id[] = getHash(pl);
		return searshElement(id[1], id[0]);
	}

	public ArrayList<Noeud>[] getTableHash() {
		return tableHash;
	}
	
	public ArrayList<Noeud> getTableStart()
	{
		return this.tableStart;
	}
	
	public ArrayList<Noeud> getTableFin()
	{
		return this.tableFin;
	}
	
	public void addPerdant(Noeud nd, int couleur)
	{
		if(couleur == 0)
		{
			this.noeudNoirPerdant[nd.getCase_TableHash()].add(nd);
		}
		else if(couleur == 1)
		{
			this.noeudBlancPerdant[nd.getCase_TableHash()].add(nd);
		}
	}

	public Noeud searchPerdant(Pion[][] jeux, int couleur)
	{
		int id[] = this.getHash(jeux);
		if(id[1] < 0 && id[1] >= 100)
			return null;
		
		if(couleur == 0)
		{
			final int max = this.noeudNoirPerdant[id[1]].size();
			int compt = 0;
			while(compt < max)
			{
				if(this.noeudNoirPerdant[id[1]].get(compt).getID_TableHash() == id[0])
				{
					return this.noeudNoirPerdant[id[1]].get(compt);
				}
				compt++;
			}
			return null;
		}
		else if(couleur == 1)
		{
			final int max = this.noeudBlancPerdant[id[1]].size();
			int compt = 0;
			while(compt < max)
			{
				if(this.noeudBlancPerdant[id[1]].get(compt).getID_TableHash() == id[0])
				{
					return this.noeudBlancPerdant[id[1]].get(compt);
				}
				compt++;
			}
			return null;
		}
		
		return null;
	}
	
	public ArrayList<Noeud>[] getNoeudNoirPerdant() {
		return this.noeudNoirPerdant;
	}
	
	public ArrayList<Noeud>[] getNoeudBlancPerdant() {
		return this.noeudBlancPerdant;
	}
}
