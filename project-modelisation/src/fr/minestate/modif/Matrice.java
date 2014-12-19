package fr.minestate.modif;

/**
 * Permet de creer une matrice
 * @author scta
 *
 */

import fr.minestate.exception.*;

public class Matrice {

	protected float[][] elem;
    public int nLignes;
    public int nColonnes;
	private final float PRECISION = 0.000001f;
	
	
	/**
     * Constructeur d'une matrice carree d'identite.
     */
    public Matrice( int n) {

	this( n, n);
	    for ( int i = 0; i < n; i++ )
		this.elem[i][i] = 1;
    }
	
	/** 
     * Constructeur d'une matrice a partir d'une existante.
     * @param m matrice a copier
     */
    public Matrice( Matrice m ) {
	nLignes = m.nLignes;
	nColonnes = m.nColonnes;
	elem = new float[nLignes][nColonnes];
	
	for ( int i = 0; i < nLignes; i++ ) {
	    for ( int j = 0; j < nColonnes; j++ ) {
		elem[i][j] = m.elem[i][j];
	    }
	}}
    
    
	/**
	 * Initialise une matrice avec un tableau
	 * @param elem le tableau avec lequel on veut initialiser la matrice
	 */
	
	public Matrice( float t[][] ) {
		nLignes = t.length;
		nColonnes = t[0].length;
		elem = new float[nLignes][nColonnes];
		int i = 0;
		int j = 0;
		
		try {
		    for ( i = 0; i < nLignes; i++ ) {
			for ( j = 0; j < nColonnes; j++ ) {
			    elem[i][j] = t[i][j];
			} 		
		    }

		} catch ( ArrayIndexOutOfBoundsException e ) {
		    System.out.println( "Erreur dans l'initialisation de matrice:" );
		    System.out.println( "l'element (" + i + "," + j +
					") n'a pas pu etre trouve." );
		    return;
		}

	    }
	
	/**
	 * Permet d'initialiser une matrice
	 * @param l le nombre de lignes que l'on souhaite
	 * @param c le nombre de colonnes que l'on souhaite
	 */
	
	public Matrice(int l, int c) {
		nLignes = l;
		nColonnes = c;
		elem = new float[nLignes][nColonnes];
		for (int i = 0; i < nLignes; i++) {
			for (int j = 0; j < nColonnes; j++) {
				elem[i][j] = 0;
			}
		}
	}
	
	/**
     * Addition de matrices element par element.
     * @param b matrice a ajouter
     * @exception IncompatibleSizeException quand les dimensions ne 
     * correspondent pas.
     * @return 
      *
     */
    public Matrice add( Matrice b ) throws IncompatibleSizeException {
	// verification des dimensions 
	if ( ( b.nLignes != nLignes ) || ( b.nColonnes != nColonnes ) )
	    incompatibilite( this, b, "addTo", "<>" );
	
	float[][] res = new float[nLignes][nColonnes];
	
	for ( int i = 0; i < nLignes; i++ ) {
	    for ( int j = 0; j < nColonnes; j++ ) {
	    	res[i][j] = retourneCase(i,j) + b.retourneCase(i, j);
	    }
	}
	return new Matrice(res);
    }
	
	/**
	 * Verifie l'egalite entre deux matrices
	 * @param matrice la matrice que l'on souhaite comparer avec la matrice actuelle
	 * @return true si les matrices sont egales, false sinon
	 */
	public boolean equals(Matrice matrice) {
		if (nLignes != matrice.nLignes || nColonnes != matrice.nColonnes)
			return false;
		
		for (int i = 0; i < nLignes; i ++)
			for (int j = 0; j < nColonnes; j++)
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
		return elem[ligne][col];
	}
	
	/**
	 * Permet de changer un nombre dans la matrice
	 * @param ligne la ligne du nombre 
	 * @param col la colonne du nombre
	 * @param value la nouvelle valeur du nombre
	 * @return
	 */
	public void set(int ligne, int col, float value) {
		elem[ligne][col] = value;
	}

	
	/**
     * Multiplication standard de matrices.
     * @param b matrice a multiplier a droite
     * @return la matrice resultat
     * @exception IncompatibleSizeException quand les dimensions ne 
     * correspondent pas
     */
    public Matrice multiply(Matrice b )
	throws IncompatibleSizeException 
    {
	// verification des dimensions 
	if ( this.nColonnes != b.nLignes ) 
	    incompatibilite( this, b, "multiply", "et" );
	
	float somme;
	
	Matrice c = new Matrice( this.nLignes, b.nColonnes );
	for ( int i = 0; i < this.nLignes; i++ ) {
	    for ( int j = 0; j < b.nColonnes; j++ ) {
		somme = 0;
		for ( int k = 0; k < this.nColonnes; k++ ) {
		    somme += this.elem[i][k] * b.elem[k][j];
		}
		c.elem[i][j] = somme;
	    }
	}
	
	return c;
    }
	
    
    /**
     * Resolution d'un systeme lineaire par la methode de Gauss avec choix du
     * pivot max.
     * @param a une matrice carree
     * @param b une matrice
     * @return la solution de a*x = b, soit: a^(-1)*b.
     * @throws Exception 
     * 
     */
    public static Matrice solution( Matrice a, Matrice b )
	throws Exception {
	
	// verification des dimensions
	if ( a.nColonnes != a.nLignes ) {
	    String message =
		new String( "Matrice pas carree dans solution(): " +
			a.nLignes + "<>" + a.nColonnes );
	    throw new IncompatibleSizeException( message );
	}

	if ( a.nColonnes != b.nLignes ) {
	    incompatibilite( a, b, "inverse()", "et" );
	}

	int N = a.nLignes;
	Matrice aa = new Matrice( a );
	Matrice x = new Matrice( b );
	// descente; r+1 est le numero d'etape et de colonne a eliminer
	for ( int r = 0; r < N; r++ ) {
	    // recherche du pivot
	    double pmax = Math.abs( aa.elem[r][r] );
	    int imax = r;
	    for ( int i = r+1; i < N; i++ ) {
		if ( Math.abs( aa.elem[i][r] ) > pmax ) {
		    imax = i;
		    pmax = Math.abs( aa.elem[i][r] );
		}
	    }
	    if ( pmax == 0.0 ) {
		throw( new Exception ( "Matrice non inversible" ) );
	    }
	    // permutation des lignes de a et b
	    float tmp;
	    for ( int j = r; j < N; j++ ) {
		tmp = aa.elem[r][j];
		aa.elem[r][j] = aa.elem[imax][j];
		aa.elem[imax][j] = tmp;
	    }
	    for ( int j = 0; j < x.nColonnes; j++ ) {
		tmp = x.elem[r][j];
		x.elem[r][j] = x.elem[imax][j];
		x.elem[imax][j] = tmp;
	    }
	    // combinaison lineaire
	    double pivot;
	    for ( int i = r+1; i < N; i++ ) {
		pivot = - aa.elem[i][r] / aa.elem[r][r];
		for ( int j = r; j < N; j++ ) {
		    aa.elem[i][j] += pivot * aa.elem[r][j];
		}
		for ( int j = 0; j < x.nColonnes; j++ ) {
		    x.elem[i][j] += pivot * x.elem[r][j];
		}
	    }
	}
	// Fin de la descente. Remontee.
	for ( int r = N-1; r >= 0; r-- ) {
	    for ( int i = 0; i < r; i++ ) {
		double pivot = - aa.elem[i][r] / aa.elem[r][r];
		for ( int j = 0; j < x.nColonnes; j++ ) {
		    x.elem[i][j] += pivot * x.elem[r][j];
		}
	    }
	    for ( int j = 0; j < x.nColonnes; j++ ) {
		x.elem[r][j] /= aa.elem[r][r];
	    }
	}
	return x;
    }

    /**
     * Inversion d'une matrice par la methode de Gauss avec choix du
     * pivot max.
     * @return la matrice a^(-1)
     * @exception IncompatibleSizeException si la matrice n'est pas carree
     */
    public Matrice inverse( )
	throws Exception, IncompatibleSizeException
    {
	// verification des dimensions
	if ( nColonnes != nLignes ) {
	    String message =
		new String( "Matrice pas carree dans inverse(): " +
			    nLignes + "<>" + nColonnes );
	    throw new IncompatibleSizeException( message );
	}

	Matrice b = new Matrice( nColonnes);
	Matrice x = solution( this, b );

	return x;
    }
	
	 /**
     * Methode de synthese pour les messages d'erreur dus aux
     * incompatibilites de dimensions.
     * @param a matrice en cause
     * @param b autre matrice en cause
     * @param fonction nom de la methode ayant cause le probleme
     * @param type texte explicatif du motif de l'erreur
     * @exception IncompatibleSizeException dans tous les cas
     */
    private static void incompatibilite( Matrice a, Matrice b, 
					 String fonction, String type ) 
	throws IncompatibleSizeException
    {
	String message =
	    new String( "Dimensions de matrices incompatibles dans "
			+ fonction + "(): (" +
			a.nLignes + "x" + a.nColonnes +
			") " + type + " (" +
			b.nLignes + "x" + b.nColonnes + ")" );
	throw new IncompatibleSizeException( message );
    }
	
	/**
	 * Permet de donner une representation de la matrice sous forme de chaine de caracteres
	 */
	public String toString() {
		if (elem == null)
			return "null";
		String res = "";
		int i = 0;
		while (i < nLignes) {
			int j = 0;
			while (j < nColonnes) {
				res = res +  retourneCase(i, j) + "\t";
				j = j + 1;
			}
			
			res  = res +  "\n";
			
			i = i + +1;
		}
		return res;
	}
}
