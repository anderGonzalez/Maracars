package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Enums.Sentido;
import recursos.Circuito;
import recursos.Coche;
import recursos.Conjunto;
import recursos.Idioma;
import recursos.Recorrido;

//TODO txukunduta
/**
 * Un dialogo que recoge los parametros de un conjunto.
 * 
 * @author Joanes
 * @see Conjunto
 */

@SuppressWarnings("serial")
public class DialogoNuevoConjunto extends JDialog implements ActionListener {
	static final int POSX = 200;
	static final int POSY = 75;
	static final int WIDTH = 400;
	static final int HEIGHT = 470;
	static final int ROWGENERAL = 8;
	static final int COLUMNGENERAL = 1;
	static final int FILARELOJ = 1;
	static final int COLUMNARELOJ = 2;
	static final int MARGEN = 10;
	static final int TAMAÑOTF = 20;
	JTextField anchoCoche_tf;
	JTextField longitudCoche_tf;
	JTextField anchoPista_tf;
	JTextField longitudRecta_tf;
	JTextField radioAncho_tf;
	JTextField coefFriccion_tf;
	JTextField circunferencia_tf;

	JRadioButton reloj;
	JRadioButton contrareloj;
	ButtonGroup grupo;
	JButton botonOk;
	Conjunto conjunto;
	Sentido sentido;
	Idioma idioma;

	boolean preparado = false;
	double cocheAncho;
	double longitudCoche;
	double anchoPista;
	double longitudRecta;
	double radio;
	double friccion;
	double circunferencia;
	/**
	 * Constructor del dialogo para crear un nuevo conjunto
	 * 
	 * @param ventana ventana principal para poder crear el dialogo
	 * @param titulo Titulo del dialogo
	 * @param modo Modo del dialogo, en este caso bloqueara la ventana principal
	 */
	public DialogoNuevoConjunto(VentanaPrincipal ventana, String titulo, boolean modo) {
		super(ventana, titulo, modo);
		this.idioma = ventana.getIdioma();
		this.setBounds(POSX, POSY, WIDTH, HEIGHT);
		this.add(this.panelPrincipal());
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		escribirTexto();
		this.setVisible(true);
		this.setResizable(false);

	}

	/**
	 * Metodo que crea el panel principal, añade el panel central al centro de
	 * dicho panel y el panel inferior a la parte inferior.
	 * 
	 * @return Devuelve el panel principal ya con los paneles añadidos
	 */
	private Container panelPrincipal() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.add(this.panelCentral(), BorderLayout.CENTER);
		panelPrincipal.add(this.panelInferior(), BorderLayout.SOUTH);
		return panelPrincipal;
	}

	/**
	 * Metodo que crea el panel central, para ello añade 6 textfields
	 * 
	 * @return devuelve el panel con los textfields
	 */
	private Container panelCentral() {

		JPanel panelCentral = new JPanel(new GridLayout(ROWGENERAL, COLUMNGENERAL, MARGEN, MARGEN));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(MARGEN, MARGEN, MARGEN, MARGEN));
		anchoCoche_tf = new JTextField("", TAMAÑOTF);
		longitudCoche_tf = new JTextField("", TAMAÑOTF);
		anchoPista_tf = new JTextField("", TAMAÑOTF);
		longitudRecta_tf = new JTextField("", TAMAÑOTF);
		radioAncho_tf = new JTextField("", TAMAÑOTF);
		coefFriccion_tf = new JTextField("", TAMAÑOTF);
		circunferencia_tf = new JTextField("", TAMAÑOTF);


		panelCentral.add(anchoCoche_tf);
		panelCentral.add(longitudCoche_tf);
		panelCentral.add(circunferencia_tf);
		panelCentral.add(anchoPista_tf);
		panelCentral.add(longitudRecta_tf);
		panelCentral.add(radioAncho_tf);
		panelCentral.add(coefFriccion_tf);
		panelCentral.add(añadirReloj());
		return panelCentral;
	}

	/**
	 * Metodo que crea un panel con dos JRadioButtons, en direccion a las agujas
	 * del reloj y en contra.
	 * 
	 * @return devuelve el panel con dos radiobuttons
	 */
	private Container añadirReloj() {
		JPanel panel = new JPanel(new GridLayout(FILARELOJ, COLUMNARELOJ));
		reloj = new JRadioButton();
		grupo = new ButtonGroup();
		reloj.setSelected(true);
		contrareloj = new JRadioButton();
		grupo.add(reloj);
		grupo.add(contrareloj);
		panel.add(reloj);
		panel.add(contrareloj);
		return panel;
	}

	/**
	 * Crea el panel inferior con un boton
	 * 
	 * @return Devuelve un panel con un boton
	 */
	private Container panelInferior() {
		JPanel behekoPanela = new JPanel(new FlowLayout());
		behekoPanela.setBorder(BorderFactory.createEmptyBorder(MARGEN, MARGEN, MARGEN, MARGEN));
		botonOk = new JButton();
		botonOk.addActionListener(this);
		botonOk.setActionCommand("Ok");
		behekoPanela.add(botonOk);
		return behekoPanela;
	}

	/**
	 * Metodo que escribe todos los textos del dialogo, teniendo en cuenta cual
	 * es el idioma que se esta utilizando en ese momento.
	 */
	public void escribirTexto() {
		anchoCoche_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("AnchoCoche", "Ancho del coche")));// metrotan
		anchoPista_tf
				.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("AnchoPista", "Ancho de la pista")));// metrotan
		longitudRecta_tf.setBorder(
				BorderFactory.createTitledBorder(idioma.getProperty("LongitudRecta", "Longitud de la recta")));// metrotan
		radioAncho_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("RadioAncho", "Radio más ancho")));// metrotan
		longitudCoche_tf
				.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("LongitudCoche", "Longitud del coche")));
		coefFriccion_tf.setBorder(
				BorderFactory.createTitledBorder(idioma.getProperty("CoeficienteFriccion", "Coeficiente de fricción")));
		botonOk.setText("Ok");
		reloj.setText(idioma.getProperty("Reloj", "Reloj"));
		contrareloj.setText(idioma.getProperty("Contrareloj", "Contrareloj"));
		circunferencia_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("CircunferenciaRueda", "Circunferencia de la rueda")));
	}
	/**
	 * Metodo que intenta guardar los datos que tenemos escritos en los
	 * textfields, con esos datos creamos un circuito, un coche y el recorrido
	 * que el coche seguirá en el circuito, si al crearlos pasa algo inesperado,
	 * saltará una excepción, sino cerrara la vista del dialogo.
	 * 
	 * @throws NumberFormatException
	 */
	private void guardarVariables() throws NumberFormatException {
		cocheAncho = getAnchoCoche();
		longitudCoche = getLongitudCoche();
		anchoPista = getAnchoPista();
		longitudRecta = getLongitudRecta();
		radio = getRadioAncho();
		circunferencia = getCircunferencia_tf();
		friccion = getCoefFriccion();
		if (cocheAncho <= anchoPista && anchoPista > 0 && longitudRecta >= 0 && friccion >= 0) {
			Circuito circuito = new Circuito(radio, anchoPista, longitudRecta, friccion);
			Coche coche = new Coche(cocheAncho, longitudCoche, circunferencia);
			Recorrido recorrido = new Recorrido();
			recorrido.crearRecorrido(circuito, coche, getReloj() ? Sentido.CLOCKWISE : Sentido.COUNTERCLOCKWISE);
			conjunto = new Conjunto(circuito, coche, recorrido);
			preparado = true;
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, idioma.getProperty("NoValido", "Estos valores no son validos"), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {

		case "Ok":
			if (anchoCoche_tf.getText().length() != 0 && longitudCoche_tf.getText().length() != 0
					&&circunferencia_tf.getText().length()!=0&& anchoPista_tf.getText().length() != 0 && longitudRecta_tf.getText().length() != 0
					&& radioAncho_tf.getText().length() != 0 && coefFriccion_tf.getText().length() != 0) {

				try {
					guardarVariables();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this,idioma.getProperty("NoFormato", "El formato de los datos no es valido"), "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(this, idioma.getProperty("NoRellenado", "Por favor rellene todos los campos"), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
			break;

		}
	}


	public double getCircunferencia_tf() {
		return Double.parseDouble(circunferencia_tf.getText());
	}

	public double getLongitudCoche() {
		return Double.parseDouble(longitudCoche_tf.getText());
	}

	public double getAnchoCoche() {
		return Double.parseDouble(anchoCoche_tf.getText());
	}

	public double getAnchoPista() {
		return Double.parseDouble(anchoPista_tf.getText());
	}

	public double getLongitudRecta() {
		return Double.parseDouble(longitudRecta_tf.getText());
	}

	public double getRadioAncho() {
		return Double.parseDouble(radioAncho_tf.getText());
	}

	public double getCoefFriccion() {
		return Double.parseDouble(coefFriccion_tf.getText());
	}

	public boolean isPreparado() {
		return preparado;
	}

	/**
	 * @return devuelve un boolean, si va en direccion a las agujas del reloj
	 *         true, sino false
	 */
	public boolean getReloj() {
		if (reloj.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	public Conjunto getConjunto() {
		return conjunto;
	}
}
