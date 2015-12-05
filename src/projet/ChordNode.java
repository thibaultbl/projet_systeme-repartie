package projet;

import akka.actor.ActorRef;

public class ChordNode implements Hashtable{
	private ActorRef actor;
	
	public int getKey(){
		actor.tell(msg, sender);
	}
	public ActorRef getRef(){
		
	}
	
	// définit l'acteur (key) lié à ce noeud
	public void setActor(ActorRef actor){
		this.actor=actor;
	}
	
	
	//trouver le successeur d'une valeur k
	public void findSuccessor(int k){
		Hashtable.nbNodes
	}
}
