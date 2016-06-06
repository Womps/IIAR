package Memoire;

import java.util.ArrayList;

import Model.Pion;

public class Noeud 
{
	private int ID_TableHash;
	private int Case_TableHash;
	private Pion[][] Tableau;
	private ArrayList<Noeud> NoeudSuiv;
	private boolean noeudFinal;
	private boolean noeudStart;
	
	public Noeud(int idHash, int caseHash, Pion[][] jeux, boolean fini, boolean start)
	{
		this.Case_TableHash = caseHash;
		this.ID_TableHash = idHash;
		this.setTableau(jeux);
		this.NoeudSuiv = new ArrayList<Noeud>();
		this.noeudFinal = fini;
		this.noeudStart = start;
	}

	public Pion[][] getTableau() {
		return Tableau;
	}

	public void setTableau(Pion[][] tableau) {
		Tableau = tableau;
	}
	
	public int getCase_TableHash() {
		return Case_TableHash;
	}

	public int getID_TableHash() {
		return ID_TableHash;
	}

	public ArrayList<Noeud> getNoeudSuiv() {
		return NoeudSuiv;
	}

	
	public boolean isNoeudFinal() {
		return noeudFinal;
	}

	public boolean isNoeudStart() {
		return noeudStart;
	}

	public void setNoeudStart(boolean noeudStart) {
		this.noeudStart = noeudStart;
	}
}
