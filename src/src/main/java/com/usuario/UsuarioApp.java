package com.usuario;

import java.util.*;
import java.io.*;

import com.usuario.models.User;
import com.usuario.Resultado;

import java.lang.String;


public class UsuarioApp extends User
{

    private String id;
    private String password;
    public String name;
    public String email;
    public int won;
    public int lost;
    public int abandoned;


    public UsuarioApp(String id) {
        //super();

        // Si no coloco esto salta error en la clase Partida!!!
        UsuarioApp usuarioapp = new UsuarioApp(id);
    }


    public String getName() {
        //Retorno el nombre del Usuario

        return name;
    }
    
    public int getGan() {
        //Retorno las partidas Ganadas

        return won;
    }    
    
    public int getPer() {
        //Retorno las partidas Perdidas

        return lost;
    }  
    
    public int getAba() {
        //Retorno las partidas Abandonadas

        return abandoned;
    }            


    private boolean saveResult(Resultado resultado) {
        // Guardo el resultado de una partida

        UsuarioApp u = new UsuarioApp(id);

        if (resultado == Resultado.ganadas) {

            int g = u.getGan();

            g++;

            u.set("ganadas", g);

            u.saveIt();

            return true;

        } else if (resultado == Resultado.perdidas) {

                    int p = u.getPer();

                    p++;

                    u.set("perdidas", p);

                    u.saveIt();

                    return true;

        } else if (resultado == Resultado.abandonadas) {

                    int a = u.getAba();

                    a++;

                    u.set("abandonadas", a);

                    u.saveIt();

                    return true;
        } else {

            System.out.println( "\n" + "el resultado ingresado no es correcto ");

            return false;
        }

    }

}
