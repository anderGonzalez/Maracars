package ui;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import recursos.Datos;

@SuppressWarnings("serial")
public class TablaDatos extends JTable{
	RendererTabla renderer= new RendererTabla();
	DefaultTableModel model;
	Datos datos;
	
	public TablaDatos (Datos datos){
		String[][] rowData = {{"Velocidad", ""+datos.getVelMax()},
				{"Obstaculo",""+datos.isObstaculo()},{"Giro", ""+datos.getGiro()},{"Motor",""+datos.getMotor()}};
		String[] columnNames = {"Izena", "Balorea"};
		this.datos= datos;
		model= new DefaultTableModel(rowData, columnNames);
		setModel(model);
		setDefaultRenderer(Object.class, renderer);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		setFillsViewportHeight(true);
		procesoTablaDatos();
	}
	
	public void procesoTablaDatos() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				setValueAt(""+datos.getVelMax(), 0, 1);
				setValueAt(""+datos.isObstaculo(), 1, 1);
				setValueAt(""+datos.getGiro(), 2, 1);
				setValueAt(""+datos.getMotor(), 3, 1);

			}
		}, 0, 1);
	}
}
