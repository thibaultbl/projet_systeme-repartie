package pingpong;

import akka.actor.UntypedActor;

public class PongActor extends UntypedActor {

	public void onReceive(Object message) throws Exception {
		if (message instanceof PingMessage) {
			final PingMessage ping = (PingMessage) message;
			System.out.println("PongActor: Received String(\"" + ping
					+ "\") message!");
			final PongMessage pong = new PongMessage();
			this.getSender().tell(pong,this.self());
			System.out.println("PongActor: Send String(\"" + pong
					+ "\") message!");
		}
	}
}