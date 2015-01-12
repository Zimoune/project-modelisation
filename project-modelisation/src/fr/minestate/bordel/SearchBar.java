/**
 * Classe permettant de creer une barre de recherche d'objets dans la BDD
 */
package fr.minestate.bordel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.minestate.bdd.Connexion;
import fr.minestate.exception.FichierException;
import fr.minestate.modif.DeplacerVolume;
import fr.minestate.utils.LireGts;

public class SearchBar extends JPanel implements KeyListener,
		ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JTextField jtf;
	Map<String, String> listObject;
	Connexion con = null;
	JList<?> jcb;
	DefaultListModel<String> model = new DefaultListModel<String>();
	Fenetre fen;

	/**
	 * Permet de creer une SearchBar avec une fenetre
	 * @param fen
	 */
	public SearchBar(Fenetre fen) {
		this.fen = fen;
		this.setVisible(true);
		this.setBackground(Color.pink);
		this.setLayout(new BorderLayout());
		initCompo();
	}

	/**
	 * Initialise la SearchBar
	 */
	private void initCompo() {
		jtf = new JTextField();
		jtf.addKeyListener(this);
		jtf.setPreferredSize(new Dimension(140, 30));
		con = new Connexion();
		this.listObject = con.getListObjet();
		con.closeConnexion();
		this.jcb = new JList<String>(model);
		this.getListObjet();
		this.jcb.setPreferredSize(new Dimension(140, 300));
		this.jcb.addListSelectionListener(this);
		this.add(jtf, BorderLayout.NORTH);
		this.add(new JScrollPane(jcb), BorderLayout.CENTER);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		this.update();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * Met a jour la SearchBar en fonction des entrees clavier
	 */
	public void update() {
		con = new Connexion();
		this.listObject.clear();
		this.listObject = con.getObjectsByLike(this.jtf.getText());
		Map<String, String> listObjectByKeyword = con.getObjectsByKeywordLike(this.jtf.getText());
		Set<String> set = listObjectByKeyword.keySet();
		Iterator<String> it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			//Si il ne contient pas déjà l'objet on l'ajoute
			String key = it.next();
			if(!this.listObject.containsKey(key)){
				this.listObject.put(key, listObjectByKeyword.get(key));
			}
		}
		this.jcb.clearSelection();
		this.getListObjet();
		con.closeConnexion();
	}

	/**
	 * Affiche les objets de la SearchBar
	 */
	private void getListObjet() {
		String[] list = new String[listObject.size()];
		Set<String> set = listObject.keySet();
		Iterator<String> it = set.iterator();
		int i = 0;
		System.out.println("+++++++++");
		while (it.hasNext()) {
			list[i] = it.next();
			System.out.println(list[i]);
			i++;
			
		}
		model.clear();
		for (String s : list){
			System.out.println("on ajoute "+s);
			model.addElement(s);
		}
	}

	/**
	 * Gere les changements d'action dans la SearchBar
	 */
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
	
		this.jcb.repaint();
		if (arg0.getValueIsAdjusting())return;
		if(this.jcb.isSelectionEmpty())return;
		System.out.println("Bou");
		boolean estGts = false;
		System.out.println(this.jcb.getSelectedIndex());
		String chemin = this.listObject.get((String)this.jcb.getSelectedValue());
		File fichier = new File(chemin);
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
			try {
				this.fen.vm = LireGts.lireFichier(fichier);
				
			} catch (FichierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.fen.info.setInfos(this.fen.vm.nom, this.fen.vm.chemin);
			this.fen.vm.initVolume();
			JPanel panel = this.fen.getPan();
			VueVolume vue = new VueVolume();
			this.fen.vm.vue = vue;
			vue.setBounds(0, 0, 1024, 700);
			vue.suppMouvementListener();
			vue.suppMouseWheel();
			vue.setVolumeModel(this.fen.vm);
			vue.addMouseMotionListener(DeplacerVolume
					.getMouseController(this.fen.vm));
			vue.addMouseWheelListener(DeplacerVolume
					.getMouseWheelController(this.fen.vm));
			vue.addMouseListener(DeplacerVolume.getMouseListenerAmaury(this.fen.vm));// test
			vue.setVisible(true);
			vue.setBackground(Color.gray);
			this.fen.menuBarre.setVue(vue);
			this.fen.info.setName(this.fen.vm.nom);

			panel.setLayout(null);
			vue.revalidate();
			panel.removeAll();
			panel.add(vue);
			panel.repaint();
			this.fen.setPan(panel);
			this.fen.getPan().repaint();
			this.fen.revalidate();
			this.fen.add(panel, BorderLayout.CENTER);
		} else {

			// this.menuBarre.initGUI();
			// this.revalidate();
			this.fen.add(new JPanel(), BorderLayout.CENTER);
		}
	}

}