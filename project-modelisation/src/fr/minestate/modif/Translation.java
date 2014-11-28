package fr.minestate.modif;

/**
 * Permet de creer une translation
 * @author scta
 *
 */

public class Translation extends Modification {

	public final static int X_AXIS = 0;
	public final static int Y_AXIS = 1;

	private int axis;
	private int norm;

	/**
	 * Permet de definir une translation
	 * @param matrix la matrice selon laquelle on definit la translation
	 */
	public Translation(float[][] matrix) {
		super(matrix);
		this.norm = (int) matrix[0][3];
	}

	/**
	 * Permet de definir une translation
	 * @param axis l'axe de la translation
	 * @param norm
	 */
	public Translation(int axis, int norm) {
		super(new float[4][4]);
		this.axis = axis;
		this.setNorm(norm);
	}

	
	public void setNorm(int norm) {
		this.norm = norm;
		updateMatrix();
	}
	
	/**
	 * Permet de renvoyer la norme
	 * @return la norme
	 */
	public int getNorm() {
		return norm;
	}

	/**
	 * Permet de mettre a ajour la matrice de translation selon l'axe
	 */
	@Override
	protected void updateMatrix() {
		float x = 0;
		float y = 0;
		
		if (axis == X_AXIS) {
			x = norm;
		} else {
			y = norm;
		}
		
		
		this.matrix = new float[][] {
									{1f, 0, 0, x},
									{0, 1, 0, y},
									{0, 0, 1, 0},
									{0, 0, 0, 1}};
	}

	/**
	 * Permet d'ajouter une norme
	 * @param norm la norme a ajouter
	 */
	public void addNorm(int norm) {
		setNorm(this.norm + norm);
	}
}