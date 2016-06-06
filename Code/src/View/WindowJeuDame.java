package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import Controller.PlateauController;
import Model.Pion;
import Model.Plateaux;
import ModelIA.IA_AB_Memoire;
import ModelIA.IA_AlphaBeta;
import ModelIA.IA_MinMax;

public class WindowJeuDame extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> strategyCombo;
	JButton clearButton;
	JButton proposeButton;
	JButton[] squares;
	JLabel statusBar;
	Plateau damier = new Plateau();
	
	public WindowJeuDame()
	{
		this.setLayout(new BorderLayout());
		JToolBar tbar = new JToolBar();
		tbar.setFloatable(false);
		strategyCombo = new JComboBox<String>(new String[] 
				{ 
						"Minimax",
						"Alpha-Beta",
						"IA & Memoire"
				}
		);
		strategyCombo.setSelectedIndex(1);
		tbar.add(strategyCombo);
		tbar.add(Box.createHorizontalGlue());
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		tbar.add(clearButton);
		proposeButton = new JButton("Propose Move");
		proposeButton.addActionListener(this);
		tbar.add(proposeButton);

		add(tbar, BorderLayout.NORTH);
		
		add(this.damier, BorderLayout.CENTER);
		
		statusBar = new JLabel(" ");
		statusBar.setBorder(BorderFactory.createEtchedBorder());
		add(statusBar, BorderLayout.SOUTH);
	}
	
	/** Handles all button events and updates the view. */
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource().equals(this.clearButton))
		{
			PlateauController.getInstance().initGame();
			this.damier.clear();
			this.damier.init();
		}
		else if(ae.getSource().equals(this.proposeButton))
		{
			proposeMove();
		}
	}

	/** Uses adversarial search for selecting the next action. */
	@SuppressWarnings("deprecation")
	private void proposeMove() 
	{
		/*int cm = 0;
		
		while(cm < 100)
		{
			PlateauController.getInstance().initGame();
			this.damier.clear();
			this.damier.init();
			*/
			int tmpTeminer = -1, chooseIA = 0;
			/*while(tmpTeminer==-1)
			{*/
				int[] numPlayer;
				if(PlateauController.getInstance().getTourNoir()){
					numPlayer = new int[]{1,-1,0};/*chooseIA = (cm>50)?1:0;*/}
				else{
					numPlayer = new int[]{0,-1,1};/*chooseIA = (cm>50)?0:1;*/}
					
				switch (strategyCombo.getSelectedIndex()) 
				{
					case 0:
					{
						IA_MinMax ia = new IA_MinMax(numPlayer, 4);
						PlateauController.getInstance().setGame((Plateaux)ia.IA_jouer(PlateauController.getInstance().getGame()));
						this.statusBar.setText("Nombre de noeud parcouru : " + ia.getNbNoeud() + " Nombre de fois evalué : " + ia.getNbEval());
						System.out.println("Min Max");
						break;
					}
					case 1:
					{
						IA_AlphaBeta iaAB = new IA_AlphaBeta(numPlayer, 4);
						PlateauController.getInstance().setGame((Plateaux)iaAB.jouerAB(PlateauController.getInstance().getGame()));
						this.statusBar.setText("Nombre de noeud parcouru : " + iaAB.getNbNoeud() + " Nombre de fois evalué : " + iaAB.getNbEval());
						System.out.println("Alpha Beta");
						break;
					}
					case 2:
					{
						IA_AB_Memoire iaABM = new IA_AB_Memoire(numPlayer, 4);
						PlateauController.getInstance().setGame((Plateaux)iaABM.jouerAB(PlateauController.getInstance().getGame()));
						this.statusBar.setText("Nombre de noeud parcouru : " + iaABM.getNbNoeud() + " Nombre de fois evalué : " + iaABM.getNbEval());
						System.out.println("Alpha Beta Memoire");
						break;
					}
					default:
					{
						IA_MinMax iaD = new IA_MinMax(numPlayer, 4);
						PlateauController.getInstance().setGame((Plateaux)iaD.IA_jouer(PlateauController.getInstance().getGame()));
						this.statusBar.setText("Nombre de noeud parcouru : " + iaD.getNbNoeud() + " Nombre de fois evalué : " + iaD.getNbEval());
						System.out.println("Min Max");
					}
				}
				PlateauController.getInstance().addSaveGame(PlateauController.getInstance().getGame().getGamePlateau());
				System.out.println("SaveCoup pour memoire");
				
				this.damier.clear();
				this.damier.init();
				PlateauController.getInstance().setTourNoir();
				
				tmpTeminer = PlateauController.getInstance().isTerminer();
				if(tmpTeminer != -1)
				{
					PlateauController.getInstance().savePartieInMemoire(tmpTeminer);
					new FinFrame(tmpTeminer);
				}
			/*}
			cm++;
		}*/
	}

}
