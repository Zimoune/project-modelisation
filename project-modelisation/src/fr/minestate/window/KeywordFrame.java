package fr.minestate.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import fr.minestate.bdd.Connexion;

public class KeywordFrame extends JFrame implements ActionListener{
	private JTextField kw1  = new JTextField();
	private JTextField kw2  = new JTextField();
	private JTextField kw3  = new JTextField();
	private JTextField kw4  = new JTextField();
	private JTextField kw5  = new JTextField();
	private JButton valid = new JButton("ok");
	private ArrayList<String> kw = new ArrayList();
	private String name;
	private String chemin;
	private MenuBarre mb;
	private Connexion con;
	

	public KeywordFrame(String name, String chemin, MenuBarre menuBarre){
		this.name = name;
		this.chemin = chemin;
		this.mb = menuBarre;
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Ajouter des mots clés");
		this.setPreferredSize(new Dimension(300, 200));
		this.pack();
		this.initCompo();
	}


	private void initCompo() {
		this.getContentPane().setLayout(new GridLayout(3,2));
		this.valid.addActionListener(this);
		this.getContentPane().add(kw1);
		this.getContentPane().add(kw2);
		this.getContentPane().add(kw3);
		this.getContentPane().add(kw4);
		this.getContentPane().add(kw5);
		this.getContentPane().add(valid);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.valid){
			if(!kw1.getText().isEmpty())
				this.kw.add(kw1.getText());
			if(!kw2.getText().isEmpty())
				this.kw.add(kw2.getText());
			if(!kw3.getText().isEmpty())
				this.kw.add(kw3.getText());
			if(!kw4.getText().isEmpty())
				this.kw.add(kw4.getText());
			if(!kw5.getText().isEmpty())
				this.kw.add(kw5.getText());
			this.con = new Connexion();
			this.con.addKeyWords(name, this.kw);
			this.con.closeConnexion();
			this.mb.updateInfoBar(this.name, this.chemin);
			this.dispose();
		}
	}


	public ArrayList<String> getKw() {
		return this.kw;
	}
	
}
