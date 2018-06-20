
public class User {
	int id;
	String nom;
	String prenom;
	String mail;
	String mot_de_passe;
	String confirm_mdp;
	String comm;
	
	public User() {
		
	}
	public User(String nom, String prenom, String mail, String mot_de_passe, String comm) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.comm = comm;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMot_de_passe() {
		return mot_de_passe;
	}
	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getConfirm_mdp() {
		return confirm_mdp;
	}
	public void setConfirm_mdp(String confirm_mdp) {
		this.confirm_mdp = confirm_mdp;
	}
	
	
	
	
	

}
