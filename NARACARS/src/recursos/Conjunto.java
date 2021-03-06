package recursos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;

import Enums.Sentido;
import Interfaces.Dibujable;

/**
 * Esta clase define el conjunto formado por un chche, el circuito en el que
 * esta y recorrido que realiza en este.
 * 
 * @author Ander
 * @see Circuito Circuito construido desde los parametros
 * @see Coche Coche construido desde los parametros
 * @see Recorrido Recorrido construido desde los parametros
 *
 */

public class Conjunto extends Observable implements Dibujable {

	static final int POSX = 0;
	static final int POSY = 1;
	/**
	 * Los cinco tramos del circuito
	 */
	static final int RECTA1 = 2;
	static final int CURVA1 = 3;
	static final int RECTA2 = 4;
	static final int CURVA2 = 5;
	static final int RECTA3 = 6;
	Circuito circuito;
	Coche coche;
	Recorrido recorrido;
	double distancia;
	double pixelsPorMetro = 20;
	int x = 0;
	int y = 0;

	/**
	 * Constructor del conjunto
	 * 
	 * @param circuito Circuito construido desde los parametros
	 * @param coche Coche construido desde los parametros
	 * @param recorrido Recorrido construido desde los parametros
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
		calcularPosicionCoche(distancia);
		g.setColor(Color.MAGENTA);
		coche.dibujar(g, this.x + x, this.y + y);

	}

	/**
	 * Metodo que devuelve la distancia desde el punto de partida del circuito
	 * hasta el final del tramo indicado
	 * 
	 * @param tramo tramo que el coche esta recorriendo
	 * @return distancia distancia hasta el final del tramo
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
	 * Metodo que calcula la posicion del coche en el trayecto segun la
	 * distancia recorrida desde el punto de partida. Seg�n cual es el tramo que
	 * est� el coche utiliza diferentes formulas.
	 * 
	 * @param distancia distancia recorrida por el coche
	 * 
	 */
	public void calcularPosicionCoche(double distancia) {
		while (distancia >= distanciaHasta(RECTA3)) {
			distancia -= distanciaHasta(RECTA3);
		}

		if (distancia <= distanciaHasta(RECTA1)) {
			if (recorrido.sentido == Sentido.CLOCKWISE) {
				this.x = recorrido.coordenadasInicio[POSX] - (int) (distancia * pixelsPorMetro);
			} else {
				this.x = recorrido.coordenadasInicio[POSX] + (int) (distancia * pixelsPorMetro);
			}
			this.y = recorrido.coordenadasInicio[POSY];

		} else if (distancia < distanciaHasta(CURVA1)) {

			double angulo = ((distancia - distanciaHasta(RECTA1)) / recorrido.getRadio());
			if (recorrido.sentido == Sentido.CLOCKWISE) {

				this.y = (recorrido.coordenadasInicio[POSY])
						+ (int) ((recorrido.getRadio() * (1 - Math.cos(angulo))) * pixelsPorMetro);

				this.x = recorrido.coordenadasInicio[POSX]
						- (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio()) * pixelsPorMetro);

			} else {

				this.y = recorrido.coordenadasInicio[POSY]
						- (int) ((recorrido.getRadio() * (Math.cos(angulo) - 1)) * pixelsPorMetro);

				this.x = recorrido.coordenadasInicio[POSX]
						+ (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio()) * pixelsPorMetro);

			}

		} else if (distancia < distanciaHasta(RECTA2)) {

			if (recorrido.sentido == Sentido.CLOCKWISE) {
				this.x = recorrido.coordenadasInicio[POSX]
						- (int) (((recorrido.longitudRecta / 2) - (distancia - distanciaHasta(CURVA1)))
								* pixelsPorMetro);
			} else {
				this.x = recorrido.coordenadasInicio[POSX]
						+ (int) (((recorrido.longitudRecta / 2) - (distancia - distanciaHasta(CURVA1)))
								* pixelsPorMetro);
			}
			this.y = recorrido.coordenadasInicio[POSY] + (int) (recorrido.getRadio() * 2 * pixelsPorMetro);

		} else if (distancia < distanciaHasta(CURVA2)) {

			double angulo = ((distancia - distanciaHasta(RECTA2)) / recorrido.getRadio());
			if (recorrido.sentido == Sentido.CLOCKWISE) {

				this.y = recorrido.coordenadasInicio[POSY]
						+ (int) ((recorrido.getRadio() * (1 + Math.cos(angulo))) * pixelsPorMetro);
				this.x = recorrido.coordenadasInicio[POSX]
						+ (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio()) * pixelsPorMetro);

			} else {

				this.y = recorrido.coordenadasInicio[POSY]
						+ (int) ((recorrido.getRadio() * (1 + Math.cos(angulo))) * pixelsPorMetro);
				this.x = recorrido.coordenadasInicio[POSX]
						- (int) ((distanciaHasta(RECTA1) + Math.sin(angulo) * recorrido.getRadio()) * pixelsPorMetro);

			}
		} else if (distancia <= distanciaHasta(RECTA3)) {

			if (recorrido.sentido == Sentido.CLOCKWISE) {
				this.x = (recorrido.coordenadasInicio[POSX]
						+ (int) ((recorrido.longitudRecta / 2 - (distancia - distanciaHasta(CURVA2)))
								* pixelsPorMetro));
			} else {
				this.x = (recorrido.coordenadasInicio[POSX]
						- (int) ((recorrido.longitudRecta / 2 - (distancia - distanciaHasta(CURVA2)))
								* pixelsPorMetro));
			}
			this.y = recorrido.coordenadasInicio[POSY];

		}

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

	public Dimension getDimension() {
		Dimension ret = new Dimension((int) ((circuito.getRadioGrande() * 2 + circuito.getRecta()) * pixelsPorMetro),
				(int) ((circuito.getRadioGrande() * 2 * pixelsPorMetro)));
		return ret;
	}

	/**
	 * Metodo que al enviar un nuevo conjunto guarda los datos del circuito, del
	 * coche y del recorrido y notifica a los observadores.
	 * 
	 * @param conjunto Conjunto (coche, recorrido, circuito)
	 */
	public void setConjunto(Conjunto conjunto) {
		circuito = conjunto.getCircuito();
		recorrido = conjunto.getRecorrido();
		coche = conjunto.getCoche();
		distancia = 0.0;
		setChanged();
		notifyObservers();
	}

	/**
	 * setea los pixels por metro del coche, del circuito y del recorrido para
	 * que todos los componentes aparezcan con la misma escala
	 * 
	 * @param pixelsPorMetro medida inventada por nosotros para poder ver bien el conjunto
	 */
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

	/**
	 * HAce set de la distancia, y avisa a los observadores de que ha cambiado.
	 * 
	 * @param distancia distancia recorrida
	 */
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
		setChanged();
		notifyObservers();
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		setChanged();
		notifyObservers();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		setChanged();
		notifyObservers();
	}

	public void dibujar(Graphics g, int margenIzquierda, int margenArriba, double distancia) {
		circuito.dibujar(g, x, y);
		recorrido.dibujar(g, getXRecorrido(x), getYRecorrido(y));
		calcularPosicionCoche(distancia);
		g.setColor(Color.MAGENTA);
		coche.dibujar(g, this.x + x, this.y + y);
	}

}