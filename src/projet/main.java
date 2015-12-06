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
		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef actor1 = system.actorOf(Props.create(ChordActor.class),"ChordActor1");
		final ActorRef actor2 = system.actorOf(Props.create(ChordActor.class),"ChordActor2");
		SetKeyMessage setKey=new SetKeyMessage(1);
		TestFingerTable test=new TestFingerTable();
		actor1.tell(test, null);
		actor1.tell(setKey, null);
		actor1.tell(test, null);
		
		

	}

}
