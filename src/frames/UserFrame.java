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

public class UserFrame extends JFrame 
{
	private static final long serialVersionUID = -760429559455328621L;
	private JPanel contentPane;
	
	public UserFrame() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 167);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPerfilDeUsuario = new JLabel("Perfil de Usuario");
		lblPerfilDeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		
		JButton btnTrabajadores = new JButton("Trabajadores");
		btnTrabajadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaTrabajadores("user");
			}
		});
		
		JButton btnEquipos = new JButton("Equipos");
		btnEquipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaEquipos("user");
			}
		});
		
		JButton btnPrestamos = new JButton("Prestamos");
		btnPrestamos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListaEquipos("user");
			}
		});
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(btnCerrarSesion, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
						.addComponent(lblPerfilDeUsuario, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnTrabajadores, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnEquipos, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnPrestamos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(70))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblPerfilDeUsuario)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTrabajadores)
						.addComponent(btnEquipos)
						.addComponent(btnPrestamos))
					.addGap(18)
					.addComponent(btnCerrarSesion)
					.addGap(23))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
