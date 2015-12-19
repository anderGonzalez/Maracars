package Interfaces;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public class ControladorCoche {

	void enviarComando(double radio, int motor) { // ambos valores 0-100

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
						outputStream.write((byte) radio);
					} catch (IOException e) {
					}
				}
			}
		}

	}
}

