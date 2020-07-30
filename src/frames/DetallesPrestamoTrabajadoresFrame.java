package frames;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

//import dao.PrestamoDAO;
import dao.TrabajadorDAO;
import dto.Prestamo;
import dto.Trabajador;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetallesPrestamoTrabajadoresFrame extends JFrame 
{
	private static final long serialVersionUID = 9133605101420653611L;
	private JPanel contentPane;

	private TrabajadorDAO trabajadordao;
	private JScrollPane scrollPane_1_1;
	
	String[][] data; 
    String[] columnNames = {"Nombre", "Apellido paterno", "Apellido materno", "Run", "Empresa" , "Correo", "Telefono"}; 
    private JTable table_1;
    private JLabel lblAsignado;
    

	public DetallesPrestamoTrabajadoresFrame(Prestamo prestamo, ListaPrestamos parentFrame) 
	{
		trabajadordao = new TrabajadorDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 522, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTitulo = new JLabel("Trabajador asociado al prestamo");
		
		updateTable();
		table_1 = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane_1_1 = new JScrollPane(table_1);
		scrollPane_1_1.setViewportView(table_1);
		
				
		String asignado = "Seleccione un trabajador";
		if (prestamo != null)
		{
			Trabajador t = trabajadordao.getTrabajador(prestamo.getTrabajadorFK());
			asignado = t.getNombre() + " " + t.getApPat() + " " + t.getApMat();
		}
			
		lblAsignado = new JLabel("Asignado: " + asignado);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String selectedRun = "";
				int selectedRow = table_1.getSelectedRow();
				
				//INSERT 
				if (selectedRow == -1 && prestamo == null)
				{
					selectedRun = table_1.getModel().getValueAt(selectedRow, 3).toString();
					Trabajador t = trabajadordao.getTrabajador(selectedRun);
					new DetallesPrestamoEquiposFrame(null, t, parentFrame);
					return;
				}
					
				//UPDATE
				//if none selected, but has a FK assigned
				if (selectedRow == -1 && prestamo.getTrabajadorFK() != 0)
					new DetallesPrestamoEquiposFrame(prestamo, trabajadordao.getTrabajador(prestamo.getTrabajadorFK()), parentFrame);
				
				//something new selected
				if (selectedRow != -1)
				{
					selectedRun = table_1.getModel().getValueAt(selectedRow, 3).toString();
					Trabajador t = trabajadordao.getTrabajador(selectedRun);
					new DetallesPrestamoEquiposFrame(prestamo, t, parentFrame);
				}
				
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane_1_1, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(125)
							.addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(132))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAsignado))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(399, Short.MAX_VALUE)
							.addComponent(btnSeleccionar)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblTitulo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAsignado)
					.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
					.addComponent(scrollPane_1_1, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSeleccionar)
					.addContainerGap())
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
		
		if (table_1 != null)
			table_1.setModel(new DefaultTableModel(data, columnNames));
	}
}
