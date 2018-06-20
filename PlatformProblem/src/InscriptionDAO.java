import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class InscriptionDAO {

	public static void addUser(User u) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("insert into user(nom,prenom,mail,mot_de_passe,comm) values(?,?,?,?,?);");
			ps.setString(1, String.valueOf(u.getNom()));
			ps.setString(2, String.valueOf(u.getPrenom()));
			ps.setString(3, String.valueOf(u.getMail()));
			ps.setString(4, String.valueOf(u.getMot_de_passe()));
			ps.setString(5, String.valueOf(u.getComm()));
			ps.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		
	}

}
