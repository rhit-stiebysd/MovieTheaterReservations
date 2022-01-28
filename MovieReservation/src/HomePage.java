import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class HomePage {

	Connection con;

	JFrame homeFrame;
	JPanel homePanel;
	int chosenTheaterID;
	int chosenShowID;
	int chosenMovieID;
	int chosenRoomID;
	JTable oldTable = new JTable();

	public HomePage(Connection con) {
		this.con = con;
		homeFrame = new JFrame("Home Page");
		homeFrame.setSize(1000, 800);
		homePanel = new JPanel();
		homeFrame.add(homePanel);
		homePanel.add(new JLabel("Select theater: "));
		homePanel.add(listTheaters());
		homePanel.add(new JLabel("Select Movie: "));
		homePanel.add(listMovies());
		homeFrame.setVisible(true);
	}

	public JComboBox<String> listTheaters() {
		String theaterQuery = "SELECT * FROM MovieTheater";
		ArrayList<String> theaters = new ArrayList<String>();
		ArrayList<Integer> theaterIds = new ArrayList<Integer>();
		try {
			PreparedStatement stmt = con.prepareStatement(theaterQuery);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				theaters.add(rs.getString("Name") + " " + rs.getString("Location") + " " + rs.getString("Franchise"));
				theaterIds.add(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JComboBox<String> box = new JComboBox<String>();
		for (int j = 0; j < theaters.size() - 1; j++) {
			box.addItem(theaters.get(j));
		}
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int chosenIndex = box.getSelectedIndex();
				chosenTheaterID = theaterIds.get(chosenIndex);
				homePanel.remove(oldTable);
				listShows();
			}
		});
		return box;
	}

	public void listShows() {
		String showQuery = "SELECT * FROM Shows WHERE MovieID = ? AND MovieTheaterID = ?";
		String[] header = { "Movie Name", "Time", "Room Number", "Theater"};
		String[][] body = new String[1000][4];
		try {
			PreparedStatement stmt = con.prepareStatement(showQuery);
			stmt.setInt(1, chosenMovieID);
			stmt.setInt(2, chosenTheaterID);
			ResultSet rs = stmt.executeQuery();
			int counter = 1;
			body[0][0] = "MovieID";
			body[0][1] = "Time";
			body[0][2] = "RoomID";
			body[0][3] = "TheaterID";
			while (rs.next()) {
				chosenMovieID = rs.getInt("MovieID");
				Timestamp time = rs.getTimestamp("Time");
				int rId = rs.getInt("RoomID");
				body[counter][0] = Integer.toString(chosenMovieID);
				body[counter][1] = time.toString();
				body[counter][2] = Integer.toString(rId);
				body[counter][3] = Integer.toString(chosenTheaterID);
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		oldTable = new JTable(body, header);
		oldTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = oldTable.rowAtPoint(evt.getPoint());
		        //int col = oldTable.columnAtPoint(evt.getPoint());
		        new ReservePage(con, body[row][0], body[row][1], body[row][2], body[row][3]);
		    }
		});
		oldTable.setPreferredSize(new Dimension(600, 400));
		homePanel.add(oldTable);
		homePanel.revalidate();
	}

	public JComboBox<String> listMovies() {
		String query = "SELECT * FROM Movie";
		ArrayList<String> movies = new ArrayList<String>();
		ArrayList<Integer> movieIDs = new ArrayList<Integer>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				movieIDs.add(rs.getInt("ID"));
				movies.add(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JComboBox<String> box = new JComboBox<String>();
		for (int j = 0; j < movies.size() - 1; j++) {
			box.addItem(movies.get(j));
		}
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int chosenIndex = box.getSelectedIndex();
				chosenMovieID = movieIDs.get(chosenIndex);
				homePanel.remove(oldTable);

				listShows();
			}
		});
		return box;
	}

}
