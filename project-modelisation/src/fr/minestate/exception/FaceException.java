package fr.minestate.exception;

/**
 * Permet de lever une exception de type 'FaceException'
 * @author scta
 *
 */
class FaceException extends Exception{ 
	private static final long serialVersionUID = 1L;

	/**
	 * Leve une exception suite a un probleme de face
	 */
public FaceException(){
	new FichierException("Fichier corrompu: problème avec les faces");
  }  
}