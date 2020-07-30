package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.EquipoDAO;
import dao.TrabajadorDAO;
import dto.Trabajador;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetallesTrabajadorFrame extends JFrame {

	private JPanel contentPane;
	private JTextField fieldNombre;
	private JTextField fieldApPat;
	private JTextField fieldApMat;
	private JTextField fieldRun;
	private JTextField fieldEmpresa;
	private JTextField fieldCorreo;
	private JTextField fieldTelefono;

	public DetallesTrabajadorFrame(String accion, Trabajador trabajador, ListaTrabajadores parentFrame) 
	{
		setResizable(false);
		TrabajadorDAO trabajadordao = new TrabajadorDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAccion = new JLabel("Accion: " + accion + " trabajador");
		lblAccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAccion.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNombre = new JLabel("Nombre");
		JLabel lblApellidoPaterno = new JLabel("Apellido paterno");
		JLabel lblApellidomaterno = new JLabel("Apellido materno");
		JLabel lblRun = new JLabel("Run");
		JLabel lblEmpresa = new JLabel("Empresa");
		JLabel lblCorreo = new JLabel("Correo");
		JLabel lblTelefono = new JLabel("Telefono");
		
		fieldNombre = new JTextField();
		fieldNombre.setColumns(10);
		
		fieldApPat = new JTextField();
		fieldApPat.setColumns(10);
		
		fieldApMat = new JTextField();
		fieldApMat.setColumns(10);
		
		fieldRun = new JTextField();
		fieldRun.setColumns(10);
		
		fieldEmpresa = new JTextField();
		fieldEmpresa.setColumns(10);
		
		fieldCorreo = new JTextField();
		fieldCorreo.setColumns(10);
		
		fieldTelefono = new JTextField();
		fieldTelefono.setColumns(10);
		
		JLabel lblResponse = new JLabel("");
		lblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		if (accion.equals("editar"))
		{
			fieldNombre.setText(trabajador.getNombre());
			fieldApPat.setText(trabajador.getApPat());
			fieldApMat.setText(trabajador.getApMat());
			fieldRun.setText(trabajador.getRun());
			fieldRun.setEnabled(false);
			fieldEmpresa.setText(trabajador.getEmpresa());
			fieldCorreo.setText(trabajador.getCorreo());
			fieldTelefono.setText(trabajador.getTelefono());
		}
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Trabajador t = new Trabajador();
				
				t.setNombre(fieldNombre.getText());
				t.setApPat(fieldApPat.getText());
				t.setApMat(fieldApMat.getText());
				t.setRun(fieldRun.getText());
				t.setEmpresa(fieldEmpresa.getText());
				t.setCorreo(fieldCorreo.getText());
				t.setTelefono(fieldTelefono.getText());
				
				if (accion.equals("crear"))
				{
					lblResponse.setText(trabajadordao.crearTrabajador(t));
					clearFields();
				}
				else if (accion.equals("editar"))
					lblResponse.setText(trabajadordao.editarTrabajador(t));
				
				parentFrame.updateTable();
			}
		});
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAccion, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNombre)
								.addComponent(lblApellidoPaterno)
								.addComponent(lblApellidomaterno)
								.addComponent(lblRun)
								.addComponent(lblEmpresa)
								.addComponent(lblCorreo)
								.addComponent(lblTelefono))
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(fieldNombre, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(fieldApPat, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(fieldApMat, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(fieldRun, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(fieldEmpresa, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(fieldCorreo, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(fieldTelefono, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(0))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(128)
					.addComponent(btnGuardar)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblResponse, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAccion)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(fieldNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApellidoPaterno)
						.addComponent(fieldApPat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApellidomaterno)
						.addComponent(fieldApMat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRun)
						.addComponent(fieldRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmpresa)
						.addComponent(fieldEmpresa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCorreo)
						.addComponent(fieldCorreo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTelefono)
						.addComponent(fieldTelefono, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnGuardar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblResponse, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	private void clearFields()
	{
		fieldNombre.setText("");
		fieldApPat.setText("");
		fieldApMat.setText("");
		fieldRun.setText("");
		fieldEmpresa.setText("");
		fieldCorreo.setText("");
		fieldTelefono.setText("");
	}
}
