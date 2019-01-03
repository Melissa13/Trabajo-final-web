package com.example.trabajo_final.Servicios.Complementarias;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Objects;

@Service
public class ServicioEncryt {

    public String encryptPassword(String passwordToHash)
    {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch(Exception e)
        {
            System.out.println("ERROR ON Encriptation Service");
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean comparePasswords(String original,String currentPassword)
    {
        return (Objects.equals(currentPassword, encryptPassword(original)));
    }
}
