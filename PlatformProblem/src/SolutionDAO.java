import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

public class SolutionDAO {
	
	public static int addComment(Comment commt) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		System.out.println("c'est une commentaire:"+"texte:"+commt.getText_comm()+"id_user:"+commt.getId_user()+"id_prob:"+commt.getId_prob());

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into commentaire(texte_comm,id_user,id_sol) values(?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, String.valueOf(commt.getText_comm()));
			ps.setString(2, String.valueOf(commt.getId_user()));
			ps.setString(3, String.valueOf(commt.getId_sol()));
			ps.executeUpdate();
			 ResultSet rSetIdGenerees = ps.getGeneratedKeys();
	            while (rSetIdGenerees.next()) {
	            	System.out.println("j'ai ajouter une commentaire le id:"+rSetIdGenerees.getInt(1));
	                return rSetIdGenerees.getInt(1);
	            }			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return 0;
		
	}

	public static void DeleteSolution(int id_sol) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			con = DataConnect.getConnection();
			ps1 = con.prepareStatement("delete from commentaire where id_sol=?;");
			ps1.setString(1, String.valueOf(id_sol));
			int rs1 = ps1.executeUpdate();
			
			con = DataConnect.getConnection();
			ps = con.prepareStatement("delete from Solution where id_sol=?;");
			ps.setString(1, String.valueOf(id_sol));
			int rs = ps.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		
	}

	public static int AddSolution(Solution solt) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into Solution(texte_sol,id_user,id_prob) values(?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, String.valueOf(solt.getTexte_sol()));
			ps.setString(2, String.valueOf(solt.getId_user()));
			ps.setString(3, String.valueOf(solt.getId_prob()));
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

}
