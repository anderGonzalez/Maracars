package ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import Interfaces.Controlador;
import Interfaces.Traducible;
import recursos.Idioma;
import recursos.Joystick;

@SuppressWarnings("serial")
public class PanelControlJoystick extends JPanel implements Traducible, Controlador, Observer{

	final int COLUMNAS = 1;
	final int FILAS = 3;
	final int MARGEN = 30; //px
	
	Joystick mando;
	JLabel estadoDelLink;
	JProgressBar aceleracion;
	JProgressBar giro;
	Idioma idioma;
	
	public PanelControlJoystick(VentanaPrincipal ventana){
		mando = new Joystick();
		mando.addObserver(this);
		this.idioma=ventana.getIdioma();
		setLayout(new GridLayout(FILAS, COLUMNAS, MARGEN, MARGEN));
		crearContenido();
		escribirTextos();
	}

	private void crearContenido() {
		add(crearLabelEstado());
		add(crearPBAceleracion());
		add(crearPBGiro());
	}

	private Component crearPBGiro() {
		giro = new JProgressBar();
		giro.setSize(900, 100);
		
		return giro;
	}

	private Component crearPBAceleracion() {
		aceleracion = new JProgressBar();
		aceleracion.setSize(900, 100);
		
		return aceleracion;
	}

	private Component crearLabelEstado() {
		estadoDelLink = new JLabel("Conectando");
	
		return estadoDelLink;
	}

	@Override
	public void enviarComando() {
		
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
		}else{
			estadoDelLink.setText(idioma.getProperty("Desconectado", "Desconectado"));
			aceleracion.setValue(0);
			giro.setValue(0);
		}
	}
	
	
	
	
}
