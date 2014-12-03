package fr.minestate.models;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import fr.minestate.figure.Face;
import fr.minestate.modif.Homothetie;
import fr.minestate.modif.Matrice;
import fr.minestate.modif.Rotation;
import fr.minestate.modif.Modification;
import fr.minestate.modif.Translation;
import fr.minestate.utils.Point;

/**
 * Permet de creer un VolumeModel pour  manipuler un ensemble de triangle
 * @author scta
 *
 */

public class ModelVolume extends Observable {
	
	private Set<Face> volume;
	private Modification rotationX;
	private Modification rotationY;
	private Modification rotationZ;
	private Modification zoom;
	private Modification translationX;
	private Modification translationY;
	private String name;
	
	/**
	 * Initialise un VolumeModel
	 */
	public ModelVolume() {
		volume = new HashSet<Face>();
		initVolume();
	}
	
	/**
	 * Initialise un VolumeModel avec une collection de triangle
	 * @param col  la collection de triangle
	 */
	public ModelVolume(Collection<Face> col) {
		this(col, "volume sans nom");
	}
	
	/**
	 * Initialise un VolumeModel avec une collection de triangle et un nom
	 * @param col la collection de triangle
	 * @param name le nom que l'on souhaite donner au VolumModel
	 */
	public ModelVolume(Collection<Face> col, String name) {
		this.name = name;
		for(Face t : col) {
			volume.add(t);
		}
		initVolume();
	}
	
	
	/**
	 * Permet d'initialiser un volume, en effectuant les reglages necessaires
	 */
	private void initVolume() {
		rotationX = new Rotation(Rotation.X_AXIS, 0);
		rotationY = new Rotation(Rotation.Y_AXIS, 0);
		rotationZ = new Rotation(Rotation.Z_AXIS, 0);
		zoom = new Homothetie(100);
		translationX = new Translation(Translation.X_AXIS, 300);
		translationY = new Translation(Translation.Y_AXIS, 300);		
	}
	
	/**
	 * Permet d'ajouter un triangle au volumeModel
	 * @param t le triangle a ajouter
	 */
	public void addFace(Face t) {
		volume.add(t);
	}
	
	/**
	 * Permet d'ajouter une collection de triangle au volume
	 * @param col la collection a ajouter au volume
	 */
	public void addAllFace(Collection<Face> col) {
		for(Face t : col) {
			volume.add(t);
		}
	}
	

	/**
	 * Cette methode renvoie les triangles transformes
	 * @return collection<triangle> 
	 */
	public Collection<Face> retourneListeTriangles() {
		Collection<Face> originals = volume;
		List<Face> out = new ArrayList<Face>();
		Matrice transformation = translationX.somme(translationY).prod(rotationX).prod(rotationY)
				.prod(rotationZ).prod(zoom);	
		for (Face t : originals) {
			out.add(t.transform(transformation));
		}
		
		Collections.sort(out);
		
		return out;
	}
	
	/**
	 * Permet de changer la translation
	 * @param axis l'axe selon lequel on veut effectuer la translation
	 * @param norm
	 */
	@Deprecated
	public void setTranslation(int axis, int norm) {
		if (axis == Translation.X_AXIS) {
			((Translation) translationX).setNorm(norm);
		} else if (axis == Translation.Y_AXIS) {
			((Translation) translationY).setNorm(norm);
		}
	}	
	
	/**
	 * Permet de modifier la rotation
	 * @param axis l'axe selon lequel on veut modifier la rotation
	 * @param angle l'angle de la rotation
	 */
	@Deprecated
	public void setRotation(int axis, int angle) {
		if (axis == Rotation.X_AXIS) {
			((Rotation) rotationX).setAngle(angle);
		} else if (axis == Rotation.Y_AXIS) {
			((Rotation) rotationY).setAngle(angle);
		} else {
			((Rotation) rotationZ).setAngle(angle);
		}
	}
	
	/**
	 * Permet de modifier le zoom
	 * @param factor le niveau de zoom
	 */
	@Deprecated
	public void setZoom(float factor) {
		((Homothetie) zoom).setfacteur(factor);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Permet d'effectuer une translation
	 * @param axis l'axe selon lequel on veut effectuer la translation
	 * @param norm
	 */
	public void translation(int axis, int norm) {
		if (axis == Translation.X_AXIS) {
			((Translation) translationX).addNorm(norm);
		} else if (axis == Translation.Y_AXIS) {
			((Translation) translationY).addNorm(norm);
		}
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Permet d'effectuer une rotation
	 * @param axis l'axe selon lequel on veut effectuer la rotation
	 * @param angle
	 */
	public void rotation(int axis, int angle) {
		if (axis == Rotation.X_AXIS) {
			((Rotation) rotationX).addAngle(angle);
		} else if (axis == Rotation.Y_AXIS) {
			((Rotation) rotationY).addAngle(angle);
		} else {
			((Rotation) rotationZ).addAngle(angle);
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Permet d'effectuer un zoom
	 * @param factor le niveau de zoom
	 */
	public void zoom(float factor) {
		((Homothetie) zoom).addfacteur(factor);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Permet de calculer le zoom optimal
	 * @param d
	 */
	public void zoom (Dimension d) {
		float[] volumeDim = getMaxDimensions();
		float minX = volumeDim[0];
		float maxX = volumeDim[1];
		float minY = volumeDim[2];
		float maxY = volumeDim[3];
		
		/* On calcule les ratios ideaux en largeur et en
		 * longueur en apportant une marge et on applique
		 * le plus petit d'entre eux
		 */
		float xRatio = (d.width - 50) / (maxX - minX);
		float yRatio = (d.height - 80) / (maxY - minY);
		float ratio = Math.min(xRatio, yRatio);
		setZoom(ratio);
		// On calcule les coordonnees apres application du zoom
		minX *= ratio;
		minY *= ratio;
		maxX *= ratio;
		maxY *= ratio;
		/* On calcule les translations a effectuer pour deplacer le centre
		* du volume sur le centre de l'ecran (en apportant une correction
		* pour la barre d'onglets en Y)
		*/
		setTranslation(Translation.X_AXIS,(int) ((d.width + maxX + minX)/2));
		setTranslation(Translation.Y_AXIS,(int) ((d.height - minY - maxY - 40)/2));
	}
	
	
	/**
	 * Permet d'obtenir les dimensions maximum
	 * @return un tableau qui contient minX, maxX, minY, maxY
	 */
	private float[] getMaxDimensions() {
		/* On remet à "zero" le zoom et les translations 
		* pour effectuer le calcul sur les coord reelles 
		* des points de l'objet 
		*/
		float factor = ((Homothetie) zoom).getfacteur();
		((Homothetie) zoom).setfacteur(1);
		int xNorm = ((Translation) translationX).getNorm();
		((Translation) translationX).setNorm(0);
		int yNorm = ((Translation) translationY).getNorm();
		((Translation) translationY).setNorm(0);
		
		Float minX = null, maxX=null,  minY=null, maxY=null;
		
		// On itere sur tous les points pour
		// recuperer les valeurs min et max
		for (Face t : retourneListeTriangles()) {
			for (Point p : t.getCoords()) {
				if (minX == null) {
					minX = p.getX();
					maxX = p.getX();
					minY = p.getY();
					maxY = p.getY();
				}
				if (p.getX() < minX) 
					minX = p.getX();
				else if (p.getX() > maxX)
				maxX = p.getX();
				if (p.getY() < minY)
					minY = p.getY();	
				else if (p.getY() > maxY)
					maxY = p.getY();
			}
		}
		
		((Homothetie) zoom).setfacteur(factor);
		((Translation) translationX).setNorm(xNorm);
		((Translation) translationY).setNorm(yNorm);
		return new float[] {minX, maxX, minY, maxY};
	}
	
	public Point[] getAffineFrame() {
		Matrice m = rotationZ.prod(rotationY).prod(rotationX).prod(new Homothetie(20));
		Point[] out = new Point[3];
		
		for (int i = 0; i < 3; i++) {
			out[i] = new Point(m.retourneCase(0, i), m.retourneCase(1, i), m.retourneCase(2, i));
		}
		
		return out;
	}
	
	/**
	 * Retourne la base
	 * @return la base (matrix)
	 */
	private Matrice getBase() {
		Matrice rotation = rotationZ.prod(rotationY).prod(rotationX);
		Matrice out = new Matrice(3, 3);
		
		for (int i = 0; i < out.tailleHaut(); i ++) {
			for (int j = 0; j < out.tailleLarge(); j++) {
				out.set(i, j, rotation.retourneCase(i,j));
			}
		}
		
		return out;
	}
	
	/**
	 * Permet d'executer une rotation selon une matrice
	 * @param vector 
	 */
	public void rotation(Matrice vector) {
		//TODO : verif si vecteur et exception ?	
		/*Matrice id = new Matrice(new float[][] {
				{1, 0, 0}, 
				{0, 1, 0},
				{0, 0, 1}});
	   */
		
		Matrice vect = getBase().inversionMatrice().prod(vector);
		rotation(Rotation.Y_AXIS, (int) vect.retourneCase(0, 0));
		rotation(Rotation.X_AXIS, (int) vect.retourneCase(1, 0));
	}

	/**
	 * Permet de retourner le nom du VolumeModel
	 * @return le nom du VolumeModel
	 */
	public String getName() {
		return name;
	}

	/**
	 * Permet de changer le nom du VolumeModel
	 * @param name le nouveau nom du VolumeModele
	 */
	public void setName(String name) {
		this.name = name;
	}
}
