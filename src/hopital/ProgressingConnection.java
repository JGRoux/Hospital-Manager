package hopital;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

/**
 * Classe permettant d'afficher une progressbar pendant les temps d'envoi de requ�tes
 *
 */
@SuppressWarnings("serial")
public class ProgressingConnection extends JDialog {
	
	private JLabel lbl;
	private JProgressBar progress;
	
	/**
	 * Constructeur de la progress bar �tendu de JDialog
	 */
	public ProgressingConnection(){
	    this.progress = new JProgressBar();
	    this.progress.setIndeterminate(true);
	    this.lbl = new JLabel("Connecting...");
	    this.setLayout(new FlowLayout());
	    this.getContentPane().add(this.lbl);
	    this.getContentPane().add(this.progress);
	    this.getRootPane().setBorder(new LineBorder(Color.BLACK));
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    this.setUndecorated(true);

	    this.pack();
	    this.setLocationRelativeTo(getWindow());
	    this.setVisible(true);
	}
	
	
	/**
	 * Fais changer l'�tat de la progress bar � "Retrieving"
	 */
	public void Retrieving(){
		this.lbl.setText("Retrieving...");
	}
	
	
	/**
	 * Fais changer l'�tat de la progress bar � "Sending"
	 */
	public void Sending(){
		this.lbl.setText("Sending...");
	}
	
	
	/**
	 * Fais changer l'�tat de la progress bar � "Error"
	 */
	public void Error(){
		this.progress.setIndeterminate(false);
		this.lbl.setText("Error!");
		this.progress.setValue(100);
		this.progress.setForeground(Color.red);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    this.setCursor(null);
		this.dispose();
	}
	
	
	/**
	 * Fais changer l'�tat de la progress bar � "Done" et ferme la progress bar
	 */
	public void Close(){
		this.progress.setIndeterminate(false);
		this.lbl.setText("Done...");
		this.progress.setValue(100);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    this.setCursor(null);
		this.dispose();
	}
	
	
	/**
	 * R�cup�re la fenetre qui a appel� la progress bar afin de centrer celle-ci par rapport � l'autre fenetre
	 * @return Window
	 */
	private Window getWindow() {
	    Window result = null;
	    Window windows[]=Window.getWindows();
	    
	    for (int i = 0; i < windows.length; i++) {
	        if (windows[i].isActive()) {
	            result = windows[i];
	        }
	    }
	    return result;
	}
}
