package projet;

import java.util.TreeMap;

import akka.actor.ActorRef;

public class FingerTable  {
	public static int NROW=3;
	TreeMap<Integer, Row> tree;


	public FingerTable(Key key, ActorRef actor, int actorId){
		tree=new TreeMap<Integer, Row>();
		for(int i=0;i<3;i++){
			this.tableEntry(key.getValue(), i, actor, actorId);
		}
	}
	
	public void tableEntry(int idNoeud,	int ordreLigne, ActorRef actor, int actorId){
		Row r=new Row(idNoeud,	 ordreLigne, actor, actorId);
		tree.put(ordreLigne, r);
	}

	public TreeMap<Integer, Row> getTree() {
		return tree;
	}

	public void setTree(TreeMap tree) {
		this.tree = tree;
	}

	
	public void afficher(){
		for(int i=0;i<3;i++){
			System.out.println(tree.get(i).toString());
		}
	}
	
	
}
