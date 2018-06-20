import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

public class ProblemDAO {
	
	public static void UpdateProblem(Problem p) {
		Connection con = null;
		PreparedStatement ps = null;
		User u=null;
		Problem pr;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Update Problem set texte_prob=?,titre=? where id_prob=? ");
			ps.setString(1, p.getText_prob());
			ps.setString(2, p.getTitre());
			ps.setInt(3,p.getId());
			int rs = ps.executeUpdate();			
				
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
	}
	
	public static int AddProbleme(Problem p){
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into problem(texte_prob,id_user,titre) values(?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, String.valueOf(p.getText_prob()));
			ps.setString(2, String.valueOf(p.getId_user()));
			ps.setString(3, String.valueOf(p.getTitre()));
			ps.executeUpdate();
			 ResultSet rSetIdGenerees = ps.getGeneratedKeys();
	            while (rSetIdGenerees.next()) {
	                return rSetIdGenerees.getInt(1);
	            }			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return 0;
	}
	
	
	public static void DeleteProblem(int p) {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		List<Problem> liste = null;

		try {
			con = DataConnect.getConnection();
			ps4 = con.prepareStatement("delete from Relation_prob where  id_sous_prob=?;");
			ps4.setString(1, String.valueOf(p));
			int rs4 = ps4.executeUpdate();
						
			con = DataConnect.getConnection();
			ps1 = con.prepareStatement("delete from Relation_prob where id_prob=?;");
			ps1.setString(1, String.valueOf(p));
			int rs1 = ps1.executeUpdate();
			
			con = DataConnect.getConnection();
			ps2 = con.prepareStatement("delete from Solution where id_prob=?;");
			ps2.setString(1, String.valueOf(p));
			int rs2 = ps2.executeUpdate();
			
			con = DataConnect.getConnection();
			ps3 = con.prepareStatement("delete from commentaire where id_prob=?;");
			ps3.setString(1, String.valueOf(p));
			int rs3 = ps3.executeUpdate();
			
			ps = con.prepareStatement("delete from problem where id_prob=?;");
			ps.setString(1, String.valueOf(p));
			int rs = ps.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
	}
	
	public static Problem getProblem(int id) {
		System.out.println("Je recupere le probleme");
		Connection con = null;
		PreparedStatement ps = null;
		Problem pr = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from problem where id_prob=?;");
			ps.setString(1, String.valueOf(id));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				//result found, means valid inputs
				pr = new Problem();
				pr.setId(rs.getInt("id_prob"));
				pr.setText_prob(rs.getString("texte_prob"));
				pr.setId_user(rs.getInt("id_user"));
				pr.setTitre(rs.getString("titre"));
				
				
				return pr;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return null;
		
	}

	public static List<Solution> getSolution(int id) {
		System.out.println("Je recupere les solutions");
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		List<Solution> sols = new ArrayList<Solution>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from Solution where id_prob=?;");
			ps.setString(1, String.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//result found, means valid inputs
				Solution sol = new Solution();
				sol.setId_sol(rs.getInt("id_sol"));
				sol.setTexte_sol(rs.getString("texte_sol"));
				sol.setId_user(rs.getInt("id_user"));
				sol.setId_prob(rs.getInt("id_prob"));
				System.out.println("j'ai recuperer une solution"+" id_sol:"+sol.getId_sol()+" texte_sol:"+sol.getTexte_sol()+" id_user:"+sol.getId_user()+"id_prob"+sol.getId_prob());
				//ajouté :)
				ps1 = con.prepareStatement("select * from user where id_user=?;");
				ps1.setString(1, String.valueOf(rs.getInt("id_user")));
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next()) {
					sol.setNom_user(rs1.getString("nom"));
					sol.setPrenom_user(rs1.getString("prenom"));
				}				
				//ajouté :)
				sols.add(sol);
							
			}
			return sols;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return null;
	}

	public static List<Comment> getComments(int id) {
		// TODO Auto-generated method stub
		System.out.println("Je recupere les comments");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		List<Comment> comts = new ArrayList<Comment>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from commentaire where id_prob=?;");
			ps.setString(1, String.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//result found, means valid inputs
				Comment comm = new Comment();
				comm.setId(rs.getInt("id_comm"));
				comm.setText_comm(rs.getString("texte_comm"));
				comm.setId_user(rs.getInt("id_user"));
				comm.setId_prob(rs.getInt("id_prob"));
		        System.out.println("j'ai recuperer une commentaire"+" id_comm:"+comm.getId()+" texte_comm:"+comm.getText_comm()+" id_user:"+comm.getId_user()+"id_prob"+comm.getId_prob());
                
		      //ajouté :)
				ps1 = con.prepareStatement("select * from user where id_user=?;");
				ps1.setString(1, String.valueOf(rs.getInt("id_user")));
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next()) {
					comm.setNom_user(rs1.getString("nom"));
					comm.setPrenom_user(rs1.getString("prenom"));
				}				
				//ajouté :)
		        
				comts.add(comm);
							
			}
			return comts;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return null;
	}

	public static List<Problem> getSousProblems(int id) {
		// TODO Auto-generated method stub
		System.out.println("Je recupere les sous_probleme");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		List<Problem> pros = new ArrayList<Problem>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from Relation_Prob where id_prob=?;");
			ps.setString(1, String.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//result found, means valid inputs
				int id2= rs.getInt("id_sous_prob");
				ps2 = con.prepareStatement("select * from problem where id_prob=?;");
				ps2.setString(1, String.valueOf(id2));
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()) {
					Problem pr = new Problem();
					pr.setId(rs2.getInt("id_prob"));
					pr.setText_prob(rs2.getString("texte_prob"));
					pr.setId_user(rs2.getInt("id_user"));
					pr.setTitre(rs2.getString("titre"));
				    System.out.println("j'ai recuperer un Sous probleme"+" id_prob:"+pr.getId()+" titre:"+pr.getTitre()+"id_user"+pr.getId_user());

					pros.add(pr);
					
				}	
			}
			return pros;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return null;
	}

	public static int addComment(Comment commt) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		System.out.println("c'est une commentaire:"+"texte:"+commt.getText_comm()+"id_user:"+commt.getId_user()+"id_prob:"+commt.getId_prob());

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into commentaire(texte_comm,id_user,id_prob) values(?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, String.valueOf(commt.getText_comm()));
			ps.setString(2, String.valueOf(commt.getId_user()));
			ps.setString(3, String.valueOf(commt.getId_prob()));
			ps.executeUpdate();
			 ResultSet rSetIdGenerees = ps.getGeneratedKeys();
	            while (rSetIdGenerees.next()) {
	            	System.out.println("j'ai ajouter un commentaire le id:"+rSetIdGenerees.getInt(1));
	                return rSetIdGenerees.getInt(1);
	            }			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return 0;
		
	}

	public static void deleteComment(int id_comment) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps= con.prepareStatement("delete from commentaire where id_comm=?;");
			ps.setString(1, String.valueOf(id_comment));
			int rs = ps.executeUpdate();
			System.out.println("j'ai supprimer une commentaire de id:"+id_comment);
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		
	}

	public static ArrayList<Comment> getCommentSol(int id) {
		// TODO Auto-generated method stub
		System.out.println("Je recupere les comments");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ArrayList<Comment> comts = new ArrayList<Comment>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from commentaire where id_sol=?;");
			ps.setString(1, String.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//result found, means valid inputs
				Comment comm = new Comment();
				comm.setId(rs.getInt("id_comm"));
				comm.setText_comm(rs.getString("texte_comm"));
				comm.setId_user(rs.getInt("id_user"));
				comm.setId_sol(rs.getInt("id_sol"));
		        System.out.println("j'ai recuperer une commentaire"+" id_comm:"+comm.getId()+" texte_comm:"+comm.getText_comm()+" id_user:"+comm.getId_user()+"id_prob"+comm.getId_prob());
                
		      //ajouté :)
				ps1 = con.prepareStatement("select * from user where id_user=?;");
				ps1.setString(1, String.valueOf(rs.getInt("id_user")));
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next()) {
					comm.setNom_user(rs1.getString("nom"));
					comm.setPrenom_user(rs1.getString("prenom"));
				}				
				//ajouté :)
		        
				comts.add(comm);
							
			}
			return comts;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return comts;
	}

	public static void addRelationProb(int id, int c) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into Relation_prob values(?,?);");
			ps.setString(1, String.valueOf(id));
			ps.setString(2, String.valueOf(c));
			ps.executeUpdate();
						
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		
		
	}

	public static List<Problem> getProblemTitre(Problem prob) {
		// TODO Auto-generated method stub
		System.out.println("Je recupere le probleme");
		Connection con = null;
		PreparedStatement ps = null;
		Problem pr = null;
		List<Problem> l=new ArrayList<Problem>();

		try {
			con = DataConnect.getConnection();
			if(prob.getTitre()!=null) {
			ps = con.prepareStatement("select * from problem where titre LIKE ?;");
			ps.setString(1, '%'+prob.getTitre()+'%');
			}
			else
			{
				ps = con.prepareStatement("select * from problem;");
			}
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//result found, means valid inputs
				pr = new Problem();
				pr.setId(rs.getInt("id_prob"));
				pr.setText_prob(rs.getString("texte_prob"));
				pr.setId_user(rs.getInt("id_user"));
				pr.setTitre(rs.getString("titre"));	
				l.add(pr);
				
			}
			return l;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return l;
	}
   
	public static List<Problem> getProblemUser(User userR){
		System.out.println("Je recupere le probleme");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps4 = null;

		Problem pr = null;
		List<Problem> l=new ArrayList<Problem>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from User where nom=?;");
			ps1 = con.prepareStatement("select * from User where prenom=?;");
			ps2 = con.prepareStatement("select * from User where nom=? and prenom=?;");
			ps4 = con.prepareStatement("select * from User;");
			ResultSet rs=null;
			if(userR.getPrenom().length()>0 && userR.getNom().length()>0){
            	System.out.println("j'ai touver rien null");
            	ps2.setString(1, userR.getNom());
            	ps2.setString(2, userR.getPrenom());
            	rs=ps2.executeQuery();
            	
            }
			else if(userR.getPrenom().length()>0) {
            	System.out.println("j'ai touver le nom null");
            	ps1.setString(1, userR.getPrenom());
            	rs=ps1.executeQuery();
            }
            else if(userR.getNom().length()>0) {
            	System.out.println("j'ai touver le prenom null");
            	ps.setString(1, userR.getNom());
            	rs=ps.executeQuery();
            }
            else {
            	System.out.println("j'ai touver tout null");
            	rs=ps4.executeQuery();     	
            }
			
			while (rs.next()) {
				//result found, means valid inputs
				System.out.println("je trouve les problemes maintenents");
				PreparedStatement ps3 = con.prepareStatement("select * from Problem where id_user=?;");
				ps3.setString(1, String.valueOf(rs.getInt("id_user")));
				ResultSet rs3 = ps3.executeQuery();
				while (rs3.next()) {
					System.out.println("haaaaaaaaa il y'a un probleme");
				    pr = new Problem();
				    pr.setId(rs3.getInt("id_prob"));
				    pr.setText_prob(rs3.getString("texte_prob"));
				    pr.setId_user(rs3.getInt("id_user"));
				    pr.setTitre(rs3.getString("titre"));	
				    l.add(pr);
				}
				
			}
			return l;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return l;
		
	}
	

	
}

