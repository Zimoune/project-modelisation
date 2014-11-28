package fr.minestate.exception;
import fr.minestate.utils.Sommet;

/**
 * Permet de lever une exception de type 'SommetException'
 * @author scta
 *
 */
public class SommetException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Leve une exception suite a un probleme avec un sommet
	 * @param s le sommet qui pose probleme
	 */
	public SommetException(Sommet s) {
		new FichierException("Fichier corrompu: problème avec le sommet "+s.toString());
	}

}
