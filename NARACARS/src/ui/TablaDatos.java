package ui;

import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Interfaces.Traducible;
import recursos.Datos;
import recursos.Idioma;

@SuppressWarnings("serial")
public class TablaDatos extends JTable implements Traducible{ 
	//TODO txukunduta
	final int DIGITOS = 2;
	final int FRECUENCIA = 1;
	final int NOMBRES = 0;
	final int DATOS = 1;
	final int VELOCIDAD = 0;
	final int OBSTACULO = 1;
	final int GIRO = 2;
	final int MOTOR = 3;
	final int DISTANCIA = 4;
	RendererTabla renderer;
	DefaultTableModel model;
	Datos datos;
	Idioma idioma;
	public TablaDatos(VentanaPrincipal ventana) {
		String[][] rowData = { {"Velocidad", ""}, { "Obstaculo", ""},
				{"Giro", ""},{"Motor",""},{"Distancia Total"} };
		String[] columnNames = { "Nombre", "Valor" };
		renderer = new RendererTabla();
		model = new DefaultTableModel(rowData, columnNames);
		this.datos = ventana.getDatos();
		this.idioma= ventana.getIdioma();
		setModel(model);
		setDefaultRenderer(Object.class, renderer);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		setFillsViewportHeight(true);
		setEnabled(false);
		escribirTextos();
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

	@Override
	public void escribirTextos() {
		 getColumnModel().getColumn(NOMBRES).setHeaderValue(idioma.getProperty("Nombres", "Nombres"));
		 getColumnModel().getColumn(DATOS).setHeaderValue(idioma.getProperty("Valores", "Valores"));
		setValueAt(idioma.getProperty("Velocidad", "Velocidad"), VELOCIDAD, NOMBRES);
		setValueAt(idioma.getProperty("Obstaculo", "Obstaculo"), OBSTACULO, NOMBRES);
		setValueAt(idioma.getProperty("Giro", "Giro"), GIRO, NOMBRES);
		setValueAt(idioma.getProperty("Motor", "Motor"), MOTOR, NOMBRES);
		setValueAt(idioma.getProperty("Distancia", "Distancia"), DISTANCIA, NOMBRES);



	}
}
