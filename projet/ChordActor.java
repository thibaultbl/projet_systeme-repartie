package projet;

import java.util.ArrayList;
import java.util.List;

import akka.actor.UntypedActor;

public class ChordActor extends UntypedActor{
	Key key;
	List<Key> othersKeys=new ArrayList<Key>();
	FingerTable table;
	
	public ChordActor() {
		super();
	}

	public void createFingerTable(){
		table=new FingerTable(this.key);
	}
	@Override
	public void onReceive(Object message) throws Exception {
		//if Research message received
		if (message instanceof RechercheMessage) {
			final RechercheMessage rechercheMessage = (RechercheMessage) message;
			if(rechercheMessage.getCleRecherche()==this.key.getValue()){
				this.getSender().tell(new TrouveMessage(),this.self());
			}
			else{
				//TODO
			}
		}
		//If the present ChordActor sent a request and the successive message cascade found the key's responsable
		else if(message instanceof TrouveMessage) {
			final TrouveMessage trouveMessage = (TrouveMessage) message;
			/**
			 * To Modify => TestFingerTable is useless in this context
			 */
			//this.getSender().tell(new TestFingerTable(), this.self());
		}
		else if(message instanceof TestFingerTable) {
			table.afficher();
		}
		else if(message instanceof SetKeyMessage) {
			final SetKeyMessage setKey = (SetKeyMessage) message;
			this.key=setKey.getKey();
			table=new FingerTable(key);
		}
		else if(message instanceof AddOthersKeysMessage) {
			final AddOthersKeysMessage addOthersKeys = (AddOthersKeysMessage) message;
			this.addOthersKeys(addOthersKeys.getoKeys());
			table=new FingerTable(key);
		}
		else if(message instanceof AfficherCleMessage) {
			this.afficherKeys();
		}
		else if(message instanceof CreateFingerTableMessage) {
			final CreateFingerTableMessage createTable = (CreateFingerTableMessage) message;
			this.key=createTable.getKey();
			this.createFingerTable() ;
		}
		else{
			unhandled(message);
		}
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public void addOthersKeys(List<Key> oKeys) {
		for(int i=0; i<oKeys.size();i++){
			othersKeys.add(oKeys.get(i));
		}
	}
	
	public void afficherKeys(){
		System.out.println("key propre :"+key);
		for(int i=0; i<othersKeys.size();i++){
			System.out.println(othersKeys.get(i));
		}
	}
	
	/*public void join(ChordNode c){
		// ChordMessage comsg = new ChordMessage(...);
		// get.Ref().tell(comsg);
	}*/
	
	/*private handle:JoinMsg(ChordMessage msg){
		ChordNode sender = msg.getSender();
		Row ligne=this.ft.lookup(sender.getKey());if(ligne==this.ft.first(j){
		}
		else{
		ligne.getReferent(').getref().forward(msg);
*/
}
