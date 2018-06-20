import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class login implements Serializable {
	
	private String pwd;
	private String msg;
	private String user;
	private Problem prob=new Problem();
	User u;
	List<Problem> liste=new ArrayList();

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public List<Problem> getListe() {
		return liste;
	}

	public void setListe(List<Problem> liste) {
		this.liste = liste;
	}


	public Problem getProb() {
		return prob;
	}

	public void setProb(Problem prob) {
		this.prob = prob;
	}

	//validate login
	public String validateUsernamePassword() {
			u = LoginDAO.validate(user, pwd);
			if (u!=null) {
				HttpSession session= SessionUtils.getSession();
				session.setAttribute("user", u);
				liste=LoginDAO.getProblemes(u, session);
				session.setAttribute("problemes", liste);
				return "Profil";
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "Acceuil";
			}
		}

		//logout event, invalidate session
		public String logout() {
			HttpSession session = SessionUtils.getSession();
			session.invalidate();
			return "Acceuil";
		}
		
		
		public String SupprimerProblem(int id_prob) {
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<jevais supprimer un probleme>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			ProblemDAO.DeleteProblem(id_prob);
			List<Problem> l=new ArrayList<Problem>();		
			for(int i=0;i<liste.size();i++) {
				if(liste.get(i).getId()!=id_prob) {
					l.add(liste.get(i));
					System.out.println("j'ai ajouter le probleme qui avait le id"+liste.get(i));
				}
			} 
			liste=l;
			return "Profil";
		}
		
		public String changerInfo() {
			boolean v=LoginDAO.validmail(u);
			if(u.getMot_de_passe().equals(u.getConfirm_mdp()) && v==true)
			{
			  LoginDAO.ChangerUserInfo(u);
			  FacesMessage message = new FacesMessage("changement avec succés");
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
			u.setConfirm_mdp(null);
			u.setMot_de_passe(null);
			return "ChangementInfo";
		}
		
		
		public String AjouterProb() {
			prob.setId_user(u.getId());
			int id_p= ProblemDAO.AddProbleme(prob);
			prob.setId(id_p);
			liste.add(prob);
			FacesMessage message = new FacesMessage("Le probleme est ajouter");
			FacesContext.getCurrentInstance().addMessage( null, message );
			prob=new Problem();
			return "AjoutProb";
		}

		public String getProb(int prob_id){
			for(int i=0;i<liste.size();i++) {
				if(liste.get(i).getId()==prob_id) {
					prob=liste.get(i);
				}
			} 
			return "ModifProb";
		}

		public String ModifierProb() {
			ProblemDAO.UpdateProblem(prob);
			return "problem";
		}
}