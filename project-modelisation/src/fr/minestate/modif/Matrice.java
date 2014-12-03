package fr.minestate.modif;

/**
 * Permet de creer une matrice
 * @author scta
 *
 */


public class Matrice {

	protected float[][] m;	
	private final float PRECISION = 0.000001f;
	
	/**
	 * Initialise une matrice avec un tableau
	 * @param m le tableau avec lequel on veut initialiser la matrice
	 */
	public Matrice(float[][] m) {
		this.m = m;
	}
	
	/**
	 * Permet d'initialiser une matrice
	 * @param rowCount le nombre de lignes que l'on souhaite
	 * @param colCount le nombre de colonnes que l'on souhaite
	 */
	public Matrice(int rowCount, int colCount) {
		this.m = new float[rowCount][colCount];
	}
	
	/**
	 * Permet de connaitre la hauteur d'une matrice
	 * @return la hauteur de la matrice
	 */
	public int tailleHaut() {
		return m.length;
	}
	
	/**
	 * Permet de connaitre la largeur d'une matrice
	 * @return la largeur d'une matrice
	 */
	public int tailleLarge() {
		return m[0].length;
	}
	
	/**
	 * 
	 * @param matrice
	 * @return la somme des deux matrices ou null si les matrices sont incompatibles
	 */
	public Matrice somme(Matrice matrice) {
		if (tailleHaut() != matrice.tailleHaut() || tailleLarge() != matrice.tailleLarge()) 
			return null;
		
		float[][] res = new float[tailleHaut()][tailleLarge()];
		
		for (int i = 0; i < tailleHaut(); i ++)
			for (int j = 0; j < tailleLarge(); j++)
				res[i][j] = retourneCase(i,j) + matrice.retourneCase(i, j);
				
		return new Matrice(res);
	}
	
	/**
	 * Verifie l'egalite entre deux matrices
	 * @param matrice la matrice que l'on souhaite comparer avec la matrice actuelle
	 * @return true si les matrices sont egales, false sinon
	 */
	public boolean equals(Matrice matrice) {
		if (tailleHaut() != matrice.tailleHaut() || tailleLarge() != matrice.tailleLarge()) 
			return false;
		
		for (int i = 0; i < tailleHaut(); i ++)
			for (int j = 0; j < tailleLarge(); j++)
				if (!close(retourneCase(i, j), matrice.retourneCase(i, j))) {
					return false;
				}			
				
		return true;
	}
	
	private boolean close(float f1, float f2) {
		return Math.abs(f1 - f2) < PRECISION;
	}
	
	/**
	 * Retourne un nombre dans la matrice
	 * @param ligne la ligne du nombre 
	 * @param col la colonne du nombre
	 * @return
	 */
	public float retourneCase(int ligne, int col) {
		return m[ligne][col];
	}
	
	/**
	 * Permet de changer un nombre dans la matrice
	 * @param ligne la ligne du nombre 
	 * @param col la colonne du nombre
	 * @param value la nouvelle valeur du nombre
	 * @return
	 */
	public void set(int ligne, int col, float value) {
		m[ligne][col] = value;
	}

	/**
	 * Permet d'effectuer un produit de matrice
	 * @param matrice la matrice a multiplier avec la matrice actuelle
	 * @return le produit des deux matrices
	 */
	public Matrice prod(Matrice matrice) {
		if (tailleLarge() != matrice.tailleHaut()) {
			return null;
		}
		
		Matrice res = new Matrice(new float[tailleHaut()][matrice.tailleLarge()]);
		
		for (int i = 0; i < res.tailleHaut(); i++) 
			for (int j = 0; j < res.tailleLarge(); j++) {
				float value = 0;
				for (int k = 0; k < tailleLarge(); k++) 
					value += retourneCase(i, k) * matrice.retourneCase(k, j);
				res.set(i, j, value);
			}
		
		return res;
	}
	
	/**
	 * Permet d'inverser une matrice
	 * @return : la matrice inverse
	 */
	public Matrice inversionMatrice() {
		if (tailleLarge() != tailleHaut())
			return null;
		
		Matrice res = initGauss();
		
		for (int i = 0; i < tailleLarge(); i++) {
			int j = i;
			while (j < res.tailleHaut() && close(res.retourneCase(j, i), 0)) {
				j++;
			}
			if (j == res.tailleHaut())
				return null;
			res.swap(j, i);
			res.multiplication(j, 1 / res.retourneCase(j, i));
			for (int k = 0; k < res.tailleHaut(); k++) {
				if (k == j)
					continue;
				res.combi(i, k, - res.retourneCase(k, i));
			}
		}
		return concludeGauss(res);
	}
	
	private Matrice initGauss() {
		Matrice res = new Matrice(new float[tailleHaut()][2 * tailleLarge()]);
		for (int i = 0; i < tailleHaut(); i ++) {
			for (int j = 0; j < tailleLarge(); j++) {
				res.set(i, j, retourneCase(i,j));
				if (i == j)
					res.set(i, j + tailleLarge(), 1);
			}
		}
		return res;
	}
	
	private Matrice concludeGauss(Matrice matrice) {
		Matrice res = new Matrice(new float[tailleHaut()][tailleLarge()]);
		for (int i = 0; i < tailleHaut(); i ++) {
			for (int j = 0; j < tailleLarge(); j++) {
				res.set(i, j, matrice.retourneCase(i, tailleLarge() + j));
			}
		}
		return res;		
	}
	
	/**
	 * Permet d'inverser deux lignes d'une matrice
	 * @param l : la 1ere ligne a inverser
	 * @param l2 : la 2eme ligne a inverser
	 */
	private void swap (int l, int l2) {
		float tmp;
		for (int i = 0; i < tailleLarge(); i++) {
			tmp = retourneCase(l, i);
			set(l, i, retourneCase(l2, i));
		}
	}
	
	
	/**
	 * Permer de multiplier une ligne par un nombre
	 * @param b : le nombre avec lequel on va multiplier la ligne
	 */
	private void multiplication(int ligne, float b) {
		for (int i = 0; i < tailleLarge(); i++) {
			set(ligne, i, retourneCase(ligne, i) * b);
		}
	}
	
	
	/**
	 * Permet de changer la valeur de l2 par b* ligne+l2
	 * @param l
	 * @param l2
	 * @param b
	 */
	private void combi(int l, int l2, float b) {
		for (int i = 0; i < tailleLarge(); i++) {
			set(l2, i, retourneCase(l2, i) + b * retourneCase(l, i));
		}
	}
	
	/**
	 * Permet de donner une representation de la matrice sous forme de chaine de caracteres
	 */
	public String toString() {
		if (m == null)
			return "null";
		String res = "";
		int i = 0;
		while (i < tailleHaut()) {
			int j = 0;
			while (j < tailleLarge ()) {
				res = res +  retourneCase(i, j) + "\t";
				j = j + 1;
			}
			
			res  = res +  "\n";
			
			i = i + +1;
		}
		return res;
	}
}
