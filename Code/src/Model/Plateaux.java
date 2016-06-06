package Model;
        
public class Plateaux implements Cloneable
{
	private Pion [][] gamePLateau;
	private int x;
	private int y;
	private Pion EMPTY;
	
	public Plateaux()
	{
		this.x = 10;
		this.y = 10;
		this.EMPTY = new Pion(-1, true);
		this.gamePLateau = new Pion[this.x][this.y];
		
		this.initPlateau();
	}
	
	public Object clone()
	{
		Plateaux monClone = null;
		try 
		{
			monClone = (Plateaux) super.clone();
	    } 
		catch(CloneNotSupportedException cnse) 
		{
	      	cnse.printStackTrace(System.err);
	    }
		
		monClone.gamePLateau = (Pion[][])this.gamePLateau.clone();
	    return monClone;
	}
	
	public void initPlateau()
	{
		for(int i = 0; i < this.x; i++)
		{
			for(int j = 0; j < this.y; j++)
			{
				switch (i)
				{
					case 0: if(j%2 != 1){this.gamePLateau[i][j] = new Pion(0, false);}break;
					case 1: if(j%2 != 0){this.gamePLateau[i][j] = new Pion(0, false);}break;
					case 2: if(j%2 != 1){this.gamePLateau[i][j] = new Pion(0, false);}break;
					case 3: if(j%2 != 0){this.gamePLateau[i][j] = new Pion(0, false);}break;
					
					case 9: if(j%2 == 1){this.gamePLateau[i][j] = new Pion(1, true);}break;
					case 8: if(j%2 == 0){this.gamePLateau[i][j] = new Pion(1, true);}break;
					case 7: if(j%2 == 1){this.gamePLateau[i][j] = new Pion(1, true);}break;
					case 6: if(j%2 == 0){this.gamePLateau[i][j] = new Pion(1, true);}break;				
				}
				if(this.gamePLateau[i][j] == null)
					this.gamePLateau[i][j] = this.EMPTY;
			}
		}	
	}
	
	public void deplacePion(int xO, int yO, int xT, int yT)
	{
		this.gamePLateau[xT][yT] = this.gamePLateau[xO][yO];
		this.gamePLateau[xO][yO] = this.EMPTY;
		if(this.gamePLateau[xT][yT].getDeplaceTop() && xT == 0)
			this.gamePLateau[xT][yT].setDeplaceTop(false);
		else if(!this.gamePLateau[xT][yT].getDeplaceTop() && xT == 9)
			this.gamePLateau[xT][yT].setDeplaceTop(true);
	}
	
	public void delPion(int xO, int yO)
	{
		this.gamePLateau[xO][yO] = this.EMPTY;
	}

	public boolean isDeuxCouleur()
	{
		int couleur1 = this.EMPTY.getCouleur();
		for(int i = 0; i < this.x; i++)
		{
			for(int j = 0; j < this.y; j++)
			{
				if(this.gamePLateau[i][j] != this.EMPTY)
				{
					if(couleur1 != 0 && couleur1 != this.gamePLateau[i][j].getCouleur())
					{
						return false;
					}
					else
					{
						couleur1 = this.gamePLateau[i][j].getCouleur();
					}
				}
			}
		}
		return true;
	}
	
	public Pion[][] getGamePlateau()
	{
		return this.gamePLateau;
	}
	
	public void setGamePlateau(Pion[][] newPlateau)
	{
		this.gamePLateau = newPlateau;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public Pion getEmpty()
	{
		return this.EMPTY;
	}
	
	public Pion getValue(int x, int y)
	{
		return this.gamePLateau[x][y];
	}
}