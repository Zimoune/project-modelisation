package fr.minestate.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import fr.minestate.figure.Segment;
import fr.minestate.figure.Face;
import fr.minestate.models.ModelEntry;
import fr.minestate.models.ModelVolume;
import fr.minestate.models.VolumeChangerModel;

/**
 * Permet de lire le fichierGTS et d'en deduire les points, les segment et les triangles
 * @author scta
 *
 */
public class GtsParser {
	public ModelVolume volume;
	public VolumeChangerModel volumeModel;

	/**
	 * Permet de definir un GtsParser selon un volumeModel
	 * @param volumeModel le volumeModel selon lequel on souhaite definir le gtsParser
	 */
	public GtsParser(VolumeChangerModel volumeModel) {
		this.volumeModel = volumeModel;
	}

	/**
	 * Permet de recuperer les points, les segments et les triangles du fichier GTS.
	 * @param selectedFile le fichier a analyser
	 * @return le VolumeModel associe au fichier GTS
	 */
	public static ModelVolume getVolumeFromFile(File selectedFile) {
		ModelVolume volume = new ModelVolume();
		try {
			FileReader fr = new FileReader(selectedFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			line = getNextLine(reader);
			String[] numbers = line.split(" ");
			ArrayList<Point> points = new ArrayList<Point>();
			ArrayList<Segment> segments = new ArrayList<Segment>();
			ArrayList<Face> triangles = new ArrayList<Face>();
			// Recuperation des points
			int nb = Integer.parseInt(numbers[0]);
			for(int i = 0 ; i < nb ; i++) {
				line = getNextLine(reader);
				String[] pts = line.split(" ");
				points.add(new Point(Float.parseFloat(pts[0]), Float.parseFloat(pts[1]), Float.parseFloat(pts[2])));
			}

			// Recuperation des segments
			nb = Integer.parseInt(numbers[1]);
			for(int i = 0 ; i < nb ; i++) {
				line = getNextLine(reader);
				String[] sgmts = line.split(" ");
				segments.add(new Segment(points.get(Integer.parseInt(sgmts[0]) - 1), 
						points.get(Integer.parseInt(sgmts[1]) - 1)));
			}

			// Recuperation des triangles
			nb = Integer.parseInt(numbers[2]);
			for(int i = 0 ; i < nb ; i++) {
				line = getNextLine(reader);
				String[] trs = line.split(" ");
				triangles.add(new Face(segments.get(Integer.parseInt(trs[0]) - 1), 
						segments.get(Integer.parseInt(trs[1]) - 1), 
						segments.get(Integer.parseInt(trs[2]) - 1)));
			}
			volume.addAllFace(triangles);
			fr.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Fichier incorrect !");
			return null;
		}

		String[] name = selectedFile.getPath().split("\\\\"); // "\\\\" separation du '\' (sinon bug..?)		
		name = name[name.length - 1].split("[.]"); // re-split pour enlever l'extension
		volume.setName(name[0]);
		return volume;
	}



	/**
	 * Permet de recuperer un VolumeModel depuis un ModelEntry
	 * @param model le ModelEntry depuis lequel on veut faire un VolumeModel
	 * @return le VolumModel recupere
	 */
	public static ModelVolume getVolumeFromModelEntry(ModelEntry model) {
		ModelVolume volume = new ModelVolume();
		try {
			BufferedReader reader = model.getModelReader();
			// on r�cup�re la 1�re ligne non comment�e
			String line = getNextLine(reader);
			String[] numbers = line.split(" ");

			ArrayList<Point> points = new ArrayList<Point>();
			ArrayList<Segment> segments = new ArrayList<Segment>();
			ArrayList<Face> triangles = new ArrayList<Face>();

			// Recuperation des points
			points = getPoints(reader, Integer.parseInt(numbers[0]));

			// Recuperation des segments
			segments = getSegments(reader, Integer.parseInt(numbers[1]), points);

			// Recuperation des triangles
			triangles = getTriangles(reader, Integer.parseInt(numbers[2]), segments);

			// Ajout des triangles au volume
			volume.addAllFace(triangles);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Fichier incorrect !");
			return null;
		}

		volume.setName(model.getName());
		return volume;
	}


	/**
	 * Permet de recuperer les triangles dans le reader
	 * @param reader
	 * @param nbLines
	 * @param segments
	 * @return ArrayList <Triangle>
	 */
	private static ArrayList<Face> getTriangles(BufferedReader reader, int nbLines, ArrayList<Segment> segments) {
		String line = "";
		ArrayList<Face> triangles = new ArrayList<Face>();
		for(int i = 0 ; i < nbLines ; i++) {
			line = getNextLine(reader);
			String[] trs = line.split(" ");
			triangles.add(new Face(segments.get(Integer.parseInt(trs[0]) - 1), 
					segments.get(Integer.parseInt(trs[1]) - 1), 
					segments.get(Integer.parseInt(trs[2]) - 1)));
		}
		return triangles;

	}


	/**
	 * Permet de recuperer les segments dans le reader
	 * @param reader
	 * @param nbLines
	 * @param segments
	 * @return ArrayList <Segment>
	 */
	private static ArrayList<Segment> getSegments(BufferedReader reader, int nbLines, ArrayList<Point> points) {
		String line = "";
		ArrayList<Segment> segments = new ArrayList<Segment>();
		for(int i = 0 ; i < nbLines ; i++) {
			line = getNextLine(reader);
			String[] sgmts = line.split(" ");
			segments.add(new Segment(points.get(Integer.parseInt(sgmts[0]) - 1), 
					points.get(Integer.parseInt(sgmts[1]) - 1)));
		}
		return segments;
	}

	/**
	 * Permet de recuperer les points dans le reader
	 * @param reader
	 * @param nbLines
	 * @param segments
	 * @return ArrayList <Points>
	 */
	private static ArrayList<Point> getPoints(BufferedReader reader, int nbLines) {
		// on r�cup�re les points
		String line = "";
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i = 0 ; i < nbLines ; i++) {
			line = getNextLine(reader);
			String[] pts = line.split(" ");
			points.add(new Point(Float.parseFloat(pts[0]), Float.parseFloat(pts[1]), Float.parseFloat(pts[2])));
		}
		return points;
	}


/**
 * Permet d'acceder a la prochaine ligne du reader
 * @param reader
 * @return la prochaine ligne du reader
 */
	private static String getNextLine(BufferedReader reader) {
		try {
			String line;
			do {
				line = reader.readLine();
			} while(line != null && isComment(line));
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Permet de savoir si une ligne est commentee
	 * @param line la ligne qui nous interesse
	 * @return true si la ligne est commentee, false sinon
	 */
	private static boolean isComment(String line) {
		for(int i = 0 ; i < line.length() ; i++) {
			char c = line.charAt(i);
			if(c == '#') 
				return true;

			if(c != ' ')
				return false;
		}
		return true;
	}

	/**
	 * Verifie si le fichier est valide ou non
	 * @param fileContent le chemin du fichier dont on veut savoir s'il est valide
	 * @return true si le fichier est valide, false sinon
	 */
	public static boolean isValidFile(String fileContent) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(fileContent));
			// Recuperation de la premiere ligne nom commentee
			String line = getNextLine(reader);
			String[] numbers = line.split(" ");

			ArrayList<Point> points = new ArrayList<Point>();
			ArrayList<Segment> segments = new ArrayList<Segment>();
			ArrayList<Face> triangles = new ArrayList<Face>();

			// Recuperation des points
			points = getPoints(reader, Integer.parseInt(numbers[0]));
			// Verification de la validite des points
			if(points == null) return false;

			// Recuperation des segments
			segments = getSegments(reader, Integer.parseInt(numbers[1]), points);
			// Verification de la validite des segments
			if(segments == null) return false;
			for(Segment s : segments) {
				if(!s.isValid()) return false;
			}

			// Recuperation des triangles
			triangles = getTriangles(reader, Integer.parseInt(numbers[2]), segments);
			// Verification de la validite des triangles
			if(triangles == null) return false;
			for(Face t : triangles) {
				if(!t.isValid()) return false;
			}
			
			if(getNextLine(reader) != null) return false;
			
		} catch (Exception e) {
			return false;
		}


		// si rien a ete retourne jusquici, cest que le fichier est valide !
		return true;
	}
}
