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
			System.out.println("recherche message reçu");
			final RechercheMessage rechercheMessage = (RechercheMessage) message;
			if(rechercheMessage.getCleRecherche()==this.key){
				System.out.println("clé trouvée => envoi trouveMessage");
				this.getSender().tell(new TrouveMessage(),this.self());
			}
			else{
				
			}
		}
		//If the present ChordActor sent a request and the successive message cascade found the key's responsable
		else if(message instanceof TrouveMessage) {
			final TrouveMessage trouveMessage = (TrouveMessage) message;
			System.out.println("reçu trouvé message ");
			this.getSender().tell(new TestFingerTable(), this.self());
		}
		else if(message instanceof TestFingerTable) {
			table.afficher();
		}
		else if(message instanceof SetKeyMessage) {
			final SetKeyMessage setKey = (SetKeyMessage) message;
			this.key=setKey.getKey();
			table=new FingerTable(key);
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
