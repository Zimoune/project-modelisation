/**
 * Cette classe represente un vecteur
 */
package fr.minestate.utils;

public class Vecteur {
	private float x;
	private float y;
	private float z;


	/**
	 * Permet de definir un vecteur avec un point
	 * @param p
	 */
	public Vecteur(Point p){
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}


	/**
	 * Permet d'initialiser un vecteur a 0
	 */
	public Vecteur(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Permet d'initialiser les composantes du vecteur
	 * @param px
	 * @param py
	 * @param pz
	 */
	public Vecteur(float px,float py,float pz){
		this.x = px;
		this.y = py;
		this.z = pz;
	}


	/**
	 * Permet de retourner la composante X d'un vecteur
	 * @return
	 */
	public float getX(){
		return this.x;
	}

	/**
	 * Permet de retourner la composante Y d'un vecteur
	 * @return
	 */
	public float getY(){
		return this.y;
	}

	/**
	 * Permet de retourner la composante Z d'un vecteur
	 * @return
	 */
	public float getZ(){
		return this.z;
	}

	/**
	 * Retourne l'oppose d'un vecteur
	 * @param v
	 * @return
	 */
	public Vecteur opposeVecteur(Vecteur v) {
		return new Vecteur(v.getY(), v.getX(), v.getZ());
	}

	/**
	 * Retourne la normale a un vecteur (passe sous forme d'un tableau de points)
	 * @param p
	 * @return
	 */
	public Vecteur normale(Point[] p) { // le tableau de point contient les coordonnées d'une face
		Vecteur vue = new Vecteur(0,0,-1); //La vue étant fixe on la définie ici pour tester l'orientation des faces
		//Vecteur correspondant a  AB
		Vecteur n1 = new Vecteur(p[1].getX()-p[0].getX(),p[1].getY()-p[0].getY(),p[1].getZ()-p[0].getZ());
		//Vecteur correspondant a  AC
		Vecteur n2 = new Vecteur(p[2].getX()-p[0].getX(),p[2].getY()-p[0].getY(),p[2].getZ()-p[0].getZ());
		//Produit Vectoriel des 2 vecteurs prÃ©cÃ©dents : obtention d'un vecteur normal Ã  la face
		Vecteur n = produitVectoriel(n1,n2);
		if(produitScalaire(vue,n)<0) { //on verifie que le vecteur vue et la normale de la face soient bien orientes à l'opposé
			return n;				   //afin de calculer l'angle dans le bon sens pour le calcul de la lumiere
		} else {
			return produitVectoriel(n2,n1);
		}
	}	
	

	/**
	 * Calcule l'angle que forme un vecteur avec un autre et le retourne
	 * @param sun
	 * @param n
	 * @return
	 */
	public float calculAngle(Vecteur sun, Vecteur n) {
		return (float) (Math.acos(produitScalaire(sun, n)/(normeVecteur(sun)*normeVecteur(n))));
	}

	/**
	 * Calcule le barycentre d'une face passe sous la forme d'un tableau de points et le retourne
	 * @param p
	 * @return
	 */
	public Point barycentreFace(Point[] p) {
		float x = (p[0].getX()+p[1].getX()+p[2].getX())/3;
		float y = (p[0].getY()+p[1].getY()+p[2].getY())/3;
		float z = (p[0].getZ()+p[1].getZ()+p[2].getZ())/3;
		return new Point(x,y,z);
	}
	
	/**
	 * Calcule les coordonnées d'un point du vecteur en paramètre à une certaine longeure rationnelle et le retourne
	 * @param pBary
	 * @param normal
	 * @param longueur
	 * @return
	 */
	public Point pointDeVecteur(Point pBary, Vecteur normal, float longueur) {
		float x = pBary.getX() + normal.getX() * longueur;
		float y = pBary.getY() + normal.getY() * longueur;
		float z = pBary.getZ() + normal.getZ() * longueur;
		Point p = new Point(x,y,z);
		return p;
	}

	/**
	 * Calcule le produit vectoriel de deux vecteurs et le retourne sous forme de vecteur
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vecteur produitVectoriel(Vecteur v1, Vecteur v2) {
		float x = (v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY()) ;
		float y = (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ()) ;
		float z = (v1.getX() * v2.getY()) - (v1.getY() * v2.getX()) ;
		return new Vecteur(x,y,z);
	}

	/**
	 * Calcule le produit scalaire de deux vecteurs et le retourne sous forme de float
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float produitScalaire(Vecteur v1, Vecteur v2) {
		return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
	}

	/**
	 * Calcule la norme d'un vecteur et la retourne sous forme de float
	 * @param v
	 * @return
	 */
	public static float normeVecteur(Vecteur v) {
		return (float) Math.sqrt(Math.pow(v.getX(),2)+Math.pow(v.getY(),2)+Math.pow(v.getZ(),2));
	}


}