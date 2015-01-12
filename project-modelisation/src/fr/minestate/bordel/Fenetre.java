package fr.minestate.bordel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.minestate.bdd.Connexion;
import fr.minestate.exception.FichierException;
import fr.minestate.modif.DeplacerVolume;
import fr.minestate.utils.LireGts;

/**
 * Permet de definir la fenetre principale d'affichage.
 * @author scta
 */
public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private VolumeChangerModel volumeChangerModel;
	public MenuBarre menuBarre;
	public ModelVolume vm = null;
	private SearchBar searchBar = new SearchBar(this);
	public InfoBar info; // rajout
	private String defaultObjet = "cube";
	static int Height = 700;
	static int Width = 1024;
	
	/**
	 * Modifie les valeurs de hauteur/largeur de la Fenetre et vue : Active ou Desactive le mode plein ecran
	 */
	public void toggleFullScreen() {
		if(Width == Height){
			Height = (int)getToolkit().getScreenSize().getHeight() - 40;
			Width = (int)getToolkit().getScreenSize().getWidth();
		}
		else{
			Height = Width;
			Width = Height;
		}
	}
	

	/**
	 * Retourne la menuBarre associee a la fenetre
	 * @return
	 */
	public MenuBarre getmenuBarre() {
		return menuBarre;
	}

	/**
	 * Permet de changer la menuBare associee a la fenetre
	 * @param menuBarre
	 */
	public void setmenuBarre(MenuBarre menuBarre) {
		this.menuBarre = menuBarre;
	}

	private JPanel panel = new JPanel();

	/**
	 * Permet d'initialiser la fenetre avec un cube préchargé
	 */
	public Fenetre() {
		Connexion con = new Connexion();
		Map<String, String> listObjet = con.getListObjet();
		System.out.println("Taille listObjet = " + listObjet.size());
		String chemin1erObjet = listObjet.get(this.defaultObjet);

		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Modélisation");
		this.setPreferredSize(new Dimension(Width, Height));
		this.volumeChangerModel = new VolumeChangerModel();
		this.menuBarre = new MenuBarre(volumeChangerModel, this, true);

		this.mainPanel = new JPanel();
		this.panel.setLayout(null);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(menuBarre, BorderLayout.NORTH);
		this.add(searchBar, BorderLayout.WEST);
		boolean estGts2 = false;

		if (!listObjet.isEmpty()) {

			File fichier2 = new File(chemin1erObjet);
			String extension2 = fichier2.getName().substring(
					fichier2.getName().length() - 4,
					fichier2.getName().length());
			if (extension2.equals(".gts")) {
				estGts2 = true;
			} else
				try {
					throw new FichierException("Format de fichier incorrect.");
				} catch (FichierException e1) {
					e1.printStackTrace();
				}
			if (estGts2) {
				try {
					vm = LireGts.lireFichier(fichier2);
				} catch (FichierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.info = new InfoBar (this, vm.nom, vm.chemin);// rajout 
				this.add(info, BorderLayout.SOUTH);// rajout
				JPanel panel = this.getPan();
				VueVolume vue = new VueVolume();
				vue.setBounds(0, 0, Width, Height);
				vue.suppMouvementListener();
				vue.suppMouseWheel();
				vm.vue = vue;
				vue.setVolumeModel(vm);
				vue.addMouseMotionListener(DeplacerVolume
						.getMouseController(vm));
				vue.addMouseWheelListener(DeplacerVolume
						.getMouseWheelController(vm));
				vue.addMouseListener(DeplacerVolume.getMouseListenerAmaury(vm));// test
				vue.setVisible(true);
				vue.setBackground(Color.gray);
				this.menuBarre.setVue(vue);

				panel.setLayout(null);
				vue.revalidate();
				panel.removeAll();
				panel.add(vue);
				panel.repaint();
				this.setPan(panel);
				this.getPan().repaint();
				this.revalidate();

				this.add(panel, BorderLayout.CENTER);
				this.pack();
			}
		}
		
		else {

			//this.menuBarre.initGUI();
			//this.revalidate();
			this.add(panel, BorderLayout.CENTER);
			this.pack();
		}
		
		
	}

	/**
	 * Retourne le panel de la fenetre
	 * 
	 * @return le panel de la fenetre
	 */
	public JPanel getPan() {
		return this.panel;
	}

	/**
	 * Retourne la searchBar
	 * @return
	 */
	public SearchBar getSearchBar(){
		return this.searchBar;
	}
	
	/**
	 * Permet de changer le panel de la fenetre
	 * 
	 * @param panel
	 *            le nouveau panel
	 */
	public void setPan(JPanel panel) {
		this.panel = panel;
	}

	public String getDefaultObjet() {
		return defaultObjet;
	}

	public void setDefaultObjet(String defaultObjet) {
		this.defaultObjet = defaultObjet;
	}
}
