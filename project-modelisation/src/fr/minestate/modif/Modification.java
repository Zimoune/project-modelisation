package fr.minestate.modif;

/**
 * Classe abstraite qui permet de definir une transformation
 * @author scta
 *
 */
public abstract class Modification extends Matrice {

	/**
	 * Permet de definir une Transformation
	 * @param matrix le tableau de float selon lequel on definit la rotation
	 */
	public Modification(float[][] matrix) {
		super(matrix);
	}
	
	/**
	 * Methode abstraite pour mettre a jour les matrices de translation 
	 */
	protected abstract void updateMatrix();
}