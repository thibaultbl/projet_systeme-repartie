package projet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import message.AddOthersKeysMessage;
import message.AfficherCleMessage;
import message.FindPredecessorMessage;
import message.GetKeyMessage;
import message.GetKeyMessageReply;
import message.InitialisationMessage;
import message.JoinMessage;
import message.JoinReplyMessage;
import message.Message;
import message.PredecessorFoundMessage;
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
	private int Idpredecessor;

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
			this.Idpredecessor=joinReply.getKey().getValue();
			System.out.println("key :"+this.key.getValue());
			System.out.println("joinReply.getKey() : "+joinReply.getKey());
			this.updateFingerTable(this.key.getValue(), joinReply.getKey(), joinReply.getFingerTable());
			System.out.println("affichage fingertable après update");
			this.table.afficher();
			FindPredecessorMessage findPredecessorMessage=new FindPredecessorMessage(this.key.getValue(), this.self());
			this.sender().tell(findPredecessorMessage, this.self());
		}
		else if(message instanceof InitialisationMessage) {
			final InitialisationMessage initialisationMessage = (InitialisationMessage) message;
			this.predecessor=this.getSelf();
			this.table=initialisationMessage.getTable();
			this.key=initialisationMessage.getKey();
			this.Idpredecessor=this.key.getValue();
		}
		else if(message instanceof GetKeyMessage) {
			GetKeyMessageReply getKeyMessageReply=new GetKeyMessageReply(this.key);
			this.sender().tell(getKeyMessageReply, this.self());
		}
		else if(message instanceof FindPredecessorMessage) {
			System.out.println("findPredecessorRecu");
			FindPredecessorMessage findPredecessorMessage=(FindPredecessorMessage) message;
			System.out.println("interval :"+this.getTable().getTree().get(0));
			System.out.println("ID : "+findPredecessorMessage.getId());
			System.out.println("fngertable de : "+this.key.getValue());
			this.table.afficher();

			if(this.inRange(findPredecessorMessage.getId())){
				PredecessorFoundMessage predecessorFoundMessage=new PredecessorFoundMessage(this.getTable().getTree().get(0).getSuccessor(), findPredecessorMessage.getId(), this.getKey().getValue(), this.getTable().getTree().get(0).getIdSuccessor());
				findPredecessorMessage.getSender().tell(predecessorFoundMessage, this.self());
			}
			else{
				FindPredecessorMessage newFindPredecessorMessage=new FindPredecessorMessage(findPredecessorMessage.getId(), findPredecessorMessage.getSender());
				System.out.println("envoi findPredecessorMessage à "+this.closestPrecedingFinger(findPredecessorMessage.getId()));
				System.out.println("fingertable de : "+this.self());
				this.table.afficher();
				this.closestPrecedingFinger(findPredecessorMessage.getId()).tell(newFindPredecessorMessage, this.self());
			}
		}
		else if(message instanceof PredecessorFoundMessage) {
			PredecessorFoundMessage predecessorFoundMessage= (PredecessorFoundMessage)message;
			System.out.println("PredcessorFoundMessage : Predecessor : "+this.sender()+" sucessor : "+predecessorFoundMessage.getSucessor());
			ActorRef idPredecessor=this.sender();
			ActorRef idSuccessor=predecessorFoundMessage.getSucessor();
			//si l'id recherché est le même que l'id de l'envoyeur, on update ces prédécesseur et sucesseur
			if(predecessorFoundMessage.getIdRechercher()==this.key.getValue()){
				this.predecessor=this.sender();
				this.getTable().getTree().get(0).setSuccessor(predecessorFoundMessage.getSucessor());
				this.Idpredecessor=predecessorFoundMessage.getIdSender();
				this.getTable().getTree().get(0).setIdSuccessor(predecessorFoundMessage.getIdSucessor());
			}
		}
		else{
			unhandled(message);
		}
		System.out.println("prédeccesseur de "+this.self()+" est : "+this.predecessor);

	}

	//on update la fingertable lorsque l'on reçoit un message d'un acteur (on ne connait pas forcément cet acteur avant de recevoir le message)
	public void updateOnReceive(Object message){
		Message messageTemp=(Message) message;
		//on update les successeurs de la finger table
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

		//on update le predecesseur du noeud
		int k=this.key.getValue();
		int id1=messageTemp.getKey().getValue();
		int id2=this.Idpredecessor;

		for(int j=0;j<Hashtable.nbNodes;j++){
			if(j!=0){
				k=((k+1) % (Hashtable.nbNodes));
			}
			if(k==id2){
				System.out.println("change predecessor");
				this.predecessor=this.sender();
				this.Idpredecessor=id1;
				break;
			}
			else if(k==id1){
				System.out.println("keep predecessor");
				break;
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
			if(this.table.getTree().get(i).inRange(id))
			{
					if(i==0){
						return this.getTable().getTree().get(FingerTable.NROW-1).getSuccessor();
					}
					else{
						return this.getTable().getTree().get(i-1).getSuccessor();
					}
				}			
			}
		return null;
	}

	public boolean inRange(int id){
		int k=this.key.getValue();
		for(int j=0;j<Hashtable.nbNodes;j++){
			k=((k+1) % (Hashtable.nbNodes));
			if(id==k){
				System.out.println("id : "+id+" est dans l'intervalle :"+this.key.getValue()+" -> "+this.getTable().getTree().get(0).getIdSuccessor());
				return true;
			}
			else if(k==this.getTable().getTree().get(0).getIdSuccessor()){
				System.out.println("id : "+id+" n'est PAS dans l'intervalle :"+this.key.getValue()+" -> "+this.getTable().getTree().get(0).getIdSuccessor());
				return false;
			}
		}
		return false;
	}



}
