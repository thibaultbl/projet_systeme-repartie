package projet;

import java.util.ArrayList;
import java.util.List;

public class AddOthersKeysMessage {

	List<Key> oKeys=new ArrayList<Key>();

	public AddOthersKeysMessage(List<Key> oKeys) {
		super();
		this.oKeys = oKeys;
	}

	public List<Key> getoKeys() {
		return oKeys;
	}

	public void setoKeys(List<Key> oKeys) {
		this.oKeys = oKeys;
	}
	
	

}
