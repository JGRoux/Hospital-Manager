package hopital;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * Classe qui g�re l'ajout d'activit�s li�es aux infirmiers et aux patients
 */
@SuppressWarnings("serial")
public class HistorisationAjoutInfirmier extends JFrame implements ActionListener {
	
	private String newTable = "hop";
	private JButton ajouter;
    private JComboBox<String> ajoutList;
	private String[] items;
	private ConnectionBDD connection;
	private JPanel pane;
	private JTextField infirmier, patient, medicament, prelevement;
	private JTextArea evolution;
	private JLabel infirmierLabel, patientLabel, medicamentLabel, evolutionLabel, prelevementLabel;
	private String medicamentText, evolutionText, prelevementText;
	private int infirmierText, patientText;
	
	/**
	 * Constructeur qui cr�er l'interface graphique pour ajouter des activit�s
	 * @param connection
	 * @throws SQLException
	 */
	HistorisationAjoutInfirmier(ConnectionBDD connection) throws SQLException{


		super("G�n�ration et historisation - Infirmier");
		
		
		this.connection = connection;

		this.pane = new JPanel(new GridLayout(13,2));
	
		this.setList();
  	   	this.pane.setBorder(BorderFactory.createTitledBorder("Types d'activit�s � ajouter : "));
  	   	this.ajoutList = new JComboBox<String>(this.items);
  	   	this.ajoutList.addActionListener(this);
       
  	   	//Ajout du comboBox au panel
  	   	this.pane.add(ajoutList);
     
  	   	//Cr�ation des TextFields
  	    this.infirmier = new JTextField("", 20);
  	    this.patient = new JTextField("", 20);
  	    
      	this.evolution = new JTextArea();
      	this.evolution.setLineWrap(true);
      	this.evolution.setWrapStyleWord(true);
      	//cr�ation des textFields
      	this.medicament = new JTextField("", 20);
      	this.infirmier.setBackground(new Color(250,250,130));
      	this.patient.setBackground(new Color(250,250,130));
      	this.prelevement = new JTextField("", 20);
 
      	
      	JScrollPane scroll = new JScrollPane(this.evolution);
      	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      	
      	// cr�ation des labels
		this.infirmierLabel=new JLabel("Num�ro d'infirmier : ");
		this.infirmierLabel.setHorizontalAlignment(JLabel.LEFT);
		this.patientLabel=new JLabel("Num�ro de patient : ");
		this.patientLabel.setHorizontalAlignment(JLabel.LEFT);
  	    this.evolutionLabel=new JLabel("Evolution et remarques: ");
		this.evolutionLabel.setHorizontalAlignment(JLabel.LEFT);
		this.medicamentLabel=new JLabel("Medicament : ");
		this.medicamentLabel.setHorizontalAlignment(JLabel.LEFT);
		this.prelevementLabel=new JLabel("Pr�l�vement/Injection : ");
		this.prelevementLabel.setHorizontalAlignment(JLabel.LEFT);

		//on ajoute les labels au panel
		
		this.pane.add(infirmierLabel);
		this.pane.add(infirmier);
		this.pane.add(patientLabel);
		this.pane.add(patient);
		this.pane.add(medicamentLabel);
		this.pane.add(medicament);
		this.pane.add(evolutionLabel);
		this.pane.add(scroll);
		this.pane.add(prelevementLabel);
		this.pane.add(prelevement);
	
      
  	   	this.ajouter=new JButton("Ajouter");
  	   	this.ajouter.addActionListener(this);
  	   	this.pane.add(ajouter);
  	    
  	    this.getContentPane().add(pane,BorderLayout.CENTER);
  	    this.getContentPane().add(ajouter, BorderLayout.PAGE_END);
  	    
		this.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
  	    this.setSize (new Dimension (600, 500));
  	    this.setResizable (true);
  	    this.setLocationRelativeTo(null);
  	    this.setVisible (true);
	}
	

	/**
	 * M�thode qui initialise les noms des tables qui seront cr��es
	 * @throws SQLException
	 */
	private void setList() throws SQLException{
		this.items =  new String[] { "prescription", "suivi", "entretien"};
	}
	
	/**
	 * M�thode permettant d'ajouter une table Prescription � la base de donn�e
	 * @param connection
	 * @param newTable
	 * @throws SQLException
	 */
	public void addTablePrescription(ConnectionBDD connection, String newTable) throws SQLException{

		try {
		    this.connection.executeUpdate("CREATE TABLE IF NOT EXISTS "+this.newTable+ "(Numero INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NumInfirmier INT NOT NULL references infirmier(numero), NumPatient DECIMAL(4,0) NOT NULL references malade(numero), Evolution TEXT, Medicament TEXT)");
		} catch (SQLException erreur) {
			erreur.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Table " + newTable + " cr��e avec succ�s !");
	}
	/**
	 * M�thode permettant d'ajouter une table Suivi � la base de donn�e
	 * @param connection
	 * @param newTable
	 * @throws SQLException
	 */
	public void addTableSuivi(ConnectionBDD connection, String newTable) throws SQLException{

		try {
		    this.connection.executeUpdate("CREATE TABLE IF NOT EXISTS "+this.newTable+ "(Numero INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NumInfirmier INT NOT NULL references infirmier(numero), NumPatient DECIMAL(4,0) NOT NULL references malade(numero), Evolution TEXT)");
		} catch (SQLException erreur) {
			erreur.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Table " + newTable + " cr��e avec succ�s !");
	}
	
	/**
	 * M�thode permettant d'ajouter une table Entretien � la base de donn�e
	 * @param connection
	 * @param newTable
	 * @throws SQLException
	 */
	public void addTableEntretien(ConnectionBDD connection, String newTable) throws SQLException{

		try {
		    this.connection.executeUpdate("CREATE TABLE IF NOT EXISTS "+this.newTable+ "(Numero INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NumInfirmier INT NOT NULL references infirmier(numero), NumPatient DECIMAL(4,0) NOT NULL references malade(numero), Evolution TEXT, Prelevement TEXT)");
		} catch (SQLException erreur) {
			erreur.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Table " + newTable + " cr��e avec succ�s !");
	}
	
	
	
	/**
	 * M�thode qui ajoute les nouveaux champs � la table Prescription qui vient d'�tre cr��e
	 * @param connection
	 * @param dateText
	 */
	public void addColumnsPrescription(ConnectionBDD connection, int infirmierText, int patientText, String evolutionText, String medicamentText){
		
			this.infirmierText = Integer.parseInt(this.infirmier.getText());
			this.patientText = Integer.parseInt(this.patient.getText());
			this.evolutionText = this.evolution.getText();
			this.medicamentText = this.medicament.getText();
		
			String temp = "INSERT INTO " + this.newTable + " (NumInfirmier, NumPatient, Evolution, Medicament) "
					+ "VALUES('"+this.infirmierText+"','"+this.patientText+"','"+this.evolutionText+"','"+this.medicamentText+"')";
			
			try {
				this.connection.executeUpdate(temp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	/**
	 *  M�thode qui ajoute les nouveaux champs � la table Suivi qui vient d'�tre cr��e
	 * @param connection
	 * @param infirmierText
	 * @param patientText
	 * @param evolutionText
	 */
	public void addColumnsSuivi(ConnectionBDD connection, int infirmierText, int patientText, String evolutionText){
		
		this.infirmierText = Integer.parseInt(this.infirmier.getText());
		this.patientText = Integer.parseInt(this.patient.getText());
		this.evolutionText = this.evolution.getText();
	
		String temp = "INSERT INTO " + this.newTable + " (NumInfirmier, NumPatient, Evolution) "
				+ "VALUES('"+this.infirmierText+"','"+this.patientText+"','"+this.evolutionText+"')";
		
		try {
			this.connection.executeUpdate(temp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
	/**
	 *  M�thode qui ajoute les nouveaux champs � la table Entretien qui vient d'�tre cr��e
	 * @param connection
	 * @param infirmierText
	 * @param patientText
	 * @param evolutionText
	 * @param prelevementText
	 * @param tensionText
	 */
	public void addColumnsEntretien(ConnectionBDD connection, int infirmierText, int patientText, String evolutionText, String prelevementText){
		
		this.infirmierText = Integer.parseInt(this.infirmier.getText());
		this.patientText = Integer.parseInt(this.patient.getText());
		this.evolutionText = this.evolution.getText();
		this.prelevementText = this.prelevement.getText();
	
		String temp = "INSERT INTO " + this.newTable + " (NumInfirmier, NumPatient, Evolution, Prelevement) "
				+ "VALUES('"+this.infirmierText+"','"+this.patientText+"','"+this.evolutionText+"','"+this.prelevementText+"')";
		
		try {
			this.connection.executeUpdate(temp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
	
	/**
	 * M�thode permettant de supprimer une table dans la base donn�e
	 * @param connection
	 * @param newTable
	 * @throws SQLException
	 */
	public void dropTable(ConnectionBDD connection, String newTable) throws SQLException{
		try{
			this.connection.executeUpdate("DROP TABLE " + this.newTable);
			
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
		
	/**
	 * M�thode qui r�cup�re le champs du JComboBox et le renvoie
	 * @return newTable Nom de la table qui va �tre cr��e
	 */
	public String getItem(){
		this.newTable = this.ajoutList.getSelectedItem().toString();
		System.out.println(this.newTable+ " s�lectionn�");
		
		if (this.newTable == "prescription") {
			this.medicament.setEditable(true);
			this.prelevement.setEditable(false);

		}
		else if(this.newTable == "suivi") {
			this.medicament.setEditable(false);
			this.prelevement.setEditable(false);

		}
		else if(this.newTable == "entretien"){
			this.medicament.setEditable(false);
			this.prelevement.setEditable(true);

		}
		return this.newTable;
	}
	
	/**
	 * M�thode qui g�re les actions li�es aux boutons et � la
	 * s�lection des champs du JComboBox. C'est ici qu'on appelle
	 * la m�thode de cr�ation de Table.
	 */
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource().equals(ajoutList)){
			/* Recuperation du champ selectionn� */
			getItem();
		}	
		
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Ajouter")){

			
			try {
				if(getItem() == "prescription"){
					addTablePrescription(this.connection, newTable);
					addColumnsPrescription(this.connection, this.infirmierText, this.patientText, this.evolutionText, this.medicamentText);
				}
				else if(getItem() == "suivi"){
					addTableSuivi(this.connection, newTable);
					addColumnsSuivi(this.connection, this.infirmierText, this.patientText, this.evolutionText);
				}
				else if(getItem() == "entretien"){
					addTableEntretien(this.connection, newTable);
					addColumnsEntretien(this.connection, this.infirmierText, this.patientText, this.evolutionText, this.prelevementText);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}
}

