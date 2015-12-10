package projet;

// implements Comparable<Key>
public class Key{
	int value;
	
	public int getValue(){
		return this.value;
	}

	public Key(int value) {
		super();
		this.value = value;
	}
	
	public String toString(){
		return ""+this.value ;
	}
	
	
}
