package ui;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import recursos.Datos;

public class TablaDatos extends JTable{
	RendererTabla renderer= new RendererTabla();
	DefaultTableModel model;
	Datos datos;
	
	public TablaDatos (Datos datos){
		String[][] rowData = {{"Velocidad", ""+datos.getVelMax()},
				{"Obstaculo",""+datos.isObstaculo()},{"Giro", ""+datos.getGiro()}};
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
				if(datos.isObstaculo()){
					datos.setObstaculo(false);
				}else{
					datos.setObstaculo(true);
				}
				datos.setVelMax(datos.getVelMax()+1);
				setValueAt(""+datos.getVelMax(), 0, 1);
				setValueAt(""+datos.isObstaculo(), 1, 1);
				setValueAt(""+datos.getGiro(), 2, 1);		

			}
		}, 0, 2000);
	}
}
