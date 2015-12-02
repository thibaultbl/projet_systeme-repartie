package pingpong;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;


public class PingActor extends UntypedActor {

	final ActorRef pongActor = getContext().actorOf(Props.create(PongActor.class),
			"ponger");

	public void onReceive(Object message) {
		if (message instanceof Start) {
			System.out.println("PingActor: Received Start message!");
			final PingMessage ping = new PingMessage();
			pongActor.tell(ping, getSelf());
			System.out.println("PingActor: Sends (\"" + ping
					+ "\") message!");
		} else if (message instanceof PongMessage) {
			final PongMessage pong = (PongMessage) message;
			System.out.println("PingActor: Received String(\"" + pong
					+ "\") message!");
		} else if (message instanceof Stop) {
			getContext().system().shutdown();
			System.out.println("PingActor: Actor System terminated!");
		}
	}
}