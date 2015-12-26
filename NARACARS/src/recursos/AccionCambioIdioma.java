package recursos;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import Interfaces.Traducible;
import ui.VentanaPrincipal;


/**
 * Esta clase contiene es una accion (Action) que realiza el cambio de un idioma.
 * 
 * @author Ander
 * @see Idioma
 */



@SuppressWarnings("serial")
public class AccionCambioIdioma extends AbstractAction implements Traducible{

	String path;
	VentanaPrincipal ventana;
	
	public AccionCambioIdioma(String nombre, String path, VentanaPrincipal ventana) {
		super(nombre);
		
		this.ventana = ventana;
		this.path = path;
		ventana.getTraducibles().add(this);
		
		escribirTextos();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			ventana.getIdioma().load(new FileInputStream(path));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventana, "No se ha cargado satisfactoriamente el idioma seleccionado");
		}

	}


	@Override
	public void escribirTextos() {
		putValue(SHORT_DESCRIPTION, ventana.getIdioma().getProperty("DescripcionCambioDeIdioma", "Cambiar al idioma seleccionado")); //DescripccionCAmbioDeIdioma
	}

}
