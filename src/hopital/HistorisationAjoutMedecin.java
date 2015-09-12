package hopital;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Cette classe ajoute les activités liées au médecin et au patient, 
 * telles que Rendez-vous, Examens et Opérations chirurgicales.
 */
public class HistorisationAjoutMedecin extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String newTable = "hop";
	private JButton ajouter;
	private JComboBox<String> ajoutList;
	private String[] items;
	private ConnectionBDD connection;
	private JPanel pane;
	private JTextField date, heure, salle, patient, medecin;
	private JLabel dateLabel, heureLabel, salleLabel, patientLabel, medecinLabel;
	private String dateText, heureText, salleText;
	private int medecinText, patientText;

	/**
	 * Constructeur de la classe HIstorisationAjoutMedecin qui créé l'interface graphique
	 * @param connection
	 * @throws SQLException
	 */
	HistorisationAjoutMedecin(ConnectionBDD connection) throws SQLException{


		super("Génération et historisation - Médecin");
		this.connection = connection;
		// Set window icon
		this.setIconImage(new ImageIcon(this.getClass().getResource("/images/hopital_logo.png")).getImage());

		// set du gridLAyout
		this.pane = new JPanel(new GridLayout(12,12));

		this.setList();
		this.pane.setBorder(BorderFactory.createTitledBorder("Types de données à ajouter : "));
		this.ajoutList = new JComboBox<String>(this.items);
		this.ajoutList.addActionListener(this);

		//Ajout du comboBox au panel
		this.pane.add(ajoutList);

		//Création des TextFields
		this.date = new JTextField("", 20);
		this.heure = new JTextField("", 20);
		this.salle = new JTextField("", 20);
		this.patient = new JTextField("", 20);
		this.medecin = new JTextField("", 20);
		this.medecin.setBackground(new Color(250,250,130));
		this.patient.setBackground(new Color(250,250,130));

		//creation des labels et placement
		this.medecinLabel=new JLabel("Numéro de médecin : ");
		this.medecinLabel.setHorizontalAlignment(JLabel.LEFT);
		this.patientLabel=new JLabel("Numéro de patient : ");
		this.patientLabel.setHorizontalAlignment(JLabel.LEFT);
		this.dateLabel=new JLabel("Date au format AAAA-MM-JJ : ");
		this.dateLabel.setHorizontalAlignment(JLabel.LEFT);
		this.heureLabel=new JLabel("Heure : ");
		this.heureLabel.setHorizontalAlignment(JLabel.LEFT);
		this.salleLabel=new JLabel("Salle : ");
		this.salleLabel.setHorizontalAlignment(JLabel.LEFT);

		// ajout des labels
		this.pane.add(medecinLabel);
		this.pane.add(medecin);
		this.pane.add(patientLabel);
		this.pane.add(patient);
		this.pane.add(dateLabel);
		this.pane.add(date);
		this.pane.add(heureLabel);
		this.pane.add(heure);
		this.pane.add(salleLabel);
		this.pane.add(salle);


		// ajout des boutons
		this.ajouter=new JButton("Ajouter");
		this.ajouter.addActionListener(this);
		this.pane.add(ajouter);

		this.getContentPane().add(pane,BorderLayout.CENTER);
		this.getContentPane().add(ajouter, BorderLayout.PAGE_END);

		this.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		this.setSize (new Dimension (565, 450));
		this.setResizable (true);
		this.setLocationRelativeTo(null);
		this.setVisible (true);
	}


	/**
	 * Méthode qui initialise la liste du nom des tables
	 * @throws SQLException
	 */
	private void setList() throws SQLException{
		this.items =  new String[] { "examen", "operation", "rdv"};
	}

	/**
	 * Méthode permettant d'ajouter une table à la base de donnée
	 * @param connection
	 * @param newTable
	 * @throws SQLException
	 */
	public void addTable(ConnectionBDD connection, String newTable) throws SQLException{

		try {
			this.connection.executeUpdate("CREATE TABLE IF NOT EXISTS "+this.newTable+ "(Numero INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NumDocteur INT NOT NULL references docteur(numero), NumPatient DECIMAL(4,0) NOT NULL references malade(numero), Date DATE, Heure TEXT, Salle TEXT)");
		} catch (SQLException erreur) {
			erreur.printStackTrace();
		}
		System.out.println("Table créée avec succès");
	}


	/**
	 * Méthode qui ajoute les nouveaux champs à la table qui vient d'être créée
	 * @param connection
	 * @param dateText
	 */
	public void addColumns(ConnectionBDD connection, int medecinText, 
			int patientText, String dateText, 
			String heureText, String salleText){

		this.medecinText = Integer.parseInt(this.medecin.getText());
		this.patientText = Integer.parseInt(this.patient.getText());
		this.dateText = this.date.getText();
		this.heureText = this.heure.getText();
		this.salleText = this.salle.getText();

		String temp = "INSERT INTO " + this.newTable + " (NumDocteur, NumPatient, Date, Heure, Salle) "
				+ "VALUES('"+this.medecinText+"','"+this.patientText+"','"+this.dateText+"','"+this.heureText+"','"+this.salleText+"')";

		try {
			this.connection.executeUpdate(temp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	/**
	 * Méthode permettant de supprimer une table dans la base donnée
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
	 * Méthode qui récupère le champs du JComboBox et le renvoie
	 * @return newTable Nom de la table qui va être créée
	 */
	public String getItem(){
		this.newTable = this.ajoutList.getSelectedItem().toString();
		System.out.println(this.newTable+ " sélectionné");
		return this.newTable;
	}

	/**
	 * Méthode qui gère les actions liées aux boutons et à la
	 * sélection des champs du JComboBox. C'est ici qu'on appelle
	 * la méthode de création de Table.
	 */
	public void actionPerformed(ActionEvent e) {


		if(e.getSource().equals(ajoutList)){
			/* Recuperation du champ selectionné */
			getItem();
		}	

		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Ajouter")){
			try {

				addTable(this.connection, newTable);
				addColumns(this.connection, this.medecinText, this.patientText, this.dateText, this.heureText, this.salleText);
				JOptionPane.showMessageDialog(null, "New entry in table " + newTable + " created successfully!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}
}
