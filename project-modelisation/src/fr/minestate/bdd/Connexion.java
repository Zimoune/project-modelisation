package fr.minestate.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Permet d'etablir la connexion a la base SQLITE
 * @author scta
 *
 */
public class Connexion {
	private String DBPath = "src/fr/minestate/bdd/Database.db";
	private Connection connexion = null;
	private PreparedStatement pstmt;

	
	/**
	 * Permet d'etablir la connexion
	 */
	public Connexion() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.connexion = this.getConnexion();
	}

	/**
	 * Permet de renvoyer la connexion
	 * @return la connexion a la bdd sqlite
	 */
	public Connection getConnexion() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	/**
	 * Permet de fermer la connexion a la bdd sqlite
	 */
	public void closeConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public int getUpdateStatement(String query) {
		int i = 0;
		this.connexion = this.getConnexion();
		try {
			this.pstmt = this.connexion.prepareStatement(query);
			i = this.pstmt.executeUpdate();
			this.pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public ResultSet getExecuteStatement(String query) {
		this.connexion = this.getConnexion();
		ResultSet r = null;
		try {
			this.pstmt = this.connexion.prepareStatement(query);
			r = this.pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * Permet d'ajouter un objet dans la bdd sqlite
	 * @param name : le nom de l'objet a ajouter
	 * @param link : le lien de l'objet a ajouter
	 * @return le nombre de mise a jour effectue
	 */
	public int ajouterObjet(String name, String link) {
		return this.getUpdateStatement("INSERT INTO objets(nom, lien) VALUES('"+ name + "','" + link + "');");
	}
	
	/**
	 * Permet d'afficher tous les objets presents dans la bdd 
	 */
	public void afficherListeObjet(){
		ResultSet rs = this.getExecuteStatement("SELECT nom, lien FROM objets");
		try {
			while(rs.next()){
				System.out.println("Nom: "+rs.getString("nom")+"\n Lien: "+rs.getString("lien"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getObjectsByLike(String word){
		Map<String, String> mapObjet = new HashMap<String, String>();
		ResultSet rs = this.getExecuteStatement("SELECT nom, lien FROM objets WHERE nom LIKE '%"+word+"%'");
		try {
			while(rs.next()){
				mapObjet.put(rs.getString("nom"), rs.getString("lien"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapObjet;
	}
	
	/**
	 * Permet de renvoyer les objets sous la forme d'une map
	 * @return une map <String,String> (nomFichier, cheminFichier)
	 */
	public Map<String, String> getListObjet(){
		Map<String, String> mapObjet = new HashMap<String, String>();
		ResultSet rs = this.getExecuteStatement("SELECT nom, lien FROM objets");
		try {
			while(rs.next()){
				mapObjet.put(rs.getString("nom"), rs.getString("lien"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapObjet;
	}
	
	/**
	 * Permet de recuperer le chemin d'un objet specifie
 	 * @param objet : l'objet dont on veut connaitre le chemin
	 * @return le chemin de l'objet dans la bdd
	 */
	public String getCheminObjet(String objet){
		ResultSet rs = this.getExecuteStatement("SELECT lien FROM objets WHERE nom='"+objet+"';");
		String path = "";
		try {
			if(rs.next()){
				path = rs.getString("lien");
			}
			else{
				System.out.println("objet introuvable");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * Permet de supprimer un BoxLayout.PAGE_AXISobjet dans la bdd
	 * @param objet : l'objet a supprimer
	 */
	public void supprimerObjet(String objet){
		int rs = this.getUpdateStatement("DELETE FROM objets WHERE nom='"+objet+"';");
		System.out.println("Result: "+rs);
		if(rs == 0){
			System.out.println("Objet non existant");
		}
		else{
			System.out.println("Suppression ok");
		}
	}
	
	public Map<String, String> getInfo(String nom){
		Map<String, String> mapObjet = new HashMap<String, String>();
		ResultSet rs = this.getExecuteStatement("SELECT nom, lien FROM objets WHERE nom = '"+nom+"';");
		try {
			while(rs.next()){
				mapObjet.put(rs.getString("lien"), rs.getString("lien"));
				mapObjet.put(rs.getString("nom"), rs.getString("nom"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapObjet;
	}

}
