package frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.UsuarioDAO;
import dto.Usuario;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetallesUsuarioFrame extends JFrame 
{
	private static final long serialVersionUID = -3173827722886044770L;
	private JPanel contentPane;
	private JTextField fieldNombre;
	private JTextField fieldApPat;
	private JTextField fieldApMat;
	private JTextField fieldRun;
	private JTextField fieldUsername;
	private JTextField fieldPassword;
	private JComboBox fieldPerfil;
	
	public DetallesUsuarioFrame(String accion, Usuario user, ListaUsuarios parentFrame) 
	{
		UsuarioDAO usuariodao = new UsuarioDAO();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 342, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAccion = new JLabel("Accion: " + accion + " usuario");
		lblAccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNombre = new JLabel("Nombre");
		JLabel lblAppat = new JLabel("Apellido paterno");
		JLabel lblApmat = new JLabel("Apellido materno");
		JLabel lblRun = new JLabel("Run");
		JLabel lblUsername = new JLabel("Username");
		JLabel lblPassword = new JLabel("Password");
		JLabel lblPerfil = new JLabel("Perfil");
		
		JLabel lblResponse = new JLabel("");
		lblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		fieldNombre = new JTextField();
		fieldNombre.setColumns(10);
		
		fieldApPat = new JTextField();
		fieldApPat.setColumns(10);
		
		fieldApMat = new JTextField();
		fieldApMat.setColumns(10);
		
		fieldRun = new JTextField();
		fieldRun.setColumns(10);
		
		fieldUsername = new JTextField();
		fieldUsername.setColumns(10);
		
		fieldPassword = new JTextField();
		fieldPassword.setColumns(10);
		
		String[] combo = {"Usuario comun", "Administrador"};
		fieldPerfil = new JComboBox(combo);
		fieldPerfil.setSelectedIndex(0);
		
		if (accion.equals("editar"))
		{
			fieldNombre.setText(user.getNombre());
			fieldApPat.setText(user.getApPat());
			fieldApMat.setText(user.getApMat());
			fieldRun.setText(user.getRun());
			fieldPerfil.setSelectedIndex(user.getPerfil()-1);
			fieldUsername.setText(user.getUsername());
			fieldUsername.setEnabled(false);
		}
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Usuario user = new Usuario();
				
				user.setNombre(fieldNombre.getText());
				user.setApMat(fieldApMat.getText());
				user.setApPat(fieldApPat.getText());
				user.setRun(fieldRun.getText());
				user.setPerfil(fieldPerfil.getSelectedIndex() + 1);
				user.setUsername(fieldUsername.getText());
				user.setPassword(fieldPassword.getText());
				
				if (accion.equals("crear"))
				{
					lblResponse.setText(usuariodao.crearUsuario(user));
					clearFields();
				}
				else if (accion.equals("editar"))
					lblResponse.setText(usuariodao.editarUsuario(user));
				
				parentFrame.updateTable();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAccion, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblResponse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNombre)
										.addComponent(lblAppat)
										.addComponent(lblApmat)
										.addComponent(lblRun)
										.addComponent(lblPerfil)
										.addComponent(lblPassword)
										.addComponent(lblUsername))
									.addGap(21)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(fieldPerfil, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(fieldNombre)
										.addComponent(fieldApPat)
										.addComponent(fieldApMat)
										.addComponent(fieldRun, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
										.addComponent(fieldUsername)
										.addComponent(fieldPassword)
										.addComponent(btnGuardar))))
							.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblAccion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(fieldNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAppat)
						.addComponent(fieldApPat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApmat)
						.addComponent(fieldApMat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRun)
						.addComponent(fieldRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPerfil)
						.addComponent(fieldPerfil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnGuardar)
					.addGap(18)
					.addComponent(lblResponse)
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
		fieldPerfil.setSelectedIndex(0);
		fieldUsername.setText("");
		fieldPassword.setText("");
	}
}
