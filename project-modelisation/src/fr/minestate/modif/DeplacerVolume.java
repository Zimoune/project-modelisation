package fr.minestate.modif;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;
import fr.minestate.bordel.ModelVolume;
import fr.minestate.exception.IncompatibleSizeException;

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


	
	
	/**
	 * Permet de controler l'objet avec la souris (au niveau des deplacements + met l'objet en fil de fer)
	 * @param vol
	 * @return
	 */
	public static MouseMotionListener getMouseController(final ModelVolume vol) {

		return new MouseMotionListener() {
			/**
			 * Permet de deplacer l'objet en maintenant enfoncé le clic gauche
			 */
			@Override
			public void mouseDragged(MouseEvent e) {

				if (SwingUtilities.isLeftMouseButton(e)) {
					vol.translation(Translation.X_AXIS, e.getX() - xSouris);
					vol.translation(Translation.Y_AXIS, e.getY() - ySouris);

					if (vol.vue != null) {
						vol.vue.setFdf(true); // test
					}

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
								{ e.getX() - xSouris }, { ySouris - e.getY() },
								{ 0 } }));
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
			 * Permet de gerer les deplacements de la souris
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				xSouris = e.getX();
				ySouris = e.getY();
			}

		};
	}

	/**
	 * Ce listener permet de controler l'objet avec la souris
	 * @param vol
	 * @return
	 */
	public static MouseListener getMouseListenerAmaury(final ModelVolume vol) {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			/**
			 * Permet d'enlever le mode fil de fer de l'objet quand on le lache avec la souris
			 */
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (SwingUtilities.isLeftMouseButton(arg0)) {
					if (vol.vue != null) {
						vol.vue.setFdf(false);
						System.out.println("Deplacer Volume : on enleve les FDF");
						vol.vue.updateUI();
					}
				}

			}
		};
	}

	/**
	 * Permet de renvoyer le MouseWheelListener associe a un VolumeModel
	 * 
	 * @param vol
	 * @return son MouseWheelListener
	 */
	public static MouseWheelListener getMouseWheelController(
			final ModelVolume vol) {
		return new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				vol.z(e.getWheelRotation() * -5);
			}
		};
	}



}
