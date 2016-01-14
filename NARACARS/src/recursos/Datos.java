package recursos;

public class Datos {
	
	double  velMax=0;
	double  tiempoInicio, tiempo, distancia;
	int  acelerometro_x,acelerometro_y, giro=50, motor=50;
	boolean obstaculo=false;
	Conjunto conjunto;
	
	Fisicas fisicas;

	public Datos(){
		fisicas= new Fisicas();
		conjunto=null;
		
	}
	
	public void inicializarTiempo(){
		this.tiempoInicio=System.currentTimeMillis();
	}

	public int getMotor() {
		return motor;
	}

	public void setMotor(int motor) {
		this.motor = motor;
	}
	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
		if(this.conjunto!=null){
			velMax=fisicas.getVmax(conjunto);
		}else{
			velMax= 0;
		}
	}
	
	public double getTiempoInicio() {
		return tiempoInicio;
	}

	public double getVelMax() {
		return velMax;
	}
	public void setVelMax(double velMax) {
		this.velMax = velMax;
	}
	public int getGiro() {
		return giro;
	}
	public void setGiro(int giro) {
		this.giro = giro;
	}
	
	public double getTiempo() {
		tiempo= System.currentTimeMillis()-tiempoInicio;
		return tiempo;
	}
	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public int getAcelerometro_x() {
		return acelerometro_x;
	}

	public void setAcelerometro_x(int acelerometro_x) {
		this.acelerometro_x = acelerometro_x;
	}

	public int getAcelerometro_y() {
		return acelerometro_y;
	}

	public void setAcelerometro_y(int acelerometro_y) {
		this.acelerometro_y = acelerometro_y;
	}

	public boolean isObstaculo() {
		return obstaculo;
	}
	public void setObstaculo(boolean obstaculo) {
		this.obstaculo = obstaculo;
	}

}
