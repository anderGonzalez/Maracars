package recursos;

import java.awt.Graphics;

/**
 * Esta clase define un coche por sus dimensiones
 * 
 * @author Ander
 *
 */

import Interfaces.Dibujable;

public class Coche implements Dibujable {

	double pixelsPorMetro = 20; // px

	double ancho;

	public Coche(Double ancho) {
		this.ancho = ancho;
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

	public void setPixelsPorMetro(double pixelsPorMetro) {
		this.pixelsPorMetro = pixelsPorMetro;
	}

	@Override
	public void dibujar(Graphics g, int x, int y) {
		g.fillOval(	(int) (x - (ancho * pixelsPorMetro / 2)),
					(int) (y - (ancho * pixelsPorMetro / 2)),
					(int) (ancho * pixelsPorMetro), 
					(int) (ancho * pixelsPorMetro));

	}

}
