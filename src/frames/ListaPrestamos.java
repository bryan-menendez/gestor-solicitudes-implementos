package frames;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.PrestamoDAO;
import dao.TrabajadorDAO;
import dto.Prestamo;
import dto.Trabajador;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;

public class ListaPrestamos extends JFrame
{
	private static final long serialVersionUID = 1179813513874725805L;
	private ListaPrestamos thisFrame = this;
	private JPanel contentPane;
	private PrestamoDAO prestamodao;
	private TrabajadorDAO trabajadordao;
	private JScrollPane scrollPane;
	private JTable table; 
	
	String[][] data;
    String[] columnNames = { "ID", "Fecha Prestamo", "Fecha Devolucion", "Descripcion", "Responsable", "Devuelto"}; 
	
	public ListaPrestamos(String perfil)
	{
		prestamodao = new PrestamoDAO();
		trabajadordao = new TrabajadorDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblListaDeUsuarios = new JLabel("Lista de Prestamos");

		updateTable();
		table = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane = new JScrollPane(table);
		
		
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DetallesPrestamoTrabajadoresFrame(null, thisFrame);
			}
		});
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedId = 0;
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedId = Integer.parseInt((String)table.getModel().getValueAt(selectedRow, 0));
					Prestamo p = prestamodao.getPrestamo(selectedId);
					
					new DetallesPrestamoTrabajadoresFrame(p, thisFrame);
				}
			}
		});
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedId = 0;
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedId = Integer.parseInt((String)table.getModel().getValueAt(selectedRow, 0));
					prestamodao.borrarPrestamo(selectedId);
					updateTable();
				}
			}
		});
		//scrollPane.setBounds(100,100,100,100);
		
		
		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedId = 0;
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedId = Integer.parseInt((String)table.getModel().getValueAt(selectedRow, 0));
					prestamodao.devolverPrestamo(selectedId);
					updateTable();
				}
			}
		});
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		
		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedId = 0;
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedId = Integer.parseInt((String)table.getModel().getValueAt(selectedRow, 0));
					Prestamo p = prestamodao.getPrestamo(selectedId);
					
				    FileDialog dialog = new FileDialog((Frame)null, "Select File");
				    dialog.setMode(FileDialog.LOAD);
				    dialog.setFile("*.txt");
				    dialog.setVisible(true);
				    String file = dialog.getDirectory() + dialog.getFile();
				    
				    System.out.println(file + " chosen.");
					System.out.println("Exportando... \n");
					
					String output = p.toString();
					
					OutputStream os = null;
			        try {
			            os = new FileOutputStream(new File(file));
			            os.write(output.getBytes(), 0, output.length());
			        } 
			        catch (Exception ex) {
			            ex.printStackTrace();
			        }
			        finally{
			            try {
			                os.close();
			            } catch (Exception ex) {
			                ex.printStackTrace();
			            }
			        }
					
				}
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(249)
					.addComponent(lblListaDeUsuarios, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
					.addGap(259))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnCrear)
					.addGap(18)
					.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnBorrar)
					.addGap(18)
					.addComponent(btnDevolver)
					.addGap(18)
					.addComponent(btnExportar)
					.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
					.addComponent(btnActualizar)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblListaDeUsuarios)
					.addGap(20)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCrear)
						.addComponent(btnEditar)
						.addComponent(btnBorrar)
						.addComponent(btnDevolver)
						.addComponent(btnActualizar)
						.addComponent(btnExportar)))
		);

		if (perfil.equals("user"))
		{
			btnEditar.setEnabled(false);
			btnBorrar.setEnabled(false);
			
		}
		
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	public void updateTable()
	{
		ArrayList<Prestamo> prestamos = prestamodao.getPrestamos();
        
		data = new String[prestamos.size()][6]; 
		
		for (int i = 0; i < prestamos.size(); i++)
		{
			Prestamo e = prestamos.get(i);
			
			Trabajador t = trabajadordao.getTrabajador(e.getTrabajadorFK());
			String nombre = t.getNombre() + " " + t.getApPat() + " " + t.getApMat();
			String devuelto = "NO";

			if (e.getDevuelto())
				devuelto = "SI";
			
			String[] dataUser = { e.getIdPrestamo() + "", e.getFechaPrestamo(), e.getFechaDevolucion(), e.getDescripcion(), nombre, devuelto };
			
			for (int j = 0; j < 6; j++)
			{
				data[i][j] = dataUser[j];
			}
		}
		
		if (table != null)
			table.setModel(new DefaultTableModel(data, columnNames));
	}
}
