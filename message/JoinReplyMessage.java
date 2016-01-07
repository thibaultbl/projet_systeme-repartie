package message;

import projet.FingerTable;
import projet.Key;

public class JoinReplyMessage {
	private FingerTable fingerTable;
	private Key myKey;
	private Key key;

	public JoinReplyMessage(FingerTable fingerTable, Key key, Key myKey) {
		this.fingerTable = fingerTable;
		this.myKey=myKey;
		this.key=key;
	}

	public FingerTable getFingerTable() {
		return fingerTable;
	}

	public void setFingerTable(FingerTable fingerTable) {
		this.fingerTable = fingerTable;
	}
	
	public Key getMyKey() {
		return myKey;
	}
	
	public Key getKey() {
		return key;
	}
	
	
}
