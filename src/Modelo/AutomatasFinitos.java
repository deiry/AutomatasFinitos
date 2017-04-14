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
        
        AFDeterministico afd = new AFDeterministico();
        String estados[]={"CPUP","CPUI","CIUP","CIUI"};
        
        for (int i = 0; i < estados.length; i++) {
            afd.agregarEstado(estados[i], i);
        }
                
        afd.agregarEstadoInicial("CPUP");
        afd.agregarEstadoAceptacion("CPUI");
       
        String simbolos[]={"0","1"};
        for (int i = 0; i < simbolos.length; i++) {
            afd.agregarSimbolos(simbolos[i], i);
        }
        
        afd.inicializar();
        
        String tran[][]={
            {"CPUI","CPUP"},
            {"CIUI","CPUI"},
            {"CPUI","CPUP"},
            {"CPUI","CIUI"}
        };
        
        for (int i = 0; i < afd.sizeEstados(); i++) {
            for (int j = 0; j < afd.sizeSimbolos(); j++) {
                System.out.println("e: "+estados[i]+" s: "+simbolos[j]+" t: "+ tran[i][j]);
                afd.agregarTransicion(estados[i], simbolos[j],tran[i][j] );
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
       
        System.out.println("Valido? "+afd.reconocer(hilera));
        afd.estadosInalcanzables();
        
          Controlador ctrl = Controlador.getInstance();
        String hola = "{[Q0, Q1,Q2,Q3 ][a1,a2] [(Q0,a1,Q1) (Q1, a2,Q3)][Q0][Q3]}";
        ctrl.construirAtomata(hola);
        
        

    }

}
