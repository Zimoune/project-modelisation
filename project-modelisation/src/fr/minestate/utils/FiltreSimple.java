package fr.minestate.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Permet de creer un FiltreSimple : format accepte par le JFileChooser
 * 
 * @author scta
 *
 */

public class FiltreSimple extends FileFilter {
	private String description;
	private String extension;

	/**
	 * Permet de definir un FiltreSimple
	 * 
	 * @param description
	 *            la description du filtre
	 * @param extension
	 *            l'extension supportee par le filtre
	 */
	public FiltreSimple(String description, String extension) {
		if (description == null || extension == null) {
			throw new NullPointerException(
					"La description (ou extension) ne peut être null.");
		}
		this.description = description;
		this.extension = extension;
	}

	/**
	 * Permet de savoir si un fichier est accepte return true s'il est accepte,
	 * false sinon
	 */
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String nomFichier = file.getName().toLowerCase();

		return nomFichier.endsWith(extension);
	}

	/**
	 * Permet de retourner la description du filtre
	 * return la description du filtre
	 */
	public String getDescription() {
		return description;
	}
}
