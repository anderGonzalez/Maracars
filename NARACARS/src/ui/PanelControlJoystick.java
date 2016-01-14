package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import Interfaces.Controlador;
import Interfaces.Traducible;
import recursos.Datos;
import recursos.Idioma;
import recursos.Joystick;

/**
 * Esta clase define un panel que es un controlador mediante un joystick
 * 
 * @author Ander
 */


@SuppressWarnings("serial")
public class PanelControlJoystick extends JPanel implements Traducible, Controlador, Observer{

	final int COLUMNAS = 1;
	final int FILAS = 3;
	final int MARGEN = 30; //px
	final int ANCHO_COMPONENTE = 900; //px
	final int ALTO_COMPONENTE = 100; //px
	
	Joystick mando;
	JLabel estadoDelLink;
	JProgressBar aceleracion;
	JProgressBar giro;
	Idioma idioma;
	Datos datos;
	Timer actualizarDatos;
	
	
	public PanelControlJoystick(VentanaPrincipal ventana){
		mando = new Joystick();
		mando.addObserver(this);
		this.idioma=ventana.getIdioma();
		setLayout(new GridLayout(FILAS, COLUMNAS, MARGEN, MARGEN));
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		datos = ventana.getDatos();
		iniciarActualizacionDatos();
		crearContenido();
		escribirTextos();
	}

	private void crearContenido() {
		add(crearLabelEstado());
		add(crearPBAceleracion());
		add(crearPBGiro());
	}

	private Component crearPBGiro() {
		JPanel panelGiro = new JPanel(new FlowLayout());
		
		giro = new JProgressBar();
		giro.setForeground(Color.BLUE);
		giro.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		panelGiro.add(giro);
		
		return panelGiro;
	}

	private Component crearPBAceleracion() {
		JPanel panelAceleracion = new JPanel(new FlowLayout());
		
		aceleracion = new JProgressBar();
		aceleracion.setForeground(Color.RED);
		aceleracion.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		panelAceleracion.add(aceleracion);
		
		return panelAceleracion;
	}

	private Component crearLabelEstado() {
		JPanel panelEstado = new JPanel(new FlowLayout());
		
		estadoDelLink = new JLabel("Conectando");
		estadoDelLink.setPreferredSize(new Dimension(ANCHO_COMPONENTE, ALTO_COMPONENTE));
		estadoDelLink.setFont(new Font("Arial", Font.BOLD, 32));
		panelEstado.add(estadoDelLink);
		
		return panelEstado;
	}
	
	@Override
	public void escribirTextos() {
		
		estadoDelLink.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
																idioma.getProperty("BordeConexion", "Estatus de la conexión: ")));
		aceleracion.setBorder(BorderFactory.createTitledBorder(	BorderFactory.createBevelBorder(BevelBorder.LOWERED),
																idioma.getProperty("BordeAceleracion", "Aceleración")));		
		giro.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
																idioma.getProperty("BordeGiro", "Giro")));
		}

	@Override
	public void update(Observable o, Object arg) {
		if(mando.isConectado()){
			estadoDelLink.setText(idioma.getProperty("Conectado", "Conectado"));
			aceleracion.setValue(mando.getAceleracion());
			giro.setValue(mando.getGiro());
			aceleracion.setEnabled(true);
			giro.setEnabled(true);
		}else{
			estadoDelLink.setText(idioma.getProperty("Desconectado", "Desconectado"));
			aceleracion.setValue(aceleracion.getMaximum()/2);
			giro.setValue(giro.getMaximum()/2);
			aceleracion.setEnabled(false);
			giro.setEnabled(false);
		}
	}
	
	@Override
	public void iniciarActualizacionDatos() {
		actualizarDatos = new Timer();
		actualizarDatos.schedule(new TimerTask() {
			
			@Override
			public void run() {
				PanelControlJoystick.this.datos.setMotor(PanelControlJoystick.this.mando.getAceleracion());
				PanelControlJoystick.this.datos.setGiro(PanelControlJoystick.this.mando.getGiro());
			}
		}, 0, 19);
		
	}

	@Override
	public void terminarActualizarDatos() {
		actualizarDatos.cancel();
		
	}
	
	
	
	
}
