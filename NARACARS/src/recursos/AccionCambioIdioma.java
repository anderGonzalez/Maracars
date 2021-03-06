package recursos;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import Interfaces.Traducible;
import ui.VentanaPrincipal;

/**
 * Esta clase contiene es una accion (Action) que realiza el cambio de un
 * idioma.
 * 
 * @author Ander
 * @see Idioma
 */

@SuppressWarnings("serial")
public class AccionCambioIdioma extends AbstractAction implements Traducible {

	final String MENSAJE_ERROR_CARGA_IDIOMA = "No se ha cargado satisfactoriamente el idioma seleccionado";
	final String DEFAULT_CAMBIODEIDIOMAMSG = "Cambiar al idioma seleccionado";
	String path;
	VentanaPrincipal ventana;

	/**
	 * Constructor de la accion de cambio de idioma
	 * 
	 * @param nombre Nombre de la accion
	 * @param path
	 *            el path del properties
	 * @param ventana
	 *            la ventana principal
	 */
	public AccionCambioIdioma(String nombre, String path, VentanaPrincipal ventana) {
		super(nombre);

		this.ventana = ventana;
		this.path = path;
		escribirTextos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			ventana.getIdioma().load(new FileInputStream(path));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventana, MENSAJE_ERROR_CARGA_IDIOMA);
		}

	}

	@Override
	public void escribirTextos() {
		putValue(SHORT_DESCRIPTION,
				ventana.getIdioma().getProperty("DescripcionCambioDeIdioma", DEFAULT_CAMBIODEIDIOMAMSG)); // DescripccionCAmbioDeIdioma
	}

}
