package test;

import message.InitialisationMessage;
import message.JoinMessage;
import message.SetKeyMessage;
import message.TestFingerTable;

import org.junit.Test;

import projet.ChordActor;
import projet.ChordNode;
import projet.Key;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ChordActorTest {

	@Test
	public void testUpdateOnReceive() {

		final ActorSystem system = ActorSystem.create("globalSystem");
		final ActorRef actor0 = system.actorOf(Props.create(ChordActor.class),"ChordActor0");
		InitialisationMessage initMessage=new InitialisationMessage(actor0);
		actor0.tell(initMessage, actor0);
		ChordNode cn0=new ChordNode(actor0, new Key(0));

		TestFingerTable table=new TestFingerTable();
		//actor0.tell(table, actor0);

		final ActorRef actor3 = system.actorOf(Props.create(ChordActor.class),"ChordActor3");
		initMessage=new InitialisationMessage(actor3);
		actor3.tell(initMessage, actor3);
		Key key3 = new Key(3);
		SetKeyMessage setKey3=new SetKeyMessage(key3);
		ChordNode cn3=new ChordNode(actor3, key3);
		actor3.tell(setKey3, actor3);
		JoinMessage join=new JoinMessage(key3);
		actor0.tell(join, actor3);	

		//actor0.tell(table, actor0);

		actor3.tell(table, actor3);

		final ActorRef actor7 = system.actorOf(Props.create(ChordActor.class),"ChordActor7");
		initMessage=new InitialisationMessage(actor7);
		actor7.tell(initMessage, actor7);
		Key key7 = new Key(7);
		SetKeyMessage setKey7=new SetKeyMessage(key7);
		actor7.tell(setKey7, actor7);
		JoinMessage join7=new JoinMessage(key7);
		actor3.tell(join7, actor7);

		actor7.tell(table, actor7);

		//fail("Not yet implemented");
	}





}
