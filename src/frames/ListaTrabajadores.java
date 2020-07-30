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

import dao.TrabajadorDAO;
import dto.Trabajador;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaTrabajadores extends JFrame
{
	private static final long serialVersionUID = 7472140230565627L;
	private ListaTrabajadores thisFrame = this;
	private JPanel contentPane;
	private TrabajadorDAO trabajadordao;
	private JScrollPane scrollPane;
	private JTable table; 
	private JButton btnCrear;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnActualizar;
	
	String[][] data; 
    String[] columnNames = {"Nombre", "Apellido paterno", "Apellido materno", "Run", "Empresa" , "Correo", "Telefono"}; 
    
	public ListaTrabajadores(String perfil)
	{
		trabajadordao = new TrabajadorDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblListaDeUsuarios = new JLabel("Lista de Trabajadores");

		
		updateTable();		
		table = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane = new JScrollPane(table);
		
		
		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DetallesTrabajadorFrame("crear", null, thisFrame);
			}
		});
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedRun = "";
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedRun = table.getModel().getValueAt(selectedRow, 3).toString();
					new DetallesTrabajadorFrame("editar", trabajadordao.getTrabajador(selectedRun), thisFrame);
				}
			}
		});
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedRun = "";
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1)
				{
					selectedRun = table.getModel().getValueAt(selectedRow, 3).toString();
					trabajadordao.borrarTrabajador(selectedRun);
					updateTable();
				}
			}
		});
		
		if (perfil.equals("user"))
		{
			btnEditar.setEnabled(false);
			btnBorrar.setEnabled(false);
		}
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
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
					.addComponent(btnEditar)
					.addGap(18)
					.addComponent(btnBorrar)
					.addGap(18)
					.addComponent(btnActualizar)
					.addContainerGap(278, Short.MAX_VALUE))
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCrear)
						.addComponent(btnEditar)
						.addComponent(btnBorrar)
						.addComponent(btnActualizar)))
		);
		
		
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	public void updateTable()
	{
		ArrayList<Trabajador> trabajadores = trabajadordao.getTrabajadores();

		data = new String[trabajadores.size()][7]; 
		
		for (int i = 0; i < trabajadores.size(); i++)
		{
			Trabajador e = trabajadores.get(i);			
			
			String[] dataUser = {e.getNombre(), e.getApPat(), e.getApMat(), e.getRun(), e.getEmpresa(), e.getCorreo(), e.getTelefono() };
			
			for (int j = 0; j < 7; j++)
			{
				data[i][j] = dataUser[j];
			}
		}
		
		if (table != null)
			table.setModel(new DefaultTableModel(data, columnNames));
	}
}
