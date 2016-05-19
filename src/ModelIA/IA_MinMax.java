package ModelIA;

import java.util.Random;

public class IA_MinMax 
{
	public int profondeur = 7;
	public int[][] plateaux = new int[6][7];
	Random rn = new Random();
        
	/**
    * Controleur de la classe IA_Min_Max qui permet d'initialiser les instances de la profondeur de l'arbre et du plateau de jeu.
    *
    * @param profondeurBis integer
    * @param plateaubis tableau a double entré integer
    */
    public IA_MinMax(int profondeurBis, int[][] plateaubis)
    {
    	this.profondeur = profondeurBis;
        this.plateaux = plateaubis;
    }
	
	private int cherche(int joueur,int nombre)// cette methode cherche tout les victoires possibles avec le joueur donné en question avec une profondeur donnée.
	{
		int compteur = 0;
		
		//horizontales
		for (int ligne = 0; ligne < 6; ligne++) 
		{
			compteur += chercheAlignes(0, ligne, 1, 0,joueur,nombre);
		}
		
		//Diagonales
		for (int col = 0; col < 7 ;col++) 
		{
			compteur += chercheAlignes(col, 0, 0, 1,joueur,nombre);
		}
		
		// Diagonales (cherche depuis la ligne du bas)
		for (int col = 0; col < 7; col++) 
		{
			// Premiere diagonale ( / )
			compteur += chercheAlignes(col, 0, 1, 1,joueur,nombre);
			// Deuxieme diagonale ( \ )
			compteur += chercheAlignes(col, 0, -1, 1,joueur,nombre);
		}

		// Diagonales (cherche depuis les colonnes gauche et droite)
		for (int ligne = 0; ligne < 6; ligne++) 
		{
			// Premiere diagonale ( / )
			compteur += chercheAlignes(0, ligne, 1, 1,joueur,nombre);


			// Deuxieme diagonale ( \ )
			compteur += chercheAlignes(7 - 1, ligne, -1, 1,joueur,nombre);

		}
		return compteur;
	}
	
	private int chercheAlignes(int oCol, int oLigne, int dCol, int dLigne, int joueur, int nombre)//compte combien de pion sont alignés 
	{

		int compteurJeton = 0;
		int compteurAlignes = 0;
		
		int curCol = oCol;
		int curRow = oLigne;
		int precedent=-1;

		while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) 
		{
			if (plateaux[curRow][curCol] != joueur) 
			{
				if ((compteurJeton == nombre) && (precedent == 0 || plateaux[curRow][curCol] == 0))
				{
					compteurAlignes++;
				}
				
				// Si la couleur change, on reinitialise le compteur
				compteurJeton = 0;
				precedent = plateaux[curRow][curCol];
			} 
			else 
			{
				// Sinon on l'incremente
				compteurJeton++;
			}

			// On passe à l'iteration suivante
			curCol += dCol;
			curRow += dLigne;
		}

		// on retourne le nombre d'alignement trouver
		return compteurAlignes;
	}
	
	private int cherche4()//cette methode cherche s'il y a un gagnant possible avec 4 pions alignés 
	{
		int vainqueur =-1;
		
		// Verifie les horizontales ( - )
		
		for (int ligne = 0; ligne < 6; ligne++) 
		{
			vainqueur = cherche4alignes(0, ligne, 1, 0);
			
			if (vainqueur!=-1) 
			{
				return vainqueur ;
			}
		}

		// Verifie les verticales ( | )
		for (int col = 0; col < 7 ;col++) 
		{
			vainqueur = cherche4alignes(col, 0, 0, 1);
			
			if (vainqueur!=-1) 
			{
				return vainqueur ;
			}
		}

		// Diagonales (cherche depuis la ligne du bas)
		for (int col = 0; col < 7; col++) 
		{
			// Premiere diagonale ( / )
			vainqueur = cherche4alignes(col, 0, 1, 1);
			if (vainqueur!=-1) 
			{
				return vainqueur ;
			}
			
			vainqueur = cherche4alignes(col, 0, -1, 1);
			// Deuxieme diagonale ( \ )
			if (vainqueur!=-1) 
			{
				return vainqueur ;
			}
		}

		// Diagonales (cherche depuis les colonnes gauche et droite)
		for (int ligne = 0; ligne < 6; ligne++) 
		{
			// Premiere diagonale ( / )
			vainqueur = cherche4alignes(0, ligne, 1, 1);

			if (vainqueur!=-1) 
			{
				return vainqueur ;
			}
			
			// Deuxieme diagonale ( \ )
			vainqueur = cherche4alignes(7 - 1, ligne, -1, 1);
			if (vainqueur!=-1) 
			{
				return vainqueur ;
			}
		}

		// On n'a rien trouve
		return -1;
	}

	private int cherche4alignes(int oCol, int oLigne, int dCol, int dLigne)//recherche 4 pions alignés (pseudo fonction gagné)
	{
		int couleur = 0;
		int compteur = 0;

		int curCol = oCol;
		int curRow = oLigne;

		while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) 
		{
			if (plateaux[curRow][curCol] != couleur) 
			{
				// Si la couleur change, on reinitialise le compteur
				couleur = plateaux[curRow][curCol];
				compteur = 1;
			} 
			else 
			{
				// Sinon on l'incremente
				compteur++;
			}

			// On sort lorsque le compteur atteint 4
			if ((couleur != 0) && (compteur == 4)) 
			{
				return couleur;
			}

			// On passe a l'iteration suivante
			curCol += dCol;
			curRow += dLigne;
		}

		// Aucun alignement n'a été trouvé
		return -1;
	}
	
	private int eval2(int profondeur, int joueur) 
	{
		int vainqueur = this.cherche4();// on regarde si il y a des gagnants

		if(vainqueur == 1)//si l'IA a gagnée on renvoie une valeur positive trés grande pour que cette valeur soit prise en compte en priorité.
		{
			return 100000 - profondeur;
		}
		else if (vainqueur== -1)//sinon si il n'y a pas de gagnant on fait une autre évaluation
		{
			int adversaire;
			
			if(joueur == 1)
			{
				adversaire = 0;
			}
			else
			{
				adversaire = 1;
			}
			
			int eval = (cherche(joueur,3)*100 - cherche(adversaire,3)*100 + cherche(joueur,2)*50 - cherche(adversaire,2)*50);
			/*
			 * on cherche une victoire pour le joueur et son adversaire dont la methode a était appellée avec 3 pions alignés et ou il y a un emplacement libre à la suite,  
			 * (exemple 3 pions alignés avec rien d'un coté)
			 * ensuite on les soustrait pour savoir si quelqu'un a gagner dans cette situation la.
			 * si le résultat est positif alors l'IA gagne sinon c'est l'autre.
			 * 
			 * ensuite on fait la même chose mais avec la profondeur 2
			 * et on additionne le tout.
			 * 
			 * le résultat de ce calcul déterminera si l'IA gagne ou pas.
			 */
			
			return eval;

		}
		else// sinon c'est que le joueur a gagné dans cette position test, donc on renvoie une valeur très basse pour qu'elle soit prise en compte.
		{
			return -100000 + profondeur;
		}

	}
	
	private boolean terminer()
	{
		for(int i = 0 ; i < 6 ; i++)
		{
			for(int j = 0 ; j < 7 ; j++)
			{
				if(plateaux[i][j] == 0)
					return false;
			}
		}
		
		return true;
	}
	
	private boolean placeDispo(int colonne)
	{
		boolean dispo = false;
		
		for(int ligne = 0 ; ligne < 6 ; ligne++)
		{
			if(plateaux[ligne][colonne] == 0)
			{
				dispo = true;
			}
		}
		return dispo;
	}
	
	private int jouerCoup(int colonne,int joueur)
	{
		for(int ligne = 5 ; ligne >= 0 ; ligne--)
		{
			if(plateaux[ligne][colonne] == 0)
			{
				plateaux[ligne][colonne] = joueur;
				return ligne;
			}
		}
		
		return -1;
	}
	
	private void annulerCoup(int colonne, int ligne) 
	{
		plateaux[ligne][colonne] = 0;
	}
	
	/*
	 * methode max cette methode va choisir le meilleur coup que l'IA pourrait sensiblement jouer pour gagner.
	*/
	private int Max(int pro, int joueur)
	{
		int jou ;
		
		if(joueur == 1)
		{
			jou = 2;
		}
		else
		{
			jou = 1;
		}
		
		     if(pro == 0 || cherche4() != -1 || terminer())
		     {
		          return eval2(pro, jou);
		     }
		 
		     int max = -10000000;
		     int tmp;
		 
		     for(int i = 0 ; i < 7 ; i++)
		     {
		    	 if(placeDispo(i))
		    	 {
		    		 int j = jouerCoup(i ,jou);
				    		
					  tmp = Min(pro-1, jou);// on rappel min pour faire un appel récursif de fonction jusqu'à ce que la condition du dessus fonctionne.
					                       
					  if(tmp > max )
					  {
					       max = tmp;
					  }
					         
					  annulerCoup(i, j);
			    }
		     }
		 
		     return max;// on retourne une valeur dans laquelle l'IA aura le plus de chance de gagner  
	}

	/*
	 * methode min, cette methode va choisir le meilleur coup que l'adversaire de l'IA pourrait sensiblement jouer pour gagner
	*/
	private int Min(int pro, int joueur)
	{
		int jou;
		
		if(joueur==1)
		{
			jou = 2;
		}
		else
		{
			jou = 1;
		} 
		
		if(pro == 0 || cherche4() != -1 || terminer())
			// si on est la plus basse profondeur ou si il y a un gagnant dans les tests et si il y a plus de place sur le plateau le jeu s'arrete et on évalue la situation.
		{
			return eval2(pro, jou);
		}
		 
		     int min = 10000000; // on charge une petite valeur pour la premiere comparaison. 
		     int tmp = 0;
		 
		     for(int i = 0 ; i < 7 ; i++)
		     {
		    	 if(placeDispo(i))
		    	 {
		    		 int j = jouerCoup(i, jou);// on place du joueur dans le plateau pour ensuite le tester
					         
					        	 tmp = Max(pro-1, jou);// on appelle max pour test toutes les possibilitées que l'IA peut jouer
					        	 
					        	 if(tmp < min)// on prend la plus petite valeur trouvée dans tout les tests et on la stock en mémoire
						         {
						        	 min = tmp;
						         }
					         
					        	 annulerCoup(i, j);
				    	}
		    	 }
		     
		     return min;// on retourne une valeur dans laquelle le joueur aura le plus de chance de gagner
	}
	
	/*
	 * cette methode est celle que l'on appelle pour commencer le min-max,
	 * elle prend un joueur en paramètre car si le joueur du début a était changé
	 * l'algorithme doit être aussi changé.
	 */
	public synchronized int IA_jouer(int joueur) 
	{
	     int max = -10000000;// on charge un nombre petit pour la premiere comparaison(pour avoir la valeur maximum de tout les tests éffectués)
	     int tmp = 0 ,maxi = 0 ,maxj = 0;

	     for(int i = 0 ; i < 7 ; i++)
	     {
	    		 if(placeDispo(i))//on regarde si dans la colonne en question il y a encore de la place. S'il n'y en a pas on excecute pas et on passe à la colonne d'après
		    	 {
	    			 int j = jouerCoup(i ,joueur);// on place le pion de l'IA dans le plateau pour ensuite le tester
	 		    	 	
	 		    		 tmp = Min(profondeur-1, joueur);// on appelle la methode min pour commencer le test
	 		    	 	  
	 		        	 if(tmp > max || ( (tmp == max) && (rn.nextInt(2) == 0) ))// on prend la valeur maximale de toutes les possibilitées testées et on la garde en memoire
	 		             {
	 		                   max = tmp;
	 		                   maxj = i;
	 		                   maxi = j;
	 		             }
	 		        	annulerCoup(i, j);//on annule le coup qui vient d'être testé(on l'enlève du plateau)
	 	    	}
	    }
	     return maxj;
	     //plateaux[maxi][maxj] = joueur;// on joue la meilleure position que le min max a choisi.
	}
}
