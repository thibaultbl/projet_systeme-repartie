package projet;

import java.util.TreeMap;

import akka.actor.ActorRef;

public class FingerTable  {
	
	TreeMap tree;
	
	public FingerTable(int key){
		for(int i=0;i<3;i++){
			this.tableEntry(key, i);
		}
	}
	
	public void tableEntry(int idNoeud,	int ordreLigne){
		Row r=new Row(idNoeud,	 ordreLigne);
		tree.put(ordreLigne, r);
	}

	public TreeMap getTree() {
		return tree;
	}

	public void setTree(TreeMap tree) {
		this.tree = tree;
	}
	
	public void afficher(){
		for(int i=0;i<3;i++){
			tree.get(i).toString();
		}
	}
}
