package projet;

import message.GetKeyMessage;
import akka.actor.ActorRef;

public class ChordNode implements KeyRoutable{
	private ActorRef actor;
	private Key key;
	
	public ChordNode(ActorRef actor, Key key){
		this.actor=actor;
		this.key=key;
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
