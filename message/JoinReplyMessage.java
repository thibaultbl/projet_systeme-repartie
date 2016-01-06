package message;

import projet.FingerTable;

public class JoinReplyMessage {
	FingerTable fingerTable;

	public JoinReplyMessage(FingerTable fingerTable) {
		super();
		this.fingerTable = fingerTable;
	}

	public FingerTable getFingerTable() {
		return fingerTable;
	}

	public void setFingerTable(FingerTable fingerTable) {
		this.fingerTable = fingerTable;
	}
	
	
	
}
