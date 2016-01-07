package message;

import java.util.TreeMap;

import projet.FingerTable;
import projet.Key;
import projet.Row;

import akka.actor.ActorRef;


public class InitialisationMessage {
	Key key;
	FingerTable table;
	
	
	
	
	public InitialisationMessage(ActorRef actor) {
		super();
		TreeMap tree=new TreeMap();
		this.key =new Key(0);
		table=new FingerTable(this.key, actor, 0);
		Row r0 =new Row(0,	 0, actor, 0);
		r0.setSuccessor(actor);
		Row r1=new Row(0,	 1, actor, 0);
		r1.setSuccessor(actor);
		Row r2=new Row(0,	 2, actor, 0);
		r2.setSuccessor(actor);
		r2.setIdSuccessor(this.key.getValue());
		tree.put(0, r0);
		tree.put(1, r1);
		tree.put(2, r2);
		table.setTree(tree);
	}




	public Key getKey() {
		return key;
	}




	public void setKey(Key key) {
		this.key = key;
	}




	public FingerTable getTable() {
		return table;
	}




	public void setTable(FingerTable table) {
		this.table = table;
	}	
	
	
}
