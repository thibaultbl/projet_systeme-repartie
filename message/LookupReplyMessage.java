package message;


public class LookupReplyMessage {
	private int ligneMAJ;
	
	public LookupReplyMessage(int ligne) {
		this.ligneMAJ=ligne;
	}
	
	public int getLigneMAJ() {
		return ligneMAJ;
	}
}
