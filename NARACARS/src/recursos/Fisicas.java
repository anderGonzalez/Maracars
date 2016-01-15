package recursos;

/**
 * Esta clase define las fisicas a las que se somete un coche durante su recorrido
 * 
 * @author Joanes
 *
 */

public class Fisicas {
	final static double GRAVEDAD = 9.8;
	double radio;
	double friccion;
	double velocidadMax;
	double angulo;
	double giro;
	
	public Fisicas(){

	}
	public double getVmax(Datos datos){
		this.radio= datos.getConjunto().getRecorrido().getRadio();
		this.friccion=datos.getConjunto().getCircuito().getFriccion();
		velocidadMax= Math.sqrt(this.radio*this.friccion*GRAVEDAD);
		return velocidadMax;
	}
	public double getAngulo(Datos datos) {
		angulo=Math.asin(datos.getConjunto().getCoche().getAncho()/
				datos.getConjunto().getRecorrido().getRadio());
		angulo= Math.toDegrees(angulo);
		return angulo;
	}
	public double getGiro(Datos datos){
		giro=datos.getAngulo()*45/50;
		return giro;
	}

	
	//TODO funtziño bat falta da, non biratzerakuan emon bihar daben giruan graduak kalkulauko dittuan.
	//Gero datu hori Datos.giro-n gordeko da, servoari bidali beharreko datua baita hori. Normalean zuzen joango da
	//baina biratu behar duenean gradu kopuru hori biratzen egon beharko da denbora osoan.

}
