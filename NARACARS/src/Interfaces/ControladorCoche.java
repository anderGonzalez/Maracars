package Interfaces;

import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import org.omg.CORBA.Environment;

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

	public void enviarComando(int radio, int motor) { // ambos valores 0-100

		@SuppressWarnings("rawtypes")
		Enumeration portList;
		CommPortIdentifier portId;
		SerialPort serialPort = null;
		OutputStream outputStream = null;

		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("He encontrado un serial jajajaja");
				try {
					serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
				} catch (PortInUseException e) {
				}
				try {
					outputStream = serialPort.getOutputStream();
				} catch (IOException e) {
				}
				try {
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
				} catch (UnsupportedCommOperationException e) {
				}
				try {
					//escribirByteStart(outputStream);
					outputStream.write(((Integer) radio).byteValue());
					//outputStream.write(((Integer) motor).byteValue());
					//escribirByteEnd(outputStream);
					System.out.println("Enviado un byte");
				} catch (IOException e) {
				}
				serialPort.close();
			}
		}

	}

	private void escribirByteEnd(OutputStream outputStream) throws IOException {
		BitSet bits = new BitSet(8);
		outputStream.write(bits.toByteArray());

	}

	private void escribirByteStart(OutputStream outputStream) throws IOException {
		BitSet bits = new BitSet(8);
		bits.set(0, 8);
		outputStream.write(bits.toByteArray());

	}
	
	public static void main(String[] args) {
		Timer timer = new  Timer();
		timer.schedule(new TimerTask() {
			int i = 0;
			@Override
			public void run() {
				ControladorCoche a = new ControladorCoche();
				a.enviarComando(i++, 0);
				if(i > 255) i = 0;
			}
		}, 0, 20);
	}
}
