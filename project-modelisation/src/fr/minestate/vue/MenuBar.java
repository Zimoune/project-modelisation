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
import fr.minestate.mouvement.MouvementVolume;
import fr.minestate.utils.FiltreSimple;
import fr.minestate.utils.LireGts;

/**
 * Permet de definir la barre de menu en haut de l'ecran
 * @author scta
 *
 */
public class MenuBar extends JMenuBar implements Observer, ActionListener {
	private VolumeChangerModel volumeSetModel;
	private Fenetre ms;
	
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
	private JMenuItem zoom;
	private JMenuItem unzoom;
	
	public MenuBar(VolumeChangerModel volumeSetModel, Fenetre ms) {
		this.volumeSetModel = volumeSetModel;
		this.ms = ms;
		initGUI();
	}

	/**
	 * Initialise la GUI de la barre de menu
	 */
	private void initGUI() {
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		save.addActionListener(this);
		open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		open.addActionListener(this);
		openBdd = new JMenuItem("Load BDD");
		openBdd.addActionListener(this);
		
		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		exit.addActionListener(this);
		
		zoom = new JMenuItem("Zoom");
		zoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, InputEvent.CTRL_MASK));
		zoom.addActionListener(this);
		unzoom = new JMenuItem("Unzoom");
		unzoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, InputEvent.CTRL_MASK));
		unzoom.addActionListener(this);
		
		file.add(save);
		file.add(open);
		file.add(openBdd);
		file.add(exit);
		
		edit.add(zoom);
		edit.add(unzoom);
		
		add(file);
		add(edit);
		this.setBackground(new Color(27, 126, 179));
	}
	
	/**
	 * Permet de change de VolumeSetModel
	 * @param vsm le nouveau VolumeSetModel
	 */
	public void setVolumeSetModel(VolumeChangerModel vsm){
		this.volumeSetModel = vsm;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
	}

	/**
	 * Permet de definir les actions realisees lorsque l'on clique sur les boutons SAVE, OPEN, on que l'on ferme / annule le JF, ou LOAD BDD
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Si on clique sur le bouton save
		if(arg0.getSource() == this.save){
			FiltreSimple gts = new FiltreSimple("Fichiers GTS", ".gts");
			JFileChooser jf = new JFileChooser();
			boolean estGts = false;
			jf.addChoosableFileFilter(gts);
			jf.showOpenDialog(null);
			//Récupération du fichier
			File fichier = jf.getSelectedFile();
			if(fichier == null)
				return;
			String extension = fichier.getName().substring(fichier.getName().length()-4, fichier.getName().length());
			if(extension.equals(".gts")){
				estGts = true;
			} else
				try {
					throw new FichierException("Format de fichier incorrect.");
				} catch (FichierException e1) {
					e1.printStackTrace();
				}
			if(estGts){
				copyFile(fichier);
			}
		
		}
		//Si on clique sur le bouton open
		else if(arg0.getSource() == this.open){
			FiltreSimple gts2 = new FiltreSimple("Fichiers GTS", ".gts");
			JFileChooser jf2 = new JFileChooser();
			boolean estGts2 = false;
			jf2.addChoosableFileFilter(gts2);
			jf2.showOpenDialog(null);
			//Récupération du fichier
			File fichier2 = jf2.getSelectedFile();
			//Si on annule ou ferme le jf
			if(fichier2 == null)
				return;
			jf2.setCurrentDirectory(Paths.get("./res/models").toAbsolutePath().toFile());
			String extension2 = fichier2.getName().substring(fichier2.getName().length()-4, fichier2.getName().length());
			if(extension2.equals(".gts")){
				estGts2 = true;
			} else
				try {
					throw new FichierException("Format de fichier incorrect.");
				} catch (FichierException e1) {
					e1.printStackTrace();
				}
			if(estGts2){
				ModelVolume vm = LireGts.getVolumeFromFile(fichier2);
				JPanel pan = this.ms.getPan();
				VueVolume vv = new VueVolume();
				vv.setBounds(0, 0, 1024, 700);
				// on enlève les anciens listeners (au cas ou l'utilisateur change d'avis)
				vv.removeMouseMotionListeners();
				vv.removeMouseWheelListener();
				// on met à jour le modèle
				vv.setVolumeModel(vm);
				// on remet les bons listeners
				vv.addMouseMotionListener(MouvementVolume.getMouseController(vm));
				vv.addMouseWheelListener(MouvementVolume.getMouseWheelController(vm));
				
				//vv.setPreferredSize(new Dimension(1024, 700));
				vv.setVisible(true);
				vv.setBackground(Color.gray);
				pan.setBounds(0, 30, 1024, 700);
				pan.setLayout(null);
				vv.revalidate();
				//On supprime les composants pour effacer l'ancien model
				pan.removeAll();
				//On ajoute le nouveau
				pan.add(vv);
				//this.ms.remove(this.ms.getPan());
				//this.ms.remove(pan);
				pan.repaint();
				this.ms.setPan(pan);
				this.ms.add(this.ms.getPan());
				this.ms.getPan().repaint();
				this.ms.revalidate();
				
			}
		}
		//Si on clique sur le bouton ouvrir bdd
		else if(arg0.getSource() == this.openBdd){
			JPanel pan = this.ms.getPan();
			pan.removeAll();
			pan.setLayout(null);
			pan.add(new ListObjetPanel(this.ms));
			pan.setBounds(0, 30, 1024, 700);
			this.ms.setPan(pan);
			this.ms.getPan().repaint();
			this.ms.revalidate();
		}
		else if(arg0.getSource() == this.zoom){
			this.volumeSetModel.zoom(30);
		}
		else if(arg0.getSource() == this.unzoom){
			this.volumeSetModel.zoom(-30);
		}
		//Si on clique sur le bouton quitter
		else if(arg0.getSource() == this.exit){
			System.exit(0);
		}
		
	}
	
	/** Copie le fichier source dans le fichier resultat
	 * return  vrai si cela réussit, false sinon
	 */
	public static boolean copyFile(File source){
		try{
			// Declaration et ouverture des flux
			java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);
	 
			try{
				java.io.FileOutputStream destinationFile = null;
				System.out.println(source.getName());
				String name = source.getName().substring(0, source.getName().length()-4);
				File file = new File("./res/models/"+source.getName());
				try{
					System.out.println(source.getPath());
					destinationFile = new FileOutputStream(file);	 
					// Lecture par segment de 0.5Mo 
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;
	 
					while ((nbLecture = sourceFile.read(buffer)) != -1){
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
					Connexion con = new Connexion();
					con.ajouterObjet(name, "./res/models/"+source.getName());
					con.closeConnexion();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e){
			e.printStackTrace();
			return false; // Erreur
		}
	 
		return true; // Résultat OK  
	}
		
}
