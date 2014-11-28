package fr.minestate.exception;

/**
 * Permet de lever une exception de type 'SegmentException'
 * @author scta
 *
 */
public class SegmentException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Leve une exception suite a un probleme avec les segments
	 */
	public SegmentException() {
		new FichierException("Fichier corrompu: problème avec les segments");
	}

	/**
	 * Leve une exception suite a un probleme avec les segments
	 * @param i
	 */
	public SegmentException(Integer i) {
		new FichierException("Fichier corrompu: problème avec les segments");	
	}
	
}