package fr.minestate.bordel; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.minestate.exception.IncompatibleSizeException;
import fr.minestate.figure.Face;
import fr.minestate.utils.Point;
import fr.minestate.utils.Vecteur;

/**
 * Panel permettant de dessiner un volume
 * @author Simon
 *
 */
public class VueVolume extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	public float puissanceLumiere = 1.25f;
	
	public ModelVolume modelVolume;

	public boolean isFdf() {
		return fdf;
	}
	public VueVolume(ModelVolume v) {
		add(new JLabel(v.getName()));
		this.modelVolume = v;
		this.modelVolume.addObserver(this);
	}

	public void setFdf(boolean fdf) {
		this.fdf = fdf;
	}

	private boolean fdf;

	/**
	 * Permet d'initialser un VolumeView sans parametre
	 */
	public VueVolume() {
		modelVolume = new ModelVolume();
	}

	/**
	 * Permet d'initialiser un VolumeView en precisant un VolumeModel
	 * @param v le VolumeModel que l'on veut dessiner
	 */
	public VueVolume(ModelVolume v, float lumiere) {
		add(new JLabel(v.getName()));
		this.modelVolume = v;
		this.modelVolume.addObserver(this);
	}

	/**
	 * Permet de changer le modelVolume
	 * @param le nouveau modelVolume
	 */
	public void setVolumeModel(ModelVolume modelVolume) {
		modelVolume.deleteObservers();
		this.modelVolume = modelVolume;
		this.modelVolume.addObserver(this);
		repaint();
	}

	/**
	 * Cette methode permet d'afficher un objet (tous les triangles)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Collection<Face> triangles = null;
		try {
			triangles = modelVolume.retourneListeTriangles();
		} catch (IncompatibleSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fdf)
			for (Face t : triangles) 
				dessineTriangleFilDeFer(t, g);
		else
			for (Face t : triangles) 
				dessineTriangle(t, g);
	}


	/**
	 * Cette fonction permet de definir la couleur de la face selon son exposition � la lumiere
	 * @param c couleur de base du triangle a afficher
	 * @param angle en radian entre la normale et le vecteur lumiere
	 * @param power puissance de luminosit�
	 */
	private Color illumine(Color c, float angle, float power) {
		if (angle<0 || angle>(Math.PI/2))
			return Color.black;
		int nvRed = (int) (power*(c.getRed() * Math.cos(angle)));
		if (nvRed>255)
			nvRed = 255;
		int nvGreen = (int) (power*(c.getGreen() * Math.cos(angle)));
		if (nvGreen>255)
			nvGreen = 255;
		int nvBlue = (int) (power*(c.getBlue() * Math.cos(angle)));
		if (nvBlue>255)
			nvBlue = 255;
		Color colorBrighten = new Color(nvRed, nvGreen, nvBlue);
		return colorBrighten;
	}


	/**
	 * Cette methode permet d'afficher un triangle
	 * @param t le triangle a afficher
	 * @param g permet d'afficher
	 */
	private void dessineTriangle(Face t, Graphics g) {
		Point[] points = t.getCoords();
		Vecteur vecteurSun = new Vecteur(0,0,1);
		Vecteur normal = new Vecteur();
		normal = normal.normale(points);
		float angleRad = normal.calculAngle(vecteurSun, normal);
		Polygon p = new Polygon();
		for (Point m : points)
			p.addPoint((int) (m.getX()),(int)m.getY());
		Color couleur = new Color(100,100,200);
		g.setColor(illumine(couleur, angleRad, puissanceLumiere));
		g.fillPolygon(p);
	}

	
	private void dessineNormaleAuFace(Face t, Graphics g) {
		Point[] points = t.getCoords();
		Vecteur normal = new Vecteur();
		normal = normal.normale(points);
		g.setColor(Color.red);
		g.drawLine((int) normal.barycentreFace(points).getX(), (int) normal.barycentreFace(points).getY(), (int) normal.pointDeVecteur(normal.barycentreFace(points), normal, 0.5f).getX(), (int) normal.pointDeVecteur(normal.barycentreFace(points), normal, 0.5f).getY());
	}
	
	
	private void dessineTriangleFilDeFer(Face t, Graphics g){
		Point[] points = t.getCoords();
		Polygon p = new Polygon();
		for (Point m : points) 
			p.addPoint((int) (m.getX()),(int)m.getY());
		g.setColor(Color.black);
		g.drawPolygon(p);
	}

	/**
	 * Permet de mettre a jour
	 */
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	/**
	 * Permet de supprimer les listeners associes a la roulette de la souris
	 */
	public void suppMouseWheel() {
		for(MouseWheelListener l : this.getMouseWheelListeners()) {
			this.removeMouseWheelListener(l);
		}
	}

	/**
	 * Permet de supprimer les listeners associes aux mouvements de la souris
	 */
	public void suppMouvementListener() {
		for(MouseMotionListener l : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(l);
		}
	}

}
