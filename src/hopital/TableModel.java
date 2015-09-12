package hopital;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

/**
 * TableModel personnalisé pour un ResultSet
 *
 */
@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {

	// Rewriting table model for resultset
	private ResultSet result;
	private boolean isEditable=false;

	public TableModel(ResultSet result) {
		this.result=result;
	}

	public void setData(ResultSet result) {
		this.result=result;
	}

	public ResultSet getData() {
		return this.result;
	}

	@Override
	public int getRowCount() {
		try {
			result.last();
			return result.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		try {
			return result.getMetaData().getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getColumnName(int column) {
		try {
			return result.getMetaData().getColumnLabel(column+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getValueAt(int row, int column) {
		try {
			result.absolute(row+1);
			return result.getObject(column+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Méthode appelée lors de la mise à jour d'un champ
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		try {
			result.absolute(row+1);
			result.updateObject(column+1,value);
			result.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Class<?> getColumnClass(int column) {
		try {
			return Class.forName(result.getMetaData().getColumnClassName(column+1));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Object.class;
	}

	// Disable table editing
	@Override
	public boolean isCellEditable(int row, int column) {
		return isEditable;
	}
	
	public void setEditable(boolean editable){
		this.isEditable=editable;
	}
	
	
}


