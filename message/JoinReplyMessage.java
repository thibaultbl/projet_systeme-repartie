package message;

import projet.FingerTable;
import projet.Key;

public class JoinReplyMessage extends Message {
	FingerTable fingerTable;

	public JoinReplyMessage(FingerTable fingerTable, Key key) {
		super(key);
		this.fingerTable = fingerTable;
	}

	public FingerTable getFingerTable() {
		return fingerTable;
	}

	public void setFingerTable(FingerTable fingerTable) {
		this.fingerTable = fingerTable;
	}
	
	
	
}
