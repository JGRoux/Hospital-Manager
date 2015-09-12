package hopital;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * 
 * Classe h�rit�e de JPanel cr�ant un panel de selection des champs utilisateur
 *
 */
@SuppressWarnings("serial")
public class RecherchePanel extends JPanel {

	protected ArrayList<RechercheChamp> selections;
	protected JPanel panel;
	private String title;

	/**
	 * Constructeur prenant en par�m�tre le titre du panel, les champs � affich�s, le listener
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
	 * M�thode permettant de r�cup�rer les champs user
	 * @return ArrayList<RechercheChamp>
	 */
	public ArrayList<RechercheChamp> getSelections(){
		return this.selections;
	}

	/**
	 * M�thode permettant de r�cup�rer le nom du panel
	 * @return String
	 */
	public String getTitle(){
		return this.title;
	}
}
