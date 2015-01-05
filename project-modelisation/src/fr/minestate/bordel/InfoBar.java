package fr.minestate.bordel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.minestate.bdd.Connexion;

/**
 * Cette classe permet d'afficher une barre d'informations sur l'objet : attention se met pas a jour quand on load 1 objet ds la bdd
 * 
 * @author dumetza
 * 
 */

public class InfoBar extends JPanel {

	private static final long serialVersionUID = 1L;

	Fenetre fen;
	JLabel labName; // le nom de l'objet
	JLabel labLien;
	JLabel affichage;
	Connexion con = null;
	private String name;
	private String lien;
	Map<String, String> info = new HashMap<String, String>();

	public InfoBar(Fenetre fen, String name, String lien) {
		this.fen = fen;
		this.name = name;
		this.lien = lien;
		System.out.println("InfoBar, name = " + this.name);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		initCompo();
		this.setVisible(true);
	}

	private void initCompo() {
		con = new Connexion();
		ArrayList<String> kw = con.getKeyWords(name);
		con.closeConnexion();
		String kwl = "Mots clés";
		for(String s: kw){
			kwl += "   "+s;
		}
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
		
		/*
		con = new Connexion();
		info = con.getInfo(name);
		con.closeConnexion();
		*/
		labLien.setText(info.get("lien"));
		this.add(affichage);
		//this.add(labName);
		//this.add(labLien);
	}
	
	public void setInfos (String nom, String chemin) {
		con = new Connexion();
		ArrayList<String> kw = con.getKeyWords(nom);
		String kwl = "Mots clés";
		for(String s: kw){
			System.out.println("mot cle: "+s);
			kwl += "   "+s;
		}
		con.closeConnexion();
		this.affichage.setText("Nom : " + nom + "   Chemin : " + chemin+"   "+kwl);
		this.revalidate();
	}
}
