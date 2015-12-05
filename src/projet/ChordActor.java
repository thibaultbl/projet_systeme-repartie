package projet;

import pingpong.PingMessage;
import akka.actor.UntypedActor;

public class ChordActor extends UntypedActor{
	int key;
	FingerTable table;
	
	public ChordActor() {
		super();
		table=new FingerTable(key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Object message) throws Exception {
		//if Research message received
		if (message instanceof RechercheMessage) {
			
		}
		//If the present ChordActor sent a request and the successive message cascade found the key's responsable
		else if(message instanceof TrouveMessage) {
			
		}
		else if(message instanceof TestFingerTable) {
			table.afficher();
		}
		else{
			unhandled(message);
		}
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
