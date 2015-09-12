package hopital;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Contient les JtextField en fonction du nombre de colonne de la jtable
 * ainsi qu'un bouton ajouter qui envois la requete d'insertion � la base de donn�es.
 * La fenetre se recharge d�s que le bouton est activ�
 * @author louis
 *
 */

@SuppressWarnings("serial")
public class PanelAjoute extends JPanel implements ActionListener  {

	private Table table;
	private JButton validation; //** Bouton de validation */
	private int nbrCol;
	private JTextField[] jtf ;
	private String[] champs;
	private String[] colonnes;
	private ConnectionBDD connection;
	private String nomTable;
	
	private ResultsFrameUpdate rfu;
	
	private Storage store;
	
	/**
	 * constructeur
	 * @param table
	 * @param connection
	 * @param nomTable
	 * @param rf
	 */
	public PanelAjoute(Table table, ConnectionBDD connection, String nomTable, ResultsFrameUpdate rf, Storage store){
		super();
		this.connection=connection;
		this.rfu = rf;
		this.store =store;
		this.setLayout(new FlowLayout());

		//** On r�cup�re la table */
		this.table = table;
		this.nomTable =nomTable;

		System.out.println("Le nom de la table est " + this.nomTable);




		//** Creation d'un bouton */
		this.validation = new JButton ("Valider");
		this.validation.addActionListener(this);


		//** On r�cup�re le nombre de colonne */
		this.nbrCol =  this.table.getTable().getColumnCount();
		//** on cr�er le tableau de jtextfield */
		this.jtf = new JTextField [this.nbrCol];

		//** et le tableau des champs � r�cuperer */
		this.champs = new String[this.nbrCol];
		this.colonnes = new String[this.nbrCol];

		//** On cr�er le m�me nombre de JTextField */
		for (int i=0; i< this.nbrCol;i++){
			this.jtf[i] = new JTextField(7);
			this.jtf[i].addActionListener(this);
			this.add(jtf[i]);

			//** on cr�er le tableau de nom de colonne */
			this.colonnes[i] = this.table.getTable().getColumnName(i);

		}
		//** On les ajoute avec le bouton � cot� */
		this.add(validation);
		
		// * Quand le bouton est appuy� (voir fonction ActionPerformed) 



		//** On recup�re les les champs */ 



		//** On envoit la requ�te  */


		/*t = new JTextField(10);
		t2 = new JTextField(10);

		this.add(t);
		this.add(t2);*/


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		 
		if(e.getSource().equals(validation)){
			
			// on ajoute la ligne quand le boutton est selectionn�
			this.table.ajouterLigne(this.nomTable, this.nbrCol, this.champs, this.jtf,this.colonnes, this.connection,this.rfu, this.store);
			
			}
			
			
	}
	}