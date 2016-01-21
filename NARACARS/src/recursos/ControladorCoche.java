package recursos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * Esta clase contiene las funcionalidades necesarias para enviar un paquete de
 * comando al coche radio control.
 *
 * @author Ander
 */
public class ControladorCoche {
	final int INICIAL = 0;
	final int ACELEROMETRO_X = 1;
	final int ACELEROMETRO_Y = 2;
	final int OBSTACULO = 3;
	final int REVOLUCIONES = 4;
	final int BAUDRATE = 9600;
	final int TIEMPOABRIR = 2000;
	final int TURNOENVIARMAX = 2;
	final int BYTEINICIAL = 177;
	final int RETRASOLEER = 1;
	final int RETRASOENVIAR = 19;
	SerialPort serialPort;
	OutputStream outputStream;
	InputStream inputStream;
	Datos datos;
	Timer timerEnviar;
	Timer timerRecibir;
	static int turnoEnviar = 0;
	static int turnoRecibir = 0;

	/**
	 * Metodo que trata de hacer la conexion serial y le asigna unos parametros
	 * a dicha conexion
	 * 
	 * @param datos
	 *            datos de los sensores + los que hay que enviar
	 */
	public ControladorCoche(Datos datos) {
		@SuppressWarnings("rawtypes")
		Enumeration portList;
		CommPortIdentifier portId;
		this.datos = datos;
		serialPort = null;
		outputStream = null;
		inputStream = null;
		portList = CommPortIdentifier.getPortIdentifiers();
		if (portList.hasMoreElements()) {

			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

				try {
					serialPort = (SerialPort) portId.open("SimpleWriteApp", TIEMPOABRIR);
				} catch (PortInUseException e) {
				}
				try {
					outputStream = serialPort.getOutputStream();
					inputStream = serialPort.getInputStream();

				} catch (IOException | NullPointerException e) {
				}
				try {
					serialPort.setSerialPortParams(BAUDRATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					procesoEnviar();
					procesoLeer();
				} catch (UnsupportedCommOperationException e) {
				}
			}
		}
	}

	/**
	 * Metodo que envia los comandos a la CPLD, es un ciclo que envia tres
	 * datos: el byte inicial, el giro y el motor.
	 * 
	 * @param giro
	 *            la cuantia del giro
	 * @param motor
	 *            la cuantia de la velocidad
	 */
	public void enviarComando(int giro, int motor) { // ambos valores 0-100

		try {

			switch (turnoEnviar) {
			case 0:
				outputStream.write(new Integer(BYTEINICIAL).byteValue());
				break;
			case 1:
				if (giro > 50) {
					giro -= 50;
					giro = giro * 30 / 50 + 50;
				}
				if (giro < 50) {
					giro = giro * 35 / 50 + 15;

				}
				outputStream.write(((Integer) giro).byteValue());
				break;
			case 2:
				outputStream.write(((Integer) motor).byteValue());
				break;

			default:
				break;
			}

		} catch (IOException e) {
		}
		turnoEnviar++;
		if (turnoEnviar > TURNOENVIARMAX)
			turnoEnviar = 0;
	}

	/**
	 * Metodo que guarda los datos recibidos desde la CPDL, es un ciclo que
	 * espera al byte de inicio (255), y después recibe los datos del
	 * acelerometro (x e y), si hay algun obstaculo o no y por ultimo las
	 * revoluciones del coche.
	 * 
	 * @param numero
	 *            el numero a guardar
	 */
	public void guardar(int numero) {
		int turnoRecibirAux = turnoRecibir;
		switch (turnoRecibirAux) {
		case INICIAL:
			if (numero == BYTEINICIAL) {
				turnoRecibir++;
			}
			break;
		case ACELEROMETRO_X:
			//System.out.println("Acel x:"+numero);

			datos.setAcelerometro_x(numero);
			turnoRecibir++;
			break;
		case ACELEROMETRO_Y:
			//System.out.println("Acel y:"+numero);

			datos.setAcelerometro_y(numero);
			turnoRecibir++;
			break;
		case OBSTACULO:
			//System.out.println("Obstaculo:"+numero);

			if (numero == 0) {
				datos.setObstaculo(false);
			} else {

				datos.setObstaculo(true);
			}
			turnoRecibir++;
			break;
		case REVOLUCIONES:
			//System.out.println("Revoluciones:"+numero);
			datos.getCocheRev().motorRevol(numero);
			turnoRecibir = 0;
			break;
		default:
			break;
		}

	}

	/**
	 * Metodo que si algún byte esta disponible para ser leido, lo lee y llama
	 * al metodo guardar para guardar en la variable correcta.
	 * 
	 */
	public void read() {
		byte[] readBuffer = new byte[8];
		try {
			while (inputStream.available() > 0) {
				int numBytes = inputStream.read(readBuffer);
				for (int i = 0; i < numBytes; i++) {
					Thread.sleep(RETRASOLEER);
					int numero = (readBuffer[i] & 0xFF);
					guardar(numero);
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Metodo que inicia el proceso de leer desde la CPLD
	 */
	public void procesoLeer() {
		timerRecibir = new Timer();
		timerRecibir.schedule(new TimerTask() {

			@Override
			public void run() {
				ControladorCoche.this.read();
			}
		}, 0, RETRASOLEER);
	}

	/**
	 * Metodo que inicia el proceso de enviar datos a la CPLD
	 */
	public void procesoEnviar() {
		timerEnviar = new Timer();
		timerEnviar.schedule(new TimerTask() {

			@Override
			public void run() {
				ControladorCoche.this.enviarComando(datos.getGiro(), datos.getMotor());
			}
		}, 0, RETRASOENVIAR);
	}

}