package hopital;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
/**
 * Classe panel contenant la Corbeille Table
 * Elle possède un bouton permettant de restaurer les paramètres
 * @author louis
 *
 */

public class HistoriquePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CorbeilleTable tableC;
	private JButton restaurer;
	private JTable jtable;
	private Storage store;
	private ConnectionBDD connection;
	
	/**
	 * Constructor
	 * @param store
	 */
	public HistoriquePanel(Storage store, ConnectionBDD connexion){
		super();
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,1));
		
		this.store=store;
		this.tableC = new CorbeilleTable(this.store);
		this.jtable = tableC.getTable();
		this.connection=connexion;
		// On complete notre panel
		p.add(tableC);
		//bouton
		this.restaurer = new JButton("Restaurer");
		this.restaurer.addActionListener(this);
		
		//ajout des composants
		this.add(p, BorderLayout.CENTER);
		this.add(this.restaurer,BorderLayout.PAGE_END);
		
		
		

		
		this.setSize(800,600);
		this.setVisible(true);

		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	      int i=this.jtable.getSelectedRow();
	      this.store.restaurer(this.connection, i);
	}

}
