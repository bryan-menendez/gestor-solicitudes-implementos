package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class AdminFrame extends JFrame 
{
	private static final long serialVersionUID = 544944121280150381L;
	private JPanel contentPane;
	
	
	public AdminFrame() 
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 156);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPerfilDeAdministracion = new JLabel("Perfil de Administracion");
		lblPerfilDeAdministracion.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnCerrarSesion = new JButton("Cerrar sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() 
				{
					public void run() 
					{
						try 
						{
							new LoginFrame();
							dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		
		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaUsuarios("admin");
			}
		});
		
		JButton btnTrabajadores = new JButton("Trabajadores");
		btnTrabajadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaTrabajadores("admin");
			}
		});
		
		JButton btnEquipos = new JButton("Equipos");
		btnEquipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaEquipos("admin");
			}
		});
		
		JButton btnPrestamos = new JButton("Prestamos");
		btnPrestamos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaPrestamos("admin");
			}
		});
		
		JButton btnEquiposSolicitados = new JButton("Equipos solicitados");
		btnEquiposSolicitados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ListaEquiposSolicitados();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblPerfilDeAdministracion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnCerrarSesion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnUsuarios)
									.addGap(18)
									.addComponent(btnTrabajadores)
									.addGap(18)
									.addComponent(btnEquipos)
									.addGap(18)
									.addComponent(btnPrestamos)
									.addGap(18)
									.addComponent(btnEquiposSolicitados)))))
					.addContainerGap(93, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblPerfilDeAdministracion)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUsuarios)
						.addComponent(btnTrabajadores)
						.addComponent(btnEquipos)
						.addComponent(btnPrestamos)
						.addComponent(btnEquiposSolicitados))
					.addGap(18)
					.addComponent(btnCerrarSesion)
					.addGap(12))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
