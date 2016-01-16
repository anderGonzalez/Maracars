package recursos;

public class Datos {
	
	double  velMax=0, velMom;
	
	double  tiempoInicio, tiempo, distanciaTotal=0.0, angulo;
	int  acelerometro_x,acelerometro_y, giro=0, motor=0, revol, giro_aux;
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
	
	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
	public int getRevol() {
		return revol;
	}

	public void setRevol(int revol) {
		this.revol = revol;
	}

	public double getVelMom() {
		return velMom;
	}

	public void setVelMom(double velMom) {
		this.velMom = velMom;
	}
	public int getMotor() {
		return motor;
	}

	public void setMotor(int motor) {
		this.motor = motor;
	}
	public void setConjunto(Conjunto conjunto) {
		if (this.conjunto == null){
			this.conjunto = conjunto;
		}
			
		this.conjunto.setConjunto(conjunto);
		if(this.conjunto!=null){
			velMax=fisicas.getVmax(this);
		}else{
			velMax= 0;
		}
		angulo= fisicas.getAngulo(this);
		giro_aux=Math.round((float)fisicas.getGiro(this));
	}
	
	public Conjunto getConjunto() {
		return conjunto;
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
		return distanciaTotal;
	}
	public void setDistancia(double distancia) {
		this.distanciaTotal = distancia;
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

	public int getGiro_aux() {
		return giro_aux;
	}

	public void setGiro_aux(int giro_aux) {
		this.giro_aux = giro_aux;
	}

}
