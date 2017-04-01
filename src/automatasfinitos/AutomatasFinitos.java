/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatasfinitos;

import controlador.Controlador;

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
        Controlador ctrl = new Controlador();
        String hilera = "{[Q0, Q1,Q2,Q3 ][a1,a2] [(Q0,a1,Q1) (Q1, ai,Q3)][Q0][Q3]}";
        ctrl.construirAtomata(hilera);
    }
    
}
