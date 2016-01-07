package projet;

import java.util.ArrayList;
import java.util.List;

import message.JoinMessage;
import message.JoinReplyMessage;
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
