package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import projet.ChordActor;
import projet.FingerTable;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class FingerTableTest {

	@Test
	public void tableEntryTest() {
		FingerTable finger=new FingerTable(1);
		finger.tableEntry(1, 1);
		finger.tableEntry(1, 2);
		assertEquals("lowBound :2 highBound : 3 idNoeud : 1 ordreLigne : 1",finger.getTree().get(1).toString() );
		assertEquals("lowBound :3 highBound : 5 idNoeud : 1 ordreLigne : 2",finger.getTree().get(2).toString() );
	}

	
}
