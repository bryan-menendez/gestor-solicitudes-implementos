package main;

import java.awt.EventQueue;

import frames.*;
import tools.*;
import dao.*;
import dto.*;

@SuppressWarnings("unused")
public class Main
{
	public static void main(String[] args)
	{
		testing();
		
		
		//TODO
		//NOMBRAR A LA APLICACION COMO PERTENECIENTE A LA COMPAÑIA FAST DEVELOPMENT LMAO
		
		try 
		{
			EventQueue.invokeLater(new Runnable() 
			{
				public void run() 
				{
					try 
					{	
						new LoginFrame();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} 
		catch (Exception e) 
		{
			System.out.println("Error abriendo ventana de login: " + e.toString());
		}
	}
	
	private static void testing()
	{
		
/*
 * 
 * OBJETOS
 * 
 */
		
//		Equipo equipo = new Equipo();
//		equipo.setCodigo("167865462");
//		equipo.setCantidad(230);
//		equipo.setDescripcion("3ksdo");
//		
//		System.out.println(new EquipoDAO().getEquipos());
		
//		Usuario user = new Usuario();
//		user.setUsername("tester");
//		user.setPassword("asd");
//		user.setPerfil(2);
//		user.setApMat("ola");
//		
//		System.out.println(new UsuarioDAO().getUsuarios());
//		
//		Trabajador trabajador = new Trabajador();
//		trabajador.setRun("544341235");
//		trabajador.setCorreo("olaola@gmail.com");
//		
//		System.out.println(new TrabajadorDAO().borrarTrabajador("544341235"));
//		
		
/*
 * 
 * FRAMES
 * 
 * 
 */
		
//		Equipo e = new Equipo();
//		
//		e.setCantidad(1);
//		e.setCodigo("3q");
//		e.setDescripcion("testing device and professional scapegoat");
//		

//		new DetallesEquipoFrame("editar", e);
		
		
//		Trabajador t = new Trabajador();
//		t.setRun("101003");
//
//		new DetallesTrabajadorFrame("editar", t);
		
//		Usuario user = new Usuario();
//		
//		user.setUsername("dw2");
//		
//		new DetallesUsuarioFrame("editar", user);
//		
		
		
	}
	
}