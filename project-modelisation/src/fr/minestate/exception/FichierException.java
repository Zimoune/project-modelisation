package fr.minestate.exception;

import javax.swing.JOptionPane;

/**
 * Permet de lever une exception de type 'FichierException'
 * @author scta
 *
 */
public class FichierException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Permet de lever une exception suite a une erreur concernant le fichier en lui-meme
	 * @param message
	 */
	public FichierException(String message) {
		new JOptionPane();
		JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

}
