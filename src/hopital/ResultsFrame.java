package hopital;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 * Classe permettant d'affcher un resultSet dans une nouvelle fenêtre et dans une table
 *
 */
@SuppressWarnings("serial")
public class ResultsFrame extends JFrame {
	protected Table table;
	protected JTable jtable;

	public ResultsFrame(Requete requete){
		super("Résultat");

		// Set window icon
		this.setIconImage(new ImageIcon(this.getClass().getResource("/images/hopital_logo.png")).getImage());

		this.table=new Table(requete);
		this.getContentPane().add(table);

		this.pack();
		this.setSize(800,600);
		this.setLocationRelativeTo(null); // center on the screen
		this.setVisible(true);

		/**
		 * On recupère la JTable créee Afin de pouvoir la rendre interactive dans la classe update
		 */
		this.jtable = this.table.getTable();
	}


	public JTable getTableAModifier(){
		return this.jtable;
	}
}