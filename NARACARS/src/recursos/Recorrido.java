package recursos;

import java.awt.Color;
import java.awt.Graphics;

import Enums.Sentido;
import Interfaces.Dibujable;

public class Recorrido implements Dibujable {

	static final int POSX = 0;
	static final int POSY = 1;
	
	double pixelsPorMetro = 20; // px
	int coordenadasInicio[]= new int [2];
	Sentido sentido;
	double longitudRecta; // m
	double radio; // m
	double anchoCoche; //m
	
	Circuito circuito;

	public Recorrido() {
		
	};

	public void crearRecorrido(Circuito circuito, Coche coche, Sentido sentido) {
		this.longitudRecta = circuito.getRecta() - 2 * circuito.getAncho() + 4 * coche.getAncho();
		this.radio = circuito.getRadioGrande() - coche.getAncho();
		this.sentido = sentido;
		anchoCoche = coche.getAncho();
		this.circuito = circuito;
		this.coordenadasInicio[POSX]= (int)((circuito.getRecta()/2+circuito.getRadioGrande())*pixelsPorMetro);//x
		this.coordenadasInicio[POSY]= (int)(anchoCoche*pixelsPorMetro);//y
	}

	
	@Override
	public void dibujar(Graphics g, int x, int y) {
		g.setColor(Color.WHITE);
		g.drawArc(	x,
					y,
					(int) ((radio * 2) * pixelsPorMetro),
					(int) (radio * 2 * pixelsPorMetro),
					90, 
					180);
		g.drawArc(	(int) (x + ((longitudRecta) * pixelsPorMetro)),
					y ,
					(int) ((radio * 2) * pixelsPorMetro),
					(int) (radio * 2 * pixelsPorMetro), 
					270, 
					180);
		g.drawLine(	(int) (x + ((radio) * pixelsPorMetro)),
					y,
					(int) (x + ((radio + longitudRecta) * pixelsPorMetro)),
					y);
		g.drawLine(	(int) (x + ((radio) * pixelsPorMetro)),
					(int) (y + ((2 * radio) * pixelsPorMetro)),
					(int) (x + ((radio + longitudRecta) * pixelsPorMetro)),
					(int) (y + ((2 * radio) * pixelsPorMetro)));
	}
	public Circuito getCircuito(){
		return circuito;
	}

	public double getLongitudRecta() {
		return longitudRecta;
	}

	public double getRadio() {
		return radio;
	}

	public double getPixelsPorMetro() {
		return pixelsPorMetro;
	}

	public void setPixelsPorMetro(double pixelsPorMetro) {
		this.pixelsPorMetro = pixelsPorMetro;
		this.coordenadasInicio[POSX]= (int)((circuito.getRecta()/2+circuito.getRadioGrande())*pixelsPorMetro);//x
		this.coordenadasInicio[POSY]= (int)(anchoCoche*pixelsPorMetro);//y
	}
	
	


	

	
}



