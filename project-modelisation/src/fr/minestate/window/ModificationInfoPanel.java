/**
 * Ne sert a rien on va le virer 
 */
package fr.minestate.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ModificationInfoPanel extends JFrame implements ActionListener{

	/**
	 * Pour modifier les mots cles d'un objet
	 */

	private static final long serialVersionUID = 1L;
	JLabel label;
	JTextField field;
	JButton valider;
	int nombreMotsCleObjet;


	public ModificationInfoPanel(int nb) {
		this.setLayout(new BorderLayout());
		this.nombreMotsCleObjet = nb;
		JButton ajoutMotCle = new JButton ("Ajouter");
		ajoutMotCle.addActionListener(this);
		
		JButton suppressionMotCle = new JButton ("Supprimer");
		suppressionMotCle.addActionListener(this);
		this.getContentPane().add(ajoutMotCle, BorderLayout.CENTER);
		
		this.getContentPane().add(suppressionMotCle, BorderLayout.CENTER);
		
		
		this.setSize(new Dimension(500, 500));
		this.setTitle("Changer mots cles");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
