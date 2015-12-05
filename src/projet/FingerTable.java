package projet;

import java.util.TreeMap;

import akka.actor.ActorRef;

public class FingerTable  {
	
	TreeMap tree;
	
	public void tableEntry(ActorRef successor, int idNoeud,	int ordreLigne){
		Row r=new Row(successor,  idNoeud,	 ordreLigne);
		tree.put(1, r);
	}

	public TreeMap getTree() {
		return tree;
	}

	public void setTree(TreeMap tree) {
		this.tree = tree;
	}
}
