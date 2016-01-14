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

/**
 * Esta clase define un panel que es un controlador automatico
 * 
 * @author Joanes
 */


@SuppressWarnings("serial")
public class PanelControlAutomatico extends JPanel implements Traducible, Controlador, Observer, ActionListener{

	final int COLUMNAS = 1;
	final int FILAS = 4;
	final int MARGEN = 30; //px
	final int ANCHO_COMPONENTE = 900; //px
	final int ALTO_COMPONENTE = 100; //px
	
	Datos datos;
	Timer actualizarDatos;
	VentanaPrincipal ventana;
	JLabel situacion;
	JButton reset_bt, start_bt, conjunto_bt;
	
	public PanelControlAutomatico(VentanaPrincipal ventana){
		
		setLayout(new GridLayout(FILAS, COLUMNAS, MARGEN, MARGEN));
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		this.ventana= ventana;
		datos = ventana.getDatos();
		iniciarActualizacionDatos();
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
		start_bt.setText("START");
		start_bt.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		start_bt.setFont(new Font("Arial", Font.BOLD, 28));
		reset_bt.setText("RESET");
		reset_bt.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		reset_bt.setFont(new Font("Arial", Font.BOLD, 28));
		conjunto_bt.setText("NUEVO CONJUNTO");
		conjunto_bt.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		conjunto_bt.setFont(new Font("Arial", Font.BOLD, 28));
		if(start_bt.isEnabled()){
			situacion.setText("Simulación no iniciada");
		}else{
			situacion.setText("Simulación iniciada");
		}
		situacion.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		situacion.setFont(new Font("Arial", Font.BOLD, 32));
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
				
			}
		}, 0, 19);
		
	}

	@Override
	public void terminarActualizarDatos() {
		actualizarDatos.cancel();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "start":
			
			break;

		default:
			break;
		}
	}
	
	
	
	
}
