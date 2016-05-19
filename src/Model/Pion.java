package Model;

public class Pion 
{
	private int couleurPion; // 0 pour noir, 1 pour blanc et -1 pour null
	private boolean deplaceTop; // true vers le haut et false vers le bas
	
	public Pion(int couleurP, boolean deplace)
	{
		this.couleurPion = couleurP;
		this.deplaceTop = deplace;
	}
	
	public int getCouleur()
	{
		return this.couleurPion;
	}
	
	public boolean getDeplaceTop()
	{
		return this.deplaceTop;
	}
	
	public void setDeplaceTop(boolean deplace)
	{
		this.deplaceTop = deplace;
	}
}
