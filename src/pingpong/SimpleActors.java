package pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SimpleActors {

	public static void main(String[] args) {
		System.out.println("Actor System started!");
		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef pingActor = system.actorOf(Props.create(PingActor.class),
				"pinger");
		GuiApp1 gui = new GuiApp1(pingActor);
//		pingActor.tell(new Start(), pingActor);
	}
}