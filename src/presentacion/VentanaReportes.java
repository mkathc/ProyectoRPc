package presentacion;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import conectionDB.ConectionDB;
import datos.ConsultasBasicas;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import entidades.Reporte;
import modeloTablas.ModeloTablaReporte;
import java.awt.Window.Type;
public class VentanaReportes extends JDialog {

	private JPanel contentPane;
	private JTextField textBusqueda;
	private JTable table;
	private List<Reporte> listadeReportes;
	private JTable TablaDeReportes;
	private JScrollPane spTablaDeReportes;
    private JComboBox comboBox;
    private JComboBox comboBox1 ;
    private ResultSet rss,ResultadodeBusqueda;
    private List<Reporte>ListaDeReportesB,ListaDeReportesC;
    private JButton btnBuscar;
	
	public VentanaReportes(VentanaPrincipal miVentanaPrincipal, boolean modal) {
		super(miVentanaPrincipal,modal);
		setTitle("Ventana Reportes");
		setBounds(100, 100, 641, 633);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBackground(Color.WHITE);
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Buscar por", "Nombre", "Tipo", "Fecha", "         semana"}));
		comboBox.setBounds(129, 110, 108, 23);
		comboBox.addActionListener(new Manejador());
		contentPane.add(comboBox);
		
		comboBox1 = new JComboBox();
		comboBox1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		comboBox1.setBackground(Color.WHITE);
		comboBox1.setModel(new DefaultComboBoxModel(new String[] {"Ordenar por","Nombre","Tipo","Fecha"}));
		comboBox1.setBounds(11, 110, 109, 23);
		comboBox1.addItemListener(new ManejadorOrdenar());
		contentPane.add(comboBox1);
		
		textBusqueda = new JTextField();
		textBusqueda.setBounds(247, 110, 229, 22);
		contentPane.add(textBusqueda);
		textBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(VentanaReportes.class.getResource("/resources/lupa.png")));
		btnBuscar.setFont(new Font("Papyrus", Font.PLAIN, 14));
		btnBuscar.setBounds(486, 101, 129, 41);
		btnBuscar.addActionListener(new Manejador());
		contentPane.add(btnBuscar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnGuardar.setBounds(502, 533, 89, 23);
		contentPane.add(btnGuardar);
		
		JLabel lblReportes = new JLabel("Reportes");
		lblReportes.setForeground(SystemColor.activeCaptionText);
		lblReportes.setFont(new Font("PeacerfulDay", Font.BOLD, 39));
		lblReportes.setBounds(10, 21, 373, 62);
		contentPane.add(lblReportes);
		
		JLabel lblListadoDeReportes = new JLabel("Listado de reportes:");
		lblListadoDeReportes.setFont(new Font("Papyrus", Font.BOLD, 15));
		lblListadoDeReportes.setBounds(10, 172, 161, 14);
		contentPane.add(lblListadoDeReportes);
		
		
		listadeReportes = new ArrayList<Reporte>();
		CargarListaDeReportes();
		TableModel tableModel = new ModeloTablaReporte(listadeReportes);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 625, 595);
		contentPane.add(label);
		TablaDeReportes = new JTable(tableModel);
		TablaDeReportes.setFont(new Font("Papyrus", Font.PLAIN, 11));
		TablaDeReportes.setFillsViewportHeight(true);
		TablaDeReportes.setColumnSelectionAllowed(true);
		TablaDeReportes.setCellSelectionEnabled(true);
		//TablaDeReportes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		spTablaDeReportes = new JScrollPane();
		spTablaDeReportes.setSize(604, 302);
		spTablaDeReportes.setLocation(11, 197);
		
		spTablaDeReportes.setViewportView(TablaDeReportes);
     
		contentPane.add(spTablaDeReportes);
	}
	public class Manejador implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnBuscar){
				 ManejadorBuscarPor();
			}else{
				
				
			}
			
		}
	
	}
	public class  ManejadorOrdenar implements ItemListener{
	
		public void itemStateChanged(ItemEvent e) {
		ConectionDB.getConection();
	    
	    Reporte r;
	    ListaDeReportesB = new ArrayList<Reporte>();
		int opc = comboBox1.getSelectedIndex();
		
		switch(opc){
		case 1 :  
		    	       
		         rss = ConsultasBasicas.consultarDatos("SELECT* FROM reportes ORDER BY Nombre");
		        
				try {
					while(rss.next()){
						 r = new Reporte(rss.getString("Nombre"),rss.getString("Tipo"),rss.getDate("Fecha"));		                   ListaDeReportesB.add(r);
		              }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		             
		          break;
		case 2: 
			rss = ConsultasBasicas.consultarDatos("SELECT* FROM reportes ORDER BY Tipo");
        
		    try {
			while(rss.next()){
            	   r = new Reporte(rss.getString("Nombre"),rss.getString("Tipo"),rss.getDate("Fecha"));
                   ListaDeReportesB.add(r);
              }
		    } catch (SQLException e1) {
			
			e1.printStackTrace();
		    }
             
          break;
		case 3: 
			rss = ConsultasBasicas.consultarDatos("SELECT* FROM reportes ORDER BY Fecha");
        
		try {
			while(rss.next()){
				 r = new Reporte(rss.getString("Nombre"),rss.getString("Tipo"),rss.getDate("Fecha"));                   
				 ListaDeReportesB.add(r);
              }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
             
          break;
		
		  default:;
		       
		   
		  }
		    
		   TableModel tableModel2 = new ModeloTablaReporte(ListaDeReportesB);
		   TablaDeReportes.setModel(tableModel2); 
	    }
			
	
		}
	
	public void ManejadorBuscarPor() {

			Reporte r ;
			ConectionDB.getConection();
			int indice= comboBox.getSelectedIndex();
			ListaDeReportesC = new ArrayList<Reporte>();
			switch (indice){
			case 1:		   		ResultadodeBusqueda = ConsultasBasicas.consultarDatos("SELECT * FROM reportes WHERE Nombre = '"+textBusqueda.getText()+"'");
		
			       				try {
			       				while (ResultadodeBusqueda.next()){
			       					r = new Reporte(ResultadodeBusqueda.getString("Nombre"),ResultadodeBusqueda.getString("Tipo"),ResultadodeBusqueda.getDate("Fecha"));                  
			       					System.out.println(r.getNombre());
			       					ListaDeReportesC.add(r);
			       					} 
			        	   		}catch (SQLException e) {
							
			        	   			e.printStackTrace();
			        	   		}
			       				break;
			       				
			case 2:     		ResultadodeBusqueda = ConsultasBasicas.consultarDatos("SELECT * FROM reportes WHERE Tipo = '"+textBusqueda.getText()+"'");
		    
			           			try {
			           			while (ResultadodeBusqueda.next()){
			           				r = new Reporte(ResultadodeBusqueda.getString("Nombre"),ResultadodeBusqueda.getString("Tipo"),ResultadodeBusqueda.getDate("Fecha"));                   
			           				ListaDeReportesC.add(r);
			           			} 
			      
			           			}catch (SQLException e1) {
				
			           				e1.printStackTrace();
			           			}
			           		   break;
			case 3: 	ResultadodeBusqueda = ConsultasBasicas.consultarDatos("SELECT * FROM reportes  WHERE Fecha='"+textBusqueda.getText()+"'");
		    
			   				try {
			   					while (ResultadodeBusqueda.next()){
			   						r = new Reporte(ResultadodeBusqueda.getString("Nombre"),ResultadodeBusqueda.getString("Tipo"),ResultadodeBusqueda.getDate("Fecha"));                  
			   						ListaDeReportesC.add(r);
			   					} 
			   					}catch (SQLException e12) {
				
			   						e12.printStackTrace();
			   					}break;
			default:;
			}
			
			TableModel tableModel3 = new ModeloTablaReporte(ListaDeReportesC);
			TablaDeReportes.setModel(tableModel3);
		}
	
		
	public void CargarListaDeReportes(){
		ResultSet rs;
		ConectionDB.getConection();

		rs = ConsultasBasicas.consultarDatos("SELECT* FROM reportes");
		
	    try {
			while (rs.next()){
				Reporte r;
				r=new Reporte(rs.getString("Nombre"),rs.getString("Tipo"),rs.getDate("Fecha"));
				listadeReportes.add(r);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
