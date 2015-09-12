package hopital;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * 
 * Classe héritée de JPanel créant un panel de selection des champs utilisateur
 *
 */
@SuppressWarnings("serial")
public class RecherchePanel extends JPanel {

	protected ArrayList<RechercheChamp> selections;
	protected JPanel panel;
	private String title;

	/**
	 * Constructeur prenant en parémètre le titre du panel, les champs à affichés, le listener
	 * @param title
	 */
	public RecherchePanel(String title){
		this.title=title;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createTitledBorder(title));

		this.panel=new JPanel();
		this.panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		this.panel.setAlignmentX(LEFT_ALIGNMENT);

		this.selections=new ArrayList<RechercheChamp>();
		this.add(panel);
	}

	/**
	 * Méthode permettant de récupérer les champs user
	 * @return ArrayList<RechercheChamp>
	 */
	public ArrayList<RechercheChamp> getSelections(){
		return this.selections;
	}

	/**
	 * Méthode permettant de récupérer le nom du panel
	 * @return String
	 */
	public String getTitle(){
		return this.title;
	}
}
