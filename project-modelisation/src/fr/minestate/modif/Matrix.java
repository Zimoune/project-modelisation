package fr.minestate.modif;

/**
 * Permet de creer une matrice
 * @author scta
 *
 */


public class Matrix {

	protected float[][] matrix;	
	private final float PRECISION = 0.000001f;
	
	/**
	 * Initialise une matrice avec un tableau
	 * @param matrix le tableau avec lequel on veut initialiser la matrice
	 */
	public Matrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Permet d'initialiser une matrice
	 * @param rowCount le nombre de lignes que l'on souhaite
	 * @param colCount le nombre de colonnes que l'on souhaite
	 */
	public Matrix(int rowCount, int colCount) {
		this.matrix = new float[rowCount][colCount];
	}
	
	/**
	 * Permet de connaitre la hauteur d'une matrice
	 * @return la hauteur de la matrice
	 */
	public int height() {
		return matrix.length;
	}
	
	/**
	 * Permet de connaitre la largeur d'une matrice
	 * @return la largeur d'une matrice
	 */
	public int width() {
		return matrix[0].length;
	}
	
	/**
	 * 
	 * @param m
	 * @return la somme des deux matrices ou null si les matrices sont incompatibles
	 */
	public Matrix add(Matrix m) {
		if (height() != m.height() || width() != m.width()) 
			return null;
		
		float[][] out = new float[height()][width()];
		
		for (int i = 0; i < height(); i ++)
			for (int j = 0; j < width(); j++)
				out[i][j] = get(i,j) + m.get(i, j);
				
		return new Matrix(out);
	}
	
	/**
	 * Verifie l'egalite entre deux matrices
	 * @param m la matrice que l'on souhaite comparer avec la matrice actuelle
	 * @return true si les matrices sont egales, false sinon
	 */
	public boolean equals(Matrix m) {
		if (height() != m.height() || width() != m.width()) 
			return false;
		
		for (int i = 0; i < height(); i ++)
			for (int j = 0; j < width(); j++)
				if (!closeEnough(get(i, j), m.get(i, j))) {
					return false;
				}			
				
		return true;
	}
	
	private boolean closeEnough(float f1, float f2) {
		return Math.abs(f1 - f2) < PRECISION;
	}
	
	/**
	 * Retourne un nombre dans la matrice
	 * @param row la ligne du nombre 
	 * @param col la colonne du nombre
	 * @return
	 */
	public float get(int row, int col) {
		return matrix[row][col];
	}
	
	/**
	 * Permet de changer un nombre dans la matrice
	 * @param row la ligne du nombre 
	 * @param col la colonne du nombre
	 * @param value la nouvelle valeur du nombre
	 * @return
	 */
	public void set(int row, int col, float value) {
		matrix[row][col] = value;
	}

	/**
	 * Permet d'effectuer un produit de matrice
	 * @param m la matrice a multiplier avec la matrice actuelle
	 * @return le produit des deux matrices
	 */
	public Matrix prod(Matrix m) {
		if (width() != m.height()) {
			return null;
		}
		
		Matrix out = new Matrix(new float[height()][m.width()]);
		
		for (int i = 0; i < out.height(); i++) 
			for (int j = 0; j < out.width(); j++) {
				float value = 0;
				for (int k = 0; k < width(); k++) 
					value += get(i, k) * m.get(k, j);
				out.set(i, j, value);
			}
		
		return out;
	}
	
	/**
	 * Permet d'inverser une matrice
	 * @return : la matrice inverse
	 */
	public Matrix invert() {
		if (width() != height())
			return null;
		
		Matrix out = initGauss();
		
		for (int i = 0; i < width(); i++) {
			int j = i;
			while (j < out.height() && closeEnough(out.get(j, i), 0)) {
				j++;
			}
			if (j == out.height())
				return null;
			out.swap(j, i);
			out.multiplication(j, 1 / out.get(j, i));
			for (int k = 0; k < out.height(); k++) {
				if (k == j)
					continue;
				out.linearCombination(i, k, - out.get(k, i));
			}
		}
		return concludeGauss(out);
	}
	
	private Matrix initGauss() {
		Matrix out = new Matrix(new float[height()][2 * width()]);
		for (int i = 0; i < height(); i ++) {
			for (int j = 0; j < width(); j++) {
				out.set(i, j, get(i,j));
				if (i == j)
					out.set(i, j + width(), 1);
			}
		}
		return out;
	}
	
	private Matrix concludeGauss(Matrix m) {
		Matrix out = new Matrix(new float[height()][width()]);
		for (int i = 0; i < height(); i ++) {
			for (int j = 0; j < width(); j++) {
				out.set(i, j, m.get(i, width() + j));
			}
		}
		return out;		
	}
	
	/**
	 * Permet d'inverser deux lignes d'une matrice
	 * @param row1 : la 1ere ligne a inverser
	 * @param row2 : la 2eme ligne a inverser
	 */
	private void swap (int row1, int row2) {
		float tmp;
		for (int i = 0; i < width(); i++) {
			tmp = get(row1, i);
			set(row1, i, get(row2, i));
			set(row2, i, tmp);
		}
	}
	
	
	/**
	 * Permer de multiplier une ligne par un nombre
	 * @param row : la ligne a multiplier
	 * @param factor : le nombre avec lequel on va multiplier la ligne
	 */
	private void multiplication(int row, float factor) {
		for (int i = 0; i < width(); i++) {
			set(row, i, get(row, i) * factor);
		}
	}
	
	
	/**
	 * Permet de changer la valeur de row2 par factor* row+row2
	 * @param row1
	 * @param row2
	 * @param factor
	 */
	private void linearCombination(int row1, int row2, float factor) {
		for (int i = 0; i < width(); i++) {
			set(row2, i, get(row2, i) + factor * get(row1, i));
		}
	}
	
	/**
	 * Permet de donner une representation de la matrice sous forme de chaine de caracteres
	 */
	public String toString() {
		if (matrix == null)
			return "null";
		String out = "";
		for (int i = 0; i < height(); i++) {
			for (int j = 0; j < width(); j++) {
				out += get(i, j) + "\t";
			}
			out += "\n";
		}
		return out;
	}
}
