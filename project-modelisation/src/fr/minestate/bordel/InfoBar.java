package fr.minestate.bordel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.minestate.bdd.Connexion;

/**
 * Cette classe permet d'afficher une barre d'informations sur l'objet
 * @author dumetza
 *
 */

public class InfoBar extends JPanel {

	Fenetre fen;
	JLabel labName;
	JLabel lien;
	Connexion con = null;
	private String name;
	Map<String, String> info = new HashMap<String, String>();
	
	public InfoBar (Fenetre fen, String name) {
		this.fen = fen;
		this.name = name;
		this.setVisible(true);
		this.setBackground(Color.GREEN);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	
	private void initCompo() {
		labName = new JLabel ();
		labName.setPreferredSize(new Dimension(140, 30));
		lien = new JLabel();
		lien.setPreferredSize(new Dimension(140, 30));
		con = new Connexion();
		info = con.getInfo(name);
		con.closeConnexion();
		labName.setText(info.get("nom"));
		lien.setText(info.get("lien"));
		this.add(labName);
		this.add(lien);
	}
}


