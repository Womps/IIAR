package ModelIA;

public class IA_AlphaBeta 
{
	private int profondeur;
    private int[][] plateaux = new int[6][7];
    private int compteur;
    
    /**
     * Controleur de la classe IA_Alpha_Beta qui permet d'initialiser les instances de la profondeur de l'arbre et du plateau de jeu.
     *
     * @param profondeurBis integer
     * @param plateaubis tableau a double entré integer
     */
    public IA_AlphaBeta(int profondeurBis, int[][] plateaubis)
    {
        this.profondeur = profondeurBis;
        this.plateaux = plateaubis;
        this.compteur = 0;
    }
    
    /**
     *
     * @param joueur integer Le numero du joueur adverse
     * @return un entier qui correspond a la colonne choisie de l'IA
     */
    public synchronized int jouerAB(int joueur){
        int alpha =  -10000000;
        int beta = 10000000;
			
	int colonne = -1;
	int ligne = -1;
	boolean continuer = true; 
			
	for(int i = 0 ; i < 7 && continuer ; i++){
            if(placeDispo(i)){
                int j = jouerCoup(i, joueur);
					
		int evaluation = this.minAB(this.profondeur-1, joueur,alpha,beta);
		if(evaluation > alpha){
                    alpha = evaluation;	
                    colonne = i;
                    ligne = j;
		}
					
		annulerCoup(i, j);
					
		if(alpha>=beta){
                    continuer = false;
		}		
            }
	}
        return colonne;
    }
	
    /**
     * Methode minAB permettant de minimiser les coups du joueur.
     *
     * @param pro integer La profonteur de l'arbre
     * @param joueur integer Le numero du joueur adverse
     * @param alpha integer 
     * @param beta integer 
     * @return un entier qui correspond a la colonne choisie de l'IA.
     */
    private int minAB(int pro, int joueur,int alpha,int beta){
        if(pro == 0 || cherche4() != -1 || terminer()){
            return eval2(pro, joueur);
	}
			
	if(joueur==1){
            joueur = 2;
	}
	else{
            joueur = 1;
	}
		
	for(int i = 0 ; i < 7 ; i++){
            if(placeDispo(i)){
                int j = jouerCoup(i, joueur);
                int evaluation = this.maxAB(pro-1, joueur, alpha, beta);

                if(evaluation<beta){
                    beta = evaluation;
                }

                annulerCoup(i, j);

                if(beta<=alpha){
                    return alpha;
                }
            }
        }
	return beta;
    }
	
    /**
     * Methode maxAB permettant de maximiser les coups de l'IA
     *
     * @param pro integer La profondeur de l'arbre
     * @param joueur integer Le numero du joueur adverse
     * @param alpha integer 
     * @param beta integer 
     * @return un entier qui correspond a la colonne choisie de l'IA
     */
    private int maxAB(int pro,int joueur,int alpha,int beta){
	if(pro == 0 || cherche4() != -1 || terminer()){
            return eval2(pro, joueur);
	}
		
	if(joueur==1){
            joueur = 2;
	}
	else{
            joueur = 1;
	}
		
	for(int i = 0 ; i < 7 ; i++){
            if(placeDispo(i)){
                int j = jouerCoup(i,joueur);

                int evaluation = this.minAB(pro-1, joueur, alpha, beta);
                if(evaluation>alpha){
                    alpha = evaluation;
                }

                annulerCoup(i, j);

                if(alpha>=beta){
                    return beta;
                }
            }
	}
	return alpha;
    }
	
    /**
     * Methode eval2 permettant d'évaluer le gaganant entre le joueur et l'IA
     * S'il n'y a pas de gagant il y aura un calcul de fait pour retourner un nombre bien précis pour déterminer précisément le meilleur coup a jouer.
     *
     * @param pro integer La profonteur de l'arbre
     * @param joueur integer Le numero du joueur adverse
     * 
     * @return un entier qui correspond a la colonne choisie de l'IA.
     */
    private int eval2(int pro, int joueur){
        this.compteur++;
        int vainqueur = this.cherche4();// on regarde si il y a des gagnants

        if(vainqueur == 1){//si l'IA a gagnée on renvoi une valeur positive trés grande pour que cette valeur soit prise en conpte en priorité
            return 100000 - (this.profondeur- pro);
	}
	else if (vainqueur == -1){//sinon si il n'y a pas de gagnant on fait une autre évaluation
            int adversaire;
			
            if(joueur == 1){
                adversaire = 0;
            }
            else{
                adversaire = 1;
            }
			
            int eval = (cherche(joueur,3)*100 - cherche(adversaire,3)*100 + cherche(joueur,2)*50 - cherche(adversaire,2)*50);
            /*
            * on cherche une victoire pour le joueur et son adversaire dont la methode a était appellé avec 3 pion alignés et où il y a un emplacement libre a la suite,  
            * (exemble 3 pion alignés avec rien d'un côté)
            * ensuite on les soustraits pour savoir qui a gagné dans cette situation la.
            * si le résultat est positif alors l'IA gagne sinon c'est l'autre.
            * 
            * ensuite on fait la même chose mais avec la profondeur 2
            * et on additionne le tout.
            * 
            * le résultat de ce calcul déterminera si l'IA gagne ou pas
            */
            return eval;

	}
        else{// sinon c que le joueur a gagner dans cette possition tests, donc on renvoi une valeur trés bas pour quel soi pri en compte.
            return -100000 + (this.profondeur-pro);
	}
    }

    /**
     * Methode cherche permettant de chercher toutes les victoires possible avec le joueur donné en question avec une profondeur donnée
     *
     * @param joueur integer Le numero du joueur adverse
     * @param nombre integer Le nombre de pion alignés de la même couleur.
     * 
     * @return un entier qui correspond au nombre de gagnant pour le joueur donné.
     */
    private int cherche(int joueur,int nombre){
		int compteur = 0;
		
		//horizontales
		for (int ligne = 0; ligne < 6; ligne++){
			compteur += chercheAlignes(0, ligne, 1, 0,joueur,nombre);
		}
		
		//Diagonales
		for (int col = 0; col < 7 ;col++){
			compteur += chercheAlignes(col, 0, 0, 1,joueur,nombre);
		}
		
		// Diagonales (cherche depuis la ligne du bas)
		for (int col = 0; col < 7; col++){
			// Premiere diagonale ( / )
			compteur += chercheAlignes(col, 0, 1, 1,joueur,nombre);
			// Deuxieme diagonale ( \ )
			compteur += chercheAlignes(col, 0, -1, 1,joueur,nombre);
		}

		// Diagonales (cherche depuis les colonnes gauche et droite)
		for (int ligne = 0; ligne < 6; ligne++){
			// Premiere diagonale ( / )
			compteur += chercheAlignes(0, ligne, 1, 1,joueur,nombre);


			// Deuxieme diagonale ( \ )
			compteur += chercheAlignes(7 - 1, ligne, -1, 1,joueur,nombre);

		}
		return compteur;
	}
	
    /**
    * 
    * Methode cherchealignes qui recherche n pions alignés selon les paramètres donnés.
    * 
    * @param oCol integer Numero de la colonne de depart
    * @param oLigne integer Numero de la ligne de depart
    * @param dCol integer Incrémentation colonne
    * @param dLigne integer Incrémentation ligne
    * @param nombre integer de pion a aligner
    * 
    * @return integer
    *           Le numero du pion qui a gagner ou 0 s'il n'y a pas de gagnant.
    */
    private int chercheAlignes(int oCol, int oLigne, int dCol, int dLigne, int joueur, int nombre){
		int compteurJeton = 0;
		int compteurAlignes = 0;
		
		int curCol = oCol;
		int curRow = oLigne;
		int precedent=-1;

		while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)){
			if (plateaux[curRow][curCol] != joueur) 
			{
				if ((compteurJeton == nombre) && (precedent == 0 || plateaux[curRow][curCol] == 0)){
					compteurAlignes++;
				}
				
				// Si la couleur change, on reinitialise le compteur
				compteurJeton = 0;
				precedent = plateaux[curRow][curCol];
			} 
			else{
				// Sinon on l'incremente
				compteurJeton++;
			}

			// On passe a l'iteration suivante
			curCol += dCol;
			curRow += dLigne;
		}

		// on retourne le nombre d'alignement trouver
		return compteurAlignes;
	}
	
    /**
    * 
    * Methode cherche4 qui permet de trouver s'il y a une personne qui a aligné 4 pions de la même couleur,
    * que ce soit a la vertical, a l'horizontal ou en diagonal.
    * 
    * @see cherche4alignes(int, int, int, int)
    * 
    * @return integer
    *           Le numero du pion qui a gagné ou 0 s'il n'y a pas de gagnant.
    */
    private int cherche4(){
		int vainqueur = (-1);
		
		// Verifie les horizontales ( - )
		
		for (int ligne = 0; ligne < 6; ligne++){
			vainqueur = cherche4alignes(0, ligne, 1, 0);
			
			if (vainqueur!=-1){
				return vainqueur ;
			}
		}

		// Verifie les verticales ( | )
		for (int col = 0; col < 7 ;col++){
			vainqueur = cherche4alignes(col, 0, 0, 1);
			
			if (vainqueur!=-1){
				return vainqueur ;
			}
		}

		// Diagonales (cherche depuis la ligne du bas)
		for (int col = 0; col < 7; col++){
			// Premiere diagonale ( / )
			vainqueur = cherche4alignes(col, 0, 1, 1);
			if (vainqueur!=-1){
				return vainqueur ;
			}
			
			vainqueur = cherche4alignes(col, 0, -1, 1);
			// Deuxieme diagonale ( \ )
			if (vainqueur!=-1){
				return vainqueur ;
			}
		}

		// Diagonales (cherche depuis les colonnes gauche et droite)
		for (int ligne = 0; ligne < 6; ligne++){
			// Premiere diagonale ( / )
			vainqueur = cherche4alignes(0, ligne, 1, 1);

			if (vainqueur!=-1){
				return vainqueur ;
			}
			
			// Deuxieme diagonale ( \ )
			vainqueur = cherche4alignes(7 - 1, ligne, -1, 1);
			if (vainqueur!=-1){
				return vainqueur ;
			}
		}

		// On n'a rien trouve
		return -1;
	}

    /**
    * 
    * Methode cherche4alignes qui recherche 4 pions alignés selon les paramètres donnés.
    * 
    * @param oCol integer Numero de la colonne de depart
    * @param oLigne integer Numero de la ligne de depart
    * @param dCol integer Incrémentation colonne
    * @param dLigne integer Incrémentation ligne
    * 
    * @return integer
    *           Le numero du pion qui a gagné ou 0 s'il n'y a pas de gagnant.
    */
    private int cherche4alignes(int oCol, int oLigne, int dCol, int dLigne){
		int couleur = 0;
		int compteur = 0;

		int curCol = oCol;
		int curRow = oLigne;

		while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)){
			if (plateaux[curRow][curCol] != couleur){
				// Si la couleur change, on reinitialise le compteur
				couleur = plateaux[curRow][curCol];
				compteur = 1;
			} 
			else{
				// Sinon on l'incremente
				compteur++;
			}

			// On sort lorsque le compteur atteint 4
			if ((couleur != 0) && (compteur == 4)){
				return couleur;
			}

			// On passe a l'iteration suivante
			curCol += dCol;
			curRow += dLigne;
		}

		// Aucun alignement n'a été trouve
		return -1;
	}
	
    /**
    * 
    * Retourne true si le plateau est plein sinon retourne false.
    * 
    * @return boolean
    * 
    */
    private boolean terminer(){
	for(int i = 0 ; i < 6 ; i++){
            for(int j = 0 ; j < 7 ; j++){
                    if(plateaux[i][j] == 0)
                        return false;
            }
        }
		
        return true;
    }
	
    /**
    * 
    * Retourne -i si la colonne et pleine sinon retourne le numero de la ligne où a été placé le pion.
    * 
    * @param colonne integer Numero de la colonne choisie
    * @param joueur integer Numero du joueur.
    * @return integer
    * 
    */
    private int jouerCoup(int colonne,int joueur){
		for(int ligne = 5 ; ligne >= 0 ; ligne--){
			if(plateaux[ligne][colonne] == 0){
				plateaux[ligne][colonne] = joueur;
				return ligne;
			}
		}
		
		return -1;
	}
	
    /**
    * 
    * Retourne true si il y a de la place dans la colonne sinon retourne false.
    * 
    * @param colonne integer Numero de la colonne choisie
    * 
    * @return boolean
    * 
    */
    private boolean placeDispo(int colonne){
		boolean dispo = false;
		
		for(int ligne = 0 ; ligne < 6 ; ligne++){
			if(plateaux[ligne][colonne] == 0){
				dispo = true;
			}
		}
		return dispo;
	}
	
    /**
    * 
    * Permet de mettre une case du plateau a zero.
    * 
    * @param colonne integer Numero de la colonne choisie
    * @param ligne integer Numero de la ligne choisie
    * 
    */
    private void annulerCoup(int colonne, int ligne){
		plateaux[ligne][colonne] = 0;
	}
    
    public int getCompteur(){
        return this.compteur;
    }
}
