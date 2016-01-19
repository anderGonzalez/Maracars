package recursos;

/**
 * Clase que tiene los datos necesarios para la simulacion
 *
 * @author Joanes
 *
 */
public class Datos {

	
	static public int PARADA = 50;
	static public int RECTO = 50;
	double velMax = 0;
	double velMom;
	double tiempoInicioVuelta;
	double tiempoVuelta;
	double tiempoInicio;
	double tiempoTotal;
	double distanciaTotal = 0.0;
	double angulo;
	int acelerometro_x;
	int acelerometro_y;
	int giro = 0;
	int motor = 0;
	int revol;
	int giro_aux;
	boolean obstaculo = false;
	Conjunto conjunto;

	Fisicas fisicas;

	/**
	 * Constructor de la clase Datos
	 */
	public Datos() {
		fisicas = new Fisicas();
		conjunto = null;
	}

	/**
	 * Guarda el tiempo para tener una referencia
	 */
	public void inicializarTiempoVuelta() {
		this.tiempoInicioVuelta = System.currentTimeMillis();
	}

	/**
	 * Guarda el tiempo para tener una referencia
	 */
	public void inicializarTiempoTotal() {
		this.tiempoInicio = System.currentTimeMillis();
	}

	/**
	 * @return Devuelve el tiempo transcurrido desde que ha empezado la
	 *         simulaci�n
	 */
	public double getTiempoTotal() {
		tiempoTotal = System.currentTimeMillis() - tiempoInicio;
		return tiempoTotal;
	}

	/**
	 * @return Devuelve el tiempo transcurrido desde que empez� la vuelta
	 */
	public double getTiempo() {
		tiempoVuelta = System.currentTimeMillis() - tiempoInicioVuelta;
		return tiempoVuelta;
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	public int getRevol() {
		return revol;
	}

	public void setRevol(int revol) {
		this.revol = revol;
	}

	public double getVelMom() {
		return velMom;
	}

	public void setVelMom(double velMom) {
		this.velMom = velMom;
	}

	public int getMotor() {
		return motor;
	}

	public void setMotor(int motor) {
		this.motor = motor;
	}

	/**
	 * Al setear un nuevo conjunto, se calcula la velocidad maxima del coche en
	 * curva, el angulo y el se�al que hay que enviar a la CPLD
	 * 
	 * @param conjunto Conjunto (circuito, recorrido, coche)
	 */
	public void setConjunto(Conjunto conjunto) {
		if (this.conjunto == null) {
			this.conjunto = conjunto;
		}

		this.conjunto.setConjunto(conjunto);
		if (this.conjunto != null) {
			velMax = fisicas.getVmax(this);
		} else {
			velMax = 0;
		}
		angulo = fisicas.getAngulo(this);
		giro_aux = Math.round((float) fisicas.getGiro(this));
	}

	public Conjunto getConjunto() {
		return conjunto;
	}

	public double getTiempoInicio() {
		return tiempoInicioVuelta;
	}

	public double getVelMax() {
		return velMax;
	}

	public void setVelMax(double velMax) {
		this.velMax = velMax;
	}

	public int getGiro() {
		return giro;
	}

	public void setGiro(int giro) {
		this.giro = giro;
	}

	public void setTiempo(double tiempo) {
		this.tiempoVuelta = tiempo;
	}

	public double getDistancia() {
		return distanciaTotal;
	}

	public void setDistancia(double distancia) {
		this.distanciaTotal = distancia;
	}

	public int getAcelerometro_x() {
		return acelerometro_x;
	}

	public void setAcelerometro_x(int acelerometro_x) {
		this.acelerometro_x = acelerometro_x;
	}

	public int getAcelerometro_y() {
		return acelerometro_y;
	}

	public void setAcelerometro_y(int acelerometro_y) {
		this.acelerometro_y = acelerometro_y;
	}

	public boolean isObstaculo() {
		return obstaculo;
	}

	public void setObstaculo(boolean obstaculo) {
		this.obstaculo = obstaculo;
	}

	public int getGiro_aux() {
		return giro_aux;
	}

	public void setGiro_aux(int giro_aux) {
		this.giro_aux = giro_aux;
	}

}
