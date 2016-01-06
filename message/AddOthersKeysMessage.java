package message;

import java.util.ArrayList;
import java.util.List;

import projet.Key;

public class AddOthersKeysMessage extends Message {

	List<Key> oKeys=new ArrayList<Key>();

	public AddOthersKeysMessage(List<Key> oKeys, Key key) {
		super(key);
		this.oKeys = oKeys;
	}

	public List<Key> getoKeys() {
		return oKeys;
	}

	public void setoKeys(List<Key> oKeys) {
		this.oKeys = oKeys;
	}
	
	

}
