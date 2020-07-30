package frames;

import tools.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class LoginFrame 
{
	private Login logon;
	
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	
	public LoginFrame() {
		initialize();
	}


	private void initialize() 
	{
		logon = new Login();
		frame = new JFrame();
		
		frame.setBounds(100, 100, 366, 317);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblTitulo = new JLabel("SISTEMA DE GESTION DE SOLICITUDES\n");
		lblTitulo.setFont(new Font("Monospaced", Font.PLAIN, 16));
		
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameField.setToolTipText("...nombre de usuario");
		usernameField.setColumns(10);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Monospaced", Font.PLAIN, 16));
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Monospaced", Font.PLAIN, 16));
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setToolTipText("...contrase\u00F1a");
		
		JLabel lblResponse = new JLabel("");
		lblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				lblResponse.setText(frameLogin());
			}
		});
		
		KeyAdapter loginKeyPress = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					btnLogin.doClick();
				}
			}
		};
		
		usernameField.addKeyListener(loginKeyPress);
		passwordField.addKeyListener(loginKeyPress);
		
		JLabel lblEmpresaFastDevelopment = new JLabel("Empresa Fast Development 2019");
		lblEmpresaFastDevelopment.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblEmpresaFastDevelopment.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTitulo)
						.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addComponent(lblUsername, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
							.addComponent(lblEmpresaFastDevelopment, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)))
					.addComponent(lblResponse, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(126))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(131)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(241, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(70)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(186, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(68)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(188, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitulo)
					.addGap(34)
					.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblResponse, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEmpresaFastDevelopment)))
					.addGap(20))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
	
	private String frameLogin()
	{
		@SuppressWarnings("deprecation")
		String response = logon.testCredentials(usernameField.getText(), passwordField.getText()); 
		
		if (response.equals("USUARIO"))
		{
			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					try 
					{
						UserFrame window = new UserFrame();
						window.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			frame.setVisible(false);
		}
		else if (response.equals("ADMIN"))
		{
			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					try 
					{
						AdminFrame window = new AdminFrame();
						window.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			frame.setVisible(false);
		}
		
		return response;
	}
}
