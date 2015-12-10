package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pingpong.PingActor;
import projet.Row;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class RowTest {

	@Test
	public void calculIntervalTest() {
		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef actor = system.actorOf(Props.create(PingActor.class),"test");
		Row r=new Row(1,	1);
		assertEquals(2,r.getLowBound() );
		assertEquals(3,r.getHighBound() );
	}
}
