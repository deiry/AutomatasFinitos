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
        Estado A = new Estado();
        A.setData("A");
        A.setEstadoAcep(false);
        A.setEstadoInicial(true);
        A.setId(posEstado + 1);
        A.setPosEstado(posEstado);
        afnd.agregarEstado(posEstado, A);

        posEstado = 1;
        Estado B = new Estado();
        B.setData("B");
        B.setEstadoAcep(false);
        B.setEstadoInicial(false);
        B.setId(posEstado + 1);
        B.setPosEstado(posEstado);
        afnd.agregarEstado(posEstado, B);

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

        A.addTransicion(A, 0);
        A.addTransicion(B, 0);

        B.addTransicion(B, 1);
        B.addTransicion(C, 1);

        C.addTransicion(C, 0);

        AFD afd = afnd.convertir();
        afd.imprimirTransiciones(afd.getEstados());

        
        Estado D = new Estado();
        D.setData("D");
        D.setEstadoAcep(true);
        D.setEstadoInicial(false);          
        afd.agregarEstado(D);
        afd.imprimirEstados(afd.getEstados());
        afd.simplificar();

    }
}
