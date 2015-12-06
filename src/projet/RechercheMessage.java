package projet;

import java.io.Serializable;

public class RechercheMessage implements Serializable {
	int cleRecherche;

	public RechercheMessage(int cleRecherche) {
		super();
		this.cleRecherche = cleRecherche;
	}

	public int getCleRecherche() {
		return cleRecherche;
	}

	public void setCleRecherche(int cleRecherche) {
		this.cleRecherche = cleRecherche;
	}
	
	

}
