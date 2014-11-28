package fr.minestate.mouvement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.minestate.models.VolumeChangerModel;
import fr.minestate.vue.VolumeSetVue;

/**
 * Permet de controler un ensemble de Volume
 * @author scta
 *
 */
public class VolumeSetMouvement {
	
	/**
	 * Permet de detecter le changement d'etat d'un VolumeSetModel
	 * @param m le VolumeSet
	 * @return changeListener
	 */
	public static ChangeListener getTabChangeController(final VolumeChangerModel m) {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				m.setCurrentVolume(((VolumeSetVue)e.getSource()).getSelectedIndex());
			}
		};
	}
}
