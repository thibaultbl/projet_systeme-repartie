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
		Row r=new Row(1,	1);
		FingerTable tree=new FingerTable();
		tree.getTree().put(1, r);
		tree.getTree().put(2, r);
		tree.getTree().put(3, r);
		

		System.out.println(tree.getTree().get(2));

	}

}
