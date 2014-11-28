package fr.minestate.vue;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;

import fr.minestate.models.ModelVolume;
import fr.minestate.models.VolumeChangerModel;
import fr.minestate.mouvement.MouvementVolume;
import fr.minestate.mouvement.VolumeSetMouvement;

/**
 * Permet d'afficher un VolumeSet
 * JTabbedPane = permet d'avoir plusieurs onglets
 */
public class VolumeSetVue extends JTabbedPane implements Observer {
	private static final long serialVersionUID = 1L;

	private VolumeChangerModel volumeSetModel;
	public VolumeSetVue(VolumeChangerModel volumeSetModel) {
		this.volumeSetModel = volumeSetModel;
		volumeSetModel.addObserver(this);
		addChangeListener(VolumeSetMouvement.getTabChangeController(volumeSetModel));
	}

	
	/**
	 * Permet d'afficher un objet dans un nouvel onglet
	 * @param v l'objet a afficher
	 */
	private void addTab(ModelVolume v) {
		VueVolume vView = new VueVolume(v);
		vView.addMouseMotionListener(MouvementVolume.getMouseController(v));
		vView.addMouseWheelListener(MouvementVolume.getMouseWheelController(v));
		addTab("tab", vView);
		int tabIdx = getTabCount() - 1;
		setSelectedIndex(tabIdx);
	}

	/**
	 * Permet de mettre a jour
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(volumeSetModel.getVolumeCount() > getTabCount()) {
			// On a ajoute un volume
			addTab(volumeSetModel.getLastVolume());
		} else if(volumeSetModel.getVolumeCount() < getTabCount()) {
			// On a supprime un volume
			remove(volumeSetModel.getRemovedId());
			setSelectedIndex(volumeSetModel.getCurrentVolumeIdx());
		}
	}
	
	/**
	 * Retourne le VolumeSetModel
	 * @return VolumeSetModel
	 */
	public VolumeChangerModel getVolumeSetModel() {
		return volumeSetModel;
	}
}




