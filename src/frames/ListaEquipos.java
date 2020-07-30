package frames;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.EquipoDAO;
import dto.Equipo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaEquipos extends JFrame
{
	private static final long serialVersionUID = -9148357355175993743L;
	private JPanel contentPane;
	private EquipoDAO equipodao;
	private JScrollPane scrollPane;
	private JTable table; 
	private JButton btnCrear;
	private JButton btnEditar;
	private JButton btnBorrar;
	private ListaEquipos thisFrame = this;
	
    String[] columnNames = { "Codigo", "Descripcion", "Cantidad"}; 
    String[][] data;
    private JButton btnActualizar;
    
	public ListaEquipos(String perfil)
	{
		equipodao = new EquipoDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblListaDeUsuarios = new JLabel("Lista de Equipos");
		
		
		updateTable();
		table = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane = new JScrollPane(table);
		//scrollPane.setBounds(100,100,100,100);
		
		if (perfil.equals("user"))
		{
			btnEditar.setEnabled(false);
			btnBorrar.setEnabled(false);
		}
		
		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DetallesEquipoFrame("crear", null, thisFrame);
			}
		});
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCodigo = "";
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedCodigo = table.getModel().getValueAt(selectedRow, 0).toString();
					new DetallesEquipoFrame("editar", equipodao.getEquipo(selectedCodigo), thisFrame);
				}
			}
		});
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCodigo = "";
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1 )
				{
					selectedCodigo = table.getModel().getValueAt(selectedRow, 0).toString();
					equipodao.borrarEquipo(selectedCodigo);
					updateTable();
				}
			}
		});
		
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
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
		ArrayList<Equipo> equipos = equipodao.getEquipos();

        
		data = new String[equipos.size()][3]; 
		
		for (int i = 0; i < equipos.size(); i++)
		{
			Equipo e = equipos.get(i);			
			
			String[] dataUser = {e.getCodigo(), e.getDescripcion(), e.getCantidad() + "" };
			
			for (int j = 0; j < 3; j++)
			{
				data[i][j] = dataUser[j];
			}
		}
		
		if (table != null)
			table.setModel(new DefaultTableModel(data, columnNames));
	}
}
