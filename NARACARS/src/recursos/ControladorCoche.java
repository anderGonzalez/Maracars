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

public class ControladorCoche{
	final int INICIAL = 0;
	final int ACELEROMETRO_X = 1;
	final int ACELEROMETRO_Y = 2;
	final int OBSTACULO = 3;
	final int REVOLUCIONES = 4;
	SerialPort serialPort;
	OutputStream outputStream;
	InputStream inputStream;
	Datos datos;
	Timer timerEnviar, timerRecibir;
	static int turno = 0;
	static int turnoEnviar=0;

	public ControladorCoche(Datos datos) {
		@SuppressWarnings("rawtypes")
		Enumeration portList;
		CommPortIdentifier portId;
		this.datos= datos;
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
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);	
					procesoEnviar();
					procesoLeer();
				} catch (UnsupportedCommOperationException e) {
				}
			}
		}
	}

	/**
	 * 
	 * @param giro 
	 * @param motor
	 */
	public void enviarComando(int giro, int motor) { // ambos valores 0-100

		try {
			
			switch (turno) {
			case 0:
				outputStream.write(new Integer(255).byteValue());
				break;
			case 1:
				if(giro>50){
					giro-=50;
					giro=giro*35/50+15;
				}
				if(giro<50){
					giro=giro*30/50+50;
					
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
		turno++;
		if (turno > 2)
			turno = 0;
	}
	
	
	public void guardar(int numero){
		int turnoEnviarAux= turnoEnviar;
		switch(turnoEnviarAux){
		case INICIAL:
			if(numero==255){
				turnoEnviar++;
			}
			break;
		case ACELEROMETRO_X:
			datos.setAcelerometro_x(numero);
			turnoEnviar++;
			break;
		case ACELEROMETRO_Y:
			datos.setAcelerometro_y(numero);
			turnoEnviar++;
			break;
		case OBSTACULO:
			if(numero==0){
				datos.setObstaculo(false);
			}else{
				datos.setObstaculo(true);
			}
			System.err.println("Obstaculo: "+numero);
			turnoEnviar++;
			break;
		case REVOLUCIONES:
			datos.setRevol(numero);
			turnoEnviar=0;
			break;
			default:
				break;
			}
		
	}

	/**
	 * 
	 */
	public void read() {
		byte[] readBuffer = new byte[8];
		try {
			while (inputStream.available() > 0) {
				int numBytes = inputStream.read(readBuffer);
				for(int i=0; i<numBytes;i++){
					Thread.sleep(1);
					int numero=(readBuffer[i] & 0xFF);
					guardar(numero);
				}
			}
		} catch (Exception e) {
		
		}
	}

	/**
	 * 
	 */
	public void procesoLeer() {
		timerRecibir = new Timer();
		timerRecibir.schedule(new TimerTask() {

			@Override
			public void run() {
				//TODO hemen irakurri biharko zan serialekua eta gero datosen gorde
				//Ez dakit baina zela juango gan gordetzen... suposatzen da ordenian datozela...				
				ControladorCoche.this.read();
			}
		}, 0, 1);
	}

	/**
	 * 
	 */
	public void procesoEnviar( ) {
		timerEnviar = new Timer();
		timerEnviar.schedule(new TimerTask() {

			@Override
			public void run() {
				ControladorCoche.this.enviarComando(datos.getGiro(), datos.getMotor());
			}
		}, 0, 19);
	}


	
	
}