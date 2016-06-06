package Memoire;

public class NoeudPointNonRetour 
{
	private int couleurPerdante;
	private Noeud noeudFilsNonRetour;
	
	public NoeudPointNonRetour(int couleurPerdante, Noeud noeudFilsNonRetour)
	{
		this.setCouleurPerdante(couleurPerdante);
		this.setNoeudFilsNonRetour(noeudFilsNonRetour);
	}
	
	public boolean isMatch(NoeudPointNonRetour pointNonRetour)
	{
		if(pointNonRetour.getCouleurPerdante() == this.couleurPerdante && pointNonRetour.getNoeudFilsNonRetour().getID_TableHash() == this.noeudFilsNonRetour.getID_TableHash())
		{
			return true;
		}
		return false;
	}

	public Noeud getNoeudFilsNonRetour() {
		return noeudFilsNonRetour;
	}

	public void setNoeudFilsNonRetour(Noeud noeudFilsNonRetour) {
		this.noeudFilsNonRetour = noeudFilsNonRetour;
	}

	public int getCouleurPerdante() {
		return couleurPerdante;
	}

	public void setCouleurPerdante(int couleurPerdante) {
		this.couleurPerdante = couleurPerdante;
	}
}
