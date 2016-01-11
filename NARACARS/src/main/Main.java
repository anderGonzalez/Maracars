package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.VentanaPrincipal;


/**
 * Esta clase contiene la funcion main de el proyecto
 * 
 * @author Ander
 *
 */
public class Main {


	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		VentanaPrincipal a = new VentanaPrincipal();
		
	}
	
}
