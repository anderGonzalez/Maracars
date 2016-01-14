package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Enums.Modo;
import Interfaces.Controlador;
import Interfaces.Traducible;
import recursos.AccionCambioConjunto;
import recursos.AccionCambioIdioma;
import recursos.AccionCambioModo;
import recursos.Conjunto;
import recursos.Datos;
import recursos.Idioma;


/**
 * Esta clase define la ventana principal de el programa principal.
 * 
 * @author Ander
 */


@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame implements Traducible, Observer {

	final String PROPIEDADES_FILE = "propiedades.txt";
	final String CASTELLANO_FILE = "castellano.txt";
	final String EUSKARA_FILE = "euskara.txt";
	final String ENGLISH_FILE = "english.txt";
	final int ICONSIZE = 24; //px

	Modo modo;
	JTabbedPane tabbedPane;
	ArrayList<Traducible> traducibles;
	Properties propiedades;
	Idioma idioma;
	Action idiomaEusk;
	Action idiomaCast;
	Action idiomaEng;
	Action modoAuto;
	Action modoMan;
	Action accionConjunto;
	Conjunto conjunto;
	JMenu opciones;
	JMenu idiomas;
	JMenu mModos;
	JMenuItem crearConjunto;
	JTable tablaDatos;
	JMenuBar menuBar;
	Controlador panelControlador;
	Datos datos;
	public VentanaPrincipal() {
		datos= new Datos();
		modo = Modo.MANUAL;
		traducibles = new ArrayList<>();
		traducibles.add(this);
		
		idioma = new Idioma(CASTELLANO_FILE);
		idioma.addObserver(this);
		
		cargarPropiedades();
		
		setJMenuBar(crearBarraMenus());
		
		ImageIcon iconoVentana = new ImageIcon(propiedades.getProperty("DirectorioImagenes", "iconos/") + propiedades.getProperty("IconoVentana", "naracars.png"));
		setIconImage(iconoVentana.getImage());
		setSize(1000, 800);
		setResizable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(crearPanelTabs(), BorderLayout.CENTER);
		this.escribirTextos();
		setVisible(true);
	}

	

	private JMenuBar crearBarraMenus() {
		menuBar = new JMenuBar();

		opciones = new JMenu("Opciones");
		accionConjunto= new AccionCambioConjunto(this);
		
		crearMenuIdiomas();
		opciones.add(idiomas);
		crearMenuModo();
		opciones.add(mModos);
		menuBar.add(opciones);
		
		return menuBar;
	}

	private void crearMenuModo() {
		mModos = new JMenu("Modos");
		modoAuto = new AccionCambioModo(Modo.AUTOMATICO, this);
		modoMan = new AccionCambioModo(Modo.MANUAL, this);
		mModos.add(modoAuto);
		mModos.add(modoMan);
		}

	private void crearMenuIdiomas() {
		
		idiomas = new JMenu("Idiomas");
		
		idiomaCast = new AccionCambioIdioma("Castellano", propiedades.getProperty("DirrectorioIdiomas", "idiomas/") + CASTELLANO_FILE , this);
		idiomaEusk = new AccionCambioIdioma("Euskara", propiedades.getProperty("DirrectorioIdiomas", "idiomas/") + EUSKARA_FILE , this);
		idiomaEng = new  AccionCambioIdioma("English", propiedades.getProperty("DirrectorioIdiomas", "idiomas/") + ENGLISH_FILE, this);
		
		idiomas.add(idiomaCast);
		idiomas.add(idiomaEusk);
		idiomas.add(idiomaEng);
		
	}

	/**
	 * @return the traducibles
	 */
	public ArrayList<Traducible> getTraducibles() {
		return traducibles;
	}

	/**
	 * @param traducibles the traducibles to set
	 */
	public void setTraducibles(ArrayList<Traducible> traducibles) {
		this.traducibles = traducibles;
	}

	public Component crearPanelTabs() {
		tabbedPane = new JTabbedPane();
		
		ImageIcon iconTabla = new ImageIcon(propiedades.getProperty("DirectorioImagenes", "iconos/") + propiedades.getProperty("IconoTabla", "table.png"));
		iconTabla = new ImageIcon(iconTabla.getImage().getScaledInstance(ICONSIZE, ICONSIZE, Image.SCALE_SMOOTH));
		JComponent panelTabla = crearPanelTabla();
		
		tabbedPane.addTab("Tabla de datos", iconTabla, panelTabla,
		                  "Tabla con los datos de los sensores");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		ImageIcon iconControl = new ImageIcon(propiedades.getProperty("DirectorioImagenes", "iconos/") + propiedades.getProperty("IconoControlador", "control.png"));
		
		switch (modo) {
		case AUTOMATICO:
			
			iconControl = new ImageIcon(iconControl.getImage().getScaledInstance(ICONSIZE, ICONSIZE, Image.SCALE_SMOOTH));
			JComponent panelControlAuto = crearPanelControlAuto();
			tabbedPane.addTab("Panel de Control Automatico", iconControl, panelControlAuto,
			                  "Panel de control del modo automatico");
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
			ImageIcon iconoMapa = new ImageIcon(propiedades.getProperty("DirectorioImagenes", "iconos/") + propiedades.getProperty("IconoMapa", "mapa.png"));
			iconoMapa = new ImageIcon(iconoMapa.getImage().getScaledInstance(ICONSIZE, ICONSIZE, Image.SCALE_SMOOTH));
			JComponent panelMapa = crearPanelMapa();
			tabbedPane.addTab("Panel de Mapa", iconoMapa, panelMapa,
			                  "Panel con el mapa del circuito");
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
			crearConjunto= new JMenuItem(accionConjunto);
			if(opciones.getMenuComponentCount()==2){
				crearConjunto= new JMenuItem(accionConjunto);
				opciones.add(crearConjunto);
				}
			

			break;
		case MANUAL:
			
			iconControl = new ImageIcon(iconControl.getImage().getScaledInstance(ICONSIZE, ICONSIZE, Image.SCALE_SMOOTH));
			JComponent panelControlMan = crearPanelControlMan();
			tabbedPane.addTab("Panel de Control Manual", iconControl, panelControlMan,
			                  "Panel de control del modo manual");
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
			if(opciones.getMenuComponentCount()==3){
				opciones.remove(crearConjunto);
			}
			
			break;
		}
		return tabbedPane;
	}
	
	

	private JComponent crearPanelControlMan() {
		panelControlador = new PanelControlJoystick(this);
		traducibles.add((Traducible) panelControlador);
		return (JComponent) panelControlador;
	}

	private JComponent crearPanelMapa() {
		
		if(conjunto == null){
			accionConjunto.actionPerformed(null);
		}
		PanelMapa panelMap = new PanelMapa(conjunto);
		JScrollPane panel = new JScrollPane(panelMap);
		
		return panel;
	}

	private JComponent crearPanelControlAuto() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Panel de control automatico"));
		return panel;
	}

	private JComponent crearPanelTabla() {
		JScrollPane panel = new JScrollPane();
		tablaDatos= new TablaDatos(datos);
		panel.setViewportView(tablaDatos);
		return panel;
	}

	private void cargarPropiedades() {
		propiedades = new Properties();
		try {
			propiedades.load(new FileReader(PROPIEDADES_FILE));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Error en la inicialización del programa");
			System.exit(0);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		for (Traducible t : traducibles) {
			t.escribirTextos();
		}
	}

	@Override
	public void escribirTextos() {
		try {
			setTitle(idioma.getProperty("TituloVentana", "MaracarsController")); // TituloVentana
			tabbedPane.setTitleAt(0, idioma.getProperty("TablaDatos", "Tabla de Datos")); //TablaDatos
			tabbedPane.setTitleAt(1, idioma.getProperty("Controlador", "Panel de Control")); //Controlador
			if(tabbedPane.getTabCount()==3){
				tabbedPane.setTitleAt(2, idioma.getProperty("Mapa", "Panel del Mapa")); //Mapa
			}
			
			
			opciones.setText(idioma.getProperty("Configuracion", "Configuracion"));   //Configuracion
			idiomas.setText(idioma.getProperty("Idiomas", "Idiomas")); //Idiomas
			mModos.setText(idioma.getProperty("Modos", "Modos")); //Modos
			if(opciones.getMenuComponentCount()==3){
				crearConjunto.setText(idioma.getProperty("AñadirPista","Añadir pista"));//AñadirPista
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setTablaDatos(JTable tablaDatos) {
		this.tablaDatos = tablaDatos;
	}



	public JTable getTablaDatos() {
		return tablaDatos;
	}



	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}

	/**
	 * @return the idioma
	 */
	public Idioma getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
	
	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Controlador getPanelControlador() {
		return panelControlador;
	}

	public void setPanelControlador(Controlador panelControlador) {
		this.panelControlador = panelControlador;
	}

	public JMenuItem getCrearConjunto() {
		return crearConjunto;
	}

	public JMenu getOpciones() {
		return opciones;
	}
	

	public Conjunto getConjunto() {
		return conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

}
