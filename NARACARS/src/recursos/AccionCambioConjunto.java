package recursos;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Enums.Modo;
import Interfaces.Traducible;
import ui.DialogoNuevoConjunto;
import ui.VentanaPrincipal;

/**
 * Esta clase es una accion que cambia el conjunto de el menu principal
 * 
 * @author Ander
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
@SuppressWarnings("serial")
public class AccionCambioConjunto extends AbstractAction implements Traducible {
	VentanaPrincipal ventana;
	
	final String DEFAULT_NUEVO_CONJUNTO = "Nuevo Conjunto";

	/**
	 * Este es el constructor de la accion
	 * @param ventana
	 */
	public AccionCambioConjunto(VentanaPrincipal ventana) {
		this.ventana = ventana;
		ventana.getTraducibles().add(this);
		escribirTextos();
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		DialogoNuevoConjunto dialogo = new DialogoNuevoConjunto(ventana, DEFAULT_NUEVO_CONJUNTO, true);
		if (dialogo.isPreparado()) {
			if (ventana.getConjunto() == null) {
				ventana.setConjunto(dialogo.getConjunto());
			} else {
				ventana.getConjunto().setConjunto(dialogo.getConjunto());
			}		
			ventana.getDatos().setConjunto(dialogo.getConjunto());

			
			
		} else {
			ventana.setModo(Modo.MANUAL);
		}
	}

	@Override
	public void escribirTextos() {

	}

}
