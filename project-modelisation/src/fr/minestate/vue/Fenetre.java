package fr.minestate.vue;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.minestate.models.VolumeChangerModel;

/**
 * Permet de definir la fenetre principale d'affichage
 * @author scta
 */
public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private VolumeChangerModel volumeSetModel;
	private MenuBar mv;
	private JPanel pan = new JPanel();
	
	/**
	 * Permet d'initialiser la fenetre
	 */
	public Fenetre() {
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Modélisation");
		this.setPreferredSize(new Dimension(1024, 728));
		this.volumeSetModel = new VolumeChangerModel();
		this.mv = new MenuBar(volumeSetModel, this);
		mv.setBounds(0, 0, 1024, 30);
		this.mainPanel = new JPanel();
		this.setBounds(0, 30, 1024, 700);
		this.pan.setLayout(null);
		this.add(mainPanel);
		this.add(mv);
		this.add(pan);
		this.pack();
	}

	/**
	 * Retourne le panel de la fenetre
	 * @return le panel de la fenetre
	 */
	public JPanel getPan() {
		return this.pan;
	}
	
	/**
	 * Permet de changer le panel de la fenetre
	 * @param panel le nouveau panel
	 */
	public void setPan(JPanel panel){
		this.pan = panel;
	}
}
