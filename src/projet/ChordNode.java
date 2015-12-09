package projet;

import akka.actor.ActorRef;

public class ChordNode implements KeyRoutable{
	private ActorRef actor;
	Key key;
	
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
	
	public Key getKey(){
		return this.key;
	}
}
