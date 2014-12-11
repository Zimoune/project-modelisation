package fr.minestate.vue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import fr.minestate.bdd.Connexion;
import fr.minestate.exception.FichierException;
import fr.minestate.ihm.ListObjetPanel;
import fr.minestate.models.ModelVolume;
import fr.minestate.models.VolumeChangerModel;
import fr.minestate.modif.DeplacerVolume;
import fr.minestate.modif.Translation;
import fr.minestate.utils.FiltreSimple;
import fr.minestate.utils.LireGts;

/**
 * Permet de definir la barre de menu en haut de l'ecran
 * 
 * @author scta
 *
 */
public class MenuBarre extends JMenuBar implements Observer, ActionListener {
	private VolumeChangerModel volumeSetModel;
	private Fenetre ms;
	VueVolume vue;
	public ModelVolume mv;
	ListObjetPanel pol = null;
	File fichier2 = null;

	/*
	 * Menus déroulant
	 */
	private JMenu file;
	private JMenu edit;

	private static final long serialVersionUID = 1L;

	/*
	 * Items du menu file
	 */
	private JMenuItem save;
	private JMenuItem open;
	private JMenuItem exit;
	private JMenuItem openBdd;

	/*
	 * Items du menu edit
	 */
	private JMenuItem addLumiere;
	private JMenuItem delLumiere;
	private JMenuItem reload;
	private JMenuItem filDeFer;
	public ListObjetPanel lop2 = null;
	boolean loadBdd = false;
	boolean load = false;

	public MenuBarre(VolumeChangerModel volumeSetModel, Fenetre ms,
			boolean affichage) {
		if (affichage) {
			this.volumeSetModel = volumeSetModel;
			this.ms = ms;
			initGUI();
		}

		else {
			this.volumeSetModel = volumeSetModel;
			this.ms = ms;
		}

	}

	/**
	 * Initialise la GUI de la barre de menu
	 */
	public void initGUI() {

		file = new JMenu("File");
		edit = new JMenu("Edit");

		save = new JMenuItem("Save");
		save.addActionListener(this);
		open = new JMenuItem("Open");
		open.addActionListener(this);
		openBdd = new JMenuItem("Load BDD");
		openBdd.addActionListener(this);

		exit = new JMenuItem("Exit");
		exit.addActionListener(this);

		addLumiere = new JMenuItem("+ lumière");
		addLumiere.addActionListener(this);

		delLumiere = new JMenuItem("- lumière");
		delLumiere.addActionListener(this);

		filDeFer = new JMenuItem("Fil de fer");
		filDeFer.addActionListener(this);

		reload = new JMenuItem("Default location");
		reload.addActionListener(this);

		file.add(save);
		file.add(open);
		file.add(openBdd);
		file.add(exit);

		edit.add(addLumiere);
		edit.add(delLumiere);
		edit.add(reload);
		edit.add(filDeFer);

		add(file);
		add(edit);
		this.setBackground(new Color(27, 126, 179));
	}

	/**
	 * Permet de change de VolumeSetModel
	 * 
	 * @param vsm
	 *            le nouveau VolumeSetModel
	 */
	public void setVolumeSetModel(VolumeChangerModel vsm) {
		this.volumeSetModel = vsm;
	}

	@Override
	public void update(Observable o, Object arg) {
	}

	/**
	 * Permet de definir les actions realisees lorsque l'on clique sur les
	 * boutons SAVE, OPEN, on que l'on ferme / annule le JF, ou LOAD BDD
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Si on clique sur le bouton save
		if (arg0.getSource() == this.save) {
			sauvegarder();
		}
		// Si on clique sur le bouton open
		if (arg0.getSource() == this.open) {
			load();
		}
		// Si on clique sur le bouton ouvrir bdd
		if (arg0.getSource() == this.openBdd) {
			loadFromBdd();

		}
		if (arg0.getSource() == this.addLumiere) {

		}
		if (arg0.getSource() == this.delLumiere) {

		}

		if (arg0.getSource() == this.reload) {
			if (loadBdd == true) {
				System.out.println("Load Bdd = true");
				this.mv = pol.vm;
				this.mv.initVolume();
				this.ms.getPan().repaint();
				this.ms.revalidate();
			}

			else if (load == true) {
				System.out.println("Load = true");
				this.mv.initVolume();
				this.ms.getPan().repaint();
				this.ms.revalidate();
			}

			else {
				System.out.println("Par defaut");
				this.mv = ms.vm;
				this.mv.initVolume();
				this.ms.getPan().repaint();
				this.ms.revalidate();
			}

		}

		if (arg0.getSource() == this.exit) {
			System.exit(0);
		}
		if (arg0.getSource() == this.filDeFer) {

			if (loadBdd == true) {

				this.mv = pol.vm;
				JPanel pan = this.ms.getPan();

				vue.setBounds(0, 0, 1024, 700);
				vue.suppMouvementListener();
				vue.suppMouseWheel();
				vue.setVolumeModel(this.mv);
				vue.addMouseMotionListener(DeplacerVolume
						.getMouseController(this.mv));
				vue.addMouseWheelListener(DeplacerVolume
						.getMouseWheelController(this.mv));

				vue.setVisible(true);
				vue.setBackground(Color.gray);
				pan.setBounds(0, 30, 1024, 700);
				pan.setLayout(null);
				vue.revalidate();
				pan.removeAll();
				pan.add(vue);

				/*
				 * pan.repaint(); this.ms.setPan(pan);
				 * this.ms.add(this.ms.getPan()); this.ms.getPan().repaint();
				 * this.ms.revalidate();
				 */

				System.out.println("fdf : Load Bdd = true");
				this.mv = pol.vm;
				this.setFilDeFer(this.mv);
			}
			if (load == true) {
				System.out.println(" fdf : Load = true");
				this.setFilDeFer(mv);
			}

			if (!loadBdd && !load) {
				System.out.println("fdf : Par defaut");
				this.mv = ms.vm;
				this.setFilDeFer(mv);
			}

			this.ms.getPan().repaint();
			this.ms.revalidate();

		}

	}

	private void recharger() {

		if (mv == null) {
			System.out.println("mv est null");
			return;
		}
		mv.initVolume();
		this.ms.repaint();

	}

	/*
	 * public ModelVolume getMv() { return mv; }
	 * 
	 * public void setMv(ModelVolume mv) { this.mv = mv; }
	 */

	private void setFilDeFer(ModelVolume mv) {

		if (mv != null) {
			if (this.vue.isFdf()) {
				System.out.println("On enleve les fils de fer !");
				this.vue.setFdf(false);
			}

			else {
				System.out.println("On met les fils de fer ! ");
				this.vue.setFdf(true);
				this.ms.repaint();
			}

		} else
			System.out.println("Fil de fer :  MV est nul");
		return;
	}

	public VueVolume getVue() {
		return vue;
	}

	public void setVue(VueVolume vue) {
		this.vue = vue;
	}

	private void load() {
		load = true;
		loadBdd = false;
		FiltreSimple gts2 = new FiltreSimple("Fichiers GTS", ".gts");
		JFileChooser jf2 = new JFileChooser();
		boolean estGts2 = false;
		jf2.addChoosableFileFilter(gts2);
		jf2.showOpenDialog(null);
		// Récupération du fichier
		File fichier2 = jf2.getSelectedFile();
		// Si on annule ou ferme le jf
		if (fichier2 == null)
			return;
		jf2.setCurrentDirectory(Paths.get("./res/models").toAbsolutePath()
				.toFile());
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
			this.mv = LireGts.lireFichier(fichier2);
			System.out.println("this.mv =  " + this.mv.toString());
			JPanel pan = this.ms.getPan();
			vue = new VueVolume();
			vue.setBounds(0, 0, 1024, 700);
			vue.suppMouvementListener();
			vue.suppMouseWheel();
			vue.setVolumeModel(this.mv);
			vue.addMouseMotionListener(DeplacerVolume
					.getMouseController(this.mv));
			vue.addMouseWheelListener(DeplacerVolume
					.getMouseWheelController(this.mv));

			vue.setVisible(true);
			vue.setBackground(Color.gray);
			pan.setBounds(0, 30, 1024, 700);
			pan.setLayout(null);
			vue.revalidate();
			pan.removeAll();
			pan.add(vue);
			pan.repaint();
			this.ms.setPan(pan);
			this.ms.add(this.ms.getPan());
			this.ms.getPan().repaint();
			this.ms.revalidate();
		}

	}

	private void loadFromBdd() {
		loadBdd = true;
		load = false;
		vue = new VueVolume();
		System.out.println("menu barre : load bdd");
		JPanel pan = this.ms.getPan();
		pan.removeAll();
		pan.setLayout(null);
		pol = new ListObjetPanel(this.ms);
		this.mv = pol.getVm();
		pan.add(pol);
		pan.setBounds(0, 30, 1024, 700);
		this.ms.setPan(pan);
		this.ms.getPan().repaint();
		this.ms.revalidate();
		System.out.println("LOAD BDD : fin");
	}

	private void sauvegarder() {
		FiltreSimple gts = new FiltreSimple("Fichiers GTS", ".gts");
		JFileChooser jf = new JFileChooser();
		boolean estGts = false;
		jf.addChoosableFileFilter(gts);
		jf.showOpenDialog(null);
		// Récupération du fichier
		File fichier = jf.getSelectedFile();
		if (fichier == null)
			return;
		String extension = fichier.getName().substring(
				fichier.getName().length() - 4, fichier.getName().length());
		if (extension.equals(".gts")) {
			estGts = true;
		} else
			try {
				throw new FichierException("Format de fichier incorrect.");
			} catch (FichierException e1) {
				e1.printStackTrace();
			}
		if (estGts) {
			copyFile(fichier);
		}

	}

	/**
	 * Copie le fichier source dans le fichier resultat return vrai si cela
	 * réussit, false sinon
	 */
	public static boolean copyFile(File source) {
		try {
			// Declaration et ouverture des flux
			java.io.FileInputStream sourceFile = new java.io.FileInputStream(
					source);

			try {
				java.io.FileOutputStream destinationFile = null;
				System.out.println(source.getName());
				String name = source.getName().substring(0,
						source.getName().length() - 4);
				File file = new File("./res/models/" + source.getName());
				try {
					System.out.println(source.getPath());
					destinationFile = new FileOutputStream(file);
					// Lecture par segment de 0.5Mo
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;

					while ((nbLecture = sourceFile.read(buffer)) != -1) {
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
					Connexion con = new Connexion();
					con.ajouterObjet(name, "./res/models/" + source.getName());
					con.closeConnexion();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Erreur
		}

		return true; // Résultat OK
	}

	public void reloadAmaury(File fichier) {
		mv = LireGts.lireFichier(fichier);
		JPanel pan = this.ms.getPan();
		vue = new VueVolume();
		vue.setBounds(0, 0, 1024, 700);
		vue.suppMouvementListener();
		vue.suppMouseWheel();
		vue.setVolumeModel(mv);
		vue.addMouseMotionListener(DeplacerVolume.getMouseController(mv));
		vue.addMouseWheelListener(DeplacerVolume.getMouseWheelController(mv));

		vue.setVisible(true);
		vue.setBackground(Color.gray);
		pan.setBounds(0, 30, 1024, 700);
		pan.setLayout(null);
		vue.revalidate();
		pan.removeAll();
		pan.add(vue);
		pan.repaint();
		this.ms.setPan(pan);
		this.ms.add(this.ms.getPan());
		this.ms.getPan().repaint();
		this.ms.revalidate();

	}

}
