package message;

import akka.actor.ActorRef;

public class PredecessorFoundMessage {
	private int idRechercher;
	private ActorRef sucessor;
	private int idSender;
	private int idSucessor;
	
	public PredecessorFoundMessage(ActorRef sucessor, int idRechercher, int idSender, int idSucessor) {
		this.sucessor=sucessor;
		this.idRechercher=idRechercher;
		this.idSender=idSender;
		this.idSucessor=idSucessor;
	}
	
	public ActorRef getSucessor() {
		return sucessor;
	}
	
	public int getIdRechercher() {
		return idRechercher;
	}
	
	public int getIdSender() {
		return idSender;
	}
	
	public int getIdSucessor() {
		return idSucessor;
	}

}
