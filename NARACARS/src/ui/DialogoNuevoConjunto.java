package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Enums.Sentido;
import recursos.Circuito;
import recursos.Coche;
import recursos.Conjunto;
import recursos.Idioma;
import recursos.Recorrido;

/**
 * Un dialogo que recoge los parametros de un conjunto.
 * 
 * @author Ander
 * @see Conjunto
 */


@SuppressWarnings("serial")
public class DialogoNuevoConjunto extends JDialog implements ActionListener {
	final int POSX = 200;
	final int POSY = 75;
	final int WIDTH = 400;
	final int HEIGHT = 470;
	final int ROW = 7;
	final int COLUMN = 1;
	final int MARGEN = 10;
	final int TAMA�OTF = 20;
	JTextField anchoCoche_tf;
	JTextField longitudCoche_tf;
	JTextField anchoPista_tf;
	JTextField longitudRecta_tf;
	JTextField radioAncho_tf;
	JTextField coefFriccion_tf; 

	JRadioButton reloj;
	JRadioButton contrareloj;
	ButtonGroup grupo;
	JButton botonOk;
	Conjunto conjunto;
	Sentido sentido;
	Idioma idioma;

	boolean preparado=false;
	double kotxezabal;
	double longitudCoche;
	double pistaZabal;
	double rektaLuze;
	double erradio;
	double marrus;
	
	
	public DialogoNuevoConjunto (VentanaPrincipal ventana,String titulo, boolean modo) {
		super(ventana,titulo,modo);
		this.idioma=ventana.getIdioma();
		this.setBounds(POSX, POSY,WIDTH ,HEIGHT);
		this.add(this.panelNagusia());
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	private Container panelNagusia(){
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.add(this.erdikoPanela(), BorderLayout.CENTER);
		panelPrincipal.add(this.behekoPanela(), BorderLayout.SOUTH);
		escribirTexto();
		return panelPrincipal;
	}
	

	private Container erdikoPanela(){

		JPanel panelCentral=new JPanel(new GridLayout(ROW,COLUMN,MARGEN,MARGEN));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(MARGEN,MARGEN,MARGEN,MARGEN));
		anchoCoche_tf=new JTextField("",TAMA�OTF);
		longitudCoche_tf= new JTextField("",TAMA�OTF);
		anchoPista_tf=new JTextField("",TAMA�OTF);
		longitudRecta_tf=new JTextField("",TAMA�OTF);
		radioAncho_tf=new JTextField("",TAMA�OTF);
		coefFriccion_tf=new JTextField("",TAMA�OTF);
		
		
		panelCentral.add(anchoCoche_tf);
		panelCentral.add(longitudCoche_tf);
		panelCentral.add(anchoPista_tf);
		panelCentral.add(longitudRecta_tf);
		panelCentral.add(radioAncho_tf);
		panelCentral.add(coefFriccion_tf);
		panelCentral.add(a�adirReloj());
		return panelCentral;
	}
	
	private Container a�adirReloj(){
		JPanel panel= new JPanel (new GridLayout(1,2));
		reloj= new JRadioButton();
		grupo= new ButtonGroup();
		reloj.setSelected(true);
		contrareloj= new JRadioButton();
		grupo.add(reloj);
		grupo.add(contrareloj);
		panel.add(reloj);
		panel.add(contrareloj);
		return panel;
	}
	
	private Container behekoPanela(){
		JPanel behekoPanela= new JPanel (new FlowLayout());
		behekoPanela.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		botonOk= new JButton();
		botonOk.addActionListener(this);
		botonOk.setActionCommand("Ok");
		behekoPanela.add(botonOk);
		return behekoPanela;
	}
	public void escribirTexto(){
		anchoCoche_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("AnchoCoche","Ancho del coche")));//metrotan
		anchoPista_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("AnchoPista","Ancho de la pista")));//metrotan
		longitudRecta_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("LongitudRecta","Longitud de la recta")));//metrotan
		radioAncho_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("RadioAncho","Radio m�s ancho")));//metrotan
		longitudCoche_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("LongitudCoche","Longitud del coche")));
		coefFriccion_tf.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("CoeficienteFriccion","Coeficiente de fricci�n")));
		botonOk.setText("Ok");
		reloj.setText(idioma.getProperty("Reloj","Reloj"));
		contrareloj.setText(idioma.getProperty("Contrareloj","Contrareloj"));
	}
	
	private void aldagaiGorde()throws NumberFormatException{
		kotxezabal= getAnchoCoche();
		longitudCoche= getLongitudCoche();
		pistaZabal=getAnchoPista();
		rektaLuze=getLongitudRecta();
		erradio= getRadioAncho();
		marrus=getCoefFriccion();
		if(kotxezabal<=pistaZabal&&pistaZabal>0&&rektaLuze>=0
				&&marrus>=0){
			Circuito circuito= new Circuito(erradio, pistaZabal, rektaLuze, marrus);
			Coche coche= new Coche(kotxezabal, longitudCoche);
			Recorrido recorrido= new Recorrido();
			recorrido.crearRecorrido(circuito, coche, getReloj()?Sentido.CLOCKWISE:Sentido.COUNTERCLOCKWISE);
			conjunto= new Conjunto(circuito, coche, recorrido);
			preparado=true;
			this.dispose();
		}else{
			JOptionPane.showMessageDialog(this, 
					"Balio horiek ez dira onak.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			}			
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		
		case "Ok":
			if(anchoCoche_tf.getText().length()!=0 &&longitudCoche_tf.getText().length()!=0
			&&anchoPista_tf.getText().length()!=0&&longitudRecta_tf.getText().length()!=0
			&&radioAncho_tf.getText().length()!=0&&coefFriccion_tf.getText().length()!=0){
				
				try{
					aldagaiGorde();
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog
					(this, 	"Formato de los datos no valido",
							"ERROR", JOptionPane.ERROR_MESSAGE);		
				}
				
				
			}else{
				JOptionPane.showMessageDialog(this, 
						"Rellene todos los campos por favor.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				}		
			break;
		
		}
	}
	
	public double getLongitudCoche() {
		return Double.parseDouble(longitudCoche_tf.getText());
	}
	public double getAnchoCoche() {
		return Double.parseDouble(anchoCoche_tf.getText());
	}
	public double getAnchoPista() {
		return Double.parseDouble(anchoPista_tf.getText());
	}
	public double getLongitudRecta() {
		return Double.parseDouble(longitudRecta_tf.getText());
	}
	public double getRadioAncho() {
		return Double.parseDouble(radioAncho_tf.getText());
	}
	public double getCoefFriccion() {
		return Double.parseDouble(coefFriccion_tf.getText());
	}
	public boolean isPreparado() {
		return preparado;
	}

	public boolean getReloj() {
		if(reloj.isSelected()){
			return true;
		}else{
			return false;
		}
	}
	public Conjunto getConjunto() {
		return conjunto;
	}
}
	
	

	
