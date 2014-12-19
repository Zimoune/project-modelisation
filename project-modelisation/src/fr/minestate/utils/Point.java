package fr.minestate.utils;



import fr.minestate.exception.IncompatibleSizeException;
import fr.minestate.modif.Matrice;

/**
 * Permet de creer un point
 * @author scta
 *
 */


public class Point {
	private float x;
	private float y;
	private float z;
	
	/**
	 * Construit un point (0,0,0)
	 */
	public Point() {
		this(0, 0, 0);
	}
	
	/**
	 * Construit un point
	 * @param x La coordonee X du point
 	 * @param y La coordonee Y du point
	 * @param z La coordonee Z du point
	 */
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Construit un point selon un tableau de float
	 * @param coord un tableau de float correspondant aux coordonees
	 */
	public Point(float[] coord) {
		x = coord[0];
		y = coord[1];
		z = coord[2];
	}
	
	/**
	 * Construit un point selon  une matrice
	 * @param m la matrice a partir de laquelle on veut faire le point
	 */
	public Point(Matrice m) {
		x = m.retourneCase(0, 0);
		y = m.retourneCase(1, 0);
		z = m.retourneCase(2, 0);
	}
	


	/**
	 * Permet de verifier l'egalite entre deux points
	 * @param p le point a comparer au point actuel
	 * @return true si les points sont egaux, false sinon
	 */
	public boolean equals(Point p) {
		return this.x == p.x && this.y == p.y && this.z == p.z;
	}

	/**
	 * Renvoie la coordonnee x d'un point
	 * @return coord x d'un point
	 */
	public float getX() {
		return x;
	}

	/**
	 * Renvoie la coordonnee y d'un point
	 * @return coord y d'un point
	 */
	public float getY() {
		return y;
	}

	/**
	 * Renvoie la coordonnee z d'un point
	 * @return coord z d'un point
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * Permet d'avoir les coordonees du point sous forme Coordonnees Homogenes
	 * @return une matrice correspondant aux coordonnees du point sous forme Homogene
	 */
	private Matrice getHomogenousVector() {
		return new Matrice(new float[][] {{getX()},
										{getY()}, 
										{getZ()},
										{1f}});
	}
	
	/**
	 * Permet de transformer un point en ses coordonnees homogenes
	 * @param transformation la matrice de transformation coordonnees homogenes
	 * @return le point en coordonnees homogenes
	 * @throws IncompatibleSizeException 
	 */
	public Point transform(Matrice transformation) throws IncompatibleSizeException {
		return new Point(transformation.multiply(getHomogenousVector()));
	}
}
