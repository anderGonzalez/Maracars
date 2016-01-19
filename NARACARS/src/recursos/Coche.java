package recursos;

import java.awt.Graphics;
import Interfaces.Dibujable;

/**
 * Esta clase define un coche por sus dimensiones
 * 
 * @author Ander
 *
 */

public class Coche implements Dibujable {

	double pixelsPorMetro = 20; // px
	double ancho;
	double longitud;
	double circunferenciaRueda;

	/**
	 * Constructor de la clase del coche
	 * 
	 * @param ancho
	 *            ancho del coche
	 * @param longitud
	 *            longitud del coche
	 */
	public Coche(Double ancho, Double longitud, Double circunferencia) {
		this.ancho = ancho;
		this.longitud = longitud;
		this.circunferenciaRueda = circunferencia;
	}

	@Override
	public void dibujar(Graphics g, int x, int y) {
		g.fillOval((int) (x - (ancho * pixelsPorMetro / 2)), (int) (y - (ancho * pixelsPorMetro / 2)),
				(int) (ancho * pixelsPorMetro), (int) (ancho * pixelsPorMetro));

	}

	public double getCircunferenciaRueda() {
		return circunferenciaRueda;
	}

	public void setCircunferenciaRueda(double circunferenciaRueda) {
		this.circunferenciaRueda = circunferenciaRueda;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getPixelsPorMetro() {
		return pixelsPorMetro;
	}

	/**
	 * @param pixelsPorMetro Los pixels por metro son una medida que hemos creado para que las medidas
	 * se puedan apreciar mejor. Este metodo hace el set de los pixels por
	 * metro.
	 */
	public void setPixelsPorMetro(double pixelsPorMetro) {
		this.pixelsPorMetro = pixelsPorMetro;
	}

}
