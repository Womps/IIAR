package ModelIA;

import java.util.ArrayList;
import Model.*;

public class IA_Evalution 
{
	private static IA_Evalution instanceIaEval = null; 
	private Plateaux plateau;
	private ArrayList<Pion[][]> coupJouer;
	private Pion[][] coupGagnant;
	
	private ArrayList<Object[]> sautePion;
	
	private IA_Evalution()
	{
		coupJouer = new ArrayList<Pion[][]>();
		sautePion = new ArrayList<Object[]>();
	}
	
	public static IA_Evalution getInstance()
	{
		if(instanceIaEval == null)
			instanceIaEval = new IA_Evalution();
			
		return instanceIaEval;
	}
	
	public Object getPlateau()
	{
		return this.plateau;
	}
	
	public void setPlateau(Object pla)
	{
		this.plateau = (Plateaux)pla;
		this.sautePion.clear();
	}
	
	public void removeCoupJouer()
	{
		this.coupJouer.clear();
	}
	
	private int getPionsZoneGagnante(int joueur)
	{
		int nbPionsZoneGagnante = 0;
		
		for(int i = 0 ; i < this.plateau.getX() ; i++)
		 {
			 for(int j = 0 ; j < this.plateau.getY() ; j++)
		     {
					switch (i)
					{
					
						case 0: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 1 && joueur == 1){nbPionsZoneGagnante++;}break;
						case 1: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 1 && joueur == 1){nbPionsZoneGagnante++;}break;
						case 2: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 1 && joueur == 1){nbPionsZoneGagnante++;}break;
						case 3: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 1 && joueur == 1){nbPionsZoneGagnante++;}break;
						
						case 9: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 0 && joueur == 0){nbPionsZoneGagnante++;}break;
						case 8: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 0 && joueur == 0){nbPionsZoneGagnante++;}break;
						case 7: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 0 && joueur == 0){nbPionsZoneGagnante++;}break;
						case 6: if(this.plateau.getGamePlateau()[i][j].getCouleur() == 0 && joueur == 0){nbPionsZoneGagnante++;}break;				
					}
				 if(((i+1) == 3) && (this.plateau.getGamePlateau()[i][j].getCouleur() == 1) && (joueur == 1))
					 nbPionsZoneGagnante++;
				 else if ((i+1 == 6) && (this.plateau.getGamePlateau()[i][j].getCouleur() == 0) && (joueur == 0))
					 nbPionsZoneGagnante++;
		     }
		 }
		
		return nbPionsZoneGagnante;
		
		
	}
	
	
	public int evaluation(int profondeur, int[] pion)
	{
		int vainqueur = this.is_terminer();// on regarde si il y a des gagnants

		if(vainqueur == pion[2])//si l'IA a gagnée 	on renvoie une valeur positive trés grande pour que cette valeur soit prise en compte en priorité.
		{
			return Integer.MAX_VALUE - profondeur;
		}
		else if (vainqueur== pion[1])//sinon si il n'y a pas de gagnant on fait une autre évaluation
		{
			int coupPossibleJoueur = get_coupPossible(pion[0]).size();
			int coupPossibleIA = get_coupPossible(pion[2]).size();
			
			int pionsRestantsIA = getNbPions(pion[2]);
			int pionsRestantsJoueur = getNbPions(pion[0]);
			
			int nbPionsIAZoneGagnante = getPionsZoneGagnante(pion[2]);
			int nbPionsJoueurZoneGagnante = getPionsZoneGagnante(pion[0]);
			
			int eval = ((pionsRestantsIA - pionsRestantsJoueur)*30) + ((coupPossibleIA - coupPossibleJoueur)*((profondeur>0)? profondeur:1)) + ((nbPionsIAZoneGagnante - nbPionsJoueurZoneGagnante)*20);
			return eval;

		}
		else// sinon c'est que le joueur a gagné dans cette position test, donc on renvoie une valeur très basse pour qu'elle soit prise en compte.
		{
			return Integer.MIN_VALUE + profondeur;
		}
	}
	
	public int evaluation2(int profondeur, int[] pion)
	{
		int vainqueur = this.is_terminer();// on regarde si il y a des gagnants

		if(vainqueur == pion[2])//si l'IA a gagnée 	on renvoie une valeur positive trés grande pour que cette valeur soit prise en compte en priorité.
		{
			return Integer.MAX_VALUE - profondeur;
		}
		else if (vainqueur== pion[1])//sinon si il n'y a pas de gagnant on fait une autre évaluation
		{
			return 0;

		}
		else// sinon c'est que le joueur a gagné dans cette position test, donc on renvoie une valeur très basse pour qu'elle soit prise en compte.
		{
			return Integer.MIN_VALUE + profondeur;
		}
	}
	
	/*
	 * 	Methode qui renvoie true s'il y a un gagnant ou si la partie est fini sinon renvoie false
	 */
	public int is_terminer()
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
	 * Retourne une liste avec l'enplacement des pions possible a jouer
	 */
	public ArrayList<int[]> get_coupPossible(int joueur)
	{
		boolean isSaute = false;
		ArrayList<int[]> listTmp = new ArrayList<int[]>();
		for(int i = 0 ; i < this.plateau.getX() ; i++)
		 {
			 for(int j = 0 ; j < this.plateau.getY() ; j++)
		     {
				 if((this.plateau.getGamePlateau())[i][j].getCouleur() == joueur)
				 {
					this.sautePion.clear();
					if(saute(new int[]{i, j}, ((joueur == 0) ? 1 : 0), true, 0))
					{
						isSaute = true;
						listTmp.add(new int[]{i,j, 1, 1});
					}
					this.sautePion.clear();
					if(saute(new int[]{i, j}, ((joueur == 0) ? 1 : 0), false, 0))
					{
						isSaute = true;
						listTmp.add(new int[]{i,j, 1, 0});
					}
					
					if(!isSaute)
					{
						if((this.plateau.getGamePlateau())[i][j].getDeplaceTop())
						{
							if(i-1<10 && i-1>=0 && j+1<10 && j+1>=0)
								if(isPossible(i-1, j+1, i, j))
								{
									listTmp.add(new int[]{i,j, 0, 1});
								}
							
							if(i-1<10 && i-1>=0 && j-1<10 && j-1>=0)
								if(isPossible( i-1, j-1, i, j))
								{
									listTmp.add(new int[]{i,j, 0, 0});
								}
						}
						else
						{
							if(i+1<10 && i+1>=0 && j+1<10 && j+1>=0)
								if(isPossible(i+1, j+1, i, j))
								{
									listTmp.add(new int[]{i,j, 0, 1});
								}
							
							if(i+1<10 && i+1>=0 && j-1<10 && j-1>=0)
								if(isPossible( i+1, j-1, i, j))
								{
									listTmp.add(new int[]{i,j, 0, 0});
								}
						}
					}
				 }
		     }
		 }
		
		if(isSaute)
		{
			ArrayList<int[]> listTmpSaute = new ArrayList<int[]>();
			int n = 0;
			while(n < listTmp.size())
			{
				if(listTmp.get(n)[2] == 1)
					listTmpSaute.add(listTmp.get(n));
				
				n++;
			}
			return listTmpSaute;
		}
		
		return listTmp;
	}
	
	private int getNbPions(int joueur)
	{
		int nbPions = 0;
		
		for(int i = 0 ; i < this.plateau.getX() ; i++)
		 {
			 for(int j = 0 ; j < this.plateau.getY() ; j++)
		     {
				 if((this.plateau.getGamePlateau())[i][j].getCouleur() == joueur)
					 nbPions++;
		     }
		 }
		
		return nbPions;
	}
	
	public void saveCoupGagnant()
	{
		Pion[][] pionAdd = new Pion[10][10];
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(this.plateau.getGamePlateau()[x][y].getCouleur() == -1)
					pionAdd[x][y] = this.plateau.getEmpty();
				else
					pionAdd[x][y] = new Pion(this.plateau.getGamePlateau()[x][y].getCouleur(), this.plateau.getGamePlateau()[x][y].getDeplaceTop());
			}
		}
		this.coupGagnant = pionAdd;
	}
	
	public Pion[][] getCoupGagnant()
	{
		return this.coupGagnant;
	}
	
	public void annulerCoup()
	{
		this.plateau.setGamePlateau(this.coupJouer.get(this.coupJouer.size()-1));
		this.coupJouer.remove(this.coupJouer.size()-1);
	}
	
	public void saveCoup()
	{
		Pion[][] pionAdd = new Pion[10][10];
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(this.plateau.getGamePlateau()[x][y].getCouleur() == -1)
					pionAdd[x][y] = this.plateau.getEmpty();
				else
					pionAdd[x][y] = new Pion(this.plateau.getGamePlateau()[x][y].getCouleur(), this.plateau.getGamePlateau()[x][y].getDeplaceTop());
			}
		}
		coupJouer.add(pionAdd);
	}
	
	public boolean jouerCoup(int joueur, int[] cordonnePion)
	{
		if(cordonnePion[2] == 1)
		{
			this.sautePion.clear();
			if(cordonnePion[3] == 1 && saute(cordonnePion, ((joueur == 0) ? 1 : 0), true, 0))
			{
				Object tmp[] = null;
				for(int i = 0; i < this.sautePion.size(); i++)
				{
					if(tmp != null && (int)(this.sautePion.get(i)[1]) > (int)tmp[1])
						tmp = this.sautePion.get(i);
					else if(tmp == null)
						tmp = this.sautePion.get(i);
				}
				saveCoup();
				this.plateau.setGamePlateau(((Pion[][])tmp[0]));
				return true;
			}
			
			this.sautePion.clear();
			if(cordonnePion[3] == 0 && saute(cordonnePion, ((joueur == 0) ? 1 : 0), false, 0))
			{
				Object tmp[] = null;
				for(int i = 0; i < this.sautePion.size(); i++)
				{
					if(tmp != null && (int)(this.sautePion.get(i)[1]) > (int)tmp[1])
						tmp = this.sautePion.get(i);
					else if(tmp == null)
						tmp = this.sautePion.get(i);
				}
				saveCoup();
				this.plateau.setGamePlateau(((Pion[][])tmp[0]));
				return true;
			}
		}
		else if(cordonnePion[3]==1)
		{
			if((this.plateau.getGamePlateau())[cordonnePion[0]][cordonnePion[1]].getDeplaceTop())
			{
				if(cordonnePion[0]-1<10 && cordonnePion[0]-1>=0 && cordonnePion[1]+1<10 && cordonnePion[1]+1>=0)
				{
					saveCoup();
					this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]-1, cordonnePion[1]+1);
					return true;
				}
			}
			else
			{
				if(cordonnePion[0]+1<10 && cordonnePion[0]+1>=0 && cordonnePion[1]+1<10 && cordonnePion[1]+1>=0)
				{
					saveCoup();
					this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]+1, cordonnePion[1]+1);
					return true;
				}
			}
		}
		else if(cordonnePion[3]==0)
		{
			if((this.plateau.getGamePlateau())[cordonnePion[0]][cordonnePion[1]].getDeplaceTop())
			{
				if(cordonnePion[0]-1<10 && cordonnePion[0]-1>=0 && cordonnePion[1]-1<10 && cordonnePion[1]-1>=0)
				{
					saveCoup();
					this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]-1, cordonnePion[1]-1);
					return true;
				}
			}
			else
			{
				if(cordonnePion[0]+1<10 && cordonnePion[0]+1>=0 && cordonnePion[1]-1<10 && cordonnePion[1]-1>=0)
				{
					saveCoup();
					this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]+1, cordonnePion[1]-1);
					return true;
				}
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unused")
	private void affiche(Pion[][] p)
	{
		for(int i = 0 ; i< 10; i++)
		{
			for(int j = 0 ; j< 10; j++)
			{
				System.out.print((p[i][j].getCouleur() == -1)?"-":p[i][j].getCouleur());
			}
			System.out.println();
		}
	}
	
	private boolean saute(int[] cordonnePion, int joueurAdverse, boolean right, int compte)
	{
		if(right)
		{
			if(this.plateau.getGamePlateau()[cordonnePion[0]][cordonnePion[1]].getDeplaceTop())
			{
				if(cordonnePion[0]-2<10 && cordonnePion[0]-2>=0 && cordonnePion[1]+2<10 && cordonnePion[1]+2>=0)
					if(isSaute(cordonnePion[0]-2, cordonnePion[1]+2, cordonnePion[0], cordonnePion[1]))
					{
						saveCoup();
						this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]-2, cordonnePion[1]+2);
						this.plateau.delPion(cordonnePion[0]-1, cordonnePion[1]+1);
						
						boolean b1 = saute(new int[]{cordonnePion[0]-2, cordonnePion[1]+2}, joueurAdverse, true, compte +1);
						boolean b2 = saute(new int[]{cordonnePion[0]-2, cordonnePion[1]+2}, joueurAdverse, false, compte +1);
						
						annulerCoup();
						return (b1 || b2);
					}
			}
			else
			{
				if(cordonnePion[0]+2<10 && cordonnePion[0]+2>=0 && cordonnePion[1]+2<10 && cordonnePion[1]+2>=0)
					if(isSaute(cordonnePion[0]+2, cordonnePion[1]+2, cordonnePion[0], cordonnePion[1]))
					{
						saveCoup();
						this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]+2, cordonnePion[1]+2);
						this.plateau.delPion(cordonnePion[0]+1, cordonnePion[1]+1);
						
						boolean b1 = saute(new int[]{cordonnePion[0]+2, cordonnePion[1]+2}, joueurAdverse, true, compte +1);
						boolean b2 = saute(new int[]{cordonnePion[0]+2, cordonnePion[1]+2}, joueurAdverse, false, compte +1);
						
						annulerCoup();
						return (b1 || b2);
					}
			}
		}
		else if(!right)
		{
			if(this.plateau.getGamePlateau()[cordonnePion[0]][cordonnePion[1]].getDeplaceTop())
			{
				if(cordonnePion[0]-2<10 && cordonnePion[0]-2>=0 && cordonnePion[1]-2<10 && cordonnePion[1]-2>=0)
					if(isSaute(cordonnePion[0]-2, cordonnePion[1]-2, cordonnePion[0], cordonnePion[1]))
					{
						saveCoup();
						this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]-2, cordonnePion[1]-2);
						this.plateau.delPion(cordonnePion[0]-1, cordonnePion[1]-1);
						
						boolean b1 = saute(new int[]{cordonnePion[0]-2, cordonnePion[1]-2}, joueurAdverse, true, compte +1);
						boolean b2 = saute(new int[]{cordonnePion[0]-2, cordonnePion[1]-2}, joueurAdverse, false, compte +1);
						
						annulerCoup();
						return (b1 || b2);
					}
			}
			else
			{
				if(cordonnePion[0]+2<10 && cordonnePion[0]+2>=0 && cordonnePion[1]-2<10 && cordonnePion[1]-2>=0)
					if(isSaute(cordonnePion[0]+2, cordonnePion[1]-2, cordonnePion[0], cordonnePion[1]))
					{
						saveCoup();
						this.plateau.deplacePion(cordonnePion[0], cordonnePion[1], cordonnePion[0]+2, cordonnePion[1]-2);
						this.plateau.delPion(cordonnePion[0]+1, cordonnePion[1]-1);
						
						boolean b1 = saute(new int[]{cordonnePion[0]+2, cordonnePion[1]-2}, joueurAdverse, true, compte +1);
						boolean b2 = saute(new int[]{cordonnePion[0]+2, cordonnePion[1]-2}, joueurAdverse, false, compte +1);
						
						annulerCoup();
						return (b1 || b2);
					}
			}
		}
		
		if(compte > 0)
		{
			saveCoup();
			if(this.coupJouer.size() > 0)
			{
				this.sautePion.add(new Object[]{this.coupJouer.get(this.coupJouer.size()-1), compte});
			}
			annulerCoup();
		}
			
		
		if(this.sautePion.size() > 0)
			return true;
		
		return false;
	}
	
	/*private boolean possibleSaute(int joueur)
	{
		boolean saute = false;
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if((this.plateau.getGamePlateau())[x][y].getCouleur() == joueur)
					if((this.plateau.getGamePlateau())[x][y].getDeplaceTop())
					{
						if(x-2<10 && x-2>=0 && y-2<10 && y-2>=0 && !saute)
							saute = isSaute(x-2, y-2, x, y);
						if(x-2<10 && x-2>=0 && y+2<10 && y+2>=0 && !saute)
							saute = isSaute(x-2, y+2, x, y);
					}
					else
					{
						if(x+2<10 && x+2>=0 && y+2<10 && y+2>=0 && !saute)
							saute = isSaute(x+2, y+2, x, y);
						if(x+2<10 && x+2>=0 && y-2<10 && y-2>=0 && !saute)
							saute = isSaute(x+2, y-2, x, y);
					}
			}
		}
		return saute;
	}*/
	
	private boolean isSaute(int xSelect, int ySelect, int xO, int yO)
	{
		if(this.plateau.getGamePlateau()[xSelect][ySelect].getCouleur() == -1)
		{
			Pion pion = this.plateau.getGamePlateau()[xO][yO];
			if(pion.getDeplaceTop())
			{
				
				if((xO-2) == xSelect && (yO-2)==ySelect
						&& this.plateau.getGamePlateau()[(xO-1)][(yO-1)].getCouleur() != -1 
						&& this.plateau.getGamePlateau()[(xO-1)][(yO-1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
				else if((xO-2) == xSelect && (yO+2)==ySelect
						&& this.plateau.getGamePlateau()[(xO-1)][(yO+1)].getCouleur() != -1
						&& this.plateau.getGamePlateau()[(xO-1)][(yO+1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
			}
			else
			{
				if((xO+2) == xSelect && (yO+2)==ySelect
						&& this.plateau.getGamePlateau()[(xO+1)][(yO+1)].getCouleur() != -1 
						&& this.plateau.getGamePlateau()[(xO+1)][(yO+1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
				else if((xO+2) == xSelect && (yO-2)==ySelect
						&& this.plateau.getGamePlateau()[(xO+1)][(yO-1)].getCouleur() != -1
						&& this.plateau.getGamePlateau()[(xO+1)][(yO-1)].getCouleur() != pion.getCouleur())
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	private boolean isPossibleDeplacement(int couleur)
	{
		boolean deplacement = true;
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(!deplacement && this.plateau.getGamePlateau()[x][y].getCouleur() == couleur)
				{
					if(this.plateau.getGamePlateau()[x][y].getDeplaceTop())
					{
						if(x-1<10 && x-1>=0 && y-1<10 && y-1>=0)
							deplacement = isPossible(x-1, y-1, x, y);
						if(x-1<10 && x-1>=0 && y+1<10 && y+1>=0 && !deplacement)
							deplacement = isPossible(x-1, y+1, x, y);
					}
					else
					{
						if(x+1<10 && x+1>=0 && y+1<10 && y+1>=0)
							deplacement = isPossible(x+1, y+1, x, y);
						if(x+1<10 && x+1>=0 && y-1<10 && y-1>=0 && !deplacement)
							deplacement = isPossible(x+1, y-1, x, y);
					}
				}
			}
		}
		return deplacement;
	}
	
	private boolean isPossible(int xSelect, int ySelect, int xO, int yO)
	{
		if(this.plateau.getGamePlateau()[xSelect][ySelect].getCouleur() == -1)
		{
			Pion pion = this.plateau.getGamePlateau()[xO][yO];
			if(pion.getDeplaceTop())
			{
				if((xO-1) == xSelect && (yO-1)==ySelect
						&& this.plateau.getGamePlateau()[xSelect][ySelect].getCouleur() == -1)
				{
					return true;
				}
				else if((xO-1) == xSelect && (yO+1)==ySelect
						&& this.plateau.getGamePlateau()[xSelect][ySelect].getCouleur() == -1)
				{
					return true;
				}
			}
			else
			{
				if((xO+1) == xSelect && (yO+1)==ySelect
						&& this.plateau.getGamePlateau()[xSelect][ySelect].getCouleur() == -1)
				{
					return true;
				}
				else if((xO+1) == xSelect && (yO-1)==ySelect
						&& this.plateau.getGamePlateau()[xSelect][ySelect].getCouleur() == -1)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean pionExistant(int couleur)
	{
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 10; y++)
			{
				if(this.plateau.getGamePlateau()[x][y].getCouleur() == couleur)
				{
					return true;
				}
			}
		}
		return false;
	}
}