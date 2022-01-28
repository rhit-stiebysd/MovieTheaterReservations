

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	JFrame frame;

	JPanel cards;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		DatabaseConnectionService dbConService = new DatabaseConnectionService("titan.csse.rose-hulman.edu",
				"MovieReservations");
		new Gui(dbConService);
	}

}
