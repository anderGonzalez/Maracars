package recursos;

public class CocheRevoluciones {

	Revoluciones revoluciones;
	Revoluciones lastRevoluciones;
	Revoluciones count;
	
	public CocheRevoluciones(){
		revoluciones = new Revoluciones();
		lastRevoluciones = new Revoluciones();
		count = new Revoluciones();
	}
	
	public int getRevTotal() {
		return count.getRevTotal();
	}
	
	public void SetRevTotal(int revol) {
		count.SetRevTotal(revol);
	}
	
	
	
	
	public Revoluciones getRevoluciones() {
		return revoluciones;
	}

	public void setRevoluciones(int revoluciones) {
		this.count.SetRevTotal(revoluciones);
		
	}

	public Revoluciones getLastRevoluciones() {
		return lastRevoluciones;
	}

	public void setLastRevoluciones(int lastRevoluciones) {
		this.lastRevoluciones.SetRevTotal(lastRevoluciones);
	}

	public void motorRevol(int numero) {
			lastRevoluciones.SetRevTotal(revoluciones.getRevTotal());
			revoluciones.motorRevol(numero);
			count.SetRevTotal(count.getRevTotal() + (revoluciones.getRevTotal() - lastRevoluciones.getRevTotal()));
	}
	
	
	
}
