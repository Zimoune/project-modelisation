package fr.minestate.models;

import java.util.ArrayList;
import java.util.Observable;

import fr.minestate.modif.DeplacerVolume;

/**
 * Permet de definir un SET de VolumeModel
 * @author scta
 *
 */
public class VolumeChangerModel extends Observable {

	private ArrayList<ModelVolume> volumes;
	int currentVolumeId = -1;
	int removedVolumeId = -1;

	
	/**
	 * Permet de definir un VolumeSetModel
	 */
	public VolumeChangerModel() {
		volumes = new ArrayList<ModelVolume>();
	}

	/**
	 * Permet de renvoyer le dernier VolumeModel
	 * @return le dernier VolumeModel de la liste volumes
	 */
	public ModelVolume getLastVolume() {
		if(volumes.size() > 0)
			return volumes.get(volumes.size() - 1);
		return null;
	}

	/**
	 * Renvoie le nombre de VolumeModel de la liste volumes
	 * @return la taille de la liste volumes
	 */
	public int getVolumeCount() {
		return volumes.size();
	}

	/**
	 * Permet de faire une translation selon un axe
	 * @param axis l'axe selon lequel on veut translater
	 * @param norm
	 */
	public void translation(int axis, int norm) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).translation(axis, norm);
	}

	/**
	 * Permet d'effectuer une rotation selon un angle et un angle
	 * @param axis l'axe de la rotation
	 * @param angle l'angle de la rotation
	 */
	public void rotation(int axis, int angle) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).rotation(axis, angle);
	}

	/**
	 * Permet de zoom sur un objet
	 * @param factor le niveau de zoom souhaite
	 */
	public void zoom(float factor) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).z(factor);
	}

	/**
	 * Permet de changer le volume courant
	 * @param current le nouveau volume
	 */
	public void setCurrentVolume(int current) {
		currentVolumeId = current;
		//VolumeController.setModel(volumes.get(current));
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'ajouter un volumeModele
	 * @param volume le nouveau volumeModel a ajoute
	 */
	public void addVolume(ModelVolume volume) {
		this.volumes.add(volume);
		setCurrentVolume(volumes.size() - 1);
	//	DeplacerVolume.optimalZoom(volume);
		notifyObservers();
	}


	/**
	 * Permet de supprimer une volumeModele de la liste volumes
	 * @param index l'index dans la liste volumes du volumeModele a supprimer
	 */
	public void deleteVolume(int index) {
		volumes.remove(index);
		removedVolumeId = index;
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet de connaitre l'id du volume courant
	 * @return l'id du volume
	 */
	public int getCurrentVolumeIdx() {
		return currentVolumeId;
	}

	/**
	 * Renvoie le removedVolumeId
	 * @return le removedVolumeId
	 */
	public int getRemovedId() {
		return removedVolumeId;
	}
}
