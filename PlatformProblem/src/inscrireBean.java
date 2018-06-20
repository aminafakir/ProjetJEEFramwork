import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class inscrireBean {
	
	private User utilisateur=new User();
	
	
	
	
	
	
	public User getUtilisateur() {
		return utilisateur;
	}






	public void setUtilisateur(User utilisateur) {
		this.utilisateur = utilisateur;
	}






	public String inscrire() {
		boolean v=LoginDAO.validmail(utilisateur);
		System.out.println("la valeur de v:"+v);
		if(utilisateur.getMot_de_passe().equals(utilisateur.getConfirm_mdp()) && v==true)
		{
		  InscriptionDAO.addUser(utilisateur);
		  FacesMessage message = new FacesMessage("Utilisateur ajouté");
		  FacesContext.getCurrentInstance().addMessage( null, message );
		}
		else if(v==false)
		{
			FacesMessage message = new FacesMessage("mail non valide");
			FacesContext.getCurrentInstance().addMessage( "change:email", message );
		}
		else
		{
			FacesMessage message = new FacesMessage("Le mot de passe et la confirmation doivent etre identique");
			FacesContext.getCurrentInstance().addMessage( "change:confirmation", message );
			
		}
		utilisateur.setConfirm_mdp(null);
		utilisateur.setMot_de_passe(null);
		return "Inscription";
	}
	

}
