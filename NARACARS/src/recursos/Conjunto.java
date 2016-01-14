package recursos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;

import Enums.Sentido;
import Interfaces.Dibujable;

/**
 * Esta clase define el conjunto formado por un chche, el circuito 
 * en el que esta y recorrido que realiza en este.
 * 
 * @author Ander
 * @see Circuito
 * @see Coche
 * @see Recorrido
 *
 */


public class Conjunto extends Observable implements Dibujable {
	static final int POSX = 0;
	static final int POSY = 1;
	static final int RECTA1 = 2;
	static final int CURVA1 = 3;
	static final int RECTA2 = 4;
	static final int CURVA2 = 5;
	static final int RECTA3 = 6;
	Circuito circuito;
	Coche coche;
	Recorrido recorrido;
	Double distancia;
	//TODO distantzia hau berez ez da hemen egongo, datos klasetik hartuko dau.

	double pixelsPorMetro = 20;

	/**
	 * Constructor del conjunto
	 * @param circuito 
	 * @param coche
	 * @param recorrido
	 */
	public Conjunto(Circuito circuito, Coche coche, Recorrido recorrido) {
		this.coche = coche;
		this.recorrido = recorrido;
		this.circuito = circuito;
		distancia = 0.0;
	}

	@Override
	public void dibujar(Graphics g, int x, int y) {
		circuito.dibujar(g, x, y);
		recorrido.dibujar(g, getXRecorrido(x), getYRecorrido(y));
		calcularPosicionCoche(distancia, g);

	}


	public Dimension getDimension() {
		Dimension ret = new Dimension((int) ((circuito.getRadioGrande() * 2 + circuito.getRecta()) * pixelsPorMetro),
				(int) ((circuito.getRadioGrande() * 2 * pixelsPorMetro)));
		return ret;
	}

	public void setConjunto(Conjunto conjunto) {
		circuito = conjunto.getCircuito();
		recorrido = conjunto.getRecorrido();
		coche = conjunto.getCoche();
		distancia = 0.0;
		setChanged();
		notifyObservers();
	}

	/**
	 * @param metros
	 */
	public void addDistancia(double metros) {
		distancia += metros;
		setChanged();
		notifyObservers();
	}
	

	/**
	 * @param tramo
	 * @return distancia
	 */
	public double distanciaHasta(int tramo) {
		switch (tramo) {
		case RECTA1:
			return recorrido.getLongitudRecta() / 2;
		case CURVA1:
			return recorrido.getLongitudRecta() / 2 + Math.PI * recorrido.getRadio();
		case RECTA2:
			return 3 * recorrido.getLongitudRecta() / 2 + Math.PI * recorrido.getRadio();
		case CURVA2:
			return 3 * recorrido.getLongitudRecta() / 2 + 2 * Math.PI * recorrido.getRadio();
		case RECTA3:
			return 2 * recorrido.getLongitudRecta() + 2 * Math.PI * recorrido.getRadio();
		default:
			return 0.0;
		}

	}

	/**
	 * @param distancia
	 * @param g
	 */
	public void calcularPosicionCoche(double distancia, Graphics g) {
		int x = 0, y = 0;
		while (distancia >= distanciaHasta(RECTA3)) {
			distancia -= distanciaHasta(RECTA3);
		}

		if (distancia <= distanciaHasta(RECTA1)) {
			if (recorrido.sentido == Sentido.CLOCKWISE) {
				x = recorrido.coordenadasInicio[POSX] - (int) (distancia * pixelsPorMetro);
			} else {
				x = recorrido.coordenadasInicio[POSX] + (int) (distancia * pixelsPorMetro);
			}
			y = recorrido.coordenadasInicio[POSY];

		} else if (distancia < distanciaHasta(CURVA1)) {
			
			double angulo = ((distancia - distanciaHasta(RECTA1)) / recorrido.getRadio());
			System.out.println("ANGELUA: "+angulo);
			if (recorrido.sentido == Sentido.CLOCKWISE) {
				
					System.err.println("gutxiau");
					y = (recorrido.coordenadasInicio[POSY])
						+ (int) ((recorrido.getRadio() * (1 - Math.cos(angulo))) * pixelsPorMetro);
				
				x = recorrido.coordenadasInicio[POSX]
						- (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio()) * pixelsPorMetro);

			} else {
				
					y = recorrido.coordenadasInicio[POSY]
						- (int) ((recorrido.getRadio() * (Math.cos(angulo) - 1)) * pixelsPorMetro);
				
				x = recorrido.coordenadasInicio[POSX]
					+ (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio()) * pixelsPorMetro);

			}

		} else if (distancia < distanciaHasta(RECTA2)) {

			if (recorrido.sentido == Sentido.CLOCKWISE) {
				x = recorrido.coordenadasInicio[POSX]
						- (int) (((recorrido.longitudRecta / 2) - (distancia - distanciaHasta(CURVA1)))
								* pixelsPorMetro);
			} else {
				x = recorrido.coordenadasInicio[POSX]
						+ (int) (((recorrido.longitudRecta / 2) - (distancia - distanciaHasta(CURVA1)))
								* pixelsPorMetro);
			}
			y = recorrido.coordenadasInicio[POSY] + (int) (recorrido.getRadio() * 2 * pixelsPorMetro);

		} else if (distancia < distanciaHasta(CURVA2)) {

			double angulo = ((distancia - distanciaHasta(RECTA2)) / recorrido.getRadio());
			if (recorrido.sentido == Sentido.CLOCKWISE) {
				
					y = recorrido.coordenadasInicio[POSY]
							+ (int) ((recorrido.getRadio() * (1 + Math.cos(angulo))) * pixelsPorMetro);
					x = recorrido.coordenadasInicio[POSX]
							+ (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio())
									* pixelsPorMetro);
				
			} else {
				
					y = recorrido.coordenadasInicio[POSY]
						+ (int) ((recorrido.getRadio() * (1 + Math.cos(angulo))) * pixelsPorMetro);
					x = recorrido.coordenadasInicio[POSX]
						- (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio())
						* pixelsPorMetro);
				
			}
		} else if (distancia <= distanciaHasta(RECTA3)) {

			if (recorrido.sentido == Sentido.CLOCKWISE) {
				x = (	recorrido.coordenadasInicio[POSX]
						+ (int) ((recorrido.longitudRecta / 2 - (distancia - distanciaHasta(CURVA2)))
						* pixelsPorMetro));
			} else {
				x = (	recorrido.coordenadasInicio[POSX]
						- (int) ((recorrido.longitudRecta / 2 - (distancia - distanciaHasta(CURVA2)))
						* pixelsPorMetro));
			}
			y = recorrido.coordenadasInicio[POSY];

		}
		g.setColor(Color.MAGENTA);
		coche.dibujar(g, x, y);
	}

	@SuppressWarnings("unused")
	private void pintarCoche(Graphics g, int x, int y) {

		coche.dibujar(g, x, y);
	}

	public Circuito getCircuito() {
		return circuito;
	}

	public void setCircuito(Circuito circuito) {
		this.circuito = circuito;
	}

	public Coche getCoche() {
		return coche;
	}

	public void setPIXELS_POR_METRO(double pixelsPorMetro) {
		this.pixelsPorMetro = pixelsPorMetro;
		coche.setPixelsPorMetro(pixelsPorMetro);
		circuito.setPixelsPorMetro(pixelsPorMetro);
		recorrido.setPixelsPorMetro(pixelsPorMetro);
	}

	public void setCoche(Coche coche) {
		this.coche = coche;
	}

	public Recorrido getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(Recorrido recorrido) {
		this.recorrido = recorrido;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public double getPIXELS_POR_METRO() {
		return pixelsPorMetro;
	}

	public int getXRecorrido(int x) {
		return (int) (x - ((coche.getAncho() - circuito.getAncho()) * pixelsPorMetro));
	}

	public int getYRecorrido(int y) {
		return (int) (y + (coche.getAncho()) * pixelsPorMetro);
	}

}