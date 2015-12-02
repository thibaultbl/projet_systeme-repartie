package projet;

import akka.actor.ActorRef;

public class Row  {
	private int lowBound;
	private int highBound;
	private ActorRef successor;
	private int idNoeud;
	private int ordreLigne;
	
	public void calculInterval(){
		lowBound=((idNoeud+(int)java.lang.Math.pow(2, ordreLigne-1)) % ((int)java.lang.Math.pow(2, 8)) ) ;
		highBound=((idNoeud+(int)java.lang.Math.pow(2, ordreLigne)) % (int)java.lang.Math.pow(2, 8));
	}
	
	public Row(ActorRef successor, int idNoeud,	int ordreLigne) {
		super();
		this.successor = successor;
		this.idNoeud = idNoeud;
		this.ordreLigne = ordreLigne;
		this.calculInterval();
	}

	public int getLowBound() {
		return lowBound;
	}

	public void setLowBound(int lowBound) {
		this.lowBound = lowBound;
	}

	public int getHighBound() {
		return highBound;
	}

	public void setHighBound(int highBound) {
		this.highBound = highBound;
	}

	public ActorRef getSuccessor() {
		return successor;
	}

	public void setSuccessor(ActorRef successor) {
		this.successor = successor;
	}

	public int getIdNoeud() {
		return idNoeud;
	}

	public void setIdNoeud(int idNoeud) {
		this.idNoeud = idNoeud;
	}

	public int getOrdreLigne() {
		return ordreLigne;
	}

	public void setOrdreLigne(int ordreLigne) {
		this.ordreLigne = ordreLigne;
	}
	
}
