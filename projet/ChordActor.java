package projet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import message.AddOthersKeysMessage;
import message.AfficherCleMessage;
import message.InitialisationMessage;
import message.JoinMessage;
import message.JoinReplyMessage;
import message.RechercheMessage;
import message.SetKeyMessage;
import message.TestFingerTable;
import message.TrouveMessage;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class ChordActor extends UntypedActor{
	private Key key;
	private List<Key> othersKeys=new ArrayList<Key>();
	private FingerTable table;
	private ActorRef predecessor;
	
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
		}
		else if(message instanceof AddOthersKeysMessage) {
			final AddOthersKeysMessage addOthersKeys = (AddOthersKeysMessage) message;
			this.addOthersKeys(addOthersKeys.getoKeys());
			table=new FingerTable(key);
		}
		else if(message instanceof AfficherCleMessage) {
			this.afficherKeys();
		}
		else if(message instanceof JoinMessage) {
			final JoinMessage join = (JoinMessage) message;
			//L'acteur récupére la FingerTable du collégue qu'il connait dans le cercle
			JoinReplyMessage joinReply=new JoinReplyMessage(this.table);
			this.getSender().tell(joinReply, this.self());
		}
		else if(message instanceof JoinReplyMessage) {
			final JoinReplyMessage joinReply = (JoinReplyMessage) message;
			//L'acteur récupére la FingerTable du collégue qu'il connait dans le cercle
			this.table=new FingerTable();
			this.predecessor=this.getSender();
			this.table=joinReply.getFingerTable();
			System.out.println("key :"+this.key.getValue());
			this.updateFingerTable(this.key.getValue());
		}
		else if(message instanceof InitialisationMessage) {
			final InitialisationMessage initialisationMessage = (InitialisationMessage) message;
			this.predecessor=this.getSelf();
			this.table=initialisationMessage.getTable();
		}
		else{
			unhandled(message);
		}
	}
	
	public void updateFingerTable(int idNoeud){
		//on récupére l liste d'actorRef de la finger Table
		HashMap<ActorRef, Integer> actor=new HashMap<ActorRef, Integer>();
		for(int i=0;i<this.table.getTree().size();i++){
			actor.put(this.table.getTree().get(i).getSuccessor(), this.table.getTree().get(i).getIdSuccessor());
		}
		
		//Pour le premier interval
		Row r0 =new Row(idNoeud ,	 0, actor);
		Row r1 =new Row(idNoeud ,	 1, actor);
		Row r2 =new Row(idNoeud ,	 2, actor);
	}
	
	/**
	 * To Modify
	 * Doit renvoyer un actorRef
	 * @param id
	 * @return
	 */
	
	public ActorRef closestPrecedingFinger(int id){
		//Pour le premier interval
		for(int i=(FingerTable.NROW-1);i>=0;i++){
			if(this.table.getTree().get(i).inRange(id))
			{
				if(this.table.getTree().get(i).getIdSuccessor()>id){
					 return this.getTable().getTree().get(i).getSuccessor();
				}
				else{
					if(i==0){
						 return this.getTable().getTree().get(FingerTable.NROW).getSuccessor();
					}
					else{
						 return this.getTable().getTree().get(i-1).getSuccessor();
					}
				}			
			}
		} 
		return null;
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

	public List<Key> getOthersKeys() {
		return othersKeys;
	}

	public void setOthersKeys(List<Key> othersKeys) {
		this.othersKeys = othersKeys;
	}

	public FingerTable getTable() {
		return table;
	}

	public void setTable(FingerTable table) {
		this.table = table;
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
