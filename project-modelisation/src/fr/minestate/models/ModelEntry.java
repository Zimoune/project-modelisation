package fr.minestate.models;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Permet de definir un modeleEntry
 * @author scta
 *
 */
public class ModelEntry {
	private int idModel;
	private String name;
	private String author;
	private String model;
	private String[] keyWords;
	
	/**
	 * Permet de construire un modele a partir de differents parametres
	 * @param idModel le numero du modele
	 * @param name le nom du modele
	 * @param author l'auteur du modele
	 * @param model le modele
	 * @param keyWords les mots cles du modele
	 */
	public ModelEntry(int idModel, String name, String author, String model, String[] keyWords) {
		this.idModel = idModel;
		this.name = name;
		this.author = author;
		this.model = model;
		this.keyWords = keyWords;
	}
	
	/**
	 * Permet de lire le modele
	 * @return le bufferedReader correspondant au modele
	 */
	public BufferedReader getModelReader() {
		return new BufferedReader(new StringReader(model));
	}
	
	/**
	 * Retourne l'id du modele
	 * @return l'id du modele
	 */
	public int getId() {
		return idModel;
	}
	
	/**
	 * Retourne le nom du modele
	 * @return le nom du modele (String)
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retourne l'auteur du modele
	 * @return l'auteur du modele (String)
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Retourne une representation du modele sous forme de chaine de caracteres
	 */
	public String toString() {
		return name;
	}

	/**
	 * Retourne les mots cles du modele
	 * @return les mots cles du modele (String[])
	 */
	public String[] getKeyWords1() {
		return keyWords;
	}
	
	/**
	 * Retourne les mots cles du modele
	 * @return les mots cles du (String)
	 */
	public String getKeyWords() {
		String res = "";
		for(int i = 0 ; i < keyWords.length ; i++) {
			if(i == 0) 
				res += keyWords[i];
			else
				res += " " + keyWords[i];
		}
		return res;
	}
}
