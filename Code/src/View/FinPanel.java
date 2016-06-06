package View;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;

public class FinPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	int joueur;
	
	public FinPanel(int joueur)
	{
		super();
		this.joueur = joueur;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setFont(new Font("SansSerif", Font.PLAIN, 25));
		g.drawString("La partie est finie.", 140, 50);
		if(this.joueur == 0) g.drawString("Les pion Noir ont gagner.", 80, 75);
		else if(this.joueur == 1) g.drawString("Les pion Blanc ont gagner", 60, 75);
	}
}