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


/**
 * @author Joanes
 *
 */


/**
 * @author Joanes
 *
 */
/**
 * @author Joanes
 *
 */
public class ControladorCoche {

	SerialPort serialPort;
	OutputStream outputStream;
	InputStream inputStream;

	static int turno = 0;

	public ControladorCoche() {
		@SuppressWarnings("rawtypes")
		Enumeration portList;
		CommPortIdentifier portId;
		serialPort = null;
		outputStream = null;
		inputStream = null;

		portList = CommPortIdentifier.getPortIdentifiers();

		if (portList.hasMoreElements()) {

			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("He encontrado un serial jajajaja");
				try {
					serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
				} catch (PortInUseException e) {
				}
				try {
					outputStream = serialPort.getOutputStream();
					inputStream = serialPort.getInputStream();

				} catch (IOException | NullPointerException e) {
				}
				try {
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
				} catch (UnsupportedCommOperationException e) {
				}
			}
		}
	}

	/**
	 * 
	 * @param radio 
	 * @param motor
	 */
	public void enviarComando(int radio, int motor) { // ambos valores 0-100

		try {
			switch (turno) {
			case 0:
				outputStream.write(new Integer(255).byteValue());
				System.out.println("Enviado un paquete: " + 255);
				break;
			case 1:
				outputStream.write(((Integer) motor).byteValue());
				System.out.println("Enviado un paquete: " + ((Integer) motor));
				break;
			case 2:
				outputStream.write(((Integer) radio).byteValue());
				System.out.println("Enviado un paquete: " + ((Integer) radio));
				break;
			default:
				break;
			}

		} catch (IOException e) {
		}
		turno++;
		if (turno > 2)
			turno = 0;
	}

	/**
	 * 
	 */
	public void read() {
		byte[] readBuffer = new byte[8];
		try {

			while (inputStream.available() > 0) {
				int numBytes = inputStream.read(readBuffer);
				System.out.print((readBuffer[0] & 0xFF) + "\n");

			}
		} catch (Exception e) {
		
		}
	}

	/**
	 * 
	 */
	public void procesoLeer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			ControladorCoche a = new ControladorCoche();

			@Override
			public void run() {
				a.read();

			}
		}, 0, 1);
	}

	/**
	 * 
	 */
	public void procesoEnviar() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int i = 0;
			ControladorCoche a = new ControladorCoche();

			@Override
			public void run() {

				a.enviarComando(i, i);
				i++;
				if (i > 250) {
					i = 0;
					}
			}
		}, 0, 1);
	}

	public static void main(String[] args) {
		ControladorCoche coche = new ControladorCoche();
		coche.procesoEnviar();
		coche.procesoLeer();
	}
}