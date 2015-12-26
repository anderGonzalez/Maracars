package recursos;

import Enums.Modo;
import ui.VentanaPrincipal;

/**
 * Esta clase define las fisicas a las que se somete un coche durante su recorrido
 * 
 * @author Joanes
 *
 */

public class Fisicas {
	final static double GRAVEDAD = 9.8;
	
	VentanaPrincipal ventana;
	Recorrido recorrido;
	double radio;
	double friccion;
	double velocidadMax;
	public Fisicas(VentanaPrincipal ventana){
		this.ventana=ventana;
		this.recorrido=ventana.getConjunto().getRecorrido();
		this.radio= this.recorrido.getRadio();
		this.friccion=this.recorrido.getCircuito().getFriccion();

	}
	
	public void calcularVelocidad(){
		velocidadMax= Math.sqrt(this.radio*this.friccion*GRAVEDAD);
		ventana.setTitle(ventana.getTitle()+velocidadMax);
	}

}
