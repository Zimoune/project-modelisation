package fr.minestate.vue;



import fr.minestate.modif.Matrix;

/**
 * Class Point permettant de manipuler les 3 coordon�es d'un point (X, Y, Z)
 * @author Louis
 *
 */


public class Sommet {
	private float x;
	private float y;
	private float z;
	
	public Sommet() {
		this(0, 0, 0);
	}
	
	public Sommet(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Sommet(float[] coord) {
		x = coord[0];
		y = coord[1];
		z = coord[2];
	}
	
	public Sommet(Matrix m) {
		x = m.get(0, 0);
		y = m.get(1, 0);
		z = m.get(2, 0);
	}
	
	/**
	 * Un point est �gale � un autre si les 3 coordon�es sont �gales.
	 * @param p
	 * @return true si les 3 coordonn�es du point actuel sont �gales aux 3 coordonn�es du point p.
	 */
	public boolean equals(Sommet p) {
		return this.x == p.x && this.y == p.y && this.z == p.z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	private Matrix getHomogenousVector() {
		return new Matrix(new float[][] {{getX()},
										{getY()}, 
										{getZ()},
										{1f}});
	}
	
	public Sommet transform(Matrix transformation) {
		return new Sommet(transformation.prod(getHomogenousVector()));
	}
}
