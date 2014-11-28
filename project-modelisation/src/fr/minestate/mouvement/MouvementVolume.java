package fr.minestate.mouvement;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import fr.minestate.models.ModelVolume;
import fr.minestate.modif.Matrix;
import fr.minestate.modif.Rotation;
import fr.minestate.modif.Translation;

/**
 * Permet de controler un volume
 * 
 * @author scta
 *
 */
public class MouvementVolume {

	public static int previousMouseX;
	public static int previousMouseY;

	private static JComponent panel;
	@SuppressWarnings("unused")
	private static ModelVolume model;

	/**
	 * Retourne le MouseMotionListener associe a un VolumeModel
	 * 
	 * @param m
	 *            le VolumeModel dont on veut recuperer le MouseMotionListener
	 * @return son MouseMotionListener
	 */
	public static MouseMotionListener getMouseController(final ModelVolume m) {
		return new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					m.translate(Translation.X_AXIS, e.getX() - previousMouseX);
					m.translate(Translation.Y_AXIS, e.getY() - previousMouseY);
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					m.rotate(Rotation.Y_AXIS, e.getX() - previousMouseX);
					m.rotate(Rotation.X_AXIS, previousMouseY - e.getY());
				}

				if (SwingUtilities.isMiddleMouseButton(e)) {
					m.rotate(new Matrix(new float[][] {
							{ e.getX() - previousMouseX },
							{ previousMouseY - e.getY() }, { 0 } }));
				}

				previousMouseX = e.getX();
				previousMouseY = e.getY();
			}

			/**
			 * Permet d'effectuer les actions associees aux mouvements de la
			 * souris
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				previousMouseX = e.getX();
				previousMouseY = e.getY();
			}
		};
	}

	/**
	 * Permet de renvoyer le MouseWheelListener associe a un VolumeModel
	 * @param m le VolumeModel dont on veut connaitre le MouseWheelListener
	 * @return son MouseWheelListener
	 */
	public static MouseWheelListener getMouseWheelController(final ModelVolume m) {
		return new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				m.zoom(e.getWheelRotation() * -5);
			}
		};
	}

	/**
	 * Permet de changer le panel
	 * @param panel le nouveau panel
	 */
	public static void setPanel(JComponent panel) {
		MouvementVolume.panel = panel;
	}

	/**
	 * Permet de definir le meilleur zoom pour un VolumeModel
	 * @param model le VolumeModel dont on veut connaitre le meilleur zoom possible
	 */
	public static void optimalZoom(ModelVolume model) {
		optimalZoom(model, panel.getSize());
	}

	/**
	 * Permet de definir le meilleur zoom pour un VolumeModel selon des dimensions
	 * @param model le VolumeModel dont on veut connaitre le meilleur zoom
	 * @param d les dimensions
	 */
	public static void optimalZoom(ModelVolume model, Dimension d) {
		model.optimalZoom(d);
	}
}
