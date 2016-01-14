package recursos;

/**
 * Esta clase define las fisicas a las que se somete un coche durante su recorrido
 * 
 * @author Joanes
 *
 */

public class Fisicas {
	final static double GRAVEDAD = 9.8;
	Recorrido recorrido;
	double radio;
	double friccion;
	double velocidadMax;
	public Fisicas(){

	}
	
	public double getVmax(Conjunto conjunto){
		recorrido= conjunto.getRecorrido();
		this.radio= this.recorrido.getRadio();
		this.friccion=this.recorrido.getCircuito().getFriccion();
		velocidadMax= Math.sqrt(this.radio*this.friccion*GRAVEDAD);
		return velocidadMax;
	}
	//TODO funtziño bat falta da, non biratzerakuan emon bihar daben giruan graduak kalkulauko dittuan.
	//Gero datu hori Datos.giro-n gordeko da, servoari bidali beharreko datua baita hori. Normalean zuzen joango da
	//baina biratu behar duenean gradu kopuru hori biratzen egon beharko da denbora osoan.

}
