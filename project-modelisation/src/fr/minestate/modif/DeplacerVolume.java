package fr.minestate.modif;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import fr.minestate.bordel.ModelVolume;
import fr.minestate.exception.IncompatibleSizeException;
import fr.minestate.utils.Point;

/**
 * Permet de controler un volume
 * 
 * @author scta
 *
 */
public class DeplacerVolume {

	public static int xSouris;
	public static int ySouris;
	private static int l = 1024;
	private static int h = 700;
	private static JComponent panel;
	@SuppressWarnings("unused")
	private static ModelVolume model;

	/**
	 * Retourne le MouseMotionListener associe a un VolumeModel
	 * 
	 * @param vol
	 *            le VolumeModel dont on veut recuperer le MouseMotionListener
	 * @return son MouseMotionListener
	 */

	public static MouseMotionListener getMouseController(final ModelVolume vol) {
		return new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					vol.translation(Translation.X_AXIS, e.getX() - xSouris);
					vol.translation(Translation.Y_AXIS, e.getY() - ySouris);
					
					if (xSouris >= l) {
						System.out.println("On ne s'enfuit pas ! ");
						vol.setTranslation(Translation.X_AXIS, 512);
					}
					
					if (xSouris <= 0) {
						System.out.println("Reviens-ici !");
						vol.setTranslation(Translation.X_AXIS, 512);
					}
					
					if (ySouris <= 0) {
						System.out.println("On ne sort pas du cadre ! ");
						vol.setTranslation(Translation.Y_AXIS, 350);
					}
					
					if (ySouris >= h) {
						System.out.println("On ne s'échappe pas ! ");
						vol.setTranslation(Translation.Y_AXIS, 350);
					}
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					vol.rotation(Rotation.Y_AXIS, e.getX() - xSouris);
					vol.rotation(Rotation.X_AXIS, ySouris - e.getY());
				}

				if (SwingUtilities.isMiddleMouseButton(e)) {
					try {
						vol.rotation(new Matrice(new float[][] {
								{ e.getX() - xSouris },
								{ ySouris - e.getY() }, { 0 } }));
					} catch (IncompatibleSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				xSouris = e.getX();
				ySouris = e.getY();
			}

			/**
			 * Permet dimension'effectuer les actions associees aux mouvements de la
			 * souris
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				xSouris = e.getX();
				ySouris = e.getY();
			}
		};
	}

	/**
	 * Permet de renvoyer le MouseWheelListener associe a un VolumeModel
	 * @param vol le VolumeModel dont on veut connaitre le MouseWheelListener
	 * @return son MouseWheelListener
	 */
	public static MouseWheelListener getMouseWheelController(final ModelVolume vol) {
		return new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				vol.z(e.getWheelRotation() * -5);
			}
		};
	}

	/**
	 * Permet de changer le panel
	 * @param panel le nouveau panel
	 */
	public static void setPanel(JComponent panel) {
		DeplacerVolume.panel = panel;
	}

	/**
	 * Permet de definir le meilleur zoom pour un VolumeModel
	 * @param model le VolumeModel dont on veut connaitre le meilleur zoom possible
	 */
	/*
	public static void optimalZoom(ModelVolume model) {
		optimalZoom(model, panel.getSize());
	}

	/**
	 * Permet de definir le meilleur zoom pour un VolumeModel selon des dimensions
	 * @param model le VolumeModel dont on veut connaitre le meilleur zoom
	 * @param dimension les dimensions
	 */
	/*
	public static void optimalZoom(ModelVolume model, Dimension dimension) {
		model.z(dimension);
	}*/
}
