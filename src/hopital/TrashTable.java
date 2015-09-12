package hopital;

import java.util.ArrayList;


import javax.swing.table.AbstractTableModel;
/**
 * AbstractTableModel dont les methodes ont été recodées afin 
 * de correspondre au role de la corbeille qui est établie par un arrayList
 * @author louis
 *
 */

public class TrashTable extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> elmtSup;
	
	
	public TrashTable(ArrayList<String> ar){
		this.elmtSup= ar;
		
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return elmtSup.size();
	}

	@Override
	public Object getValueAt(int index, int col) {
		// TODO Auto-generated method stub
		return this.elmtSup.get(index);
	}
	/**
	 * Retourne le arryaList de la corbeille
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getArray(){
		return this.elmtSup;
	}
	
	@Override
	public String getColumnName(int index){
		return "Corbeille";
		
	}

}
