import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class gererproblem implements Serializable  {
	
	  private int id;
	  private Comment commt=new Comment();
	  private Solution solt=new Solution();
	  private Problem prob=new Problem();
	  private User userR=new User();
      private Problem pr=new Problem();
	  private List<Solution> sol =new ArrayList<Solution>();
	  private List<Comment> comm =new ArrayList<Comment>();
	  private List<Problem> pros =new ArrayList<Problem>();
	  private List<Comment> comm_sol =new ArrayList<Comment>();
	  private Comment comv=new Comment();
	  

	public Comment getComv() {
		return comv;
	}
	public void setComv(Comment comv) {
		this.comv = comv;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Problem getPr() {
		return pr;
	}
	public void setPr(Problem pr) {
		this.pr = pr;
	}
	public List<Comment> getComm() {
		return comm;
	}
	public void setComm(List<Comment> comm) {
		this.comm = comm;
	}
	
	
	  
	public User getUserR() {
		return userR;
	}
	public void setUserR(User userR) {
		this.userR = userR;
	}
	public Problem getProb() {
		return prob;
	}
	public void setProb(Problem prob) {
		this.prob = prob;
	}
	public List<Comment> getComm_sol() {
		return comm_sol;
	}
	public void setComm_sol(List<Comment> comm_sol) {
		this.comm_sol = comm_sol;
	}
	public Comment getCommt() {
		return commt;
	}
	public void setCommt(Comment commt) {
		this.commt = commt;
	}
	public Solution getSolt() {
		return solt;
	}
	public void setSolt(Solution solt) {
		this.solt = solt;
	}
	public List<Solution> getSol() {
		return sol;
	}
	public void setSol(List<Solution> sol) {
		this.sol = sol;
	}
	public List<Problem> getPros() {
		return pros;
	}
	public void setPros(List<Problem> pros) {
		this.pros = pros;
	}
	public String detail(int id_prob) {
		comm_sol=new ArrayList<Comment>();
		sol=new ArrayList<Solution>();
		comm=new ArrayList<Comment>();
		pros=new ArrayList<Problem>();
		
		//System.out.println("yaaaayy je suis dans la fonction detail du problem:"+id_prob);
		id=id_prob;
		pr=ProblemDAO.getProblem(id_prob);
		//System.out.println("j'ai recuperer le probleme");
		sol=ProblemDAO.getSolution(id_prob);
		//System.out.println("j'ai recuperer les solutions");
		
		comm=ProblemDAO.getComments(id_prob);
		//System.out.println("j'ai recuperer les commentaires");	
		pros=ProblemDAO.getSousProblems(id_prob);
		//System.out.println("j'ai recuperer les sous problemes");
		 if(sol.size()!=0) {
			for(int i=0;i<sol.size();i++) {
			ArrayList<Comment> a=new ArrayList<Comment>();
			a=ProblemDAO.getCommentSol(sol.get(i).getId_sol());
			if(a.size()!=0) {
				for(int j=0;j<a.size();j++) {
				  comm_sol.add(a.get(j));
				  System.out.println("la commentaire------------->id:"+a.get(j).getId()+"texte_commentaire:"+a.get(j).getText_comm()+"id_user"+a.get(j).getId_user());
				}
				
			}
			}
		}
		return "probleme";
		
	}
	
	public String AjouterComment(int id) {
		commt.setId_prob(pr.getId());
		commt.setId_user(id);
		int id_c=ProblemDAO.addComment(commt);
		/*System.out.println("le id retourner par la fonction addComment est:"+id_c);
		System.out.println("La liste des commentaire est:");
		for(int i=0;i<comm.size();i++) {
			System.out.println("la problem:"+comm.get(i).getId()+" "+comm.get(i).getText_comm());
		}
		*/
		commt.setId(id_c);
		comm.add(commt);
		commt=new Comment();
		return "probleme";
		
	}
	
	public String SupprimerComment(int id_comment) {
		ProblemDAO.deleteComment(id_comment);
		ArrayList<Comment> c = new ArrayList<Comment>();
		for(int i=0;i<comm.size();i++) {
			if(comm.get(i).getId()!=id_comment) { 
				c.add(comm.get(i));
			}
		}
		comm=c;
		return "probleme";
		
	}
	
	public String addComSolution(int id_sol,int id) {
		comv.setId_sol(id_sol);
		comv.setId_user(id);
		System.out.println("le texte du commentaire:"+comv.getText_comm());
		int id_c=SolutionDAO.addComment(comv);
		System.out.println("le id retourner par la fonction addComment est:"+id_c);
		System.out.println("La liste des commentaire est:");
		comv.setId(id_c);
		comm_sol.add(comv);
		for(int i=0;i<comm_sol.size();i++) {
			System.out.println("le commentaire:"+comm_sol.get(i).getId()+" "+comm_sol.get(i).getText_comm());
		}
		
		return "probleme";
		
	}
	
	public String SupprimerSol(int id_sol) {
		
	  SolutionDAO.DeleteSolution(id_sol);
	  List<Solution> s=new ArrayList<Solution>();
	  if(sol.size()!=0) {
		  for(int i=0;i<sol.size();i++) {
			  if(sol.get(i).getId_sol()!=id_sol) {
				  s.add(sol.get(i));
			  }
		  }
	  }
	  sol=s;
	  
	  return "problem";
		
	}
	
	public String SupprimerCom(int id) {
		
		CommentDAO.DeleteComment(id);
		  List<Comment> s=new ArrayList<Comment>();
		  if(comm_sol.size()!=0) {
			  for(int i=0;i<comm_sol.size();i++) {
				  if(comm_sol.get(i).getId()!=id) {
					  s.add(comm_sol.get(i));
				  }
			  }
		  }	
		comm_sol=s;
		return "problem";
	}
	
public String AjouterSolution(int id) {
	   
	   solt.setId_prob(pr.getId());
	   solt.setId_user(id);
	   int id_s=SolutionDAO.AddSolution(solt);
	   solt.setId_sol(id_s);
	   sol.add(solt);
	   solt=new Solution();
	   return "problem";
	}

public String AjouterSousProb(int id) {
	prob.setId_user(id);
	int c=ProblemDAO.AddProbleme(prob);
	prob.setId(c);
	ProblemDAO.addRelationProb(pr.getId(),c);
	pros.add(prob);
	prob=new Problem();
	return "problem";
}
	

public String SupprimerSousProblem(int id) {
	System.out.println("je vais supprimer un probleme");
	ProblemDAO.DeleteProblem(id);
	List<Problem> l=new ArrayList<Problem>();		
	for(int i=0;i<pros.size();i++) {
		if(pros.get(i).getId()!=id) {
			l.add(pros.get(i));
		}
	} 
	pros=l;
	return "problem";
	
}
public String RechercheProblem() {
	System.out.println("je vais executer la fonction RechercheProblem ");
	pros=ProblemDAO.getProblemTitre(prob);
	for(int j=0;j<pros.size();j++) {
		System.out.println("pros: id="+pros.get(j).getId());
	}
	return "ResultatRecherche";
}

 public String RechercheUser() {
	 System.out.println("je vais executer la fonction RechercheUser ");
	 pros=ProblemDAO.getProblemUser(userR);
	 for(int j=0;j<pros.size();j++) {
			System.out.println("pros: id="+pros.get(j).getId());
		}
		return "ResultatRecherche";
 }
	
	

}
