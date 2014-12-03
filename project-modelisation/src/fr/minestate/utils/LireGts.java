package fr.minestate.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import fr.minestate.figure.Segment;
import fr.minestate.figure.Face;
import fr.minestate.models.ModelVolume;
import fr.minestate.models.VolumeChangerModel;

/**
 * Permet de lire le fichierGTS et d'en deduire les listePoints, les segment et les listeTriangles
 * @author scta
 *
 */
public class LireGts {
	public ModelVolume volume;
	public VolumeChangerModel volumeModel;

	/**
	 * Permet de definir un GtsParser selon un volumeModel
	 * @param volumeModel le volumeModel selon lequel on souhaite definir le gtsParser
	 */
	public LireGts(VolumeChangerModel volumeModel) {
		this.volumeModel = volumeModel;
	}

	/**
	 * Permet de recuperer les listePoints, les listeSegments et les listeTriangles du fichier GTS.
	 * @param selectedFile le fichier a analyser
	 * @return le VolumeModel associe au fichier GTS
	 */
	public static ModelVolume lireFichier(File selectedFile) {
		ModelVolume volume = new ModelVolume();
		try {
			FileReader fr = new FileReader(selectedFile);
			BufferedReader buff = new BufferedReader(fr);
			String s = buff.readLine();
			String[] nombres = s.split(" ");
			ArrayList<Point> listePoints = new ArrayList<Point>();
			ArrayList<Segment> listeSegments = new ArrayList<Segment>();
			ArrayList<Face> listeTriangles = new ArrayList<Face>();
			int nombre = Integer.parseInt(nombres[0]);
			for(int i = 0 ; i < nombre ; i++) {
				s = buff.readLine();
				String[] pts = s.split(" ");
				listePoints.add(new Point(Float.parseFloat(pts[0]), Float.parseFloat(pts[1]), Float.parseFloat(pts[2])));
			}

			nombre = Integer.parseInt(nombres[1]);
			for(int i = 0 ; i < nombre ; i++) {
				s = buff.readLine();
				String[] sgmts = s.split(" ");
				listeSegments.add(new Segment(listePoints.get(Integer.parseInt(sgmts[0]) - 1), 
						listePoints.get(Integer.parseInt(sgmts[1]) - 1)));
			}
			nombre = Integer.parseInt(nombres[2]);
			for(int i = 0 ; i < nombre ; i++) {
				s = buff.readLine();
				String[] tri = s.split(" ");
				listeTriangles.add(new Face(listeSegments.get(Integer.parseInt(tri[0]) - 1), 
						listeSegments.get(Integer.parseInt(tri[1]) - 1), 
						listeSegments.get(Integer.parseInt(tri[2]) - 1)));
			}
			volume.addAllFace(listeTriangles);
			fr.close();
			buff.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Fichier incorrect !");
			return null;
		}

		String[] nom = selectedFile.getPath().split("\\\\");	
		nom = nom[nom.length - 1].split("[.]");
		volume.setName(nom[0]);
		return volume;
	}






}