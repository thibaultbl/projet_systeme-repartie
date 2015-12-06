package projet;

import java.util.ArrayList;
import java.util.List;

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
		SetKeyMessage setKey0=new SetKeyMessage(0);
		SetKeyMessage setKey1=new SetKeyMessage(1);
		SetKeyMessage setKey3=new SetKeyMessage(3);
		actor0.tell(setKey0, null);
		actor1.tell(setKey1, null);
		actor3.tell(setKey3, null);
		
		RechercheMessage recherche=new RechercheMessage(1);
		actor1.tell(recherche, actor0);
		
		/**
		 * On ajoute l'ensemble des clés au successeur correspondant
		 */
		//Pour l'acteur 3
		List<Integer> liste=new ArrayList<Integer>();
		liste.add(2);
		AddOthersKeysMessage addOthers=new AddOthersKeysMessage(liste);
		actor3.tell(addOthers, null);
		AfficherCleMessage afficher=new AfficherCleMessage();
		actor3.tell(afficher, null);
		
		//pour l'acteur 0
		List<Integer> liste2=new ArrayList<Integer>();
		for(int i=4;i<8;i++){
			liste2.add(i);
		}
		AddOthersKeysMessage addOthers2=new AddOthersKeysMessage(liste2);
		actor0.tell(addOthers2, null);;
		actor0.tell(afficher, null);
		
		
		
	}

}
