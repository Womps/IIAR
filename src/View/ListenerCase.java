package View;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

<<<<<<< HEAD
public class ListenerCase implements MouseListener
{
	private Case case1;
	private Plateau plateau;
	
	public ListenerCase(Case case1, Plateau plateau)
	{
=======

public class ListenerCase implements MouseListener{
	
	private Case case1;
	private Plateau plateau;

	
	public ListenerCase(Case case1, Plateau plateau) {
>>>>>>> 96c490c7fcbba74e99762518ccc644055cc0baa0
		super();
		this.case1 = case1;
		this.plateau = plateau;
	}

<<<<<<< HEAD
	public void mouseClicked(MouseEvent arg0)
	{
		if(this.case1.isSelectionnee())
		{
			this.plateau.addCase(this.case1);
		}
	}

	public void mouseEntered(MouseEvent arg0)
	{
		
	}

	public void mouseExited(MouseEvent arg0)
	{
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		if(this.case1.isSelectionnee())
		{
			this.plateau.deplacer(this.case1);
		}
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
		if(case1.isSelectionnee()){
			plateau.deplacer(case1);
		}
	}


	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	

}
>>>>>>> 96c490c7fcbba74e99762518ccc644055cc0baa0
