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
import fr.minestate.bordel.*;
import fr.minestate.exception.FichierException;
import fr.minestate.modif.DeplacerVolume;
import fr.minestate.utils.LireGts;

/**
 * Permet de lister les objets de la bdd
 * 
 * @author scta
 *
 */
public class ListObjetPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Map<String, String> listObjet;
	private JComboBox<?> comboBox;
	private JButton valider = new JButton("Valider");
	private JButton supprimer = new JButton("Supprimer");
	private JLabel titre = new JLabel("Choisissez un model à charger");
	private Fenetre fen;
	public boolean estValide = false;
	public ModelVolume vm = null;
	VolumeChangerModel vsm = null;

	/**
	 * Permet de definir une ListObjetPanel
	 * 
	 * @param fen
	 */
	public ListObjetPanel(Fenetre fen) {

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
		Connexion con = new Connexion();
		this.listObjet = con.getListObjet();
		con.closeConnexion();
		this.comboBox = this.getComboBoxObjet();
		this.comboBox.setVisible(true);
		this.add(titre);
		this.add(supprimer);
		this.add(valider);
		this.add(comboBox);
		this.revalidate();

	}

	/**
	 * Permet de creer une combo box avec tous les objets de la bdd
	 * 
	 * @return une JComboBox contenant tous les objets de la bdd
	 */
	private JComboBox<?> getComboBoxObjet() {
		String[] list = new String[listObjet.size()];
		System.out.println(listObjet.size());
		Set<String> set = listObjet.keySet();
		Iterator<String> it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			list[i] = (String) it.next();
			i++;
		}
		JComboBox<?> objetList = new JComboBox<Object>(list);
		objetList.setBounds(350, 80, 300, 20);
		return objetList;
	}

	/**
	 * Permet de definir les actions quand on clique sur les boutons valider et
	 * supprimer
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == valider) {
			estValide = true;
			Connexion con = new Connexion();
			String chemin = con.getCheminObjet(this.comboBox.getSelectedItem()
					.toString());
			try {
				this.vm = this.loadFile(chemin);
			} catch (FichierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setMv(vm);

			con.closeConnexion();
			this.revalidate();
		}

		else if (arg0.getSource() == supprimer) {
			Connexion con = new Connexion();
			con.supprimerObjet(this.comboBox.getSelectedItem().toString());
			con.closeConnexion();
			this.comboBox = this.getComboBoxObjet();
			this.comboBox.revalidate();
			JPanel pan = new JPanel();
			pan = this.fen.getPan();
			pan.setBounds(0, 30, 1024, 700);
			pan.setLayout(null);
			pan.removeAll();
			pan.add(new ListObjetPanel(this.fen));
			pan.repaint();
			this.fen.setPan(pan);
			this.fen.add(this.fen.getPan());
			this.fen.getPan().repaint();
			this.fen.revalidate();
		}
	}

	/**
	 * Permet de charger un fichier depuis son lien
	 * 
	 * @param lien
	 * @throws FichierException
	 */
	private ModelVolume loadFile(String lien) throws FichierException {
		boolean estGts2 = false;
		File fichier2 = new File(lien);
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

			this.vm = LireGts.lireFichier(fichier2);
			this.fen.info.setInfos(vm.nom, vm.chemin); // maj infos
			this.vm.initVolume();
			JPanel pan = this.fen.getPan();

			VueVolume vue = new VueVolume();
			this.vm.vue = vue;
			vue.setBounds(0, 0, 1024, 700);
			vue.suppMouvementListener();
			vue.suppMouseWheel();
			vue.setVolumeModel(vm);
			this.fen.menuBarre.vue = this.vm.vue; // test
			vue.addMouseMotionListener(DeplacerVolume.getMouseController(vm));
			vue.addMouseWheelListener(DeplacerVolume
					.getMouseWheelController(vm));
			vue.addMouseListener(DeplacerVolume.getMouseListenerAmaury(this.vm));
			vue.setVisible(true);
			vue.setBackground(Color.gray);
			pan.setBounds(0, 30, 1024, 700);
			pan.setLayout(null);
			vue.revalidate();
			pan.removeAll();
			pan.add(vue);

			pan.repaint();
			this.fen.setPan(pan);
			this.fen.add(this.fen.getPan());
			this.fen.getPan().repaint();
			this.fen.revalidate();
		}

		return this.vm;
	}

	/**
	 * Retourne le ModelVolume associe a la ListeObjetPanel
	 * @return
	 */
	public ModelVolume getMv() {
		return vm;
	}


	/**
	 * Permet de changer le ModelVolume associe a la ListObjetPanel
	 * @param mv
	 */
	public void setMv(ModelVolume mv) {
		this.vm = mv;
	}

}