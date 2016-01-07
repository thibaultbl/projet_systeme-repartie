package projet;

import java.util.ArrayList;
import java.util.List;

import message.JoinMessage;
import message.JoinReplyMessage;
import message.LookupMessage;
import message.LookupReplyMessage;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class PseudoCode extends UntypedActor{
	private Key key;
	private List<Key> othersKeys=new ArrayList<Key>();
	private FingerTable table;
	private ActorRef predecessor;

	@Override
	public void onReceive(Object message) throws Exception {
		// l'acteur reçoit un message d'un autre acteur désirant rejoindre le système
		if(message instanceof JoinMessage) {
			// on recherche le meilleur noeud référent dans sa fingerTable
			int dif1=difference(((JoinMessage) message).getKey().getValue(),this.key.getValue());
			int difMin=dif1;
			int indexMin=0;
			for(int i=0;i<this.table.size();i++){
				int dif2 = difference(((JoinMessage) message).getKey().getValue(),this.table.tree.get(i).getIdSuccessor());
				if(dif2<difMin){
					indexMin=i;
					difMin=dif2;
				}
			}

			// on met à jour la fingerTable du noeud
			for(int i=0;i<this.table.size();i++){
				int dif0=difference(this.table.tree.get(i).getIdSuccessor(),this.table.tree.get(i).getLowBound());
				int dif2=difference(((JoinMessage) message).getKey().getValue(),this.table.tree.get(i).getLowBound());
				if(dif2<dif0){
					this.table.setSuccessor(i, getSender(), ((JoinMessage) message).getKey().getValue());
				}
			}

			if(difMin<dif1){
				// si on a trouvé un meilleur noeud référent alors on lui refile le message
				this.table.tree.get(indexMin).getSuccessor().tell(new JoinMessage(((JoinMessage) message).getKey()), getSender());
			}
			else{
				// on initialise la fingerTable du noeud qui cherche à rentrer
				FingerTable fingerTable = this.init_finger_table(((JoinMessage) message),((JoinMessage) message).getKey().getValue());
				getSender().tell(new JoinReplyMessage(fingerTable), getSelf());
			}
		}
		if(message instanceof JoinReplyMessage) {
			// on récupère la fingerTable créee par l'autre noeud
			this.table=((JoinReplyMessage) message).getFingerTable();

			// on va chercher à améliorer les successeurs de la fingerTable en les contactant
			// on parcourt chaque successeur de la fingerTable
			for(int i=0;i<this.table.size();i++){
				// on recherche à chaque fois le meilleur successeur à contacter
				int dif1=difference(this.table.tree.get(i).getIdSuccessor(),this.table.getTree().get(i).getLowBound());
				int difMin=dif1;
				int indexMin=0;
				for(int j=0;j<this.table.size();j++){
					int dif2=difference(this.table.tree.get(j).getIdSuccessor(),this.table.getTree().get(i).getLowBound());
					if(dif2<difMin){
						indexMin=j;
						difMin=dif2;
					}
				}
				// contacter le meilleur successeur
				if(difMin<dif1){
					this.table.tree.get(indexMin).getSuccessor().tell(new LookupMessage(this.table.tree.get(i).getLowBound(),difMin,i,this.key.getValue()), getSelf());
				}
				else{
					this.table.tree.get(i).getSuccessor().tell(new LookupMessage(this.table.tree.get(i).getLowBound(),difMin,i,this.key.getValue()), getSelf());
				}
			}
		}

		if(message instanceof LookupMessage) {
			// on met à jour la fingerTable du noeud
			for(int i=0;i<this.table.size();i++){
				int dif0=difference(this.table.tree.get(i).getIdSuccessor(),this.table.tree.get(i).getLowBound());
				int dif2=difference(((LookupMessage) message).getIdActor(),this.table.tree.get(i).getLowBound());
				if(dif2<dif0){
					this.table.setSuccessor(i, getSender(), ((JoinMessage) message).getKey().getValue());
				}
			}

			// on parcourt chaque successeur de la fingerTable
			int dif1=((LookupMessage) message).getDif();
			int difMin=dif1;
			int indexMin=0;
			for(int i=0;i<this.table.size();i++){
				int dif2=difference(this.table.tree.get(i).getIdSuccessor(),((LookupMessage) message).getIdRecherche());
				if(dif2<difMin){
					indexMin=i;
					difMin=dif2;
				}
			}
			if(difMin<dif1){
				// si on a trouvé un meilleur successeur on refile le lookupMessage
				this.table.tree.get(indexMin).getSuccessor().tell(new LookupMessage(((LookupMessage) message).getIdRecherche(),difMin,((LookupMessage) message).getLigneMAJ(),((LookupMessage) message).getIdActor()), getSender());
			}
			else{
				// sinon on se renvoie soi-même
				getSender().tell(new LookupReplyMessage(((LookupMessage) message).getLigneMAJ()), getSelf());
			}
		}
	}

	public FingerTable init_finger_table(JoinMessage message, int idActor){
		FingerTable fingerTable = new FingerTable(message.getKey(),getSender(),message.getKey().getValue());
		for(int i=0;i<fingerTable.size();i++){
			int dif1=difference(fingerTable.tree.get(i).getIdSuccessor(),fingerTable.tree.get(i).getLowBound());
			int difMin=dif1;
			int indexMin=0;
			for(int j=0;j<this.table.size();j++){
				int dif2=difference(this.table.tree.get(j).getIdSuccessor(),fingerTable.tree.get(i).getLowBound());
				if(dif2<difMin){
					difMin=dif2;
					indexMin=j;
				}
			}
			if(difMin<dif1){
				fingerTable.setSuccessor(i, this.table.tree.get(indexMin).getSuccessor(), this.table.tree.get(indexMin).getIdSuccessor());
			}
		}
		return fingerTable;
	}

	public int difference(int idSuccesseur, int id){
		int dif;
		if(idSuccesseur>=id){
			dif=idSuccesseur-id;
		}
		else{
			dif=8-id+idSuccesseur;
		}
		return dif;
	}

}
