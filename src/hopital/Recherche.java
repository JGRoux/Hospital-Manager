package hopital;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * Classe héritée de JPanel formant le panel de recherche
 *
 */
@SuppressWarnings("serial")
public class Recherche extends JPanel {
	private JButton rechercher;
	private JPanel global_panel;
	private RecherchePanel myPanel;


	/**
	 * Constructeur avec en paramètre la connection
	 * @param connection
	 */
	public Recherche(ConnectionBDD connection){
		this.global_panel=new JPanel();
		this.global_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollPane=new JScrollPane(this.global_panel);

		this.rechercher=new JButton("Rechercher");
		Listener myListener=new Listener(connection,this.global_panel);
		this.rechercher.addActionListener(myListener);

		this.setLayout(new BorderLayout());
		String[] selections_str={"Service","Chambre","Docteur","Infirmier","Malade"};
		this.myPanel=new RecherchePanelBase("Concerne",selections_str,myListener);
		this.global_panel.add(this.myPanel);
		this.add(this.rechercher,BorderLayout.PAGE_END);
		this.add(scrollPane,BorderLayout.CENTER);
	}
}

