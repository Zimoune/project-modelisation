package fr.minestate.modif;

/**
 * Permet de definir une Homothetie
 */
public class Homothetie extends Modification {

	private float facteur;
	
	/**
	 * Permet de definir une homothetie
	 * @param matrix un tableau de float qui represente une matrice
	 */
	public Homothetie(float[][] matrix) {
		super(matrix);
	}

	/**
	 * Permet de definir une homothetie selon un facteur
	 * @param facteur le facteur souhaite pour l'homothetie
	 */
	public Homothetie(int facteur) {
		super(new float[4][4]);
		setfacteur(facteur);
	}

	/**
	 * Permet de changer le facteur de l'homothetie
	 * @param facteur le nouveau facteur souhaite
	 */
	public void setfacteur(float facteur) {
		this.facteur = facteur;
		if (this.facteur < 0.1) 
			this.facteur = 0.1f;
		updateMatrix();		
	}

	/**
	 * Permet de renvoyer le facteur de l'homothetie
	 * @return le facteur de l'homothetie
	 */
	public float getfacteur() {
		return facteur;
	}
	
	/**
	 * Permet de mettre a jour la matrice d'homothetie selon le facteur
	 */
	protected void updateMatrix() {
		this.m = new float [][] {
				{facteur, 0, 0, 0},
				{0, facteur, 0, 0},
				{0, 0, facteur, 0},
				{0, 0, 0, 1}};
	}

	/**
	 * Permet d'ajouter un facteur pour l'homothetie
	 * @param facteur le facteur a ajouter
	 */
	public void addfacteur(float facteur) {
		setfacteur(this.facteur * (1 + (facteur / 100)));
	}
}
