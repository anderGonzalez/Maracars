package Interfaces;

import java.awt.Graphics;

import ui.VentanaPrincipal;

/**
 * Esta interfaz define la funcion necesaria que debe tener cualquer elemento no
 * perteneciente a Swing para ser dibujado en un conponente Graphics.
 * 
 * @author Ander
 * @see Graphics
 *
 */

public interface Dibujable {

	void dibujar(Graphics g, int x, int y);

}
