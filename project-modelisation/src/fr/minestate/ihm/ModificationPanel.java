/**
 * A continuer ... ?
 */
package fr.minestate.ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.minestate.bdd.Connexion;
import fr.minestate.window.*;

/**
 * Permet de proposer des modifications concernant l'objet actuel (changement nom, mots cles, etc)
 *
 */
public class ModificationPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Map<String, String> listObjet;
	private JComboBox<?> comboBox;
	private JButton valider = new JButton("Valider");
	private JButton supprimer = new JButton("Annuler");
	private JLabel titre = new JLabel("Vous pouvez ici modifier les mots clefs de l'objet");
	
	private Fenetre fen;
	public boolean estValide = false;
	public ModelVolume vm = null;
	VolumeChangerModel vsm = null;
	MenuBarre mb = new MenuBarre(vsm, fen, false);

	public ModificationPanel(Fenetre fen) {
		
			this.setVisible(true);
			this.fen = fen;
			this.setLayout(null);
			this.setBounds(0, 0, 1024, 700);
			this.setBackground(new Color(58, 146, 194));
			this.valider.addActionListener(this);
			this.valider.setBounds(350, 350, 180, 25);
			this.valider.setVisible(true);
			this.supprimer.addActionListener(this);
			this.supprimer.setBounds(550, 350, 180, 25);
			this.supprimer.setVisible(true);
			this.titre.setBounds(350, 50, 300, 20);

			
		
			this.add(titre);
			this.add(supprimer);
			this.add(valider);
			this.revalidate();
			this.fen.setPan(this);

	}



	/**
	 * Permet de definir les actions quand on clique sur les boutons valider et
	 * supprimer
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == valider) {
			
			this.revalidate();
		}

		else if (arg0.getSource() == supprimer) {
	
			this.fen.getPan().repaint();
			this.fen.revalidate();
		}
	}

	

	public ModelVolume getVm() {
		return vm;
	}

	public void setVm(ModelVolume vm) {
		this.vm = vm;
	}

	public void setMv(ModelVolume mv) {
		this.vm = mv;
	}

}