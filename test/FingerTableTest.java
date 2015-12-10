package test;

import static org.junit.Assert.*;

import org.junit.Test;

import projet.FingerTable;
import projet.Key;

public class FingerTableTest {

	@Test
	public void tableEntryTest() {
		Key key1 = new Key(1);
		FingerTable finger=new FingerTable(key1);
		finger.tableEntry(1, 1);
		finger.tableEntry(1, 2);
		assertEquals("lowBound :2 highBound : 3 idNoeud : 1 ordreLigne : 1",finger.getTree().get(1).toString() );
		assertEquals("lowBound :3 highBound : 5 idNoeud : 1 ordreLigne : 2",finger.getTree().get(2).toString() );
	}

	
}
