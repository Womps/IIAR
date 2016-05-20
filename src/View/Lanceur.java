package View;
import javax.swing.JFrame;
import javax.swing.UIManager;


<<<<<<< HEAD
public class Lanceur
{
	public static void main(String[] args)
	{
		try
		{
=======
public class Lanceur {

	
	public static void main(String[] args) {
		try{
>>>>>>> 96c490c7fcbba74e99762518ccc644055cc0baa0
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){}
		JFrame f = new JFrame();
		f.setSize(600, 600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Plateau(9));
		f.setVisible(true);
<<<<<<< HEAD
	}
}
=======

	}

}
>>>>>>> 96c490c7fcbba74e99762518ccc644055cc0baa0
