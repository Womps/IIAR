package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class WindowJeuDame extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JComboBox strategyCombo;
	JButton clearButton;
	JButton proposeButton;
	JButton[] squares;
	JLabel statusBar;
	
	public WindowJeuDame()
	{
		this.setLayout(new BorderLayout());
		JToolBar tbar = new JToolBar();
		tbar.setFloatable(false);
		strategyCombo = new JComboBox(new String[] 
				{ 
						"Joueur - Joueur", 
						"Joueur - Minimax",
						"Joueur - Alpha-Beta",
						"Joueur - IA & Memoire",
						"IA - IA"
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
		
		add(new Plateau(9), BorderLayout.CENTER);
		
		statusBar = new JLabel(" ");
		statusBar.setBorder(BorderFactory.createEtchedBorder());
		add(statusBar, BorderLayout.SOUTH);

		//game = new TicTacToeGame();
		actionPerformed(null);
	}
	
	/** Handles all button events and updates the view. */
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		/*switch (strategyCombo.getSelectedIndex()) 
		{
			case 0:
				search = MinimaxSearch.createFor(game);
				break;
			case 1:
				search = AlphaBetaSearch.createFor(game);
				break;
			case 2:
				search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,
						1.0, 1000);
				break;
			default:
				search = IterativeDeepeningAlphaBetaSearch.createFor(game, 0.0,
						1.0, 1000);
				((IterativeDeepeningAlphaBetaSearch<?, ?, ?>) search)
						.setLogEnabled(true);
		}*/
	}

	/** Uses adversarial search for selecting the next action. */
	private void proposeMove() 
	{
		
	}

}
