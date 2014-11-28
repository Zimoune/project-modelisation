package fr.minestate.utils;
/**
 * 
 * @author MineState
 *
 */
public class Sommet {

	double x;
	double y;
	double z;
	
	/**
	 * Permet de construire un Sommet avec ses 3 coordonnees
	 * @param x
	 * @param y
	 * @param z
	 */
	public Sommet (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Retourne la composante X d'un Sommet
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Permet de changer la composante X d'un Sommet
	 * @param x
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * Retourne, sous forme de String, les composantes d'un sommet
	 */
	@Override
	public String toString() {
		return "Sommet [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
	}

	/**
	 * Retourne la composante Y d'un Sommet
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Permet de changer la composante Y d'un Sommet
	 * @param y
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * Retourne la composante Z d'un Sommet
	 * @return
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Permet de changer la composante Z d'un Sommet
	 * @param z
	 */
	public void setZ(Integer z) {
		this.z = z;
	}
	

}
