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
        AFDeterministico afd = new AFDeterministico();
        String estados[] = {"CPUP", "CPUI", "CIUP", "CIUI"};

        for (int i = 0; i < estados.length; i++) {
            afd.agregarEstado(estados[i], i);
        }

        afd.agregarEstadoInicial("CPUP");
        afd.agregarEstadoAceptacion("CPUI");
        afd.agregarEstadoAceptacion("CIUP");

        String simbolos[] = {"0", "1"};
        for (int i = 0; i < simbolos.length; i++) {
            afd.agregarSimbolos(simbolos[i], i);
        }

        afd.inicializar();

        String tran[][] = {
            {"CPUI", "CPUP"},
            {"CIUI", "CPUI"},
            {"CPUI", "CPUP"},
            {"CPUI", "CIUI"}
        };

        for (int i = 0; i < afd.sizeEstados(); i++) {
            for (int j = 0; j < afd.sizeSimbolos(); j++) {
                afd.agregarTransicion(estados[i], simbolos[j], tran[i][j]);
            }
        }

        Vector<String> hilera = new Vector<>();
        hilera.add("1");
        hilera.add("1");
        hilera.add("1");
        hilera.add("1");
        hilera.add("0");
        hilera.add("0");
        hilera.add("0");
        hilera.add("0");
        hilera.add("0");

        System.out.println("Valido? " + afd.reconocer(hilera));
        // afd.analizarEstadosInalcanzables();
        System.out.println("Valido? " + afd.reconocer(hilera));
        // afd.creacionParticiones();
        afd.simplificar();


    }

}
