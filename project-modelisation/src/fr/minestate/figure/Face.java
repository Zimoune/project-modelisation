package fr.minestate.figure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import fr.minestate.modif.Matrice;
import fr.minestate.figure.Segment;
import fr.minestate.utils.Point;

/**
 * Permet de creer un triangle constitue de 3 segments
 * 
 * @author scta
 *
 */
public class Face implements Comparable<Face> {

	private Point[] points;
	private Segment[] segments;

	/**
	 * Permet de creer un triangle a partir de trois points
	 * 
	 * @param p1
	 *            : le premier point du triangle
	 * @param p2
	 *            : le deuxieme point du triangle
	 * @param p3
	 *            : le troisieme point du triangle
	 */
	public Face(Point p1, Point p2, Point p3) {
		points = new Point[] { p1, p2, p3 };
	}

	/**
	 * Permet de creer un triangle a partir de trois segments
	 * 
	 * @param s1
	 *            : le premier segment du triangle
	 * @param s2
	 *            : le deuxieme segment du triangle
	 * @param s3
	 *            : le troisieme du segment du triangle
	 */
	public Face(Segment s1, Segment s2, Segment s3) {
		segments = new Segment[] { s1, s2, s3 };

		points = new Point[3];
		Set<Point> set = new HashSet<Point>();
		set.add(s1.getPoint1());
		set.add(s1.getPoint2());
		set.add(s2.getPoint1());
		set.add(s2.getPoint2());
		set.add(s3.getPoint1());
		set.add(s3.getPoint2());
		int i = 0;
		for (Point p : set) {
			points[i] = p;
			i++;
		}
	}

	/**
	 * Permet de connaitre la coordonnee Z du centre de gravite du triangle
	 * 
	 * @return : la coordonnee Z du centre de gravite du triangle
	 */
	public float getGravityZ() {
		float z = 0;
		for (int i = 0; i < 3; i++)
			z += points[i].getZ();
		return z / 3;

	}

	/**
	 * Permet d'effectuer la comparaison pour l'algorithme du peintre
	 */
	@Override
	public int compareTo(Face t) {
		float comp = getGravityZ() - t.getGravityZ();
		if (comp > 0)
			return 1;
		if (comp == 0)
			return 0;
		return -1;
	}

	/**
	 * Permet de connaitre les coordonnees d'un point sous forme de tableau
	 * 
	 * @return : les coordonnees d'un point sous forme de tableau
	 */
	public Point[] getCoords() {
		return points;
	}

	/**
	 * Permet de transformer un triangle selon une matrice donnee
	 * 
	 * @param m
	 *            : la matrice de transformation
	 * @return le triangle transforme
	 */
	public Face transform(Matrice m) {
		Point[] out = new Point[3];

		for (int i = 0; i < out.length; i++) {
			out[i] = points[i].transform(m);
		}

		return new Face(out[0], out[1], out[2]);
	}

	/**
	 * Verifie la validite du segment
	 * 
	 * @return true si le triangle est valide, false sinon
	 */
	public boolean isValid() {
		return allSegmentValid() && getNumberOfPoint() == 3
				&& allSegmentDifferents();
	}

	/**
	 * Verifie si les trois segment constituant le triangle sont differents
	 * @return true si les segments sont diffents, false sinon
	 */
	private boolean allSegmentDifferents() {

		return !segments[0].equals(segments[1])
				&& !segments[1].equals(segments[2])
				&& !segments[2].equals(segments[0]);
	}

	
	/**
	 * Permet de connaitre le nombre de points differents constituant le segment
	 * @return  le nombre de points differents du segment
	 */
	private int getNumberOfPoint() {
		List<Point> points = new ArrayList<Point>();
		for (Segment s : segments) {
			if (!points.contains(s.getPoint1()))
				points.add(s.getPoint1());
			if (!points.contains(s.getPoint2()))
				points.add(s.getPoint2());
		}
		return points.size();
	}

	
	/**
	 * Verifie si les trois segments du triangle sont valides
	 * @return true si les trois segments du triangle sont valide, false sinon
	 */
	private boolean allSegmentValid() {
		for (Segment s : segments) {
			if (!s.isValid())
				return false;
		}
		return true;
	}

}