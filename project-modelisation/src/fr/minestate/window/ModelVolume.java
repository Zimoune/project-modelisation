package fr.minestate.window;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import fr.minestate.exception.IncompatibleSizeException;
import fr.minestate.figure.Face;
import fr.minestate.modif.Homothetie;
import fr.minestate.modif.Matrice;
import fr.minestate.modif.Modification;
import fr.minestate.modif.Rotation;
import fr.minestate.modif.Translation;
import fr.minestate.utils.Point;

/**
 * Permet de creer un ModelVolume pour manipuler un ensemble de triangle
 * 
 * @author scta
 *
 */

public class ModelVolume extends Observable {

	private Set<Face> volume;
	private Modification rotX;
	public String nom;
	private Modification rotY;
	private Modification rotZ;
	private Modification trX;
	private Modification trY;
	private Modification z;
	public String chemin;
	public String[] motsCles;
	public VueVolume vue;

	/**
	 * Retourne les mots cles associes a un ModelVolume
	 * 
	 * @return
	 */
	public String[] getMotsCles() {
		return motsCles;
	}

	/**
	 * Permet de changer les mots cles d'un ModelVolume
	 * 
	 * @param motsCles
	 */
	public void setMotsCles(String[] motsCles) {
		this.motsCles = motsCles;
	}

	/**
	 * Retourne le chemin d'un objet
	 * 
	 * @return
	 */
	public String getChemin() {
		return chemin;
	}

	/**
	 * Permet de changer le chemin d'un ModelVolume
	 * 
	 * @param chemin
	 */
	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	/**
	 * Initialise un ModelVolume
	 */
	public ModelVolume() {
		volume = new HashSet<Face>();

		initVolume();
	}

	/**
	 * Initialise un ModelVolume avec une VueVolume
	 * 
	 * @param vue
	 */
	public ModelVolume(VueVolume vue) {
		volume = new HashSet<Face>();
		this.vue = vue;
		initVolume();
	}

	/**
	 * Initialise un ModelVolume avec une collection de faces
	 * 
	 * @param col
	 */

	public ModelVolume(Collection<Face> col) {
		this(col, "Volume");
	}

	/**
	 * Initialise un VolumeModel avec une collection de triangle et un nom
	 */
	public ModelVolume(Collection<Face> c, String nom) {
		this.motsCles = new String[5];
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
	public void initVolume() {
		rotX = new Rotation(Rotation.X_AXIS, 0);
		rotY = new Rotation(Rotation.Y_AXIS, 0);
		rotZ = new Rotation(Rotation.Z_AXIS, 0);
		z = new Homothetie(42);
		trX = new Translation(Translation.X_AXIS, 1024 / 2);
		trY = new Translation(Translation.Y_AXIS, 700 / 2);
	}

	/**
	 * Permet d'ajouter un triangle au volumeModel
	 */
	public void addFace(Face face) {
		volume.add(face);
	}

	/**
	 * Permet d'ajouter une collection de triangle au volume
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
	 * @throws IncompatibleSizeException
	 */
	public Collection<Face> retourneListeTriangles()
			throws IncompatibleSizeException {
		Collection<Face> originals = volume;
		List<Face> res = new ArrayList<Face>();
		Matrice transformation = null;
		try {
			transformation = trX.add(trY).multiply(rotX).multiply(rotY)
					.multiply(rotZ).multiply(z);
		} catch (IncompatibleSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @param angle
	 * 
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
	 * Permet de retourner les points d'un ModelVolume
	 * 
	 * @return
	 */
	public Point[] getTabPoints() {
		Matrice m = null;
		try {
			m = rotZ.multiply(rotY).multiply(rotX).multiply(new Homothetie(20));
		} catch (IncompatibleSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @return
	 */
	private Matrice getBase() {
		Matrice rotation = null;
		try {
			rotation = rotZ.multiply(rotY).multiply(rotX);
		} catch (IncompatibleSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Matrice res = new Matrice(3, 3);

		for (int i = 0; i < res.nLignes; i++) {
			for (int j = 0; j < res.nColonnes; j++) {
				res.set(i, j, rotation.retourneCase(i, j));
			}
		}

		return res;
	}

	/**
	 * Permet d'executer une rotation selon une matrice
	 * 
	 * @param vector
	 * @throws Exception
	 * @throws IncompatibleSizeException
	 */
	public void rotation(Matrice vector) throws IncompatibleSizeException,
			Exception {
		Matrice vect = null;
		try {
			vect = getBase().inverse().multiply(vector);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
