package hopital;



import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * Classe affichant la corbeille à l'écran à partir d'un arrayList 
 * passé par la classe Storage dans le constructeur
 * @author louis
 *
 */
public class CorbeilleTable extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable myCorbeille;
	private int row;

	private Storage store;



	public CorbeilleTable (Storage store) {
		// Use rewriting model


		this.store =store;
		TrashTable myTrash = new TrashTable(this.store.getArrayAff());

		this.myCorbeille = new JTable(myTrash);
		this.myCorbeille.setAutoCreateRowSorter(true);
		this.myCorbeille.setDefaultRenderer(Object.class, new MyRenderer());
		this.myCorbeille.setDefaultRenderer(BigDecimal.class, new MyRenderer());
		this.myCorbeille.setCursor(new Cursor(Cursor.HAND_CURSOR));

		this.myCorbeille.setIntercellSpacing(new Dimension(0, 0));
		this.myCorbeille.setOpaque(false);
		// this.myTable.setRowSelectionAllowed(true);

		// Get motions
		this.myCorbeille.addMouseMotionListener(new TableListener());
		// Get double click on table
		this.myCorbeille.addMouseListener(new TableListener());
		this.setViewportView(this.myCorbeille);
	}
	
	/**
	 * retourne la jtable de la table corbeille
	 * @return JTable
	 */
	public JTable getTable(){
		return this.myCorbeille;
	}


	private class TableListener extends MouseAdapter {
		// Highlighting table when mouse move
		public void mouseMoved(MouseEvent e) {
			row = myCorbeille.rowAtPoint(e.getPoint());
			myCorbeille.repaint();
		}
	}

	@SuppressWarnings("serial")
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

}

