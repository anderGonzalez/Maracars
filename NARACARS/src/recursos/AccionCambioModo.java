package recursos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Enums.Modo;
import Interfaces.Traducible;
import ui.VentanaPrincipal;

/**
 * Esta clase que extiende a una accion realiza un cambio en el modo de 
 * de una VentanaPrincipal.
 * 
 * @author Ander
 * @see Modo
 * @see VentanaPrincipal
 *
 */

@SuppressWarnings("serial")
public class AccionCambioModo extends AbstractAction implements Traducible {

	VentanaPrincipal ventana;
	Modo modo;
	/**
	 * Constructor de la accion de cambio de modo (automatico, manual)
	 * 
	 * @param modo 
	 * @param ventana
	 */
	public AccionCambioModo(Modo modo, VentanaPrincipal ventana) {
		super();
		this.ventana = ventana;
		this.modo = modo;
		ventana.getTraducibles().add(this);

		escribirTextos();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AccionCambioConjunto conjunto= new AccionCambioConjunto(ventana);
		if(modo==Modo.AUTOMATICO){
			conjunto.actionPerformed(null);
		}
		ventana.getPanelControlador().terminarActualizarDatos();
		ventana.setModo(modo);
		ventana.getContentPane().removeAll();
		ventana.getContentPane().add(ventana.crearPanelTabs(), BorderLayout.CENTER);
		try{
			if(ventana.getOpciones().getMenuComponentCount()==3){
				if(modo==Modo.MANUAL){
					ventana.getOpciones().remove(ventana.getCrearConjunto());
				}
				
			}else if(ventana.getOpciones().getMenuComponentCount()==2){
				if(modo==Modo.AUTOMATICO){
					ventana.getOpciones().add(ventana.getCrearConjunto());
				}
			}
			
			ventana.escribirTextos();
			
		}catch(Exception a){
			a.getMessage();
		}
		
	}

	@Override
	public void escribirTextos() {

		putValue(SHORT_DESCRIPTION, ventana.getIdioma().getProperty("DescripcionCambioDeModo", "Cambiar al modo seleccionado")); // DescripccionCAmbioDeModo
		switch (modo) {
		case AUTOMATICO:
			putValue(NAME, ventana.getIdioma().getProperty("Automatico", "Automático")); // Automatico
			break;
		case MANUAL:
			putValue(NAME, ventana.getIdioma().getProperty("Manual", "Manual")); // Manual
			break;
		}

	}

}
