package projet;

import java.util.ArrayList;
import java.util.List;

public class AddOthersKeysMessage {

	List<Integer> oKeys=new ArrayList<Integer>();

	public AddOthersKeysMessage(List<Integer> oKeys) {
		super();
		this.oKeys = oKeys;
	}

	public List<Integer> getoKeys() {
		return oKeys;
	}

	public void setoKeys(List<Integer> oKeys) {
		this.oKeys = oKeys;
	}
	
	

}
