package hopital;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Panel Update qui affiche une liste deroulante de toutes les tables de données
 * ainsi qu'un bouton qui appelle la fenêtre d'affichage en fonction de la table selectionnée
 * @author louis
 *
 */
@SuppressWarnings("serial")
public class Update extends JPanel implements ActionListener{

	private ConnectionBDD connection;
	protected String[] items;
	private JButton update;
	private JComboBox<String> list;
	private ResultsFrame rf;
	private String NameTable = "chambre"; /** Valeur par défaut */
	//private JPanel bottomP;
	private JPanel topP;
	private Storage store;
	
	/** 
	 * Constructeur 
	 */
	public Update(ConnectionBDD connection, Storage store){
		super();
		this.connection=connection;
		this.store =store;

		/** Creation des panels */
		//this.bottomP =  new JPanel();
		this.topP = new JPanel();
		this.topP.setLayout(new GridLayout(2,1));
		this.topP.setSize(WIDTH, 100);
		//this.bottomP.setLayout(new FlowLayout());

		try {
			this.setList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Création d'une liste de séelction interactive */
		this.list=new JComboBox<String>(this.items);
		this.list.addActionListener(this);

		// Ajout d'un texte explicatif */
		// Ajout d'un bouton */
		

		/*java.net.URL iconLocation = this.getClass().getResource("/images/update.png"); 
		System.out.println("Icon is at: " + iconLocation);
		ImageIcon icon = new ImageIcon(iconLocation, "Mettre à jour");*/

		this.update=new JButton("Mettre à jour");
		this.update.addActionListener(this);
		
		this.setLayout(new BorderLayout());
		this.topP.add(this.add(new JLabel("Sélectionnez la table a mettre à jour : ")));
		this.topP.add(this.list);
		this.add(topP,BorderLayout.NORTH);
		this.add(this.update,BorderLayout.SOUTH);
	}

	
	/**
	 * Methode retournant un tableau de string items contenant les noms des différentes tables 
	 * @param connection
	 * @return String tables[]
	 * @throws SQLException
	 */
	public String[] getTables (ConnectionBDD connection) throws SQLException{
		int tempCount = 0; //** Compteur temporelle pour remplir le tableau */
		//** On recupère les données à partir de la connection */
		DatabaseMetaData database = connection.getConnection().getMetaData();

		//** On récupère les infos */
		ResultSet tables = database.getTables(connection.getConnection().getCatalog(),null, "%", null);

		//** On les range dans un tableau */
		System.out.println(tables.getMetaData().getColumnCount());
		String [] itemTables = new String[tables.getMetaData().getColumnCount()+10];
		while (tables.next()){

			String nomTable = tables.getString("TABLE_NAME");
			//System.out.println(nomTable);
			itemTables[tempCount] = nomTable;
			tempCount++;
		}
		return itemTables;



	}
	private void setList() throws SQLException{

		//.items = new String[] {"soigne","hospitalisation","malaade","chambre","infirmier","service","docteur","employé"};

		this.items = getTables(this.connection);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(list)){
			//* Recuperation du champ selectionner */
			String s = this.list.getSelectedItem().toString();
			//System.out.println(s);
			this.NameTable = s;	
		}


		if (e.getSource().equals(update))
		{
			final Requete requete=new Requete(this.NameTable);
			final ProgressingConnection progress=new ProgressingConnection();
			final Storage store = this.store;

			// Lancement de la progress bar dans un nouveau Thread car le Thread swing est bloqué sur l'action button pendant la connexion
			new Thread(new Runnable() {
				public void run() {
					try {
						requete.SendRequete(connection,progress);
						rf = new ResultsFrameUpdate(requete,connection, NameTable,store);
						rf.getTableAModifier();
					} catch (SQLException e) {
						e.printStackTrace();
						progress.Error();
					}
				}
			}).start();
		}
	}
}
 