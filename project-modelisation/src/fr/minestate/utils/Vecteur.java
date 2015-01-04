package fr.minestate.utils;

public class Vecteur {
	private float x;
	private float y;
	private float z;


	public Vecteur(Point p){
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}


	public Vecteur(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vecteur(float px,float py,float pz){
		this.x = px;
		this.y = py;
		this.z = pz;
	}


	public float getX(){
		return this.x;
	}

	public float getY(){
		return this.y;
	}

	public float getZ(){
		return this.z;
	}

	public Vecteur opposeVecteur(Vecteur v) {
		return new Vecteur(v.getY(), v.getX(), v.getZ());
	}

	public Vecteur normale(Point[] p) {
		Vecteur vue = new Vecteur(0,0,-1);
		//Vecteur correspondant Ã  AB
		Vecteur n1 = new Vecteur(p[1].getX()-p[0].getX(),p[1].getY()-p[0].getY(),p[1].getZ()-p[0].getZ());
		//Vecteur correspondant Ã  AC
		Vecteur n2 = new Vecteur(p[2].getX()-p[0].getX(),p[2].getY()-p[0].getY(),p[2].getZ()-p[0].getZ());
		//Produit Vectoriel des 2 vecteurs prÃ©cÃ©dents : obtention d'un vecteur normal Ã  la face
		//On vérifie que la caméra observe le bon côté de la face
		Vecteur n = produitVectoriel(n1,n2);
		if(bonCote(vue, n)) {
			return n;
		} else {
			return produitVectoriel(n2,n1);
		}
	}

	private boolean bonCote(Vecteur vue, Vecteur v) {
		if (produitScalaire(vue,v)>=0)
			return false;
		else return true;
	}

	public float calculAngle(Vecteur sun, Vecteur n) {
		return (float) (Math.acos(produitScalaire(sun, n)/(normeVecteur(sun)*normeVecteur(n))));
	}

	public Point barycentreFace(Point[] p) {
		float x = (p[0].getX()+p[1].getX()+p[2].getX())/3;
		float y = (p[0].getY()+p[1].getY()+p[2].getY())/3;
		float z = (p[0].getZ()+p[1].getZ()+p[2].getZ())/3;
		return new Point(x,y,z);
	}
	
	public Point pointDeVecteur(Point pBary, Vecteur normal, float longueur) {
		float x = pBary.getX() + normal.getX() * longueur;
		float y = pBary.getY() + normal.getY() * longueur;
		float z = pBary.getZ() + normal.getZ() * longueur;
		Point p = new Point(x,y,z);
		return p;
	}

	public static Vecteur produitVectoriel(Vecteur v1, Vecteur v2) {
		float x = (v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY()) ;
		float y = (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ()) ;
		float z = (v1.getX() * v2.getY()) - (v1.getY() * v2.getX()) ;
		return new Vecteur(x,y,z);
	}

	public static float produitScalaire(Vecteur v1, Vecteur v2) {
		return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
	}

	public static float normeVecteur(Vecteur v) {
		return (float) Math.sqrt(Math.pow(v.getX(),2)+Math.pow(v.getY(),2)+Math.pow(v.getZ(),2));
	}


}