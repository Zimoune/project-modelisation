package fr.minestate.modif;

/**
 * Permet d'effectuer des rotations
 * @author scta
 *
 */
public class Rotation extends Modification {

	public final static int X_AXIS = 0;
	public final static int Y_AXIS = 1;
	public final static int Z_AXIS = 2;

	private int axis;
	private int angle;

	/**
	 * Permet de definir une rotation
	 * @param matrix le tableau de float qui va servir a creer la rotation
	 */
	public Rotation(float[][] matrix) {
		super(matrix);
	}

	/**
	 * Permet de definir une rotation
	 * @param axis l'axe de la rotation
	 * @param angle l'angle selon lequel on veut l'effectuer
	 */
	public Rotation(int axis, int angle) {
		super(new float[4][4]);
		this.axis = axis;
		setAngle(angle);
	}

	/**
	 * Permet d'ajouter un angle a la rotation
	 * @param angle l'angle a ajouter
	 */
	public void addAngle(int angle) {
		setAngle((this.angle + angle) %360);
	}

	/**
	 * Permet de changer l'angle de la rotation
	 * @param angle le nouvel angle de la rotation
	 */
	public void setAngle(int angle) {
		this.angle = angle;
		updateMatrix();
	}

	/**
	 * Permet de mettre a jour la matrice de rotation selon l'angle et selon l'axe de rotation
	 */
	protected void updateMatrix() {
		float rad = (float) Math.toRadians(angle);
		if (axis == X_AXIS) {
			this.m = new float [][] {
					{1f, 0, 0, 0},
					{0, (float) Math.cos(rad), (float) (-1 * Math.sin(rad)), 0},
					{0, (float) Math.sin(rad), (float) Math.cos(rad), 0},
					{0, 0, 0, 1}};
		} else if (axis == Y_AXIS) {
			this.m = new float [][] {
					{(float) Math.cos(rad), 0, (float) Math.sin(rad), 0},
					{0 ,1 ,0 , 0},
					{-1 * (float) Math.sin(rad), 0, (float) Math.cos(rad), 0},
					{0, 0, 0, 1}};
		} else {
			this.m = new float [][] {
					{(float) Math.cos(rad), -1 * (float) Math.sin(rad), 0, 0},
					{(float) Math.sin(rad), (float) Math.cos(rad), 0 , 0},
					{0, 0, 1, 0},
					{0, 0, 0, 1}};
		}
	}
}


