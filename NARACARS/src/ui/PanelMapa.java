package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import recursos.Conjunto;

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
	Conjunto conjunto;

	/**
	 * Constructor del panel mapa
	 * 
	 * @param conjunto Conjunto del panel para poder pintarlo en el panel
	 */
	public PanelMapa(Conjunto conjunto) {
		super();
		this.conjunto = conjunto;
		conjunto.addObserver(this);
		escalar();
		setPreferredSize(conjunto.getDimension());
	}

	/**
	 * Metodo que escala la imagen, cambiando la variable pixel por metro para
	 * asi poder ver mejor el conjunto
	 */
	public void escalar() {
		Dimension defaultDim = conjunto.getDimension();
		double newPixelPM;
		if (defaultDim.getWidth() < defaultDim.getHeight()) {
			newPixelPM = ALTO / defaultDim.getHeight() * conjunto.getPIXELS_POR_METRO();
			conjunto.setPIXELS_POR_METRO(newPixelPM);
		} else {
			newPixelPM = ANCHO / defaultDim.getWidth() * conjunto.getPIXELS_POR_METRO();
			conjunto.setPIXELS_POR_METRO(newPixelPM);
		}
	}

	@Override
	public void paint(Graphics g) {
		setOpaque(false);
		conjunto.dibujar(g, MARGEN_IZQUIERDA, MARGEN_ARRIBA);

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		escalar();

		setPreferredSize(conjunto.getDimension());

		this.repaint();
	}

	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto.setConjunto(conjunto);
	}

}
