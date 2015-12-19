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

@SuppressWarnings("serial")
public class DialogoNuevoConjunto extends JDialog implements ActionListener {
	
	JTextField texto1;
	JTextField texto2;
	JTextField texto3;
	JTextField texto4;
	JTextField texto5; 
	JRadioButton reloj;
	JRadioButton contrareloj;
	ButtonGroup grupo;
	JButton botonOk;
	Conjunto conjunto;
	Sentido sentido;
	Idioma idioma;

	boolean prest=false;
	double kotxezabal;
	double pistaZabal;
	double rektaLuze;
	double erradio;
	double marrus;
	
	
	public DialogoNuevoConjunto (VentanaPrincipal ventana,String titulo, boolean modo) {
		super(ventana,titulo,modo);
		this.idioma=ventana.getIdioma();
		this.setBounds(200, 75, 400,400);
		this.add(this.panelNagusia());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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

		JPanel panelCentral=new JPanel(new GridLayout(6,1,10,10));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		texto1=new JTextField("",20);
		texto2=new JTextField("",20);
		texto3=new JTextField("",20);
		texto4=new JTextField("",20);
		texto5=new JTextField("",20);
		
		panelCentral.add(texto1);
		panelCentral.add(texto2);
		panelCentral.add(texto3);
		panelCentral.add(texto4);
		panelCentral.add(texto5);
		panelCentral.add(añadirReloj());
		return panelCentral;
	}
	
	private Container añadirReloj(){
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
		texto1.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("AnchoCoche","Ancho del coche")));//metrotan
		texto2.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("AnchoPista","Ancho de la pista")));//metrotan
		texto3.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("LongitudRecta","Longitud de la recta")));//metrotan
		texto4.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("RadioAncho","Radio más ancho")));//metrotan
		texto5.setBorder(BorderFactory.createTitledBorder(idioma.getProperty("CoeficienteFriccion","Coeficiente de fricción")));
		botonOk.setText("Ok");
		reloj.setText(idioma.getProperty("Reloj","Reloj"));
		contrareloj.setText(idioma.getProperty("Contrareloj","Contrareloj"));
	}
	
	private void aldagaiGorde()throws NumberFormatException{
		kotxezabal= getTexto1();pistaZabal=getTexto2();rektaLuze=getTexto3();
		erradio= getTexto4();marrus=getTexto5();
		if(kotxezabal<=pistaZabal&&pistaZabal>0&&rektaLuze>=0
				&&erradio>(pistaZabal*2)&&marrus>=0){
			Circuito circuito= new Circuito(erradio, pistaZabal, rektaLuze);
			Coche coche= new Coche(kotxezabal);
			Recorrido recorrido= new Recorrido();
			recorrido.crearRecorrido(circuito, coche, getReloj()?Sentido.CLOCKWISE:Sentido.COUNTERCLOCKWISE);
			conjunto= new Conjunto(circuito, coche, recorrido);
			prest=true;
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
			if(texto1.getText().length()!=0 &&texto2.getText().length()!=0
			&&texto3.getText().length()!=0&&texto4.getText().length()!=0
			&&texto5.getText().length()!=0){
				
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
	
	public double getTexto1() {
		return Double.parseDouble(texto1.getText());
	}
	public double getTexto2() {
		return Double.parseDouble(texto2.getText());
	}
	public double getTexto3() {
		return Double.parseDouble(texto3.getText());
	}
	public double getTexto4() {
		return Double.parseDouble(texto4.getText());
	}
	public double getTexto5() {
		return Double.parseDouble(texto5.getText());
	}
	public boolean isPrest() {
		return prest;
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
	
	

	
