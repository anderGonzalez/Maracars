package ui;

import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import recursos.Datos;

@SuppressWarnings("serial")
public class TablaDatos extends JTable { 
	//TODO txukunduta
	final int DIGITOS = 2;
	final int FRECUENCIA = 1;
	final int DATOS = 1;
	final int VELOCIDAD = 0;
	final int OBSTACULO = 1;
	final int GIRO = 2;
	final int MOTOR = 3;
	final int DISTANCIA = 4;
	RendererTabla renderer;
	DefaultTableModel model;
	Datos datos;

	public TablaDatos(Datos datos) {
		String[][] rowData = { {"Velocidad", ""}, { "Obstaculo", ""},
				{"Giro", ""},{"Motor",""},{"Distancia Total"} };
		String[] columnNames = { "Nombre", "Valor" };
		renderer = new RendererTabla();
		model = new DefaultTableModel(rowData, columnNames);
		setModel(model);
		setDefaultRenderer(Object.class, renderer);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		setFillsViewportHeight(true);
		setEnabled(false);
		this.datos = datos;
		procesoTablaDatos();
	}

	public void procesoTablaDatos() {
		NumberFormat nf= NumberFormat.getInstance();
		nf.setMaximumFractionDigits(DIGITOS);
		nf.setMinimumFractionDigits(DIGITOS);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				setValueAt(""+datos.getVelMax(), VELOCIDAD, DATOS);
				setValueAt(""+datos.isObstaculo(), OBSTACULO, DATOS);
				setValueAt(""+datos.getAngulo(), GIRO, DATOS);
				setValueAt(""+datos.getGiro(), MOTOR, DATOS);
				if(datos.getConjunto()!=null){
					setValueAt(""+nf.format(datos.getDistancia()), DISTANCIA, DATOS);
				}else{
					setValueAt("0.0", DISTANCIA, DATOS);
				}
				

			}
		}, 0, FRECUENCIA);
	}
}
