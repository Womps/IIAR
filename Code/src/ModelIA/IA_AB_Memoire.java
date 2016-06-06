package ModelIA;

import java.util.ArrayList;

import Memoire.PointRetour;
import Model.Plateaux;

public class IA_AB_Memoire 
{
	/**
     * Controleur de la classe IA_Alpha_Beta qui permet d'initialiser les instances de la profondeur de l'arbre et du plateau de jeu.
     *
     * @param profondeurBis integer
     * @param plateaubis tableau a double entré integer
     */
	public IA_AB_Memoire(int[] numPlayer, int prof)
	{
		this.player = numPlayer;
		this.profondeur = prof;
	}
	
	private int profondeur = 6;
	private int[] player; 
    private int nbNoeud = 0;
	private int nbEval = 0;
	private int nbMemoire = 0;
    
    /**
     *
     * @param joueur integer Le numero du joueur adverse
     * @return un entier qui correspond a la colonne choisie de l'IA
     */
    public synchronized Object jouerAB(Object plateau)
    {
        int alpha =  Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        IA_Evalution.getInstance().setPlateau(plateau);
        IA_Evalution.getInstance().removeCoupJouer();
		this.nbNoeud = 0;
		this.nbEval = 0;
		this.nbMemoire = 0;
			
		boolean continuer = true; 
				
		ArrayList<int[]> pion = IA_Evalution.getInstance().get_coupPossible(this.player[2]);
		for(int i = 0 ; i < pion.size() && continuer ; i++)
		{
			if(IA_Evalution.getInstance().jouerCoup(this.player[2], pion.get(i)))
	    	{
				int evaluation;
				if(PointRetour.getInstance().searchCoupPerdant(this.player[2], ((Plateaux)IA_Evalution.getInstance().getPlateau()).getGamePlateau()))
				{
					this.nbMemoire++;
					evaluation = Integer.MIN_VALUE;
				}
				evaluation = this.minAB(this.profondeur-1, this.player[2],alpha,beta);

				if(evaluation > alpha)
				{
					alpha = evaluation;	
					IA_Evalution.getInstance().saveCoupGagnant();
				}
							
				if(alpha>=beta)
				{
					continuer = false;
				}
				IA_Evalution.getInstance().annulerCoup();
	    	}
		}
		((Plateaux)plateau).setGamePlateau(IA_Evalution.getInstance().getCoupGagnant());
	     affichePlateau(plateau);
	     System.out.println("Memoire :"+this.nbMemoire);
	     return plateau;
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
    private int minAB(int pro, int joueur,int alpha,int beta)
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
		
		if(PointRetour.getInstance().searchCoupPerdant(jou, ((Plateaux)IA_Evalution.getInstance().getPlateau()).getGamePlateau()))
		{
			this.nbMemoire++;
			return Integer.MIN_VALUE;
		}
    	
		if(pro == 0 || IA_Evalution.getInstance().is_terminer() != -1)
	     {
	    	 this.nbEval += 1;
	          return IA_Evalution.getInstance().evaluation(1, this.player);
	     }
		
		ArrayList<int[]> pion = IA_Evalution.getInstance().get_coupPossible(jou);	

		for(int i = 0 ; i < pion.size() ; i++)
		{
			if(IA_Evalution.getInstance().jouerCoup(jou, pion.get(i)))
	    	{
                int evaluation = this.maxAB(pro-1, jou, alpha, beta);
                IA_Evalution.getInstance().annulerCoup();
                
                if(evaluation<beta)
                {
                    beta = evaluation;
                }
                if(evaluation<=alpha)
                {
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
    private int maxAB(int pro,int joueur,int alpha,int beta)
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
		
		if(PointRetour.getInstance().searchCoupPerdant(jou, ((Plateaux)IA_Evalution.getInstance().getPlateau()).getGamePlateau()))
		{
			this.nbMemoire++;
			return Integer.MAX_VALUE;
		}
    	
		if(pro == 0 || IA_Evalution.getInstance().is_terminer() != -1)
	     {
	    	 this.nbEval += 1;
	          return IA_Evalution.getInstance().evaluation(1, this.player);
	     }
		ArrayList<int[]> pion = IA_Evalution.getInstance().get_coupPossible(jou);

		for(int i = 0 ; i < pion.size() ; i++)
		{
            if(IA_Evalution.getInstance().jouerCoup(jou, pion.get(i)))
            {
                int evaluation = this.minAB(pro-1, jou, alpha, beta);
                IA_Evalution.getInstance().annulerCoup();
                
                if(evaluation>alpha)
                {
                    alpha = evaluation;
                }
                if(evaluation>=beta)
                {
                    return beta;
                }
            }
		}
		return alpha;
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
