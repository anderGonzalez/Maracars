package recursos;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Reproductor implements Observer {
	boolean reproduciendo = false;
	Timer timerRepro = new Timer();
	Clip sonido;
	Datos datos;

	/**
	 * Abre un fichero de sonido wav y lo reproduce
	 * 
	 * @param args
	 */
	public Reproductor(Datos datos) {

		try {
			datos.addObserver(this);

		} catch (Exception e) {
			System.out.println("" + e);
		}

	}

	public void reproducirProceso() {

		timerRepro.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					reproduciendo = true;
					sonido = AudioSystem.getClip();
					sonido.open(AudioSystem.getAudioInputStream(new File("sonidos/TrataDeArrancarlo.wav")));
					sonido.start();
					Thread.sleep(sonido.getMicrosecondLength() * 1000);
					sonido.stop();
					sonido.close();
					reproduciendo=false;
					timerRepro.cancel();

				} catch (InterruptedException | LineUnavailableException e) {
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Datos datos = (Datos) arg0;
		if (datos.isObstaculo() && !reproduciendo) {
			reproducirProceso();
		}
	}

}