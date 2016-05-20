package Controller;

import View.Case;
import View.Couleur;
import View.Pion;
import View.Plateau;

public class PlateauController
{
	private static final int TAILLE=9;

	private Case caseActive;
	
	private Plateau monPlateau;

	private boolean tourNoir;

	public PlateauController()
	{
		
	}

	public void afficherPossibilites(Pion p)
	{
		if((p.getCouleur().equals(Couleur.NOIR) && tourNoir) || (p.getCouleur().equals(Couleur.BLANC) && !tourNoir))
		{
			int i=0;
			int j=0;
			for(int k=0; k<TAILLE*TAILLE; k++)
			{
				monPlateau.getCase(k).setSelectionnee(false);
				if(monPlateau.getCase(k).getComponentCount()!=0 && monPlateau.getCase(k).getComponent(0).equals(p))
				{
					caseActive=monPlateau.getCase(k);
					i=k/TAILLE;
					j=k%TAILLE;

				}
			}
			selectionnerCases(i, j, p.getCouleur());
		}
	}

	public void selectionnerCases(int i, int j, Couleur couleur)
	{
		Pion pion = (Pion)(monPlateau.getCase(i, j).getComponent(0));
		if(pion.isMonte())
		{
			if(i-1>=0 && j-1>=0 && monPlateau.getCase(i-1, j-1).getComponentCount()==0)
			{
				monPlateau.getCase(i-1, j-1).setSelectionnee(true);
			}
			else if(i-2>=0 && j-2>=0 && monPlateau.getCase(i-2, j-2).getComponentCount()==0 && !((Pion)(monPlateau.getCase(i-1, j-1).getComponent(0))).getCouleur().equals(couleur))
			{
				monPlateau.getCase(i-2, j-2).setSelectionnee(true);
			}
			if(i-1>=0 && j+1<TAILLE && monPlateau.getCase(i-1, j+1).getComponentCount()==0)
			{
				monPlateau.getCase(i-1, j+1).setSelectionnee(true);
			}
			else if(i-2>=0 && j+2<TAILLE && monPlateau.getCase(i-2, j+2).getComponentCount()==0 && !((Pion)(monPlateau.getCase(i-1, j+1).getComponent(0))).getCouleur().equals(couleur))
			{
				monPlateau.getCase(i-2, j+2).setSelectionnee(true);
			}
		}
		else{
			if(i+1<TAILLE && j+1<TAILLE && monPlateau.getCase(i+1, j+1).getComponentCount()==0)
			{
				monPlateau.getCase(i+1, j+1).setSelectionnee(true);
			}
			else if(i+2<TAILLE && j+2<TAILLE && monPlateau.getCase(i+2, j+2).getComponentCount()==0 && !((Pion)(monPlateau.getCase(i+1, j+1).getComponent(0))).getCouleur().equals(couleur))
			{
				monPlateau.getCase(i+2, j+2).setSelectionnee(true);
			}
			if(i+1<TAILLE && j-1>=0 && monPlateau.getCase(i+1, j-1).getComponentCount()==0)
			{
				monPlateau.getCase(i+1, j-1).setSelectionnee(true);
			}
			else if(i+2<TAILLE && j-2>=0 && monPlateau.getCase(i+2, j-2).getComponentCount()==0 && !((Pion)(monPlateau.getCase(i+1, j-1).getComponent(0))).getCouleur().equals(couleur))
			{
				monPlateau.getCase(i+2, j-2).setSelectionnee(true);
			}
			
		}
	}

	public void deplacer(Case case1)
	{
		case1.add(caseActive.getComponent(0));
		for(int k=0; k<TAILLE*TAILLE; k++)
		{
			monPlateau.getCase(k).setSelectionnee(false);
		}
		if(Math.abs(getLigne(case1)-getLigne(caseActive))==2)
		{
			int i = (getLigne(case1)+getLigne(caseActive))/2;
			int j = (getColonne(case1)+getColonne(caseActive))/2;
			monPlateau.getCase(i, j).removeAll();
			monPlateau.getCase(i, j).validate();
			monPlateau.getCase(i, j).repaint();
		}
		tourNoir=!tourNoir;
		caseActive.removeAll();
		caseActive.repaint();
		caseActive=null;
		case1.repaint();
		if(getLigne(case1)==0)
		{
			Pion p=(Pion)(case1.getComponent(0));
			p.setMonte(false);
		}
		if(getLigne(case1)==TAILLE-1)
		{
			Pion p=(Pion)(case1.getComponent(0));
			p.setMonte(true);
		}
	}

	private int getLigne(Case case1)
	{
		int res=0;
		for(int i=0; i<TAILLE*TAILLE; i+=2)
		{
			if(monPlateau.getCase(i).equals(case1))
			{
				res=i/TAILLE;
			}
		}
		return res;
	}

	private int getColonne(Case case1)
	{
		int res=0;
		for(int i=0; i<TAILLE*TAILLE; i+=2)
		{
			if(monPlateau.getCase(i).equals(case1))
			{
				res=i%TAILLE;
			}
		}
		return res;
	}
}
