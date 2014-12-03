package fr.minestate.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.minestate.bdd.Connexion;
import fr.minestate.exception.FichierException;
import fr.minestate.models.ModelVolume;
import fr.minestate.models.VolumeChangerModel;
import fr.minestate.mouvement.MouvementVolume;
import fr.minestate.utils.LireGts;

/**
 * Permet de definir la fenetre principale d'affichage. On définit la figure à afficher des le lancement par un cube.
 * @author scta
 */
public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private VolumeChangerModel volumeChangerModel;
	private MenuBar menuBar;
	private JPanel panel = new JPanel();
	
	/**
	 * Permet d'initialiser la fenetre avec un cube préchargé
	 */
	public Fenetre() {
		Connexion con = new Connexion();
		Map<String, String> listObjet = con.getListObjet();
		System.out.println("Taille listObjet = " + listObjet.size());
		String chemin1erObjet = listObjet.get("cube");
		
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Modélisation");
		this.setPreferredSize(new Dimension(1024, 728));
		this.volumeChangerModel = new VolumeChangerModel();
		this.menuBar = new MenuBar(volumeChangerModel, this);
		menuBar.setBounds(0, 0, 1024, 30);
		this.mainPanel = new JPanel();
		this.setBounds(0, 30, 1024, 700);
		this.panel.setLayout(null);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.add(mainPanel);
		this.add(menuBar);

		
		boolean estGts2 = false;
		System.out.println("Ahhhh ! " + chemin1erObjet);
		File fichier2 = new File(chemin1erObjet);
		String extension2 = fichier2.getName().substring(
				fichier2.getName().length() - 4, fichier2.getName().length());
		if (extension2.equals(".gts")) {
			estGts2 = true;
		} else
			try {
				throw new FichierException("Format de fichier incorrect.");
			} catch (FichierException e1) {
				e1.printStackTrace();
			}
		if (estGts2) {
			ModelVolume vm = LireGts.lireFichier(fichier2);
			JPanel panel = this.getPan();
			VueVolume vue = new VueVolume();
			vue.setBounds(0, 0, 1024, 700);
			vue.suppMouvementListener();
			vue.suppMouseWheel();
			vue.setVolumeModel(vm);
			vue.addMouseMotionListener(MouvementVolume.getMouseController(vm));
			vue.addMouseWheelListener(MouvementVolume
					.getMouseWheelController(vm));
			vue.setVisible(true);
			vue.setBackground(Color.gray);
			panel.setBounds(0, 30, 1024, 700);
			panel.setLayout(null);
			vue.revalidate();
			panel.removeAll();
			panel.add(vue);
			panel.repaint();
			this.setPan(panel);
			this.getPan().repaint();
			this.revalidate();
		
		
		this.add(panel);
		this.pack();
		}
	}

	/**
	 * Retourne le panel de la fenetre
	 * @return le panel de la fenetre
	 */
	public JPanel getPan() {
		return this.panel;
	}
	
	/**
	 * Permet de changer le panel de la fenetre
	 * @param panel le nouveau panel
	 */
	public void setPan(JPanel panel){
		this.panel = panel;
	}
}
