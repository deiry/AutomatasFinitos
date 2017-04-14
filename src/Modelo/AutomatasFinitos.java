/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Estado;
import controlador.Controlador;
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
        HashMap<String, Integer> aux = new HashMap<String, Integer>();
        aux.put("CPUP", 0);
        aux.put("CPUI", 1);        
        aux.put("CIUP", 2);        
        aux.put("CIUI", 3);        
        afd.setEstados(aux);  
        aux = new HashMap<>();
        aux.put("0", 0);
        aux.put("1", 1);        
        afd.setSimbolos(aux);
        
        afd.setEstadoInicial("CPUP");
        afd.setEstadoAceptacion("CPUI");
        
        String [][] transiciones = new String[afd.sizeEstados()][afd.sizeSimbolos()];
        
        int posEstado = afd.posEstado("CPUP");
        int posSimbolo = afd.posSimbolo("0");        
        transiciones[posEstado][posSimbolo] = "CPUI";
        
        posSimbolo = afd.posSimbolo("1");        
        transiciones[posEstado][posSimbolo] = "CPUP";
        
        posEstado = afd.posEstado("CPUI");
        posSimbolo = afd.posSimbolo("0");
        transiciones[posEstado][posSimbolo] = "CIUI";
        
        posSimbolo = afd.posSimbolo("1");
        transiciones[posEstado][posSimbolo] = "CPUI";
        
        posEstado = afd.posEstado("CIUP");
        posSimbolo = afd.posSimbolo("0");
        transiciones[posEstado][posSimbolo] = "CPUI";
        
        posSimbolo = afd.posSimbolo("1");
        transiciones[posEstado][posSimbolo] = "CPUP";
        
        posEstado = afd.posEstado("CIUI");
        posSimbolo = afd.posSimbolo("0");
        transiciones[posEstado][posSimbolo] = "CPUI";
        
        posSimbolo = afd.posSimbolo("1");
        transiciones[posEstado][posSimbolo] = "CIUI";
        
        afd.setTransiciones(transiciones);
        Vector<String> hilera = new Vector<>();
        hilera.add("0");
        hilera.add("0");
        hilera.add("0");
        hilera.add("1");
        hilera.add("1");
        hilera.add("1");
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
