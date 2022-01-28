import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomePage {
	
	Connection con;
	
	JFrame homeFrame;
	JPanel homePanel;
	
	public HomePage(Connection con) {
		this.con = con;
		homeFrame = new JFrame();
		homeFrame.setSize(1000,800);
		homeFrame.setVisible(true);
		listTheaters();
	}
	
	public void listTheaters() {
		String theaterQuery = "SELECT * FROM MovieTheater";
		ArrayList<String> theaters = new ArrayList<String>();
		try {
			PreparedStatement stmt = con.prepareStatement(theaterQuery);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				theaters.add(rs.getString("Name") + " " + rs.getString("Location") + " " + rs.getString("Franchise"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JComboBox<String> box = new JComboBox<String>();
		box.setBounds(1,1,1,1);
		for (int j = 0; j < theaters.size() - 1; j++) {
			box.addItem(theaters.get(j));
		}
		homeFrame.add(box);
	}
	public void listMovies() {
		String testQuery = "SELECT * FROM Movie";
		try {
			PreparedStatement stmt = con.prepareStatement(testQuery);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> movies = new ArrayList<String>();
			while (rs.next()) {
				
				movies.add(rs.getString("Name"));
				System.out.println(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
