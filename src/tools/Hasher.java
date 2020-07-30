/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author Ita
 */
public abstract class Hasher
{
    /**
     * Hashea un string en SHA_512 con un salt predefinido
     * @param passwordToHash
     * @return 
     */
    public static String encrypt_SHA_512(String passwordToHash)
    {
        String generatedPassword = null;
        String salt = "a49670c3c18b9ea49670c3c18b9e";       
        try 
        {
             MessageDigest md = MessageDigest.getInstance("SHA-512");
             md.update(salt.getBytes(StandardCharsets.UTF_8));
             byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
             StringBuilder sb = new StringBuilder();
             
             for(int i=0; i< bytes.length ;i++)
             {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
             }
             generatedPassword = sb.toString();
        } 
        catch (Exception ex)
        {
            System.out.println("Error al generar hash");ex.printStackTrace();
        }
        
        return generatedPassword;
    }
    /**
     * Encripta una cadena de texto en SHA-512
     * @param passwordToHash contraseña
     * @param salt salt, para aleatorizar la encripcion
     * @return hash
     */
    public static String encrypt_SHA_512(String passwordToHash, String salt)
    {
        String generatedPassword = null;
                
        try 
        {
             MessageDigest md = MessageDigest.getInstance("SHA-512");
             md.update(salt.getBytes(StandardCharsets.UTF_8));
             byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
             StringBuilder sb = new StringBuilder();
             
             for(int i=0; i< bytes.length ;i++)
             {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
             }
             generatedPassword = sb.toString();
        } 
        catch (Exception ex)
        {
            System.out.println("Error al generar hash");
            ex.printStackTrace();
        }
        
        return generatedPassword;
    }
}
