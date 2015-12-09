package projet;

import akka.actor.ActorRef;

public class ChordNode {
	private ActorRef actor;
	int key;
	
	public ActorRef getRef(){
		return actor;
	}
	
	// définit l'acteur (key) lié à ce noeud
	public void setActor(ActorRef actor){
		this.actor=actor;
	}
	
	
	//trouver le successeur d'une valeur k
	public void findSuccessor(int k){
		//Hashtable.nbNodes;
	}
}
