package message;

import akka.actor.ActorRef;

public class FindPredecessorMessage {
		private int id;
		private ActorRef sender;
		
		public FindPredecessorMessage(int id, ActorRef sender) {
			this.id=id;
			this.sender=sender;
		}
		
		public int getId() {
			return id;
		}
		
		public ActorRef getSender() {
			return sender;
		}

}
