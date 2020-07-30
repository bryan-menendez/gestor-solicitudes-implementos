/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
package cl.inacap.inmobiliaria.dao;

import cl.inacap.inmobiliaria.dto.Cuenta;
import cl.inacap.inmobiliaria.dto.Empleado;
import cl.inacap.inmobiliaria.frames.Login;
import cl.inacap.inmobiliaria.tools.Connector;
import cl.inacap.inmobiliaria.tools.Hasher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CuentaDAO extends Connector
{
    
    public CuentaDAO()
    {
        //blank 
    }
    
    
    public ArrayList<Cuenta> getCuentas()
    {
        ArrayList<Cuenta> list = new ArrayList<>();
        
        try
        {
            this.connect();
            String sql="SELECT * FROM cuentas WHERE estado = " + true;
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
                list.add(new Cuenta(rs.getInt("idCuenta"), rs.getString("username"), rs.getString("password"), rs.getString("fechaConexion"), 
                    rs.getInt("idEmpleado"), rs.getBoolean("estado")));
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::getCuentas(): " + ex.toString());
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
            }
        }
        
        return list;
    }
    

    
    public int addCuenta(String username, String password, int idEmpleado)
    {
        //detectar si el nombre de la cuenta esta en uso
        try
        {
            this.connect();
            String sql="SELECT * FROM cuentas WHERE username = \"" + username + "\"";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs= st.executeQuery();
            
            //si existe alguna cuenta con ese username
            //mensaje error 2, username ya existe
            if (rs.next())
            {
                System.out.println("Ya existe una cuenta con ese nombre");
                return 2;
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::addCuenta(Cuenta cuenta) (al detectar nombre de cuenta): " + ex.toString());
        }
        
        //creacion de cuenta
        try
        {
            this.connect();
            //get salt based on username
            String salt = Hasher.encrypt_SHA_512(username);
            //use that salt to encrypt the password
            String hashed = Hasher.encrypt_SHA_512(password, salt);
            String sql="INSERT INTO cuentas"
                    + "(username, password, salt, idEmpleado)" 
                    + " VALUES (\"" 
                    + username + "\", \"" + hashed + "\", \"" + salt + "\", " + idEmpleado
                    +")";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.execute();
            
            System.out.println("CUENTA CREADA");
            //todo en orden
            return 1;
        }

        catch (Exception ex)
        {
           System.out.println("Error CuentaDAO::addCuenta(Cuenta cuenta):(al intentar crear cuenta) " + ex.toString());
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
            }
        }
        
        //error, no se pudo crear cuenta, salida por defecto en false
        return 0;
    }
    
    
    public int getCuentaAutoincrement()
    {
        int res = -1;
        
        try
        {
            this.connect();
            String sql="SELECT `AUTO_INCREMENT`\n" +
                        "FROM  INFORMATION_SCHEMA.TABLES\n" +
                        "WHERE TABLE_SCHEMA = 'inmobiliaria'\n" +
                        "AND   TABLE_NAME   = 'cuentas'";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            rs.next();
            res = rs.getInt(1);
        }
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::getCuentaAutoincrement(): " + ex.toString());
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error CuentaDAO::getCuentaAutoincrement():disconnect " + ex.toString());
            }
        }
        
        return res;
    }
    
    public String[] getListaTipoEmpleado()
    {
        String[] list = new String[getCuentaAutoincrement()];
        
        try
        {
            this.connect();
            String sql="SELECT descripcion FROM tipoEmpleado";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            for(int i = 0; rs.next(); i++)
                list[i] = rs.getString("descripcion");
                
        }
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::getListaTipoEmpleado(): " + ex.toString());
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error CuentaDAO::getListaTipoEmpleado():disconnect " + ex.toString());
            }
        }
        
        return list;
    }
    
    public Cuenta getCuenta(int id)
    {
        Cuenta result;
        
        try
        {
            this.connect();
            String sql="SELECT * FROM cuentas WHERE idCuenta = " + id;
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            rs.next();
            result = new Cuenta(rs.getInt("idCuenta"), rs.getString("username"), rs.getString("password"), rs.getString("fechaConexion"), 
                    rs.getInt("idEmpleado"), rs.getBoolean("estado"));
          
        }
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::getCuenta(): " + ex.toString());
            System.out.println("CREANDO CUENTA EN BLANCO..." + ex.toString());
            result = new Cuenta();
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
            }
        }
        
        return result;
    }
    
    public void updateCuenta(int idCuenta, String username, String password, int idEmpleado)
    {
        try
        {
            this.connect();
            
            String salt = Hasher.encrypt_SHA_512(username);
            String hashed = Hasher.encrypt_SHA_512(password, salt);
            
            
            String sql="UPDATE cuentas SET "
                    + "username = \"" + username + "\", password = \"" + hashed + "\", salt = \"" + salt
                    + "\", idEmpleado = " + idEmpleado 
                    + " WHERE idCuenta = " + idCuenta;
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.execute();
        }
        
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::updateCuenta(Cuenta acc): " + ex.toString());
        }
        
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception e)
            {
                System.out.println("Error CuentaDAO::updateCuenta(Cuenta acc):try:disconnect " + e.toString());
            }
        }
    }
    
    public void updateFechaCuenta(int id)
    {
        try
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
            LocalDateTime now = LocalDateTime.now();  
            String date = dtf.format(now);  
   
            String sql="UPDATE cuentas SET fechaConexion = '" + date
                    + "' WHERE idCuenta = " + id;
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.execute();
        }
        
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::updateFechaCuenta(Cuenta acc): " + ex.toString());
        }
    }
    
    public void deleteCuenta(int id)
    {
        try
        {
            this.connect();

            String sql="UPDATE cuentas SET "
                    + "estado = " + false + " "
                    + "WHERE idCuenta = " + id;
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.execute();
        }
        
        catch (Exception ex)
        {
            System.out.println("Error CuentaDAO::deleteCuenta(int id): " + ex.toString());
        }
        
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception e)
            {
                System.out.println("Error CuentaDAO::deleteCuenta(int id):try:disconnect " + e.toString());
            }
        }
    }
}
*/