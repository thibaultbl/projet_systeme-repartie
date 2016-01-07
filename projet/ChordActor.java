package projet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import message.AddOthersKeysMessage;
import message.AfficherCleMessage;
import message.GetKeyMessage;
import message.GetKeyMessageReply;
import message.InitialisationMessage;
import message.JoinMessage;
import message.JoinReplyMessage;
import message.Message;
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
		this.self().tell(new InitialisationMessage(this.self()), this.self());
	}

	public void createFingerTable(){
		table=new FingerTable(this.key, this.self(), this.key.getValue());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Message){
			this.updateOnReceive(message);
		}

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
			table=new FingerTable(key, this.self(), this.key.getValue());
		}
		else if(message instanceof AfficherCleMessage) {
			this.afficherKeys();
		}
		else if(message instanceof JoinMessage) {
			final JoinMessage join = (JoinMessage) message;
			//L'acteur récupére la FingerTable du collégue qu'il connait dans le cercle
			System.out.println("join.getKey() -> this.key : "+this.key);
			JoinReplyMessage joinReply=new JoinReplyMessage(this.table, this.key, join.getKey());
			this.getSender().tell(joinReply, this.self());
		}
		else if(message instanceof JoinReplyMessage) {
			final JoinReplyMessage joinReply = (JoinReplyMessage) message;
			//L'acteur récupére la FingerTable du collégue qu'il connait dans le cercle
			this.key=joinReply.getMyKey();
			this.table=new FingerTable(this.key, this.self(), this.key.getValue());
			System.out.println("affichage fingertable avant update");
			this.table.afficher();
			this.predecessor=this.getSender();
			System.out.println("key :"+this.key.getValue());
			System.out.println("joinReply.getKey() : "+joinReply.getKey());
			this.updateFingerTable(this.key.getValue(), joinReply.getKey(), joinReply.getFingerTable());
			System.out.println("affichage fingertable après update");
			this.table.afficher();
		}
		else if(message instanceof InitialisationMessage) {
			final InitialisationMessage initialisationMessage = (InitialisationMessage) message;
			this.predecessor=this.getSelf();
			this.table=initialisationMessage.getTable();
			this.key=initialisationMessage.getKey();
		}
		else if(message instanceof GetKeyMessage) {
			GetKeyMessageReply getKeyMessageReply=new GetKeyMessageReply(this.key);
			this.sender().tell(getKeyMessageReply, this.self());

		}
		else{
			unhandled(message);
		}
	}

	//on update la fingertable lorsque l'on reçoit un message d'un acteur (on ne connait pas forcément cet acteur avant de recevoir le message)
	public void updateOnReceive(Object message){
		Message messageTemp=(Message) message;

		for(int i=0;i<this.table.getTree().size();i++){
			int idCible=this.table.getTree().get(i).getLowBound();
			int id1=messageTemp.getKey().getValue();
			int id2=this.table.getTree().get(i).getIdSuccessor();

			int k=idCible;
			for(int j=0;j<Hashtable.nbNodes;j++){
				if(j!=0){
					k=((k+1) % (Hashtable.nbNodes));
				}
				if(k==id2){
					System.out.println("keep referent last modification");
					break;
				}
				else if(k==id1){
					System.out.println("change referent");
					this.table.getTree().get(i).setIdSuccessor(id1);
					this.table.getTree().get(i).setSuccessor(this.sender());
					break;
				}
			}
		}		
	}

	public void updateFingerTable(int idNoeud, Key keySender, FingerTable fingerTableSender){
		//on récupére l liste d'actorRef de la finger Table
		HashMap<Integer, ActorRef> actor=new HashMap<Integer, ActorRef>();
		for(int i=0;i<fingerTableSender.getTree().size();i++){
			actor.put(fingerTableSender.getTree().get(i).getIdSuccessor(), fingerTableSender.getTree().get(i).getSuccessor());
		}

		//on récupére le meilleur référent dans actor pour chaque ligne
		for(int i=0;i<this.table.getTree().size();i++){
			Collection<Object> result=bestReferent(actor, this.table.getTree().get(i), keySender);
			Iterator<Object> iterator = result.iterator();
			this.table.getTree().get(i).setSuccessor((ActorRef)iterator.next());
			this.table.getTree().get(i).setIdSuccessor((Integer)iterator.next());
		}

		//On utilise les connaissances du référent pour actualiser notre table

	}

	public Collection<Object> bestReferent(HashMap<Integer, ActorRef> actor, Row row, Key keySender){
		Collection<Object> result=new ArrayList<Object>();
		ActorRef referent=row.getSuccessor();
		int id=row.getIdSuccessor();
		Set<Integer> keys=actor.keySet();
		Iterator i=keys.iterator(); // on crée un Iterator pour parcourir notre HashSet
		while(i.hasNext()) // tant qu'on a un suivant
		{
			int idCible=row.getLowBound();
			int id1=row.getIdSuccessor();
			int id2=(Integer) i.next();
			int id3=this.key.getValue();
			int id4=keySender.getValue();
			int k=idCible;
			for(int j=0;j<Hashtable.nbNodes;j++){
				if(j!=0){
					k=((k+1) % (Hashtable.nbNodes));
				}
				System.out.println("id1 :"+id1);
				System.out.println("id2 :"+id2);
				System.out.println("id3 :"+id3);
				System.out.println("id4 :"+id4);
				if(k==id){
					System.out.println("keep referent last modification");
					break;
				}
				else if(k==id1){
					System.out.println("keep referent");
					break;
				}
				else if(k==id2){
					System.out.println("change referent");
					referent=actor.get(id2);
					id=id2;
					break;
				}
				else if(k==id3){
					System.out.println("change referent for himself");
					referent=this.self();
					id=id3;
					break;
				}
				else if(k==id4){
					System.out.println("change referent for sender");
					referent=this.sender();
					id=id4;
					break;
				}
			}
		}
		result.add(referent);
		result.add(id);
		return result;
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



	/**
	 * Pseudo-code implementation
	 */
	/**
	 * To Modify
	 * Doit renvoyer un actorRef
	 * @param id
	 * @return
	 */

	public ActorRef closestPrecedingFinger(int id){
		//Pour le premier interval
		for(int i=(FingerTable.NROW-1);i>=0;i--){
			System.out.println(i);
			if(this.table.getTree().get(i).inRange(id))
			{
				System.out.println("in range");
				if(this.table.getTree().get(i).getIdSuccessor()<id){
					System.out.println("id successeur : "+this.getTable().getTree().get(i).getIdSuccessor());
					return this.getTable().getTree().get(i).getSuccessor();
				}
				else{
					if(i==0){
						System.out.println("id successeur : "+this.getTable().getTree().get(FingerTable.NROW).getIdSuccessor());
						return this.getTable().getTree().get(FingerTable.NROW).getSuccessor();
					}
					else{
						System.out.println("id successeur : "+this.getTable().getTree().get(i-1).getIdSuccessor());
						return this.getTable().getTree().get(i-1).getSuccessor();
					}
				}			
			}
		} 
		return null;
	}
	
	public ActorRef findPredecessor(int id){
		int temp=this.key;
		while()
		
		return null;
	}



}
