/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Estado;
import controlador.Controlador;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author alejandro
 */
public class AutomatasFinitos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Controlador controlador = Controlador.getInstance();

        AFDeterministico afd2 = new AFDeterministico();
        String estados2[] = {"A", "B", "C", "D", "E", "F", "G"};

        for (int i = 0; i < estados2.length; i++) {
            afd2.agregarEstado(estados2[i], i);
        }

        afd2.agregarEstadoInicial("A");
        afd2.agregarEstadoAceptacion("E");
        //afd.agregarEstadoAceptacion("CIUP");

        String simbolos2[] = {"1", "0"};
        for (int i = 0; i < simbolos2.length; i++) {
            afd2.agregarSimbolos(simbolos2[i], i);
        }

        afd2.inicializar();

        String tran2[][] = {
            {"B", "G"},
            {"B", "C"},
            {"A", "E"},
            {"D", "G"},
            {"E", "E"},
            {"G", "F"},
            {"G", "G"}
        };
        Vector<String> hilera = new Vector<>();
        hilera.add("1");
        hilera.add("0");
        hilera.add("0");

        for (int i = 0; i < afd2.sizeEstados(); i++) {
            for (int j = 0; j < afd2.sizeSimbolos(); j++) {
                afd2.agregarTransicion(estados2[i], simbolos2[j], tran2[i][j]);
            }
        }
        System.out.println("Reconcer " + afd2.reconocer(hilera));
        
        afd2.simplificar();
        System.out.println("Reconcer " + afd2.reconocer(hilera));

    }

}
