package View;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListenerPion implements MouseListener
{
	private Plateau plateau;
	private Pion pion;
	
	public ListenerPion(Pion pion, Plateau plateau)
	{
		this.plateau=plateau;
		this.pion=pion;
	}

	public void mouseClicked(MouseEvent arg0)
	{
		
	}

	public void mouseEntered(MouseEvent arg0)
	{
		
	}

	public void mouseExited(MouseEvent arg0)
	{
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		this.plateau.afficherPossibilites(this.pion);
	}

	public void mouseReleased(MouseEvent arg0)
	{
		
	}
}
