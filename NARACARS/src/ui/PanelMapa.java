package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import recursos.Conjunto;


/**
 * Panel en el que se dibuja un conjunto
 * 
 * @author Ander
 */


public class PanelMapa extends JPanel implements Observer {
	private static final long serialVersionUID = 2245416530216305191L;
	final int ANCHO = 900;
	final int ALTO = 900;
	
	Conjunto conjunto;
	
	public PanelMapa(Conjunto conjunto) {
		super();
		this.conjunto = conjunto;
		conjunto.addObserver(this);
		escalar();
		setPreferredSize(conjunto.getDimension());
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				conjunto.addDistancia(0.15);
				
			}
		}, 0, 50);
		
	}
	
	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}
	
	@Override
	public void paint(Graphics g){
		setOpaque(false);
		conjunto.dibujar(g, 0, 0);
		
	}

	public void escalar(){
		Dimension defaultDim = conjunto.getDimension();
		double newPixelPM;
		if(defaultDim.getWidth() < defaultDim.getHeight()){
			newPixelPM = ALTO/defaultDim.getHeight() * conjunto.getPIXELS_POR_METRO();
			conjunto.setPIXELS_POR_METRO(newPixelPM);
		}else{
			newPixelPM = ANCHO/defaultDim.getWidth() * conjunto.getPIXELS_POR_METRO();
			conjunto.setPIXELS_POR_METRO(newPixelPM);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		escalar();
		
		setPreferredSize(conjunto.getDimension());
		
		this.repaint();
	}
	
	
	
	
}
