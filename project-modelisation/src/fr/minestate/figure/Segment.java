package fr.minestate.figure;

import fr.minestate.utils.Point;


/**
 * Permet de creer un segment constitue de deux points et d'y appliquer les manipulations de bases
 * @author scta
 *
 */

public class Segment {
	private Point p1;
	private Point p2;
	
	/**
	 * Permet de contruire un segment sans specifier de points
	 */
	public Segment() {
		p1 = new Point();
		p2 = new Point();
	}
	
	/**
	 * Permet de creer un segment en specifiant les deux points
	 * @param p1 : le premier point constituant le segment
	 * @param p2 : le deuxieme point constituant le segment
	 */
	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * Retourne le premier point du segment
	 * @return : le premier point du segment
	 */
	public Point getPoint1() {
		return p1;
	}
	
	/**
	 * Retourne le deuxieme point du segment
	 * @return : le deuxieme point du segment
	 */
	public Point getPoint2() {
		return p2;
	}
	


	/**
	 * Verifie l'egalite entre deux segment
	 * @param s : le segment que l'on souhaite comparer au segment actuel
	 * @return true si les segments sont egaux, false sinon
	 */
	public boolean equals(Segment s) {
		return (this.p1.equals(s.p1) && this.p2.equals(s.p2)) || (this.p1.equals(s.p2) && this.p2.equals(s.p1)); 
	}

	/**
	 * Verifie si le segment est valide (si les points le  constituant sont differents
	 * @return true si le segment est valide, false sinon
	 */
	public boolean isValid() {
		return !p1.equals(p2);
	}
	
	
	
}
