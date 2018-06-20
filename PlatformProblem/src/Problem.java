import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


public class Problem {
	
   private  int id;
   private String text_prob;
   private int id_user;
   private String titre;
 
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText_prob() {
		return text_prob;
	}
	public void setText_prob(String text_prob) {
		this.text_prob = text_prob;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	
	

	 

}
