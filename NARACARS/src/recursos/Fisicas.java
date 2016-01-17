package recursos;

/**
 * Esta clase define las fisicas a las que se somete un coche durante su
 * recorrido
 * 
 * @author Joanes
 *
 */

public class Fisicas {
	final static double GRAVEDAD = 9.8;
	double radio;
	double friccion;
	double velocidadMax;
	double angulo;
	double giro;

	/**
	 * Metodo que calcula la velocidad maxima del coche en una curva
	 * 
	 * @param datos datos de los sensores + los que hay que enviar
	 * @return Devuelve la velocidad maxima
	 */
	public double getVmax(Datos datos) {
		this.radio = datos.getConjunto().getRecorrido().getRadio();
		this.friccion = datos.getConjunto().getCircuito().getFriccion();
		velocidadMax = Math.sqrt(this.radio * this.friccion * GRAVEDAD);
		return velocidadMax;
	}

	/**
	 * Metodo que calcula el angulo de las ruedas que debe dar el coche en la
	 * curva
	 * 
	 * @param datos datos recibidos mas los que hay qu enviar o enseñar
	 * @return devuelve el angulo de giro que el coche debe dar en la curva
	 */
	public double getAngulo(Datos datos) {
		angulo = Math.asin(datos.getConjunto().getCoche().getAncho() / datos.getConjunto().getRecorrido().getRadio());
		angulo = Math.toDegrees(angulo);
		return angulo;
	}

	/**
	 * Metodo que calcula lo que tiene enviar el programa a la CPLD (giro)
	 * 
	 * @param datos datos recibidos mas los que hay qu enviar o enseñar
	 * @return cifra que hay que enviar a la CPLD para girar lo adecuado
	 */
	public double getGiro(Datos datos) {
		giro = datos.getAngulo() * 45 / 50;
		return giro;
	}

}
