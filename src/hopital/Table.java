package hopital;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cette classe permet d'afficher une table contenant les résultats d'un resultSet
 *
 */
@SuppressWarnings("serial")
public class Table extends JScrollPane {

	private JTable myTable;
	private int row;
    private Requete req;
	private Storage store;
	
    
	public Table(Requete requete) {
		// Use rewriting model
		TableModel myModel = new TableModel(requete.getResult());
		this.req = requete;
		this.myTable = new JTable(myModel);
		this.myTable.setAutoCreateRowSorter(true);
		this.myTable.setDefaultRenderer(Object.class, new MyRenderer());
		this.myTable.setDefaultRenderer(BigDecimal.class, new MyRenderer());
		this.myTable.setDefaultRenderer(Long.class, new MyRenderer());
		this.myTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		/*
		// Custom rendering for columns 0 and 1 (icons) && edit col/row sizes
		for (int i = 0; i < 2; i++) {
			this.myTable.getColumnModel().getColumn(i).setMaxWidth(25);
		}
		this.myTable.getTableHeader().setReorderingAllowed(false);
		this.myTable.getColumnModel().getColumn(4).setMaxWidth(100);
		this.myTable.getColumnModel().getColumn(4).setMinWidth(100);
		this.myTable.getColumnModel().getColumn(2).setMinWidth(200);
		this.myTable.setRowHeight(20);
		this.myTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		 */
		// No borders
		this.myTable.setIntercellSpacing(new Dimension(0, 0));
		this.myTable.setOpaque(false);
		// this.myTable.setRowSelectionAllowed(true);

		// Get motions
		this.myTable.addMouseMotionListener(new TableListener());
		// Get double click on table
		this.myTable.addMouseListener(new TableListener());
		this.setViewportView(this.myTable);
	}


	public JTable getTable() {
		return this.myTable;
	}

	private class TableListener extends MouseAdapter {
		// Highlighting table when mouse move
		public void mouseMoved(MouseEvent e) {
			row = myTable.rowAtPoint(e.getPoint());
			myTable.repaint();
		}
	}

	private class MyRenderer extends DefaultTableCellRenderer {
		// Table modified renderer

		public MyRenderer() {
			setOpaque(true);
		};

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowc, int column) {

			this.setText(value.toString());
			// On select highlight the row
			if (isSelected) {
				this.setBackground(table.getSelectionBackground());
				this.setForeground(table.getSelectionForeground());
			} else {
				// On mouse over (only if no selected row)
				if (row == rowc) {
					this.setBackground(new Color(230, 240, 250));
					this.setForeground(Color.black);
				} else {
					this.setBackground(table.getBackground());
					this.setForeground(table.getForeground());
				}
			}
			return this;
		}
		
		
			}
	/**
	 * Supprime une ligne du rang selectionné dans le resultSEt
	 * @param row
	 * @param progress
	 */
	public void supprimerLigne ( int row, ProgressingConnection progress){
		progress.Retrieving();
		try {

			this.req.getResult().absolute(row+1);
			this.req.getResult().deleteRow();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		progress.Close();
	}
	/**
	 * Ajoute une ligne dans la database
	 * @param nomTable
	 * @param nbrCol
	 * @param champs
	 * @param jtf
	 * @param colonne
	 * @param conne
	 * @param rf
	 */
 public void ajouterLigne(String nomTable, final int nbrCol, String[] champs, final JTextField[] jtf, String [] colonne, ConnectionBDD conne, final ResultsFrameUpdate rf,Storage store){

		// TODO Auto-generated method stub
	 this.store=store;
		String temp = "INSERT "+nomTable + " (";
		{

			for (int i = 0;i<nbrCol;i++){

				champs[i] = jtf[i].getText();
				//System.out.println(this.colonnes[i]);
				temp =  temp.concat("" + colonne[i] +"," );
				//System.out.println(this.champs[i]);


			}
			temp =  temp.substring(0, temp.length()-1);
			temp = temp.concat(") VALUES (");

			for (int i = 0; i<nbrCol;i++){

				temp= temp.concat("'"+ champs[i] + "',");
			}
			temp = temp.substring(0, temp.length()-1);
			temp = temp.concat(")");
			
			System.out.println(temp);

			final Requete r = new Requete();
			
			final String requete = temp;
			final ProgressingConnection progress=new ProgressingConnection();
			final ConnectionBDD connection = conne;
			// Lancement de la progress bar dans un nouveau Thread car le Thread swing est bloqué sur l'action button pendant la connexion
			new Thread(new Runnable() {
				public void run() {
					try {
						r.requetUpdate(requete);
						r.SendUpdate(connection, progress);
						new JOptionPane();
						JOptionPane.showMessageDialog(null, "Ligne insérée !", "Information", JOptionPane.INFORMATION_MESSAGE);
						for(int i=0; i<nbrCol;i++){
							jtf[i].setText("");
						}
						
						
						rf.dispose();
						
						//Recuperer le nouveau result set
						//new ResultsFrameUpdate(rfu.getRequete(),connection,nomTable);
					} catch (SQLException e) {
						e.printStackTrace();
						progress.Error();
					}
				}
			}).start();
		}
		ProgressingConnection progress=new ProgressingConnection();
		this.req = new Requete(nomTable);
		try {
			req.SendRequete(conne,progress );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ResultsFrameUpdate(req,conne,nomTable,this.store);
 }
}

