package recursos;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase que tiene los datos necesarios para la simulacion
 *
 * @author Joanes
 *
 */
public class Datos extends Observable {
	static public int PARADA = 50;
	static public int RECTO = 50;
	static public int NODISTANCIA = 0;
	static final int FRECUENCIA = 500;
	static final double CIRCUNFDEFAULT = 0.2;
	double velMax = 0;
	double velMom = 0;
	double tiempoInicioVuelta;
	double tiempoVuelta;
	double tiempoInicio;
	double tiempoTotal;
	double distanciaTotal = 0.0;
	double angulo;
	int acelerometro_x;
	int acelerometro_y;
	int giro = 50;
	int motor = 50;
	int giro_aux;
	boolean obstaculo = false;
	Conjunto conjunto;
	CocheRevoluciones cocheRev;
	Timer timerVel;
	Fisicas fisicas;

	/**
	 * Constructor de la clase Datos
	 */
	public Datos() {
		cocheRev = new CocheRevoluciones();
		fisicas = new Fisicas();
		conjunto = null;
		calcVelMomento();
	}

	public void calcVelMomento() {

		timerVel = new Timer();
		timerVel.schedule(new TimerTask() {
			int revAnterior;
			int rev;
			double dist, vel, cnt = 0;

			@Override
			public void run() {
				if (cnt < 3) {
					cnt++;
					revAnterior = cocheRev.getRevTotal();
					cocheRev.SetRevTotal(0);
				} else {

					rev = Datos.this.cocheRev.getRevTotal() - revAnterior;

					dist = getDistancia(rev);

					if (dist == 0) {
						vel = 0;
					} else {
						vel = dist / ((double) FRECUENCIA / 1000.0);

					}
					velMom = vel;
					if (rev != 0) {
						revAnterior = Datos.this.cocheRev.getRevTotal();
					}

				}

			}
		}, 0, FRECUENCIA);
	}

	public void pararCalcVelMomento() {
		timerVel.cancel();
	}

	/**
	 * Guarda el tiempo para tener una referencia
	 */
	public void inicializarTiempoVuelta() {
		this.tiempoInicioVuelta = System.currentTimeMillis();
		this.cocheRev.SetRevTotal(0);
	}

	/**
	 * Guarda el tiempo para tener una referencia
	 */
	public void inicializarTiempoTotal() {
		this.tiempoInicio = System.currentTimeMillis();
	}

	public CocheRevoluciones getCocheRev() {
		return cocheRev;
	}

	/**
	 * @return Devuelve el tiempo transcurrido desde que ha empezado la
	 *         simulación
	 */
	public double getTiempoTotal() {
		tiempoTotal = System.currentTimeMillis() - tiempoInicio;
		return tiempoTotal;
	}

	/**
	 * @return Devuelve el tiempo transcurrido desde que empezó la vuelta
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

	public void setRevol(int revol) {
		cocheRev.SetRevTotal(revol);
		notifyObservers();
		setChanged();
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
	 * curva, el angulo y el señal que hay que enviar a la CPLD
	 * 
	 * @param conjunto
	 *            Conjunto (circuito, recorrido, coche)
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
		if (conjunto != null) {
			return (double) cocheRev.getRevTotal() * (conjunto.getCoche().getCircunferenciaRueda() / 2.0);

		} else {
			return (double) cocheRev.getRevTotal() * (CIRCUNFDEFAULT / 2.0);
		}
	}

	public double getDistancia(int revoluciones) {
		if (conjunto != null) {
			return (double) revoluciones * (conjunto.getCoche().getCircunferenciaRueda() / 2.0);

		} else {
			return (double) revoluciones * (CIRCUNFDEFAULT / 2.0);
		}
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
		notifyObservers();
		setChanged();


		this.obstaculo = obstaculo;
	}

	public int getGiro_aux() {
		return giro_aux;
	}

	public void setGiro_aux(int giro_aux) {
		this.giro_aux = giro_aux;
	}

	public void entradaRevSerial(int numero) {
		cocheRev.motorRevol(numero);
		notifyObservers();
		setChanged();

	}

}
