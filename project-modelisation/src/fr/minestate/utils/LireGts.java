package fr.minestate.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.minestate.exception.FichierException;
import fr.minestate.figure.Segment;
import fr.minestate.figure.Face;
import fr.minestate.window.ModelVolume;
import fr.minestate.window.VolumeChangerModel;

/**
 * Permet de lire le fichierGTS et d'en deduire les listePoints, les segment et les listeTriangles
 * @author scta
 *
 */
public class LireGts {
	public ModelVolume volume;
	public VolumeChangerModel volumeModel;
	private static BufferedReader buff;
	

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
	 * @throws FichierException 
	 */
	public static ModelVolume lireFichier(File selectedFile) throws FichierException {
		boolean multiplicateur = false;
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
			multiplicateur = testFichier(selectedFile);
			for(int i = 0 ; i < nombre ; i++) {
				s = buff.readLine();
				String[] pts = s.split(" ");
				if(multiplicateur){
				listePoints.add(new Point(Float.parseFloat(pts[0])*20, Float.parseFloat(pts[1])*20, Float.parseFloat(pts[2])*20));
				}
				else{
					listePoints.add(new Point(Float.parseFloat(pts[0]), Float.parseFloat(pts[1]), Float.parseFloat(pts[2])));
				}
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
			volume.ajoutFaces(listeTriangles);
			fr.close();
			buff.close();
		} catch (FileNotFoundException e) {
			throw new FichierException("Fichier non trouvé !");
		} catch (IOException e) {
			throw new FichierException("Fichier incorrect !");
		} catch(NumberFormatException e) {
			throw new FichierException("Fichier incorrect !");
		} catch(Exception e){
			throw new FichierException("Fichier incorrect !");
		}


		String[] nom = selectedFile.getPath().split("\\\\");	
		nom = nom[nom.length - 1].split("[.]");

		/* Recuperer nom objet : Prenom represente le nom de l'objet */
		int indicePrenom = 0;
		int cptSlash = 0;
		String nom1 = nom[1];
		System.out.println("Nom1 = " + nom1);
		
		for (int i =0; i < nom1.length(); i++) {
			if (nom1.charAt(i) == '/') {
					cptSlash ++;
					if (cptSlash ==3) {
						indicePrenom = i+1;
					}
			}
		}

		System.out.println("Indice prenom = " + indicePrenom);
		String prenom = nom1.substring(indicePrenom, nom1.length());
		volume.setName(prenom);
		
		/* Recuperer chemin objet*/
		System.out.println("LireGts, le chemin de l'objet est : " + nom[1]);
		volume.setChemin(nom[1]);

		return volume;
	}
	
	public static boolean testFichier(File selectedFile) throws IOException, FichierException {
		boolean multiplicateur = false;
		try {
			FileReader  fr = new FileReader(selectedFile);
			buff = new BufferedReader(fr);
			String s = buff.readLine();
			String[] nombres = s.split(" ");
			int nombre = Integer.parseInt(nombres[0]);
			for (int i = 0; i < nombre; i++) {
				s = buff.readLine();
				String[] pts = s.split(" ");
				if (Float.parseFloat(pts[0]) < 0.001
						&& Float.parseFloat(pts[0]) > 0
						|| Float.parseFloat(pts[0]) > -0.001
						&& Float.parseFloat(pts[0]) < 0 || multiplicateur) {
					multiplicateur = true;
				}
			}
		} catch (FileNotFoundException e) {
			throw new FichierException("Fichier non trouvé !");
		} catch (IOException e) {
			throw new FichierException("Fichier incorrect !");
		} catch(NumberFormatException e) {
			throw new FichierException("Fichier incorrect !");
		} catch(Exception e){
			throw new FichierException("Fichier incorrect !");
		}
		return multiplicateur;
	}
	
		





}