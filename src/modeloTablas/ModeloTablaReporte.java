package modeloTablas;

import entidades.Reporte;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaReporte extends AbstractTableModel {
	private static final int COLUMN_NO = 0;
	private static final int COLUMN_NOMBRE = 1;
	private static final int COLUMN_TIPO=2;
	private static final int COLUMN_FECHA=3;
	
	private String [] columnas = {"NO","NOMBRE","TIPO","FECHA"};
	
	private List<Reporte> listadeReportes;
	
	public ModeloTablaReporte(List<Reporte> listadeReportes){
		this.listadeReportes = listadeReportes;
		int indice = 1;
		for (Reporte e: listadeReportes) {
			e.setIndice(indice++);
		}
	}

	public int getColumnCount() {
		return columnas.length;
		
	}

	public int getRowCount() {
		return listadeReportes.size();
		
	}
	public String getColumnName(int idColumna){
		return columnas[idColumna];
	}
	
	
	public Class<?> getColumnClass(int idColumna) {
		if (listadeReportes.isEmpty()) {
			return Object.class;
		}
		return getValueAt(0, idColumna).getClass();
	}


	public Object getValueAt(int rowIndex, int columnIndex) {
		Reporte nuevoreporte = listadeReportes.get(rowIndex);
		Object returnValue = null;

		switch (columnIndex) {
		case COLUMN_NO:
			returnValue = nuevoreporte.getIndice();
			break;
		case COLUMN_NOMBRE:
			returnValue = nuevoreporte.getNombre();
			break;
		case COLUMN_TIPO:
			returnValue = nuevoreporte.getTipo();
			break;
		case COLUMN_FECHA:
			returnValue = nuevoreporte.getFecha();
			break;
	
		default:
			throw new IllegalArgumentException("Numero de columna invalido!");
		}

		return returnValue;
	}
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Reporte empresa = listadeReportes.get(rowIndex);
		if (columnIndex == COLUMN_NO) {
			empresa.setIndice((int) value);
		}
	}

}
