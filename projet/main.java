package projet;

import java.util.ArrayList;
import java.util.List;

import message.InitialisationMessage;
import message.JoinMessage;
import message.SetKeyMessage;
import message.TestFingerTable;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		/**
		 * Initialisation
		 */


		/**
		 * Test : on a un acteur en 0
		 */



		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef actor0 = system.actorOf(Props.create(ChordActor.class),"ChordActor0");
		InitialisationMessage initMessage=new InitialisationMessage(actor0);
		actor0.tell(initMessage, actor0);

		TestFingerTable table=new TestFingerTable();
		//	actor0.tell(table, actor0);

		Thread.sleep(500);

		/**
		 * On fait rentrer un nouvel acteur en 3
		 */

		final ActorRef actor3 = system.actorOf(Props.create(ChordActor.class),"ChordActor3");
		Key key3 = new Key(3);
		SetKeyMessage setKey3=new SetKeyMessage(key3);
		actor3.tell(setKey3, actor3);
		JoinMessage join=new JoinMessage(key3);
		actor0.tell(join, actor3);	

		Thread.sleep(500);

		final ActorRef actor4 = system.actorOf(Props.create(ChordActor.class),"ChordActor4");
		Key key4 = new Key(4);
		SetKeyMessage setKey4=new SetKeyMessage(key4);
		actor4.tell(setKey4, actor4);
		join=new JoinMessage(key4);
		actor3.tell(join, actor4);	

		Thread.sleep(500);

		final ActorRef actor2 = system.actorOf(Props.create(ChordActor.class),"ChordActor2");
		Key key2 = new Key(2);
		SetKeyMessage setKey2=new SetKeyMessage(key2);
		actor2.tell(setKey2, actor2);
		join=new JoinMessage(key2);
		actor4.tell(join, actor2);	
		
		Thread.sleep(500);
		TestFingerTable testFingerTable=new TestFingerTable();
		actor3.tell(testFingerTable, null);	
	}

}
