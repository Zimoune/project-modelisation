package fr.minestate.vue;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.minestate.bdd.Connexion;

public class SearchBar extends JPanel implements KeyListener{
private JTextField jtf;
private JTextArea jta;
private JScrollPane jsp;
Map<String, String> listObject;
	
	public SearchBar(){
		this.setBounds(0, 0, 160, 700);
		this.setVisible(true);
		this.setBackground(Color.pink);
		this.setLayout(null);
		initCompo();
		
	}

	private void initCompo() {
		jtf = new JTextField();
		jtf.setBounds(10, 40, 140, 30);
		jtf.addKeyListener(this);
		jta = new JTextArea();
		jta.setBounds(10, 75, 140, 300);
		jta.setEditable(false);
		jsp = new JScrollPane(jta);
		jsp.setBounds(10, 75, 140, 300);
		this.add(jtf);
		this.add(jsp);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		Connexion con = new Connexion();
		this.listObject = con.getObjectsByLike(this.jtf.getText());
		System.out.println(this.jtf.getText());
		this.update();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	private void update(){
		
		Set<String> set = listObject.keySet();
		Iterator<String> it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			this.jta.append((String) it.next()+"\n");
			i++;
		}
	}
	
	private JComboBox<?> getComboBoxObjet() {
		String[] list = new String[listObject.size()];
		System.out.println(listObject.size());
		Set<String> set = listObject.keySet();
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
	
}
