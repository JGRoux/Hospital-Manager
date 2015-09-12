package hopital;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Cette classe permet d'identifier l'utilisateur selon si c'est un médecin 
 * ou un infirmier
 */
public class HistorisationChoix extends JPanel implements ActionListener{
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JRadioButton medecin, infirmier;
	private JButton continuer;
	private JPanel pane;
	private ConnectionBDD connection;
	private ButtonGroup group;

	static String doc = "Docteur";
	static String infir = "Infirmier et Infirmière";
	static String cont = "Confirmer et Continuer";
	
	
	/**
	 * Constructeur de la classe HistorisationChoix qui initialise les 
	 * composants graphiques et les met en place dans la fenêtre.
	 * @param connection
	 */
	public HistorisationChoix(ConnectionBDD connection) {
		
		this.connection=connection;
		this.pane = new JPanel();
		this.pane.setLayout(new GridLayout(2,1));

		
		this.medecin=new JRadioButton(doc);
		this.medecin.setActionCommand(doc);
		this.medecin.setSelected(true);
		this.medecin.addActionListener(this);
		
		this.infirmier=new JRadioButton(infir);
		this.infirmier.setActionCommand(infir);
		this.infirmier.addActionListener(this);
		
		this.pane.setBorder(BorderFactory.createTitledBorder("Avant d'ajouter de nouvelles données, "
				+ "veuillez préciser votre poste au sein de l'hôpital : "));
		this.pane.add(this.medecin);
		this.pane.add(this.infirmier);
		
		this.group = new ButtonGroup();
	    this.group.add(medecin);
	    this.group.add(infirmier);
    
		this.continuer=new JButton(cont);
		this.continuer.addActionListener(this);
    
		this.setLayout(new BorderLayout());
		this.add(this.continuer, BorderLayout.SOUTH);
    	this.add(this.pane,BorderLayout.CENTER);
	}

	/**
	 * Méthode qui gère les actions et événement liées à l'utilisateur
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.print("ActionEvent received: ");
		
	     ButtonModel b = this.group.getSelection();
	     String t = "";
	     if (b!=null) t = b.getActionCommand();
	
	     //
	     if(t == doc && e.getActionCommand().equals(cont) ){
				try {
					new HistorisationAjoutMedecin(this.connection);
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	     }
	     else if(t == infir && e.getActionCommand().equals(cont)){
				try {
					new HistorisationAjoutInfirmier(this.connection);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
	     }
		
	}
}
