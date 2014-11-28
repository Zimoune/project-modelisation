package fr.minestate.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Permet de lire un fichier et definit des methodes pratiques
 * @author scta
 *
 */
public class Utils {

	/**
	 * Supprime les espaces indesirables d'une chaine de caracteres
	 * @param str la chaine dont on souhaite supprimer les espaces
	 * @return la chaine sans espace
	 */
	public static String removeUselessSpaces(String str) {
		return str.replaceAll("^[\\s]*", "").replaceAll("[\\s]*$", "");
	}

	/**
	 * Retourne le chemin du fichier
	 * @param file le fichier dont on veut avoir le chemin
	 * @return le chemin du fichier
	 */
	public static String readFile(File file) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			return new String(encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
