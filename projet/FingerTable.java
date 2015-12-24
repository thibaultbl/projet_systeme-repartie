package projet;

import java.util.TreeMap;

public class FingerTable  {
	public static int NROW=3;
	TreeMap<Integer, Row> tree;

	public FingerTable() {
		super();
	}

	public FingerTable(Key key){
		tree=new TreeMap<Integer, Row>();
		for(int i=0;i<3;i++){
			this.tableEntry(key.getValue(), i);
		}
	}
	
	public void tableEntry(int idNoeud,	int ordreLigne){
		Row r=new Row(idNoeud,	 ordreLigne);
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
