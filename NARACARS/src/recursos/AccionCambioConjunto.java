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

@SuppressWarnings("serial")
public class AccionCambioConjunto extends AbstractAction implements Traducible {
	VentanaPrincipal ventana;

	public AccionCambioConjunto(VentanaPrincipal ventana) {
		this.ventana = ventana;
		ventana.getTraducibles().add(this);
		escribirTextos();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DialogoNuevoConjunto dialogo = new DialogoNuevoConjunto(ventana, "Nuevo Conjunto", true);
		if (dialogo.isPrest()) {
			if (ventana.getConjunto() == null) {
				ventana.setConjunto(dialogo.getConjunto());
			} else {
				ventana.getConjunto().setConjunto(dialogo.getConjunto());
				ventana.repaint();
			}
		} else {
			ventana.setModo(Modo.MANUAL);
		}
	}

	@Override
	public void escribirTextos() {

	}

}
