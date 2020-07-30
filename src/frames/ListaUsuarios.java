package frames;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.UsuarioDAO;
import dto.Usuario;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaUsuarios extends JFrame 
{
	private static final long serialVersionUID = 4486562739188661872L;
	private JPanel contentPane;
	private JTable table;
	private UsuarioDAO usuariodao;
	private JScrollPane scrollPane;
	private JButton btnCrear;
	private JButton btnBorrar;
	private JButton btnEditar;
	private JButton btnActualizar;

	String[][] data;
    String[] columnNames = { "Nombre", "Apellido paterno", "Apellido materno", "Run", "Perfil" , "Username", "Password" }; 
	
	public ListaUsuarios(String perfil) 
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		usuariodao = new UsuarioDAO();
		setBounds(100, 100, 640, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		ListaUsuarios thisFrame = this;
		
		JLabel lblListaDeUsuarios = new JLabel("Lista de Usuarios");

		updateTable();		
		table = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane = new JScrollPane(table);
		
		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DetallesUsuarioFrame("crear", null, thisFrame);
			}
		});
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectedUsername = "";
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1)
				{
					selectedUsername = table.getModel().getValueAt(selectedRow, 5).toString();
					
					usuariodao.borrarUsuario(selectedUsername);
					updateTable();
				}
				
			}
		});
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedUsername = "";
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1)
				{
					selectedUsername = table.getModel().getValueAt(selectedRow, 5).toString();
					
					new DetallesUsuarioFrame("editar", usuariodao.getUsuario(selectedUsername), thisFrame);
				}
			}
		});
		//scrollPane.setBounds(100,100,100,100);
		
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
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
		ArrayList<Usuario> usuarios = usuariodao.getUsuarios();
        
		data = new String[usuarios.size()][7]; 
		
		for (int i = 0; i < usuarios.size(); i++)
		{
			String perfil = "";
			Usuario u = usuarios.get(i);			
			
			if (u.getPerfil() == 1)
				perfil = "Usuario comun";
			if (u.getPerfil() == 2)
				perfil = "Administrador";
			
			String[] dataUser = {u.getNombre(), u.getApPat(), u.getApMat(), u.getRun(), perfil, u.getUsername(), u.getPassword() };
			
			for (int j = 0; j < 7; j++)
			{
				data[i][j] = dataUser[j];
			}
		}
		
		if (table != null)
			table.setModel(new DefaultTableModel(data, columnNames));
	}
}
