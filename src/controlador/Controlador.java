/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author alejandro
 */
public class Controlador {

    public Controlador() {
        
    }
    
    public void construirAtomata(String stringAutomata)
    {
        ArrayList<String> automata = new ArrayList<>();
        //elimimando los espacios
        stringAutomata = stringAutomata.replaceAll("\\s","");
        int size = stringAutomata.length();
        if (stringAutomata.charAt(0) == '{' && stringAutomata.charAt(size-1) == '}')
        {
            int i = 0;
            while (i < size) {                
                if(stringAutomata.charAt(i) == '[')
                {
                    String s = "";
                    while (stringAutomata.charAt(i) != ']') {
                        s = s.concat(String.valueOf(stringAutomata.charAt(i)));
                        i++;
                    }
                    automata.add(s);
                }
                i++;
            }
        }
        else
        {
            error();
        }
    }
    
    private void error()
    {
        System.out.println("ERROR");
    }
    
}
