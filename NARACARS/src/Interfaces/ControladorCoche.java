package Interfaces;

import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import ui.VentanaPrincipal;

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
				if (portId.getName().equals("COM1")) {
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
						escribirByteStart(outputStream);
						outputStream.write(((Integer) radio).byteValue());
						outputStream.write(((Integer) motor).byteValue());
						escribirByteEnd(outputStream);
					} catch (IOException e) {
					}
				}
			}
		}

	}

	private void escribirByteEnd(OutputStream outputStream) throws IOException {
		BitSet bits = new BitSet(8);
		bits.set(0);
		outputStream.write(bits.toByteArray());

	}

	private void escribirByteStart(OutputStream outputStream) throws IOException {
		BitSet bits = new BitSet(8);
		bits.set(0, 8);
		outputStream.write(bits.toByteArray());

	}
}
