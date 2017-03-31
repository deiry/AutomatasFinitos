/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.AFD;
import Modelo.Automata;
import Modelo.Estado;
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
        
        Automata afd = new AFD();
        Vector<Estado> estados = new Vector<>();
        estados.set(0, new Estado("UP",0));
        estados.set(1, new Estado("UI",0));
        afd.setEstados(estados);
        
        Vector<Simbolo> simbolos = new Vector<>();
        simbolos.set(0, new Simbolo("0", 0));
        simbolos.set(1, new Simbolo("1", 1));
        afd.setAlfabeto(simbolos);
        
        
        

       
    }
    
}
