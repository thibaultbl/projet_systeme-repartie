package message;

public class LookupMessage {
	private int idRecherche;
	private int dif;
	private int ligneMAJ;
	private int idActor;
	
	public LookupMessage(int idRecherche, int dif, int ligne, int idActor) {
		this.idRecherche = idRecherche;
		this.dif = dif;
		this.ligneMAJ=ligne;
		this.idActor=idActor;
	}
	
	public int getDif() {
		return dif;
	}
	
	public int getIdRecherche() {
		return idRecherche;
	}
	
	public int getLigneMAJ() {
		return ligneMAJ;
	}
	
	public int getIdActor() {
		return idActor;
	}
}
