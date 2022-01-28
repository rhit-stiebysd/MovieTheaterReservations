

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MovieService {

	private DatabaseConnectionService dbService = null;

	public MovieService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addMovie(String restName, String addr, String contact) {
		try {
			String call = "EXEC AddRestaurant @restName = '" + restName + "'";
			if (addr != null && !addr.isEmpty()) {
				call += ", @addr = '" + addr + "'";
			}
			if (contact != null && !contact.isEmpty()) {
				call += ", @contact = '" + contact + "'";
			}
			CallableStatement cs = this.dbService.getConnection().prepareCall(call);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred during adding restaurant. See stack trace.");
			return false;
		}
		return true;
	}

	public ArrayList<String> getMovies() {
		ArrayList<String> movies = new ArrayList<String>();
		String query = "SELECT * from Movie";
		try {
			Statement stmt = this.dbService.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				movies.add(rs.getString("Movie"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}
}
