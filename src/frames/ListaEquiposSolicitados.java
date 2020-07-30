package frames;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.EquiposPrestamoDAO;
import dto.Equipo;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaEquiposSolicitados extends JFrame 
{
	private static final long serialVersionUID = 6350226784725283878L;
	private JPanel contentPane;
	private JTextField fieldDiaL;
	private JTextField fieldMesL;
	private JTextField fieldYearL;
	private JTextField fieldDiaI;
	private JTextField fieldMesI;
	private JTextField fieldYearI;
	private JTable table;
	
	String[] columnNames = { "Codigo", "Descripcion", "Cantidad"}; 
    String[][] data;
    
    private EquiposPrestamoDAO equiposprestamodao;

	public ListaEquiposSolicitados() 
	{
		equiposprestamodao = new EquiposPrestamoDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTitulo = new JLabel("Sistema de conteo de equipos solicitados");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblRangoInicial = new JLabel("Rango inicial");
		
		JLabel lblDia = new JLabel("Dia");
		
		fieldDiaL = new JTextField();
		fieldDiaL.setText("");
		fieldDiaL.setColumns(10);
		
		JLabel lblMes = new JLabel("Mes");
		
		fieldMesL = new JTextField();
		fieldMesL.setText("");
		fieldMesL.setColumns(10);
		
		JLabel lblAo = new JLabel("A\u00F1o");
		
		fieldYearL = new JTextField();
		fieldYearL.setText("");
		fieldYearL.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//if any entry is blank, take the whole thing as blank
				if (fieldDiaI.getText().equals("") || fieldMesI.getText().equals("") || fieldYearI.getText().equals("")
						|| fieldDiaL.getText().equals("") || fieldMesL.getText().equals("") || fieldYearL.getText().equals(""))
				{
					updateTable();
					return;
				}
					
				String fechaI = fieldYearI.getText() + "-" + fieldMesI.getText() + "-" + fieldDiaI.getText();
				String fechaL = fieldYearL.getText() + "-" + fieldMesL.getText() + "-" + fieldDiaL.getText();
				updateTable(fechaI, fechaL);
			}
		});
		
		JLabel lblRangoLimite = new JLabel("Rango limite");
		
		JLabel label = new JLabel("Dia");
		
		fieldDiaI = new JTextField();
		fieldDiaI.setText("");
		fieldDiaI.setColumns(10);
		
		JLabel label_1 = new JLabel("Mes");
		
		fieldMesI = new JTextField();
		fieldMesI.setText("");
		fieldMesI.setColumns(10);
		
		JLabel label_2 = new JLabel("A\u00F1o");
		
		fieldYearI = new JTextField();
		fieldYearI.setText("");
		fieldYearI.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		updateTable();
		table = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane.setViewportView(table);
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
						.addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
						.addComponent(btnBuscar, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblRangoLimite)
									.addGap(18)
									.addComponent(lblDia, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(lblRangoInicial)
									.addGap(18)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(fieldDiaI, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(fieldDiaL, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblMes, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(fieldMesI, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(fieldMesL, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblAo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(label_2, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(fieldYearL, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
								.addComponent(fieldYearI, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitulo)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRangoInicial)
						.addComponent(label)
						.addComponent(fieldDiaI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(fieldMesI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(fieldYearI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRangoLimite)
						.addComponent(lblDia)
						.addComponent(fieldDiaL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMes)
						.addComponent(fieldMesL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAo)
						.addComponent(fieldYearL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addComponent(btnBuscar))
		);

		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	public void updateTable()
	{
		ArrayList<Equipo> equipos = equiposprestamodao.getEquiposPrestamo();
        
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
	
	public void updateTable(String inicio, String limite)
	{
		ArrayList<Equipo> equipos = equiposprestamodao.getEquiposPrestamo(inicio, limite);
        
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
