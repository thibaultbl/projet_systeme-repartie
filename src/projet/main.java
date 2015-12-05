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
		final ActorRef actor = system.actorOf(Props.create(ChordActor.class),"ChordActor");
		TestFingerTable test=new TestFingerTable();
		actor.tell(test, null);

	}

}
