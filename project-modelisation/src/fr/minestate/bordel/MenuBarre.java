package fr.minestate.bordel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.minestate.bdd.Connexion;
import fr.minestate.exception.FichierException;
import fr.minestate.ihm.ListObjetPanel;
import fr.minestate.modif.DeplacerVolume;
import fr.minestate.utils.FiltreSimple;
import fr.minestate.utils.LireGts;

/**
 * Permet de definir la barre de menu en haut de l'ecran
 * 
 * @author scta
 * 
 */
public class MenuBarre extends JMenuBar implements Observer, ActionListener,
		ChangeListener{
	private VolumeChangerModel volumeSetModel;
	private Fenetre ms;
	public VueVolume vue;
	public ModelVolume mv;
	ListObjetPanel pol = null;
	File fichier2 = null;
	private Map<String, String> listObjet; // nom & lien
	JSlider slider;
	JFloatSlider jfs;
	JFloatSlider jns;
	private Color color = new Color(100, 100, 200);
	private String nameToAdd;

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

	/*
	 * Items du menu edit
	 */
	private JMenuItem addLumiere;
	private JMenuItem changeColor;
	private JMenuItem delLumiere;
	private JMenuItem reload;
	private JMenuItem filDeFer;
	private JMenuItem motsCles;
	private JMenuItem FullScreen;
	public ListObjetPanel lop2 = null;
	private JColorChooser jcc;
	private JMenuItem normales;

	boolean loadBdd = false;
	boolean load = false;

	/**
	 * Permet de creer une MenuBarre
	 * 
	 * @param volumeSetModel
	 * @param ms
	 * @param affichage
	 */
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



		save = new JMenuItem("Sauvegarder");
		save.addActionListener(this);
		open = new JMenuItem("Ouvrir");
		open.addActionListener(this);

		exit = new JMenuItem("Quitter");
		exit.addActionListener(this);

		addLumiere = new JMenuItem("+ lumiere");
		addLumiere.addActionListener(this);
		
		changeColor = new JMenuItem("Couleur objet");
		delLumiere = new JMenuItem("- lumiere");
		delLumiere.addActionListener(this);
		changeColor.addActionListener(this);
		filDeFer = new JMenuItem("Fil de fer");
		filDeFer.addActionListener(this);

		reload = new JMenuItem("Default location");
		reload.addActionListener(this);

		motsCles = new JMenuItem("Mots clefs");
		motsCles.addActionListener(this);

		FullScreen = new JMenuItem("FullScreen");
		FullScreen.addActionListener(this);
		
		normales = new JMenuItem("Normales");
		normales.addActionListener(this);
		edit.add(normales);

		file.add(save);
		file.add(open);
		file.add(exit);

		edit.add(addLumiere);
		edit.add(delLumiere);
		edit.add(reload);
		edit.add(filDeFer);
		edit.add(motsCles);
		edit.add(FullScreen);
		edit.add(changeColor);



		add(file);
		add(edit);

		this.setBackground(Color.lightGray);
//		this.setBackground(new Color(27, 126, 179));
		this.add(new JLabel("Luminosité: "));
		// Controleur de puissance lumineuse
		jfs = new JFloatSlider(0, 0.0f, 2.5f, 1.20f, 0.5f);
		jfs.addChangeListener(this);
		this.add(jfs);
		this.add(new JLabel("Longueur normales: "));
		// Controleur de la longueur des normales
		jns = new JFloatSlider(0, 0.0f, 3f, 0.75f, 1f);
		jns.addChangeListener(this);
		this.add(jns);

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
			try {
				sauvegarder();
			} catch (FichierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Si on clique sur le bouton open
		if (arg0.getSource() == this.open) {
			try {
				load();
			} catch (FichierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Si on clique sur le bouton ouvrir bdd
		if (arg0.getSource() == this.addLumiere) {
			vue.puissanceLumiere += 0.1;
			vue.revalidate();
			ms.getPan().removeAll();
			ms.getPan().add(vue);
			vue.repaint();
		}
		if (arg0.getSource() == this.delLumiere) {
			vue.puissanceLumiere -= 0.1;
			vue.revalidate();
			ms.getPan().removeAll();
			ms.getPan().add(vue);
			vue.repaint();
		}
		if (arg0.getSource() == this.FullScreen) {
			ms.toggleFullScreen();
			ms.setPreferredSize(new Dimension(ms.Width,ms.Height));
			ms.repaint();
			vue.setBounds(0, 0, ms.Width,ms.Height);
			vue.revalidate();
			ms.getPan().removeAll();
			ms.getPan().add(vue);
			vue.repaint();
			ms.pack();
					
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
		if(arg0.getSource() == this.changeColor){
			/*JFrame frame = new JFrame();
			ChangeColorFrame panel = new ChangeColorFrame(this.vue);
			frame.setPreferredSize(new Dimension(400, 500));
			frame.setVisible(true);
			frame.getContentPane().add(panel);
			frame.pack();*/
			jcc = new JColorChooser();
			ActionListener okActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        vue.colorB = jcc.getColor().getBlue();
			        vue.colorR = jcc.getColor().getRed();
			        vue.colorG = jcc.getColor().getGreen();
			        vue.repaint();
			      }
			    };

			    // For cancel selection, change button background to red
			    ActionListener cancelActionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        System.out.println("cancled");
			      }
			    };

			    final JDialog dialog = JColorChooser.createDialog(null, "Change Button Background", true,
			        jcc, okActionListener, cancelActionListener);

			    dialog.setVisible(true);
			
		}

		// active ou desactive l'affichage des normales aux faces
		if (arg0.getSource() == this.normales) {

			if (loadBdd == true) {

				System.out.println("Normales : load bdd  =true");
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

				System.out.println("fdf : Load Bdd = true");
				this.mv = pol.vm;
				this.setNormales(this.mv);
			}
			if (load == true) {
				System.out.println("normales: Load = true");
				this.setNormales(mv);
			}

			if (!loadBdd && !load) {
				System.out.println("normales: Par defaut");
				this.mv = ms.vm;
				this.setNormales(mv);
			}

			this.ms.getPan().repaint();
			this.ms.revalidate();

		}

	}

	/**
	 * Active ou desactive l'affichage des normales aux face d'un ModelVolume
	 * @param mv
	 */
	public void setNormales(ModelVolume mv) {
		if (mv != null) {
			if (this.vue.isNormales()) {
				System.out.println("On enleve les normales!");
				this.vue.setNormales(false);
			}

			else {
				System.out.println("On met les normales");
				this.vue.setNormales(true);
				this.ms.repaint();
			}

		} else
			System.out.println("normales:  MV est nul");
		return;

	}

	/**
	 * Permet d'activer / desactiver le mode fil de fer
	 * 
	 * @param mv
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

	/**
	 * Retourne le VueVolume de la MenuBarre
	 * 
	 * @return
	 */
	public VueVolume getVue() {
		return vue;
	}

	/**
	 * Permet de changer le VueVolume de la MenuBarre
	 * 
	 * @param vue
	 */
	public void setVue(VueVolume vue) {
		this.vue = vue;
	}

	/**
	 * Permet de charger un fichier depuis l'ordinateur
	 * 
	 * @throws FichierException
	 */
	private void load() throws FichierException {
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

	/**
	 * Permet de sauvegarder un fichier depuis le PC dans la BDD
	 * @throws FichierException 
	 */
	private void sauvegarder() throws FichierException {
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
			try{
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
				copyFile(fichier2);
				
				
				this.ms.getSearchBar().update();
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
			}catch(Exception e){
				System.out.println("Erreur durant la lecture");
			}
			
		}

	}
	
	

	/**
	 * Copie le fichier source dans le fichier resultat return vrai si cela
	 * réussit, false sinon
	 */
	public boolean copyFile(File source) {
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
					String chemin = "./res/models/" + source.getName();
					destinationFile.close();
					Connexion con = new Connexion();
					con.closeConnexion();
					con.ajouterObjet(name, chemin);
					con.closeConnexion();
					KeywordFrame keywordFrame = new KeywordFrame(name, chemin, this);
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void updateInfoBar(String name, String chemin){
		this.ms.info.setInfos(name, chemin);
	}
	
	/**
	 * Permet d'ecouter le JFloatSLider et de regler la lumiere
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {

		vue.puissanceLumiere = (jfs.getFloatValue());
		vue.longeurNormale = (jns.getFloatValue());
		vue.revalidate();
		ms.getPan().removeAll();
		ms.getPan().add(vue);
		// update vue
		vue.repaint();

	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
