package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import recursos.Conjunto;
import recursos.Datos;

//TODO txukunduta
/**
 * Panel en el que se dibuja un conjunto
 * 
 * @author Ander
 */

@SuppressWarnings("serial")
public class PanelMapa extends JPanel implements Observer {
	private static final int MARGEN_IZQUIERDA = 100; //px
	private static final int MARGEN_ARRIBA = 100; //px
	final int ANCHO = 900; //px
	final int ALTO = 900; //px
	Datos datos;

	/**
	 * Constructor del panel mapa
	 * 
	 * @param conjunto Conjunto del panel para poder pintarlo en el panel
	 */
	public PanelMapa(Datos datos) {
		super();
		this.datos = datos;
		datos.addObserver(this);
		escalar();
		setPreferredSize(datos.getConjunto().getDimension());
	}

	/**
	 * Metodo que escala la imagen, cambiando la variable pixel por metro para
	 * asi poder ver mejor el conjunto
	 */
	public void escalar() {
		Dimension defaultDim = datos.getConjunto().getDimension();
		double newPixelPM;
		if (defaultDim.getWidth() < defaultDim.getHeight()) {
			newPixelPM = ALTO / defaultDim.getHeight() * datos.getConjunto().getPIXELS_POR_METRO();
			datos.getConjunto().setPIXELS_POR_METRO(newPixelPM);
		} else {
			newPixelPM = ANCHO / defaultDim.getWidth() * datos.getConjunto().getPIXELS_POR_METRO();
			datos.getConjunto().setPIXELS_POR_METRO(newPixelPM);
		}
	}

	@Override
	public void paint(Graphics g) {
		setOpaque(false);
		datos.getConjunto().dibujar(g, MARGEN_IZQUIERDA, MARGEN_ARRIBA);

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		escalar();

		setPreferredSize(datos.getConjunto().getDimension());

		this.repaint();
	}

	public Conjunto getConjunto() {
		return datos.getConjunto();
	}

	public void setConjunto(Conjunto conjunto) {
		this.datos.getConjunto().setConjunto(conjunto);
	}

}
