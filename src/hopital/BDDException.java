package hopital;

import javax.swing.JOptionPane;

/**
 * Classe d'exception perso avec popup
 *
 */
@SuppressWarnings("serial")
public class BDDException extends Exception {

	public BDDException(String message) {
		super(message);
		JOptionPane.showMessageDialog(null, message, "Error",JOptionPane.ERROR_MESSAGE);
	}

}
