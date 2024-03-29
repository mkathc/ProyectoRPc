package presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import conectionDB.ConectionDB;
import datos.ConsultasBasicas;
import entidades.Tienda;
import modeloTablas.ModeloTablaTienda;
import negocio.BuscarTiendas;
import negocio.OrdenarTiendas;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

public class VentanaTiendas extends JDialog {
	private JTextField textFieldBusqueda;
	private static JTable tableTiendas;
	private VentanaTiendas miVentanaTiendas;
	private List<Tienda> listaDeTiendas;
	private List<Tienda> listaActual;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public VentanaTiendas(VentanaPrincipal miVentanaPrincipal, boolean modal) {
		
		super(miVentanaPrincipal, modal);
		setResizable(false);
		setTitle("Tiendas - RantiyPC");
		setBounds(100, 100, 1027, 775);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPaneTiendas = new JScrollPane();
		scrollPaneTiendas.setBounds(49, 193, 938, 397);
		getContentPane().add(scrollPaneTiendas);
		
		listaDeTiendas = new ArrayList<Tienda>();
		listaActual = new ArrayList<Tienda>();
		
		CargarListaDeTiendas(); 
		TableModel tableModel = new ModeloTablaTienda(listaDeTiendas);
		
		tableTiendas = new JTable(tableModel); //asigna el modelo a la tabla
		
		listaActual = listaDeTiendas;
		
		scrollPaneTiendas.setViewportView(tableTiendas);
		
		JRadioButton rdbtnNombre = new JRadioButton("");
		rdbtnNombre.setContentAreaFilled(false);
		rdbtnNombre.setSelected(true);
		buttonGroup.add(rdbtnNombre);
		rdbtnNombre.setBounds(524, 41, 21, 23);
		getContentPane().add(rdbtnNombre);
		
		JRadioButton rdbtnLugar = new JRadioButton("");
		rdbtnLugar.setContentAreaFilled(false);
		buttonGroup.add(rdbtnLugar);
		rdbtnLugar.setBounds(747, 41, 21, 23);
		getContentPane().add(rdbtnLugar);
		
		JComboBox comboBoxOrden = new JComboBox();
		comboBoxOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OrdenarTiendas.ordenar(comboBoxOrden.getSelectedItem(), listaActual);
			}
		});
		comboBoxOrden.setFont(new Font("Tahoma", Font.PLAIN, 25));
		comboBoxOrden.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "Ubicaci\u00F3n"}));
		comboBoxOrden.setBounds(256, 115, 135, 33);
		getContentPane().add(comboBoxOrden);
		
		JLabel lblOdenarPor = new JLabel("Odenar por:");
		lblOdenarPor.setFont(new Font("Berlin Sans FB", Font.PLAIN, 30));
		lblOdenarPor.setForeground(Color.WHITE);
		lblOdenarPor.setBounds(70, 114, 216, 35);
		getContentPane().add(lblOdenarPor);
		
		textFieldBusqueda = new JTextField();
		textFieldBusqueda.setForeground(Color.WHITE);
		textFieldBusqueda.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));
		textFieldBusqueda.setOpaque(false);
		textFieldBusqueda.setBorder(null);
		textFieldBusqueda.setBounds(487, 88, 435, 30);
		getContentPane().add(textFieldBusqueda);
		textFieldBusqueda.setColumns(10);
		
		JButton buttonBuscar = new JButton("");
		buttonBuscar.setContentAreaFilled(false);
		buttonBuscar.setBorder(null);
		buttonBuscar.setIcon(new ImageIcon(VentanaTiendas.class.getResource("/resources/seleccionarItem.png")));
		buttonBuscar.setSelectedIcon(new ImageIcon(VentanaTiendas.class.getResource("/resources/lupa.png")));
		buttonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BuscarTiendas.buscar(textFieldBusqueda.getText().toLowerCase(), rdbtnNombre.isSelected(), listaDeTiendas);
			}
		});
		buttonBuscar.setBounds(947, 77, 53, 41);
		getContentPane().add(buttonBuscar);
		
		JButton buttonMostrar = new JButton("Mostrar");
		buttonMostrar.setBorderPainted(false);
		buttonMostrar.setContentAreaFilled(false);
		buttonMostrar.setIcon(new ImageIcon(VentanaTiendas.class.getResource("/resources/BotonMostrarTienda.png")));
		buttonMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == buttonMostrar){
					if(tableTiendas.getSelectedRow() > -1){
						VentanaTiendaElegida miVentanaTiendaElegida = new VentanaTiendaElegida(miVentanaTiendas, true, tableTiendas.getSelectedRow(), listaDeTiendas);
						miVentanaTiendaElegida.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						miVentanaTiendaElegida.setVisible(true);
					}
					
				}
			}
		});
		buttonMostrar.setBounds(311, 641, 395, 95);
		getContentPane().add(buttonMostrar);
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setIcon(new ImageIcon(VentanaTiendas.class.getResource("/resources/fondo-Tienda.png")));
		labelFondo.setBounds(0, 0, 1034, 747);
		getContentPane().add(labelFondo);
		
	}
	
	//llena la lista de tiendas
	public void CargarListaDeTiendas(){
		//conectamos la base de datos
		ConectionDB.getConection();
		ResultSet datos;
		datos = ConsultasBasicas.consultarDatos("SELECT* FROM tiendas");
		try {
			
			while(datos.next()){
				Tienda t;
				t = new Tienda(datos.getString("nombre"), datos.getString("ubicacion"), datos.getString("linkDeUbicacion"),
						datos.getString("linkDeTienda"), datos.getString("telefono"), datos.getString("email"));
				//llenamos la lista de tiendas
				listaDeTiendas.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setVentanaPrincipal(VentanaTiendas miVentana) {
		miVentanaTiendas = miVentana;
	}
	
	public static void setModel(TableModel modelo){
		tableTiendas.setModel(modelo);
	}
}
