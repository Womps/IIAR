package View;
import javax.swing.JFrame;

public class FinFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public FinFrame(int joueur)
	{
		super();
		setSize(540, 200);
		setResizable(false);
		FinPanel panel = new FinPanel(joueur);
		getContentPane().add(panel);
		setVisible(true);
		panel.repaint();
	}
}