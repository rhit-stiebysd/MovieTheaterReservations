import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

public class ReservePage {

	public Connection con;
	JFrame homeFrame;
	JPanel homePanel;
	int TheaterID;
	int ShowID;
	int MovieID;
	int RoomID;
	String time;

	public ReservePage(Connection con, String movie, String time, String room, String theater) {
		this.con = con;
		this.MovieID = Integer.parseInt(movie);
		this.time = time;
		this.RoomID = Integer.parseInt(room);
		this.TheaterID = Integer.parseInt(theater);
		getTheaterCapacity();
		TheaterJToggle();
	}

	private int getTheaterCapacity() {
		String query = "SELECT RoomCapacity FROM MovieTheater WHERE ID = ?";
		int seatNo = 0;
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, TheaterID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				seatNo = rs.getInt("RoomCapacity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seatNo;
	}

	private void TheaterJToggle() {
		int rows = 6;
		int columns = 8;
		Icon res = (UIManager.getIcon("OptionPane.errorIcon"));

		JPanel panel = new JPanel(new GridLayout(columns, rows));
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				final JToggleButton button = new JToggleButton("Row " + row + " seat " + column);
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
						boolean selected = abstractButton.getModel().isSelected();
						if (selected) {
							button.setIcon(res);
						} else {
							button.setIcon(null);
						}
					}
				});
				panel.add(button);
			}
		}
		final JFrame frame = new JFrame("JToggleButton Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

}