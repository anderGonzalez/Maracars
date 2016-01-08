package Interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.BitSet;
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

	CommPortIdentifier portId = null;
	OutputStream outputStream = null;
	BufferedReader input = null;

	public void enviarComando(int radio, int motor) { // ambos valores 0-100

		@SuppressWarnings("rawtypes")
		Enumeration portList;
		SerialPort serialPort = null;
		
		if (portId==null) {
			portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					System.out.println("He encontrado un serial jajajaja");
				}
			}

		}
		
		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			try {
				serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
			} catch (PortInUseException e) {
			}
			try {
				input= new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				outputStream = serialPort.getOutputStream();
			} catch (IOException e) {
			}
			try {
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
			}
			try {
				escribirByteStart(outputStream);
				System.out.println("Enviado un byte");
				// outputStream.write(((Integer) radio).byteValue());
				// outputStream.write(((Integer) motor).byteValue());
				//read();
				//escribirByteEnd(outputStream);
			} catch (IOException e) {
			}
			serialPort.close();
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
	public synchronized int read(){
		int b = 0;
		try {
			if(input.ready()){
				//TODO: Berez hau byte bat da, eta datosen gordeko da jasotakua kalkuluak eitteko
				b = (int)input.read();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return b;
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



