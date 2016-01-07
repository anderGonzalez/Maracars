package recursos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

import Interfaces.Dibujable;

/**
 * Esta clase define un circuito compuesto por dos rectas paralelas y dos semicirculos del mismo radio.
 * 
 * @author Ander
 *
 */


public class Circuito extends Observable implements Dibujable {

	double pixelsPorMetro = 20;

	double radioGrande, radioPeque�o;
	double ancho;
	double recta;
	double friccion;

	public Circuito(double radioGrande, double anchoDePista, double recta, double friccion) {
		this.ancho = anchoDePista;
		this.radioGrande = radioGrande;
		this.radioPeque�o = radioGrande - anchoDePista;
		this.recta = recta;
		this.friccion= friccion;
		
	}
	
	@Override
	public void dibujar(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect(	(int)(x + (radioGrande * pixelsPorMetro)),
					(int) (y),
					(int) (recta * pixelsPorMetro), 
					(int) (radioGrande * 2 * pixelsPorMetro));
		g.fillOval(	x ,
					y ,
					(int) (2 * radioGrande * pixelsPorMetro),
					(int) (2* radioGrande * pixelsPorMetro));
		g.fillOval(	(int) (x + (recta * pixelsPorMetro)),
					y ,
					(int) (2 * radioGrande * pixelsPorMetro),
					(int) (2* radioGrande * pixelsPorMetro));
		g.setColor(Color.WHITE);
		g.fillRect(	(int)(x + ((ancho + radioPeque�o) * pixelsPorMetro)),
					(int) (y + (ancho * pixelsPorMetro)),
					(int) (recta * pixelsPorMetro), 
					(int) (radioPeque�o * 2 * pixelsPorMetro));
		g.fillOval(	(int) (x + (ancho * pixelsPorMetro)),
					(int) (y + (ancho * pixelsPorMetro)),
					(int) (2 * radioPeque�o * pixelsPorMetro),
					(int) (2 * radioPeque�o * pixelsPorMetro));
		g.fillOval(	(int) (x + ((recta + ancho)* pixelsPorMetro)),
					(int) (y + (ancho * pixelsPorMetro)),
					(int) (2 * radioPeque�o * pixelsPorMetro),
					(int) (2 * radioPeque�o * pixelsPorMetro));
		
			
	}
	public double getFriccion() {
		return friccion;
	}

	public double getRadioGrande() {
		return radioGrande;
	}

	public void setRadioGrande(double radioGrande) {
		this.radioGrande = radioGrande;
	}

	public double getRadioPeque�o() {
		return radioPeque�o;
	}

	public void setRadioPeque�o(double radioPeque�o) {
		this.radioPeque�o = radioPeque�o;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getRecta() {
		return recta;
	}

	public void setRecta(double recta) {
		this.recta = recta;
	}

	public double getPixelsPorMetro() {
		return pixelsPorMetro;
	}

	public void setPixelsPorMetro(double pixelsPorMetro) {
		this.pixelsPorMetro = pixelsPorMetro;
	}

}
