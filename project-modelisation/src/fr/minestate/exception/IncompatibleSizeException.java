package fr.minestate.exception;

public class IncompatibleSizeException extends Exception {

	/**
	 * Permet de lever une exception de type IncompatibleSizeException
	 * On l'utilise par exemple dans le cas ou une matrice n'a pas la bonne taille
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Permet de lever une exception de type IncompatibleSizeException et d'afficher un message
	 * @param message
	 */
	public IncompatibleSizeException(String message ){
		System.out.println(message);
	}
		

}
