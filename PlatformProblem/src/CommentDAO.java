import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CommentDAO {

	public static void DeleteComment(int id) {
		// TODO Auto-generated method stu	Connection con = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("delete from commentaire where id_comm=?;");
			ps.setString(1, String.valueOf(id));
			int rs = ps.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		
	}

}
