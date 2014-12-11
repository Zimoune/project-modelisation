package fr.minestate.vue; 

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

import fr.minestate.figure.Face;
import fr.minestate.models.ModelVolume;
import fr.minestate.utils.Point;

/**
 * Panel permettant de dessiner un volume
 * @author Simon
 *
 */
public class VueVolume extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	
	
	private ModelVolume modelVolume;
	
	public boolean isFdf() {
		return fdf;
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
	public VueVolume(ModelVolume v) {
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
		Collection<Face> triangles = modelVolume.retourneListeTriangles();
		if(fdf)
			for (Face t : triangles) 
				dessineTriangleFilDeFer(t, g);
		else
			for (Face t : triangles) 
				dessineTriangle(t, g);
	}
	
	/**
	 * Cette methode permet d'afficher un triangle
	 * @param t le triangle a afficher
	 * @param g permet d'afficher
	 */
	private void dessineTriangle(Face t, Graphics g) {
		Point[] points = t.getCoords();
		Polygon p = new Polygon();
		for (Point m : points) 
			p.addPoint((int) (m.getX()),(int)m.getY());
		g.setColor(Color.red);
		g.fillPolygon(p);
		g.setColor(Color.black);
		g.drawPolygon(p);
	}
	
	private void dessineTriangleFilDeFer(Face t, Graphics g){
		Point[] points = t.getCoords();
		Polygon p = new Polygon();
		for (Point m : points) 
			p.addPoint((int) (m.getX()),(int)m.getY());
		g.setColor(Color.red);
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
