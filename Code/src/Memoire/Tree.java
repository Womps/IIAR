package Memoire;

import java.util.ArrayList;

import Model.FilesDatas;
import Model.Pion;

public class Tree 
{
	private TableNoeud tableHash;
	private FilesDatas filsData;
	
	public Tree()
	{
		this.tableHash = new TableNoeud();
		this.filsData = new FilesDatas();
		this.addAllPartie();
	}
	
	public void addPartie(ArrayList<Pion[][]> listPlateauPartie)
	{
		Noeud pere = null;
		int nbPlateau = listPlateauPartie.size(), compt = 0;
		
		while(compt < nbPlateau)
		{
			this.tableHash.addElement(pere, listPlateauPartie.get(compt), ((nbPlateau == (compt+1)) ? true : false), ((0 == compt) ? true : false));
			pere = this.tableHash.searshElement(listPlateauPartie.get(compt));
			compt++;
		}
	}

	private void addAllPartie()
	{
		ArrayList<ArrayList<Pion[][]>> chargeParties = filsData.charger();
		
		int compt = 0, max = chargeParties.size();
		while(compt < max)
		{
			this.addPartie(chargeParties.get(compt));
			compt++;
		}
	}
	
	public TableNoeud getTableHash()
	{
		return this.tableHash;
	}
}
