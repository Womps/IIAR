package View;
import java.awt.GridLayout;

import javax.swing.JPanel;

import Controller.PlateauController;


public class Plateau extends JPanel 
{
	private static final long serialVersionUID = 6726708245444190460L;

	private static final int TAILLE=10;

	public Plateau()
	{
		setLayout(new GridLayout(TAILLE, TAILLE));
		for(int i=0; i<TAILLE; i++){
			for(int j=0; j<TAILLE; j++)
			{
				if((j%2==0 && i%2==0) || (j%2!=0 && i%2!=0))
				{
					ajouterCase(Couleur.NOIR);
				}
				else
				{
					ajouterCase(Couleur.BLANC);
				}
			}
		}
		init();
	}

	private void ajouterCase(Couleur couleur)
	{
		Case case1 = new Case(couleur);
		case1.addMouseListener(new ListenerCase(case1, this));
		add(case1);
	}

	private Pion creerPion(Couleur couleur)
	{
		Pion pion = new Pion(couleur);
		pion.addMouseListener(new ListenerPion(pion, this));
		return pion;
	}

	public void init()
	{
		for(int x = 0; x < PlateauController.getInstance().getGame().getX(); x++)
		{
			for(int y = 0; y < PlateauController.getInstance().getGame().getY(); y++)
			{
				if(PlateauController.getInstance().getGame().getGamePlateau()[x][y] != PlateauController.getInstance().getGame().getEmpty())
				{
					if(PlateauController.getInstance().getGame().getGamePlateau()[x][y].getCouleur() == 1)
					{
						getCase(x, y).add(creerPion(Couleur.BLANC));
					}
					else
					{
						getCase(x, y).add(creerPion(Couleur.NOIR));
					}
					getCase(x, y).validate();
					getCase(x, y).repaint();
				}
			}
		}
	}

	public Case getCase(int i, int j)
	{
		return (Case)getComponent(j+i*TAILLE);
	}

	public Case getCase(int i)
	{
		return (Case)getComponent(i);
	}
	
	
	

	public void afficherPossibilites(Pion p)
	{
		if((p.getCouleur().equals(Couleur.NOIR) && PlateauController.getInstance().getTourNoir()) || (p.getCouleur().equals(Couleur.BLANC) && !PlateauController.getInstance().getTourNoir()))
		{
			int i=0;
			int j=0;
			for(int k=0; k<TAILLE*TAILLE; k++)
			{
				getCase(k).setSelectionnee(false);
				if(getCase(k).getComponentCount()!=0 && getCase(k).getComponent(0).equals(p))
				{
					i=k/TAILLE;
					j=k%TAILLE;
				}
			}
			
				PlateauController.getInstance().setPionActif(i, j);
				getCase(i, j).setSelectionnee(true);
		}
	}
	
	public void clear()
	{
		for(int k=0; k<TAILLE*TAILLE; k++)
		{
			getCase(k).setSelectionnee(false);
			if(getCase(k).getComponentCount()!=0 )
			{
				getCase(k).removeAll();
				getCase(k).validate();
				getCase(k).repaint();
			}
		}
	}
	
	public void getCaseSelect(Case c)
	{
		boolean continuer = false;
			int i=0;
			int j=0;
			for(int k=0; k<TAILLE*TAILLE; k++)
			{
				if(getCase(k)==c)
				{
					i=k/TAILLE;
					j=k%TAILLE;
				}
			}
			if(PlateauController.getInstance().possibleSaute())
			{
				if(PlateauController.getInstance().isSaute(i, j))
				{
					continuer = PlateauController.getInstance().validSelection(true, i, j);
				}
			}
			else if(PlateauController.getInstance().isPossible(i, j))
			{
				continuer=PlateauController.getInstance().validSelection(false, i, j);
			}
			
			this.clear();
			this.init();
			
			if(continuer)
			{
				PlateauController.getInstance().setPionActif(i, j);
				getCase(i, j).setSelectionnee(true);
			}
				
	}

	public void affichePlateau()
	{
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		for(int x = 0; x < PlateauController.getInstance().getGame().getX(); x++)
		{
			for(int y = 0; y < PlateauController.getInstance().getGame().getY(); y++)
			{
				System.out.print(PlateauController.getInstance().getGame().getGamePlateau()[x][y].getCouleur()+" ");
			}
			System.out.println();
		}
	}

	

	@SuppressWarnings("unused")
	private int getLigne(Case case1)
	{
		int res=0;
		for(int i=0; i<TAILLE*TAILLE; i+=2)
		{
			if(getCase(i).equals(case1))
			{
				res=i/TAILLE;
			}
		}
		return res;
	}

	@SuppressWarnings("unused")
	private int getColonne(Case case1)
	{
		int res=0;
		for(int i=0; i<TAILLE*TAILLE; i+=2)
		{
			if(getCase(i).equals(case1))
			{
				res=i%TAILLE;
			}
		}
		return res;
	}
}