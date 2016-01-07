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
		Row r=new Row(1,	1, actor, 0);
		assertEquals(3,r.getLowBound() );
		assertEquals(5,r.getHighBound() );
	}
	
	@Test
	public void inRangeTest() {
		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef actor = system.actorOf(Props.create(PingActor.class),"test");
		Row r=new Row(1,	1, actor, 0);
		assertEquals(false,r.inRange(2) );
		assertEquals(true,r.inRange(3) );
		assertEquals(true,r.inRange(5) );
		r=new Row(3,	2, actor, 0);
		assertEquals(false,r.inRange(6) );
		assertEquals(false,r.inRange(4) );
		assertEquals(true,r.inRange(3) );
		assertEquals(true,r.inRange(7) );
		assertEquals(true,r.inRange(0) );
		assertEquals(true,r.inRange(2) );
		
		r=new Row(3,	0, actor, 0);
		assertEquals(false,r.inRange(2) );


		//assertEquals(3,r.getHighBound() );
	}
}
