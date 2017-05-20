/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.util.Iterator;

/**
 *
 * @author DEIRY
 */
public class Main {

    public static void main(String[] args) {
        AFND afnd = new AFND();
        //Construir estados
        int posEstado = 0;
        Estado CP = new Estado();
        CP.setData("CP");
        CP.setEstadoAcep(true);
        CP.setEstadoInicial(true);
        CP.setId(posEstado + 1);
        CP.setPosEstado(posEstado);
        afnd.agregarEstado(posEstado, CP);

        posEstado = 1;
        Estado CI = new Estado();
        CI.setData("B");
        CI.setEstadoAcep(false);
        CI.setEstadoInicial(true);
        CI.setId(posEstado + 1);
        CI.setPosEstado(posEstado);
        afnd.agregarEstado(posEstado, CI);

        posEstado = 2;
        Estado C = new Estado();
        C.setData("C");
        C.setEstadoAcep(true);
        C.setEstadoInicial(false);
        C.setId(posEstado + 1);
        C.setPosEstado(posEstado);
        afnd.agregarEstado(posEstado, C);

        //Agregar simbolos
        afnd.agregarSimbolo(0, "0");
        afnd.agregarSimbolo(1, "1");

        CP.addTransicion(CP, 0);

        CI.addTransicion(CI, 1);

        C.addTransicion(C, 0);

        AFD afd = afnd.convertir(false);
        
        afd.imprimirTransiciones(afd.getEstados());

        
        Estado D = new Estado();
        D.setData("D");
        D.setEstadoAcep(true);
        D.setEstadoInicial(false);          
        afd.agregarEstado(D);
        
        AFD simplicado = afd.simplificar();
        simplicado.imprimirTransiciones(simplicado.getEstados());

    }
}
