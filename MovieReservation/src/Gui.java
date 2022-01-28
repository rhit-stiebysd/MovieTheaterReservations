import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Gui {
	DatabaseConnectionService dbCon;
	Connection con;
	JFrame frame;
	JPanel loginPanel;

	String user;
	char[] pass;

	public Gui(DatabaseConnectionService con) {
		this.dbCon = con;
		frame = new JFrame("Movie Reservations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		loginPanel = new JPanel();
		createLoginView();
		frame.add(loginPanel);
		frame.setVisible(true);
	}

	private void createLoginView() {
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		JTextField userField = new JTextField(16);
		JPasswordField passField = new JPasswordField(16);
		JButton submitButton = new JButton("Login");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = userField.getText();
				pass = passField.getPassword();
				String password = "";
				for (int i = 0; i < pass.length; i++) {
					password+=pass[i];
				}
				dbCon.connect(user, password);
				con = dbCon.getConnection();
				new HomePage(con);
				frame.dispose();
			}
		});
		loginPanel.add(userLabel);
		loginPanel.add(userField);
		loginPanel.add(passLabel);
		loginPanel.add(passField);
		loginPanel.add(submitButton);

	}

}
