package recursos;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

//TODO javadoc eitteko.

/**
 * Esta clase define un joystick.
 * 
 * @author Ander
 */
public class Joystick extends Observable {

	Controller mando;
	boolean conectado;
	Timer refresh;
	int aceleracion;
	int giro;
	double deadZone = 0.15;
	final int REFRESH_PERIOD = 20; //ms

	/**
	 * Constructor del Joystick, inicializa un timer
	 */
	public Joystick() {
		refresh = new Timer();
		conectado = false;
		aceleracion = Datos.PARADA;
		giro = Datos.RECTO;
		refresh.schedule(new TimerTask() {

			@Override
			public void run() {
				loop();
			}
		}, 0, REFRESH_PERIOD);
	}

	/**
	 * @return
	 * @throws ReflectiveOperationException
	 */
	private static ControllerEnvironment crearDefaultEnvironment() throws ReflectiveOperationException {
		@SuppressWarnings("unchecked")
		Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>) Class
				.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		return constructor.newInstance();
	}

	/**
	 * Metodo que busca en el sistema todos los controladores de juego conectados y despues selecciona el primero
	 * que necuentra que pertenezca al tipo JOYSTICK para ser usado como input.
	 * 
	 */
	private void buscarMando() {
		Controller[] controllers = null;
		try {
			controllers = crearDefaultEnvironment().getControllers();
		} catch (Exception e) {
		}
		try {
			for (int i = 0; i < controllers.length; i++) {
				Controller controller = controllers[i];

				if (controller.getType() == Controller.Type.STICK || controller.getType() == Controller.Type.GAMEPAD
						|| controller.getType() == Controller.Type.WHEEL
						|| controller.getType() == Controller.Type.FINGERSTICK) {
					mando = controller;
					break;
				}
			}
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Modificador estatico de la clase que permite que la busqueda de controladotres se realize constantemente y no solo 
	 * la primera vez que se llama a la clase para permitir la reconexion de un controlador.
	 */
	
	
	static {
		AccessController.doPrivilegedWithCombiner(new PrivilegedAction<Object>() {
			public Object run() {
				String os = System.getProperty("os.name", "").trim();
				if (os.startsWith("Windows 10")) {
					System.setProperty("jinput.useDefaultPlugin", "false");
					System.setProperty("net.java.games.input.plugins",
							"net.java.games.input.DirectAndRawInputEnvironmentPlugin");
				}
				return null;
			}
		});
	}

	/**
	 * Loop que se realiza cada vez que esta clase refrersca sus datos.
	 * <p>
	 * Busca el mando y acontinuacion lee de el.
	 */
	private void loop() {

		if (mando == null || !mando.poll()) {
			conectado = false;
			mando = null;
			buscarMando();
		} else {
			conectado = true;

			Component[] components = mando.getComponents();
			for (int i = 0; i < components.length; i++) {
				Component component = components[i];
				Identifier componentIdentifier = component.getIdentifier();

				if (component.isAnalog()) {
					float axisValue = component.getPollData();
					int axisValueInPercentage;

					if (componentIdentifier == Component.Identifier.Axis.Z) {
						axisValueInPercentage = sacarValor(-axisValue);
						aceleracion = axisValueInPercentage;
						continue;
					}

					if (componentIdentifier == Component.Identifier.Axis.X) {
						axisValueInPercentage = sacarValor(-axisValue);
						giro = axisValueInPercentage;
						continue;
					}
				}
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Devuelve el valor de un eje reciviendo el valor de entrada como un double de -1 a 1 y transformandolo
	 *  en un int de 0 a 100.
	 * 
	 * @param axisValue
	 * @return valor del eje seleccionado entre 0 y 100
	 */
	private int sacarValor(double axisValue) {
		if (Math.abs(axisValue) < deadZone) {
			axisValue = 0;
		} else if (0 < axisValue) {
			axisValue = (axisValue - deadZone) / (1 - deadZone);
		} else {
			axisValue = - ( (axisValue + deadZone) / (-1 + deadZone));
		}
		return (int) ((axisValue + 1) * 50);
	}

	public Controller getMando() {
		return mando;
	}

	public void setMando(Controller mando) {
		this.mando = mando;
	}

	public boolean isConectado() {
		return conectado;
	}

	public Timer getRefresh() {
		return refresh;
	}

	public int getAceleracion() {
		return aceleracion;
	}

	public int getGiro() {
		return giro;
	}

	public void setDeadZone(double deadZone) {
		this.deadZone = deadZone;
	}

}
