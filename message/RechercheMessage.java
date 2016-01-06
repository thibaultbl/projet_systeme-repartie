package message;

import projet.Key;


public class RechercheMessage extends Message {
	int cleRecherche;

	public RechercheMessage(int cleRecherche, Key key) {
		super(key);
		this.cleRecherche = cleRecherche;
	}

	public int getCleRecherche() {
		return cleRecherche;
	}

	public void setCleRecherche(int cleRecherche) {
		this.cleRecherche = cleRecherche;
	}
	
	

}
