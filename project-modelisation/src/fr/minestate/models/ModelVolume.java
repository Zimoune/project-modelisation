package fr.minestate.models;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import fr.minestate.figure.Face;
import fr.minestate.modif.Homothetie;
import fr.minestate.modif.Matrice;
import fr.minestate.modif.Modification;
import fr.minestate.modif.Rotation;
import fr.minestate.modif.Translation;
import fr.minestate.utils.Point;

/**
 * Permet de creer un VolumeModel pour manipuler un ensemble de triangle
 * 
 * @author scta
 *
 */

public class ModelVolume extends Observable {

	private Set<Face> volume;
	private Modification rotX;
	private String nom;
	private Modification rotY;
	private Modification rotZ;	
	private Modification trX;
	private Modification trY;
	private Modification z;
	

	/**
	 * Initialise un VolumeModel
	 */
	public ModelVolume() {
		volume = new HashSet<Face>();
		initVolume();
	}

	/**
	 * Initialise un VolumeModel avec une collection de triangle
	 * 
	 * @param col
	 *            la collection de triangle
	 */
	public ModelVolume(Collection<Face> col) {
		this(col, "Volume");
	}

	/**
	 * Initialise un VolumeModel avec une collection de triangle et un nom
	 * 
	 * @param col
	 *            la collection de triangle
	 * @param nom
	 *            le nom que l'on souhaite donner au VolumModel
	 */
	public ModelVolume(Collection<Face> c, String nom) {
		this.nom = nom;
		Iterator<Face> it = c.iterator();
		while (it.hasNext()) {
			Face face = (Face) it.next();
			volume.add(face);
		}
		initVolume();
	}

	/**
	 * Permet d'initialiser un volume, en effectuant les reglages necessaires
	 */
	private void initVolume() {
		rotX = new Rotation(Rotation.X_AXIS, 0);
		rotY = new Rotation(Rotation.Y_AXIS, 0);
		rotZ = new Rotation(Rotation.Z_AXIS, 0);
		z = new Homothetie(42);
		trX = new Translation(Translation.X_AXIS, 1024/2);
		trY = new Translation(Translation.Y_AXIS, 700/2);
	}

	/**
	 * Permet d'ajouter un triangle au volumeModel
	 * 
	 * @param face
	 *            le triangle a ajouter
	 */
	public void addFace(Face face) {
		volume.add(face);
	}

	/**
	 * Permet d'ajouter une collection de triangle au volume
	 * 
	 * @param col
	 *            la collection a ajouter au volume
	 */
	public void ajoutFaces(Collection<Face> c) {
		Iterator<Face> it = c.iterator();
		while (it.hasNext()) {
			Face face = (Face) it.next();
			volume.add(face);
		}
	}

	/**
	 * Cette methode renvoie les triangles transformes
	 * 
	 * @return collection<triangle>
	 */
	public Collection<Face> retourneListeTriangles() {
		Collection<Face> originals = volume;
		List<Face> res = new ArrayList<Face>();
		Matrice transformation = trX.somme(trY)
				.prod(rotX).prod(rotY).prod(rotZ).prod(z);
		for (Face face : originals) {
			res.add(face.transform(transformation));
		}

		Collections.sort(res);

		return res;
	}

	/**
	 * Permet de changer la translation
	 * 
	 * @param axis
	 *            l'axe selon lequel on veut effectuer la translation
	 * @param norm
	 */

	public void setTranslation(int axis, int norm) {
		if (axis == Translation.X_AXIS) {
			((Translation) trX).setNorm(norm);
		} else if (axis == Translation.Y_AXIS) {
			((Translation) trY).setNorm(norm);
		}
	}

	/**
	 * Permet de modifier la rotation
	 * 
	 * @param axis
	 *            l'axe selon lequel on veut modifier la rotation
	 * @param angle
	 *            l'angle de la rotation
	 */

	public void setRotation(int axis, int angle) {
		if (axis == Rotation.X_AXIS) {
			((Rotation) rotX).setAngle(angle);
		} else if (axis == Rotation.Y_AXIS) {
			((Rotation) rotY).setAngle(angle);
		} else {
			((Rotation) rotZ).setAngle(angle);
		}
	}

	/**
	 * Permet de modifier le z
	 * 
	 * @param factor
	 *            le niveau de z
	 */
	
	public void changerZoom(float factor) {
		((Homothetie) z).setfacteur(factor);
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'effectuer une translation
	 * 
	 * @param axis
	 *            l'axe selon lequel on veut effectuer la translation
	 * @param norm
	 */
	public void translation(int axis, int norm) {
		if (axis == Translation.X_AXIS) {
			((Translation) trX).addNorm(norm);
		} else if (axis == Translation.Y_AXIS) {
			((Translation) trY).addNorm(norm);
		}

		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'effectuer une rotation
	 * 
	 * @param axis
	 *            l'axe selon lequel on veut effectuer la rotation
	 * @param angle
	 */
	public void rotation(int axis, int angle) {
		if (axis == Rotation.X_AXIS) {
			((Rotation) rotX).addAngle(angle);
		} else if (axis == Rotation.Y_AXIS) {
			((Rotation) rotY).addAngle(angle);
		} else {
			((Rotation) rotZ).addAngle(angle);
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'effectuer un z
	 * 
	 * @param factor
	 *            le niveau de z
	 */
	public void z(float factor) {
		((Homothetie) z).addfacteur(factor);
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet de calculer le z optimal
	 * 
	 * @param d
	 */
	public void z(Dimension d) {
		float[] volumeDim = getMaxDimensions();
		float mix = volumeDim[0];
		float max = volumeDim[1];
		float miy = volumeDim[2];
		float may = volumeDim[3];
		float xRatio = (d.width - 50) / (max - mix);
		float yRatio = (d.height - 80) / (may - miy);
		float ratio = Math.min(xRatio, yRatio);
		changerZoom(ratio);
		mix = mix * ratio;
		miy = miy * ratio;
		max = max * ratio;
		may = may * ratio;
		setTranslation(Translation.X_AXIS, (int) ((d.width + max + mix) / 2));
		setTranslation(Translation.Y_AXIS,
				(int) ((d.height - miy - may - 40) / 2));
	}

	/**
	 * Permet d'obtenir les dimensions maximum
	 * 
	 * @return un tableau qui contient mix, max, miy, may
	 */
	private float[] getMaxDimensions() {
		float factor = ((Homothetie) z).getfacteur();
		((Homothetie) z).setfacteur(1);
		int xNorm = ((Translation) trX).getNorm();
		((Translation) trX).setNorm(0);
		int yNorm = ((Translation) trY).getNorm();
		((Translation) trY).setNorm(0);

		Float mix = null, max = null, miy = null, may = null;
		for (Face face : retourneListeTriangles()) {
			for (Point points : face.getCoords()) {
				if (mix == null) {
					mix = points.getX();
					max = points.getX();
					miy = points.getY();
					may = points.getY();
				}
				if (points.getX() < mix)
					mix = points.getX();
				else if (points.getX() > max)
					max = points.getX();
				if (points.getY() < miy)
					miy = points.getY();
				else if (points.getY() > may)
					may = points.getY();
			}
		}

		((Homothetie) z).setfacteur(factor);
		((Translation) trX).setNorm(xNorm);
		((Translation) trY).setNorm(yNorm);
		return new float[] { mix, max, miy, may };
	}

	public Point[] getTabPoints() {
		Matrice m = rotZ.prod(rotY).prod(rotX)
				.prod(new Homothetie(20));
		Point[] res = new Point[3];

		for (int i = 0; i < 3; i++) {
			res[i] = new Point(m.retourneCase(0, i), m.retourneCase(1, i),
					m.retourneCase(2, i));
		}

		return res;
	}

	/**
	 * Retourne la base
	 * 
	 * @return la base (matrix)
	 */
	private Matrice getBase() {
		Matrice rotation = rotZ.prod(rotY).prod(rotX);
		Matrice res = new Matrice(3, 3);

		for (int i = 0; i < res.tailleHaut(); i++) {
			for (int j = 0; j < res.tailleLarge(); j++) {
				res.set(i, j, rotation.retourneCase(i, j));
			}
		}

		return res;
	}

	/**
	 * Permet d'executer une rotation selon une matrice
	 * 
	 * @param vector
	 */
	public void rotation(Matrice vector) {
		Matrice vect = getBase().inversionMatrice().prod(vector);
		rotation(Rotation.Y_AXIS, (int) vect.retourneCase(0, 0));
		rotation(Rotation.X_AXIS, (int) vect.retourneCase(1, 0));
	}

	/**
	 * Permet de retourner le nom du VolumeModel
	 * 
	 * @return le nom du VolumeModel
	 */
	public String getName() {
		return nom;
	}

	/**
	 * Permet de changer le nom du VolumeModel
	 * 
	 * @param nom
	 *            le nouveau nom du VolumeModele
	 */
	public void setName(String nom) {
		this.nom = nom;
	}
}
