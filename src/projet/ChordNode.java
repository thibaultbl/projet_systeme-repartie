package projet;

import akka.actor.ActorRef;

public class ChordNode implements KeyRoutable{
	private ActorRef actor;
	private Key key;
	
	public ChordNode(){
		
	}
	
	public ActorRef getRef(){
		return actor;
	}
	
	// définit l'acteur (key) lié à ce noeud
	public void setActor(ActorRef actor){
		this.actor=actor;
	}
	
	public Key getKey(){
		return this.key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	
}
