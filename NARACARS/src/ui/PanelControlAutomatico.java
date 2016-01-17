package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.Controlador;
import Interfaces.Traducible;
import recursos.Datos;
import recursos.Idioma;

/**
 * Esta clase define un panel que es un controlador automatico
 * 
 * @author Joanes
 */


@SuppressWarnings("serial")
public class PanelControlAutomatico extends JPanel implements Traducible, Controlador, Observer, ActionListener{

	static final int RECTA1 = 2;
	static final int CURVA1 = 3;
	static final int RECTA2 = 4;
	static final int CURVA2 = 5;
	static final int RECTA3 = 6;
	final int COLUMNAS = 1;
	final int FILAS = 4;
	final int MARGEN = 30; //px
	final int ANCHO_COMPONENTE = 900; //px
	final int ALTO_COMPONENTE = 100; //px
	final int TAMAÑOFUENTE = 28;
	static int turno = 0;
	Datos datos;
	Idioma idioma;
	Timer actualizarDatos;
	VentanaPrincipal ventana;
	JLabel situacion;
	JButton reset_bt;
	JButton start_bt;
	JButton conjunto_bt;
	
	/**
	 * @param ventana
	 */
	public PanelControlAutomatico(VentanaPrincipal ventana){
		
		setLayout(new GridLayout(FILAS, COLUMNAS, MARGEN, MARGEN));
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		this.ventana= ventana;
		idioma= ventana.getIdioma();
		datos = ventana.getDatos();
		crearContenido();
		escribirTextos();
	}

	private void crearContenido() {
		add(crearPanelSituacion());
		add(crearSTART());
		add(crearRESET());
		add(crearNuevoConjunto());

	}

	private Component crearSTART() {
		JPanel panelStart = new JPanel(new FlowLayout());
		start_bt= new JButton();
		start_bt.addActionListener(this);
		start_bt.setActionCommand("start");
		panelStart.add(start_bt);		
		return panelStart;
	}

	private Component crearRESET() {
		JPanel panelReset = new JPanel(new FlowLayout());
		reset_bt= new JButton();
		reset_bt.setEnabled(false);
		reset_bt.addActionListener(this);
		reset_bt.setActionCommand("reset");
		panelReset.add(reset_bt);				
		return panelReset;
	}

	private Component crearNuevoConjunto() {
		JPanel panelNuevoConjunto = new JPanel(new FlowLayout());
		conjunto_bt = new JButton();
		conjunto_bt.addActionListener(this);
		conjunto_bt.setActionCommand("nuevo");
		panelNuevoConjunto.add(conjunto_bt);
		return panelNuevoConjunto;
	}
	
	private Component crearPanelSituacion() {
		JPanel panelEstado = new JPanel(new FlowLayout());
		situacion= new JLabel();
		panelEstado.add(situacion);
		return panelEstado;
	}
	
	@Override
	public void escribirTextos() {
		start_bt.setText(idioma.getProperty("Empezar", "Empezar"));
		start_bt.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		start_bt.setFont(new Font("Arial", Font.BOLD, TAMAÑOFUENTE));
		reset_bt.setText(idioma.getProperty("Parar", "Parar"));
		reset_bt.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		reset_bt.setFont(new Font("Arial", Font.BOLD, TAMAÑOFUENTE));
		conjunto_bt.setText(idioma.getProperty("NuevoConjunto", "Nuevo circuito"));
		conjunto_bt.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		conjunto_bt.setFont(new Font("Arial", Font.BOLD, TAMAÑOFUENTE));
		if(start_bt.isEnabled()){
			situacion.setText(idioma.getProperty("SimulacionNo", "Simulacion no empezada"));
		}else{
			situacion.setText(idioma.getProperty("SimulacionSi", "Simulacion empezada"));
		}
		situacion.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		situacion.setFont(new Font("Arial", Font.BOLD, TAMAÑOFUENTE));
		}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	@Override
	public void iniciarActualizacionDatos() {
		actualizarDatos = new Timer();
		actualizarDatos.schedule(new TimerTask() {
			
			@Override
			public void run() {
				calcularDistanciaRecorrida();			}
		}, 0, 1);
		
	}
	public void calcularDistanciaRecorrida(){
		double distanciaVuelta=(datos.getTiempo()/1000)*datos.getVelMax();
		double distanciaTotal=(datos.getTiempoTotal()/1000)*datos.getVelMax();
	
		switch (turno) {
		case 0:
			datos.setGiro(50);
			datos.setMotor(50);//TODO kalkulatu egin behar da zenbat den bidali beharrekoa
			if(datos.getConjunto().distanciaHasta(RECTA1)<=distanciaVuelta){
				turno++;
			}
			break;
		case 1:
			datos.setGiro(50+datos.getGiro_aux());
			datos.setMotor(50);//TODO kalkulatu egin behar da zenbat den bidali beharrekoa
			if(datos.getConjunto().distanciaHasta(CURVA1)<=distanciaVuelta){
				turno++;
			}
			break;
		case 2:
			datos.setGiro(50);
			datos.setMotor(50);//TODO kalkulatu egin behar da zenbat den bidali beharrekoa
			if(datos.getConjunto().distanciaHasta(RECTA2)<=distanciaVuelta){
				turno++;
			}
			break;
		case 3:
			datos.setGiro(50-datos.getGiro_aux());
			datos.setMotor(50);//TODO kalkulatu egin behar da zenbat den bidali beharrekoa
			if(datos.getConjunto().distanciaHasta(CURVA2)<=distanciaVuelta){
				turno++;
			}
			break;
		case 4:
			datos.setGiro(50);
			datos.setMotor(50);//TODO kalkulatu egin behar da zenbat den bidali beharrekoa
			if(datos.getConjunto().distanciaHasta(RECTA3)<=distanciaVuelta){
				datos.inicializarTiempoVuelta();
				turno=0;
			}
			break;

		default:
			break;
		}
		datos.setDistancia(distanciaTotal);
		datos.getConjunto().setDistancia(distanciaVuelta);
	}

	@Override
	public void terminarActualizarDatos() {
		actualizarDatos.cancel();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "start":
			datos.inicializarTiempoVuelta();
			datos.inicializarTiempoTotal();
			turno=0;
			iniciarActualizacionDatos();
			situacion.setText(idioma.getProperty("SimulacionSi", "Simulacion empezada"));
			reset_bt.setEnabled(true);
			start_bt.setEnabled(false);
			break;
		case "nuevo":
			ventana.accionConjunto.actionPerformed(null);
		case "reset":
			situacion.setText(idioma.getProperty("SimulacionNo", "Simulacion no empezada"));
			datos.inicializarTiempoVuelta();
			reset_bt.setEnabled(false);
			start_bt.setEnabled(true);
			actualizarDatos.cancel();
			break;

		default:
			break;
		}
	}
	
	
	
	
}
