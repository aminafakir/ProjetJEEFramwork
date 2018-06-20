import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class LoginDAO {

	public static User validate(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		User u=null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from User where mail = ? and mot_de_passe = ?");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
				u=new User();
				u.setId(rs.getInt("id_user"));
				u.setNom(rs.getString("nom"));
				u.setPrenom(rs.getString("prenom"));
				u.setMail(rs.getString("mail"));
				u.setMot_de_passe(rs.getString("mot_de_passe"));
				u.setComm(rs.getString("comm"));
				
				return u;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return u;
		} finally {
			DataConnect.close(con);
		}
		return u;
	}
	
	public static List<Problem> getProblemes(User u,HttpSession session){
		Connection con = null;
		PreparedStatement ps = null;
		List<Problem> liste = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from problem where id_user=?;");
			ps.setString(1, String.valueOf(u.getId()));
			ResultSet rs = ps.executeQuery();
		    liste=new ArrayList<Problem>();
			System.out.println("je vais charcher des problemes tel que le id du user est:"+u.getId());
			while (rs.next()) {
				//result found, means valid inputs
				System.out.println("yaaaaaaay j'ai trouvé des problemes");
				Problem p=new Problem();
				p.setId(rs.getInt("id_prob"));
				p.setText_prob(rs.getString("texte_prob"));
				p.setId_user(rs.getInt("id_user"));
				p.setTitre(rs.getString("titre"));
				liste.add(p);
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return liste;				
	}

	public static boolean ChangerUserInfo(User u) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Update User set nom=? , prenom=? , comm=? , mail=? , mot_de_passe=? where id_user=? ");
			ps.setString(1, u.getNom());
			ps.setString(2, u.getPrenom());
			ps.setString(3, u.getComm());
			ps.setString(4,u.getMail());
			ps.setString(5,u.getMot_de_passe());
			ps.setInt(6,u.getId());
			int rs = ps.executeUpdate();			
				
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		
		return false;
	}

	public static boolean validmail(User u) {
		// TODO Auto-generated method stub
		System.out.println("je valide le mail");
		Connection con = null;
		PreparedStatement ps = null;
		String mail=null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select * from User where id_user!=?");
			ps.setInt(1,u.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				//result found, means valid inputs
				mail=rs.getString("mail");
				int idd=rs.getInt("id_user");
			    if(mail.equals(u.getMail()) && u.getId()!=idd)
			    {
			    	System.out.println("le mail n'est pas valide");
			    	return false;
			    }		
			}
			return true;
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
	}
}