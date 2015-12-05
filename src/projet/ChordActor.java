package projet;

import akka.actor.UntypedActor;

public class ChordActor extends UntypedActor{
	int key;
	
	public ChordActor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
