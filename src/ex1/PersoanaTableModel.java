package ex1;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PersoanaTableModel extends AbstractTableModel{

	private static final int ID_COL=0;
	private static final int NUME_COL=1;
	private static final int VARSTA_COL=2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String[] columnNames = {
			"id",
			"nume",
			"varsta"
	};
	private List<Persoana> list_pers;
	
	public PersoanaTableModel(List<Persoana> persons) {
		list_pers=persons;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return list_pers.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		Persoana tempPers=list_pers.get(row);
		
		switch(col) {
		case ID_COL:
			return tempPers.getId();
		case NUME_COL:
			return tempPers.getNume();
		case VARSTA_COL:
			return tempPers.getVarsta();
			default:
				return tempPers.getId();
		}
	}
		
	
	@Override
	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0,c).getClass();
	}

}
