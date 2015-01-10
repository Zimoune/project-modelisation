package fr.minestate.bordel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.minestate.bdd.Connexion;

/**
 * Cette classe permet d'afficher une barre d'informations sur l'objet
 * @author scta
 */

public class InfoBar extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	Fenetre fen;
	JLabel labName;
	JLabel labLien;
	JLabel affichage;
	Connexion con = null;
	private String name;
	private String lien;
	int nombreMotsCle;
	Map<String, String> info = new HashMap<String, String>();
	private JButton modifierInfos;
	

	/**
	 * Contructeur de InfoBar : permet de creer une InfoBar
	 * @param fen
	 * @param name
	 * @param lien
	 */
	public InfoBar(Fenetre fen, String name, String lien) {
		this.fen = fen;
		this.name = name;
		this.lien = lien;
		this.modifierInfos = new JButton ("Modifier Infos");
		this.modifierInfos.addMouseListener(this);
		System.out.println("InfoBar, name = " + this.name);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		initCompo();
		this.setVisible(true);
		
	}

	/**
	 * Permet d'initialiser une InfoBar
	 */
	private void initCompo() {
		con = new Connexion();
		ArrayList<String> kw = con.getKeyWords(name);
		con.closeConnexion();
		String kwl = "Mots clés";
		for(String s: kw){
			kwl += "   "+s;
			nombreMotsCle ++;

		}
		System.out.println("On a : "+ nombreMotsCle + " mots cles");
		// pour le nom
		labName = new JLabel();
		labName.setText("Nom : " + name);
		labName.setPreferredSize(new Dimension(140, 30));
		
		// pour le chemin
		labLien = new JLabel();
		labName.setText("Chemin : " + lien);
		labLien.setPreferredSize(new Dimension(140, 30));
		
		affichage = new JLabel();
		this.affichage.setText("Nom : " + name + "   Chemin : " + lien +"   "+kwl);
		affichage.setPreferredSize(new Dimension(500, 30));

		labLien.setText(info.get("lien"));
		this.add(affichage);
		this.add(modifierInfos);

	}
	
	/**
	 * Permet de changer les informations affichees sur l'InfoBar
	 * @param nom
	 * @param chemin
	 */
	public void setInfos (String nom, String chemin) {
		con = new Connexion();
		ArrayList<String> kw = con.getKeyWords(nom);
		String kwl = "Mots clés";
		nombreMotsCle = 0;
		for(String s: kw){
			System.out.println("mot cle: "+s);
			kwl += "   "+s;
			nombreMotsCle ++;
		}
		System.out.println("InfoBar, setInfos : nombreMotsCle = " + nombreMotsCle);
		System.out.println();
		
		con.closeConnexion();
		this.affichage.setText("Nom : " + nom + "   Chemin : " + chemin+"   "+kwl);
		this.revalidate();
	}

	/**
	 * Permet de definir l'action a realiser quand on clique
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("On clique sur Modifier Infos :-) ");
		ModificationInfoPanel Milf = new ModificationInfoPanel (nombreMotsCle);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
