
public class Comment {
	
	int id;
	String text_comm;
    int id_user;
    int id_prob;
    int id_sol;
    String nom_user;
    String prenom_user;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText_comm() {
		return text_comm;
	}
	public void setText_comm(String text_comm) {
		this.text_comm = text_comm;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getId_prob() {
		return id_prob;
	}
	public void setId_prob(int id_prob) {
		this.id_prob = id_prob;
	}
	public int getId_sol() {
		return id_sol;
	}
	public void setId_sol(int id_sol) {
		this.id_sol = id_sol;
	}
	public String getNom_user() {
		return nom_user;
	}
	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}
	public String getPrenom_user() {
		return prenom_user;
	}
	public void setPrenom_user(String prenom_user) {
		this.prenom_user = prenom_user;
	}
    
	
    

}
