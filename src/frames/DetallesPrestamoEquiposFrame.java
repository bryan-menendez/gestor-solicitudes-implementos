package frames;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.EquipoDAO;
import dao.PrestamoDAO;
import dto.Equipo;
import dto.Prestamo;
import dto.Trabajador;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class DetallesPrestamoEquiposFrame extends JFrame 
{
	private static final long serialVersionUID = 9133605101420653611L;
	private JPanel contentPane;
	private JTextField fieldCantidad;
	private EquipoDAO equipodao;
	private PrestamoDAO prestamodao;
	private JTable tableL; 
	private JTable tableR;
	private JLabel lblResponse;
	private JTextArea fieldDescripcion;
	private ListaPrestamos parentFrame;
	
    String[] columnNames = { "Codigo", "Descripcion", "Cantidad"}; 
    String[][] dataL;
    String[][] dataR = { };
    int dataLSize;
    
    ArrayList<Equipo> equiposR;
    private JScrollPane scrollPaneL_1;
    private JScrollPane scrollPaneR_1;
    private JButton btnGuardar;
    private JLabel lblDescripcion;

	public DetallesPrestamoEquiposFrame(Prestamo p, Trabajador t, ListaPrestamos parentFrame) 
	{
		equipodao = new EquipoDAO();
		prestamodao = new PrestamoDAO();
		equiposR = new ArrayList<Equipo>();
		this.parentFrame = parentFrame;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 759, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblEquiposPertenecientesAl = new JLabel("Equipos pertenecientes al prestamo");
		
		JLabel lblResponsable = new JLabel("Responsable: " + t.getNombre() + " " + t.getApPat() + " " + t.getApMat());
		
		fieldCantidad = new JTextField();
		fieldCantidad.setHorizontalAlignment(SwingConstants.CENTER);
		fieldCantidad.setText("1");
		fieldCantidad.setColumns(10);
		fieldDescripcion = new JTextArea();
		
		getDataTable();
		tableL = new JTable(new DefaultTableModel(dataL, columnNames));
		scrollPaneL_1 = new JScrollPane(tableL);
		scrollPaneL_1.setViewportView(tableL);
		
		if (p != null)
		{
			lblEquiposPertenecientesAl.setText("Equipos pertenecientes al prestamo: " + p.getIdPrestamo() );
			fieldDescripcion.setText(p.getDescripcion());
			getEquiposPrestamo(p.getIdPrestamo()); //if modifying, get the previous data
			updateTableR();
		}
			
		
		tableR = new JTable(new DefaultTableModel(dataR, columnNames));
		scrollPaneR_1 = new JScrollPane(tableR);
		scrollPaneR_1.setViewportView(tableR);
		
		
		JButton btnAdd = new JButton(">");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEquipo();
			}
		});
		JButton btnDel = new JButton("<");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delEquipo();
			}
		});
		
		lblResponse = new JLabel("");
		lblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnGuardar = new JButton("Guardar prestamo");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarPrestamo(p, t);
			}
		});
		
		lblDescripcion = new JLabel("Descripcion");
		

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblResponse, GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblResponsable)
								.addComponent(lblEquiposPertenecientesAl)
								.addComponent(scrollPaneL_1, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(btnAdd)
											.addComponent(fieldCantidad, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnDel))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(scrollPaneR_1, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))))
						.addComponent(fieldDescripcion, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescripcion))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEquiposPertenecientesAl)
							.addGap(25)
							.addComponent(lblResponsable)
							.addGap(18))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addGap(23)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneR_1, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(fieldCantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAdd)
							.addGap(55)
							.addComponent(btnDel)
							.addGap(77))
						.addComponent(scrollPaneL_1, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 11, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblDescripcion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(fieldDescripcion, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblResponse))
		);
		contentPane.setLayout(gl_contentPane);
		
		setVisible(true);
	}

	private void guardarPrestamo(Prestamo p, Trabajador t)
	{
		//check if prestamo is null
		//if so, this is an insert, pass some values
		if (p == null)
		{
			p = new Prestamo();
			p.setDescripcion(fieldDescripcion.getText());
			p.setTrabajadorFK(t.getIdTrabajador());
			p.setEquipos(equiposR);
			
			lblResponse.setText(prestamodao.crearPrestamo(p));
			
			if (lblResponse.getText().equals("Prestamo creado con exito"))
			{
				parentFrame.updateTable();
				dispose();
			}
				
			return;
		}
		else 
		{
			p.setDescripcion(fieldDescripcion.getText());
			p.setTrabajadorFK(t.getIdTrabajador());
			p.setEquipos(equiposR);
				
			lblResponse.setText(prestamodao.editarPrestamo(p));
			
			if (lblResponse.getText().equals("Prestamo editado con exito"))
			{
				parentFrame.updateTable();
				dispose();
			}
				
			return;
		}
	}
	
	private void addEquipo()
	{
		//read selected item
		//String selectedCodigo = "";
		int selectedCantidad = 0;
		int requestedCantidad = 0;
		
		int selectedRow = tableL.getSelectedRow();
		
		if (selectedRow != -1)
		{
			//selectedCodigo = tableL.getModel().getValueAt(selectedRow, 0).toString();
			selectedCantidad = Integer.parseInt((String) tableL.getModel().getValueAt(selectedRow, 2));
			requestedCantidad = Integer.parseInt(fieldCantidad.getText());
		}
		else
			return;
			
		
		//validate that the amount requested is less or equal to the amount available
		if (requestedCantidad <= selectedCantidad)
		{
			//subtract the quantity fieldCantidad.text from the selected item
			dataL[selectedRow][2] = "" + (Integer.parseInt(dataL[selectedRow][2]) - requestedCantidad);
			
			//add the selected item to the R list with quantity fieldCantidad.text
			Equipo e = new Equipo();
			
			e.setCodigo(dataL[selectedRow][0]);
			e.setDescripcion(dataL[selectedRow][1]);
			e.setCantidad(requestedCantidad);
			
			equiposR.add(e);
			
			//update both lists
			updateTableL();
			updateTableR();
		}
		else
		{
			lblResponse.setText("No hay suficiente stock");
			return;
		}
		
		lblResponse.setText("");
	}
	
	private void delEquipo()
	{
		int selectedRow = tableR.getSelectedRow();
		
		if (selectedRow != -1)
		{
			int cantidadDevuelta = equiposR.get(selectedRow).getCantidad();
			String codigoDevuelto = (String) tableR.getValueAt(selectedRow, 0);
			
			equiposR.remove(selectedRow);
			
			//return the amount deleted
			for (int i = 0; i < dataLSize; i++)
			{
				//find the entry, and add the value back
				if (dataL[i][0].equals(codigoDevuelto))
				{
					dataL[i][2] = "" + (Integer.parseInt(dataL[i][2]) + cantidadDevuelta);
					break;
				}
			}

			updateTableL();
			updateTableR();
		}
		else
			return;
	}
	
	public void updateTableL()
	{
		tableL.setModel(new DefaultTableModel(dataL, columnNames));
	}
	
	public void updateTableR()
	{
		dataR = new String[equiposR.size()][7]; 
		
		for (int i = 0; i < equiposR.size(); i++)
		{
			Equipo e = equiposR.get(i);			
			
			String[] dataUser = {e.getCodigo(), e.getDescripcion(), e.getCantidad() + "" };
			
			for (int j = 0; j < 3; j++)
			{
				dataR[i][j] = dataUser[j];
			}
		}
		
		if (tableR != null)
			tableR.setModel(new DefaultTableModel(dataR, columnNames));
	}
	
	public void getDataTable()
	{
		ArrayList<Equipo> equipos = equipodao.getEquipos();
		dataLSize = equipos.size();
		
		dataL = new String[dataLSize][3]; 
		
		for (int i = 0; i < equipos.size(); i++)
		{
			Equipo e = equipos.get(i);			
			
			String[] dataUser = {e.getCodigo(), e.getDescripcion(), e.getCantidad() + "" };
			
			for (int j = 0; j < 3; j++)
			{
				dataL[i][j] = dataUser[j];
			}
		}
		
		if (tableL != null)
			tableL.setModel(new DefaultTableModel(dataL, columnNames));
	}
	
	public void getEquiposPrestamo(int id)
	{
		equiposR = prestamodao.getEquiposPrestamo(id);
		
		dataR = new String[equiposR.size()][3]; 
		
		for (int i = 0; i < equiposR.size(); i++)
		{
			Equipo e = equiposR.get(i);			
			
			String[] dataUser = {e.getCodigo(), e.getDescripcion(), e.getCantidad() + "" };
			
			for (int j = 0; j < 3; j++)
			{
				dataR[i][j] = dataUser[j];
			}
		}
		
		if (tableR != null)
			tableR.setModel(new DefaultTableModel(dataR, columnNames));
	}
}
