package View;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

<<<<<<< HEAD
public class ListenerPion implements MouseListener
{
	private Plateau plateau;
	private Pion pion;
	
	public ListenerPion(Pion pion, Plateau plateau)
	{
=======

public class ListenerPion implements MouseListener {
	
	private Plateau plateau;
	private Pion pion;
	
	public ListenerPion(Pion pion, Plateau plateau){
>>>>>>> 96c490c7fcbba74e99762518ccc644055cc0baa0
		this.plateau=plateau;
		this.pion=pion;
	}

<<<<<<< HEAD
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
=======
	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
	
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		plateau.afficherPossibilites(pion);
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

}
>>>>>>> 96c490c7fcbba74e99762518ccc644055cc0baa0
