package hopital;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
/**
 * Classe héritant de ResultsFrame affichant la table à ajourner
 * elle contient un bouton supprimer qui modifie la 
 * Base de données via le ResultSet
 *
 */
@SuppressWarnings("serial")
public class ResultsFrameUpdate extends ResultsFrame implements ActionListener {


	private JButton supprimerLigne;

	private ConnectionBDD connexion;
	private String nomTable;

	private JPanel pan2;
	private JPanel pButtons;

	private Requete req;

	private Object[] valeurs;
	private String[] colonnes;

	private Storage store;
	/**
	 * Constructeur de la fenetre d'affichage de la table à mettre à jour
	 * @param requete
	 * @param connection
	 * @param nomTable
	 */

	public ResultsFrameUpdate(Requete requete, ConnectionBDD connection, String nomTable,Storage store){
		///** Appel du constructeur parent */
		super(requete);
		this.jtable.setDefaultEditor(getClass(), new DefaultCellEditor ( new JTextField()));
		this.connexion = connection;
		this.nomTable = nomTable;
		this.req=requete;
		this.store=store;

		//On rend la table éditable pour modification
		((TableModel)this.table.getTable().getModel()).setEditable(true); 

		this.pan2 = new JPanel();
		this.pan2.setLayout(new BoxLayout(pan2,BoxLayout.Y_AXIS));
		///** On initialise les boutons */

		this.supprimerLigne = new JButton("Supprimer une ligne");


		this.supprimerLigne.addActionListener(this);


		///** On les ajoute à un panel*/
		this.pButtons = new JPanel();
		this.pButtons.setLayout(new FlowLayout());

		pButtons.add(supprimerLigne);

		pan2.add(new PanelAjoute(this.table,this.connexion, this.nomTable, this, this.store),BorderLayout.NORTH);
		pan2.add(pButtons);

		///** On ajoute le panel au sud */
		this.getContentPane().add(pan2,BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// on initialise les tableaux de récupération
		this.valeurs = new String[this.jtable.getColumnCount()];
		this.colonnes = new String[this.jtable.getColumnCount()];
	}

	/**
	 * Renvois la requete de la resultFrame update afin de pouvoir recharger à nouveau 
	 * cette table dés que l'ajout d'une ligne est faite
	 * @return Requete
	 */
	public Requete getRequete(){
		return this.req;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(supprimerLigne)){

			// on stocke les info afin de pouvoir restaurer cette ligne
			int row = jtable.getSelectedRow();		
			for (int i =0; i<jtable.getColumnCount();i++){
				this.colonnes[i]=	table.getTable().getColumnName(i);
				this.valeurs[i]= table.getTable().getValueAt(row, i).toString();
			}
			this.store.addAff(this.nomTable, this.valeurs, this.colonnes);


			final ProgressingConnection progress=new ProgressingConnection();
			final JTable jtable = this.jtable;
			final Table table = this.table;



			new Thread(new Runnable() {
				public void run() {
					progress.Retrieving();
					// On recupère le rang à supprimer
					int row = jtable.getSelectedRow();					
					//on supprime la ligne

					table.supprimerLigne( row,progress);


					//on avertit l'utilisateur
					new JOptionPane();
					JOptionPane.showMessageDialog(null, "Ligne Supprimée !", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}).start();



		}

	}	
}
