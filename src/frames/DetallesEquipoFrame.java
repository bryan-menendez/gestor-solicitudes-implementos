package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.EquipoDAO;
import dto.Equipo;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;

public class DetallesEquipoFrame extends JFrame {

	private JPanel contentPane;
	private JTextField fieldCodigo;
	private JTextField fieldCantidad;
	private JEditorPane fieldDescripcion;
	
	public DetallesEquipoFrame(String accion, Equipo equipo, ListaEquipos parentFrame) 
	{
		EquipoDAO equipodao = new EquipoDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 374, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblCodigo = new JLabel("Codigo");		
		JLabel lblCantidad = new JLabel("Cantidad");
		fieldDescripcion = new JEditorPane();

		fieldCodigo = new JTextField();
		fieldCodigo.setColumns(10);
		
		fieldCantidad = new JTextField();
		fieldCantidad.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblAccion = new JLabel("Accion: " + accion + " equipo");
		lblAccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		
		JLabel lblResponse = new JLabel("");
		lblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		if (accion.equals("editar"))
		{
			fieldCodigo.setText(equipo.getCodigo());
			fieldCodigo.setEnabled(false);
			fieldCantidad.setText("" + equipo.getCantidad());
			fieldDescripcion.setText(equipo.getDescripcion());
		}
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{	
				lblResponse.setText("");
				
				Equipo e = new Equipo();
			
				e.setCantidad(Integer.parseInt(fieldCantidad.getText()));
				e.setCodigo(fieldCodigo.getText());
				e.setDescripcion(fieldDescripcion.getText());
				
				if (accion.equals("crear"))
				{
					lblResponse.setText(equipodao.crearEquipo(e));
					clearFields();
				}
				
				else if (accion.equals("editar"))
					lblResponse.setText(equipodao.editarEquipo(e));
				
				parentFrame.updateTable();
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblResponse, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(136)
					.addComponent(btnGuardar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(133))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAccion, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(fieldDescripcion, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
						.addComponent(lblDescripcion)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCodigo)
								.addComponent(lblCantidad))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(fieldCantidad, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
								.addComponent(fieldCodigo, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAccion)
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCodigo)
						.addComponent(fieldCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCantidad)
						.addComponent(fieldCantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescripcion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(fieldDescripcion, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblResponse)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		setVisible(true);
	}
	
	private void clearFields()
	{
		fieldCodigo.setText("");
		fieldCantidad.setText("");
		fieldDescripcion.setText("");
	}
}
