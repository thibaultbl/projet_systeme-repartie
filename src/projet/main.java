package projet;

import pingpong.PingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * Initialisation
		 */
		ChordNode cn0=new ChordNode();
		ChordNode cn1=new ChordNode();
		ChordNode cn2=new ChordNode();
		ChordNode cn3=new ChordNode();
		ChordNode cn4=new ChordNode();
		ChordNode cn5=new ChordNode();
		ChordNode cn6=new ChordNode();
		ChordNode cn7=new ChordNode();
		
		/**
		 * Test avec 3 => Actor en 0 (clé : 6), 1 (clé : 1) et 3 (clé : 2)
		 */
		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef actor0 = system.actorOf(Props.create(ChordActor.class),"ChordActor0");
		final ActorRef actor1 = system.actorOf(Props.create(ChordActor.class),"ChordActor1");
		final ActorRef actor3 = system.actorOf(Props.create(ChordActor.class),"ChordActor3");
		SetKeyMessage setKey6=new SetKeyMessage(6);
		SetKeyMessage setKey1=new SetKeyMessage(1);
		SetKeyMessage setKey2=new SetKeyMessage(2);
		actor0.tell(setKey6, null);
		actor1.tell(setKey1, null);
		actor3.tell(setKey2, null);
		
		RechercheMessage recherche=new RechercheMessage(1);
		actor1.tell(recherche, actor0);
		
		/**
		 * Test de recherche en rentrant la clé recherché à la main
		 */
		
	}

}
