package ModelIA;

import java.util.ArrayList;
import java.util.Random;

import Model.Plateaux;

public class IA_MinMax 
{
	private int profondeur = 6;
	private int[] player; 
	private int nbNoeud = 0;
	private int nbEval = 0;
	/*
	 *  player[0] => num�ro du pion joueur
	 *  player[1] => num�ro d'une case vide
	 *  player[2] => num�ro du pion IA
	 * 
	 */
	
	Random rn = new Random();
        
	/**
    * Controleur de la classe IA_Min_Max qui permet d'initialiser les instances de la profondeur de l'arbre et du plateau de jeu et des num�ro de joueur.
    *
    * @param plateau Object 
    * @param numPlayer numPlayer
    * @param prof integer
    */
	public IA_MinMax(int[] numPlayer, int prof)
	{
		this.player = numPlayer;
		this.profondeur = prof;
	}
	
	
	/*
	 * methode max cette methode va choisir le meilleur coup que l'IA pourrait sensiblement jouer pour gagner.
	*/
	private int Max(int pro, int joueur)
	{
		this.nbNoeud += 1;
		int jou ;
		
		if(joueur == this.player[0])
		{
			jou = this.player[2];
		}
		else
		{
			jou = this.player[0];
		}
		
	     if(pro == 0 || IA_Evalution.getInstance().is_terminer() != -1)
	     {
	    	 this.nbEval += 1;
	          return IA_Evalution.getInstance().evaluation2(1, this.player);
	     }
	 
	     int max = Integer.MIN_VALUE;
	     int tmp;
	     
	     ArrayList<int[]> pion = IA_Evalution.getInstance().get_coupPossible(jou);
	 
	     for(int i = 0 ; i < pion.size() ; i++)
	     {
	    	 if(IA_Evalution.getInstance().jouerCoup(jou, pion.get(i)))
	    	 {
				  tmp = Min(pro-1, jou);// on rappel min pour faire un appel récursif de fonction jusqu'à ce que la condition du dessus fonctionne.
				                       
				  if(tmp > max )
				  {
				       max = tmp;
				  }
				  IA_Evalution.getInstance().annulerCoup();
		    }
	     }
	 
	     return max;// on retourne une valeur dans laquelle l'IA aura le plus de chance de gagner  
	}

	/*
	 * methode min, cette methode va choisir le meilleur coup que l'adversaire de l'IA pourrait sensiblement jouer pour gagner
	*/
	private int Min(int pro, int joueur)
	{
		this.nbNoeud += 1;
		int jou ;
		
		if(joueur == this.player[0])
		{
			jou = this.player[2];
		}
		else
		{
			jou = this.player[0];
		} 
		
		if(pro == 0 || IA_Evalution.getInstance().is_terminer() != -1)
			// si on est la plus basse profondeur ou si il y a un gagnant dans les tests et si il y a plus de place sur le plateau le jeu s'arrete et on évalue la situation.
		{
			this.nbEval += 1;
			return IA_Evalution.getInstance().evaluation2(1, this.player);
		}
		 
		     int min = Integer.MAX_VALUE; // on charge une petite valeur pour la premiere comparaison. 
		     int tmp = 0;
		     
		     ArrayList<int[]> pion = IA_Evalution.getInstance().get_coupPossible(jou);
			 
		     for(int i = 0 ; i < pion.size() ; i++)
		     {
		    	 if(IA_Evalution.getInstance().jouerCoup(jou, pion.get(i)))
		    	 {
		    		 tmp = Max(pro-1, jou);// on appelle max pour test toutes les possibilitées que l'IA peut jouer
					                      
					  if(tmp < min)// on prend la plus petite valeur trouvée dans tout les tests et on la stock en mémoire
			         {
			        	 min = tmp;
			         }
					  IA_Evalution.getInstance().annulerCoup();
			    }
		     }
		     
		     return min;// on retourne une valeur dans laquelle le joueur aura le plus de chance de gagner
	}
	
	/*
	 * cette methode est celle que l'on appelle pour commencer le min-max,
	 * elle prend un joueur en paramètre car si le joueur du début a était changé
	 * l'algorithme doit être aussi changé.
	 */
	public synchronized Object IA_jouer(Object plateau) 
	{
	     int max = Integer.MIN_VALUE;// on charge un nombre petit pour la premiere comparaison(pour avoir la valeur maximum de tout les tests éffectués)
	     int tmp = 0;
	     IA_Evalution.getInstance().setPlateau(plateau);
	     this.nbNoeud = 0;
	     this.nbEval = 0;
	     
	     ArrayList<int[]> pion = IA_Evalution.getInstance().get_coupPossible(this.player[2]);
	     for(int i = 0 ; i < pion.size() ; i++)
	     {
	    	 if(IA_Evalution.getInstance().jouerCoup(this.player[2], pion.get(i)))
	    	 {
	    		 tmp = Min(this.profondeur-1, this.player[2]);// on appelle max pour test toutes les possibilitées que l'IA peut jouer
				                      
	    		 if(tmp > max || ( (tmp == max) && (rn.nextInt(2) == 0) ))// on prend la valeur maximale de toutes les possibilitées testées et on la garde en memoire
	             {
	                   max = tmp;
	                   IA_Evalution.getInstance().saveCoupGagnant();
	             }
	    		 IA_Evalution.getInstance().annulerCoup();
		    }
	     }
	     
	     ((Plateaux)plateau).setGamePlateau(IA_Evalution.getInstance().getCoupGagnant());
	     affichePlateau(plateau);
	     return plateau;
	}
	
	public int getNbNoeud()
	{
		return this.nbNoeud;
	}
	
	public int getNbEval()
	{
		return this.nbEval;
	}
	
	public void affichePlateau(Object pla)
	{
		System.out.println();
		System.out.println();
		for(int x = 0; x < ((Plateaux)pla).getX(); x++)
		{
			for(int y = 0; y < ((Plateaux)pla).getY(); y++)
			{
				System.out.print((((Plateaux)pla).getGamePlateau()[x][y].getCouleur()==-1 ? "-" : ((Plateaux)pla).getGamePlateau()[x][y].getCouleur())+" ");
			}
			System.out.println();
		}
	}
	
	
	
}
