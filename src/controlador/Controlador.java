/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.AFDeterministico;
import Modelo.AFNoDeterministico;
import Modelo.AutomataFinito;
import java.util.ArrayList;
import java.util.HashMap;
import vista.VistaPrincipal;

/**
 *
 * @author alejandro
 */
public class Controlador {
    
    private AFDeterministico afd;
    private AFNoDeterministico afnd;
    private int contadorEstados;
    private int contadorSimbolos;
    private AutomataFinito af;
    private static Controlador instance = null;

    protected Controlador() {
        VistaPrincipal vp = new VistaPrincipal();
        vp.setVisible(true);
        this.contadorEstados = 0;
        this.contadorSimbolos = 0;
        af = new AFNoDeterministico();
        
        af.agregarEstado("q1", 0);
        af.agregarEstado("q2", 1);
        af.agregarEstado("q3", 2);
        af.agregarSimbolos("0", 0);
        af.agregarSimbolos("1", 1);
    }
    
    public static Controlador getInstance()
    {
        if (instance == null) {
            instance = new Controlador();
        }
        return instance;
    }
    
    public void construirAtomata(String stringAutomata)
    {
        ArrayList<String> componentesAF = obtenerCompontesAtomata(stringAutomata);
        
        if (!componentesAF.isEmpty()) 
        {
            HashMap estados = obtenerEstados(componentesAF.get(0));
            HashMap lenguaje =obtenerLenguaje(componentesAF.get(1));
            HashMap estadosIniciales =obtenerEstadosIniciales(componentesAF.get(3));
            HashMap estadosAceptacion = obtenerEstadosAceptacion(componentesAF.get(4));
            ArrayList[][] transiciones = obtenerTransiciones(componentesAF.get(2),estados,lenguaje);
            
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
    
    private ArrayList obtenerCompontesAtomata(String stringAutomata)
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
                    i++;
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
        
        return automata;
    }

    private HashMap obtenerEstados(String strEstados) 
    {
        return subStringComa(strEstados);  
    }

    private HashMap obtenerLenguaje(String strLenguaje) 
    {
        return subStringComa(strLenguaje);
    }

    

    private HashMap obtenerEstadosIniciales(String strEstadosIniciales) 
    {
         return subStringComa(strEstadosIniciales);
    }

    private HashMap obtenerEstadosAceptacion(String strEstadosAceptacion) {
        return subStringComa(strEstadosAceptacion);
    }
    
    private HashMap subStringComa(String hilera)
    {
        HashMap<String,Integer> retorno = new HashMap<>();
        int size = hilera.length();
        
        int i = 0;
        int j = 0;
        while (i < size) 
        {                
            String s = "";
            while (i< size && hilera.charAt(i) != ',') 
            {
                s = s.concat(String.valueOf(hilera.charAt(i)));
                i++;
            }
                retorno.put(s, j);
                i++;
                j++;
        }
      
        return retorno;
    }
    
    private ArrayList<String> subStringComa(String hilera,int a)
    {
        ArrayList<String> retorno = new ArrayList<>();
        int size = hilera.length();
        
        int i = 0;
        int j = 0;
        while (i < size) 
        {                
            String s = "";
            while (i< size && hilera.charAt(i) != ',') 
            {
                s = s.concat(String.valueOf(hilera.charAt(i)));
                i++;
            }
                retorno.add(s);
                i++;
                j++;
        }
      
        return retorno;
    }

    private ArrayList[][] obtenerTransiciones(String hilera, HashMap<String,Integer> estados, HashMap<String,Integer> lenguaje) 
    {
        ArrayList<String>[][] matriz = new ArrayList[estados.size()][lenguaje.size()];
        ArrayList<String> transiciones = splitTransiciones(hilera);
        for (int i = 0; i < transiciones.size(); i++) {
            ArrayList<String> transicionArrayList = subStringComa(transiciones.get(i),0);
            
            String estadoActual = transicionArrayList.get(0);
            String estadoSiguiente = transicionArrayList.get(2);
            String simbolo = transicionArrayList.get(1);
            
            int fila = estados.get(estadoActual);
            int columna = lenguaje.get(simbolo);
            
            ArrayList<String> posicionArrayList = matriz[fila][columna];
            if (posicionArrayList != null) 
            {
                posicionArrayList.add(estadoSiguiente);
            }
            else
            {
                posicionArrayList = new ArrayList<>();
                posicionArrayList.add(estadoSiguiente);
            }
            
            matriz[fila][columna] = posicionArrayList;            
        }
        
        
        return matriz;
    }

    private ArrayList<String> splitTransiciones(String hilera) {
        
        ArrayList<String> retorno = new ArrayList<>();
        int size = hilera.length();
        
        int i = 0;
        int j = 0;
        while (i < size) 
        {                
            String s = "";
            if(hilera.charAt(i) == '(')
            {
                i++;
            }
            while (i< size && hilera.charAt(i) != ')') 
            {
                s = s.concat(String.valueOf(hilera.charAt(i)));
                i++;
            }
                retorno.add(s);
                i++;
                j++;
        }
        return retorno;
    }
    
    public void agregarEstado(String estado)
    {
        af.agregarEstado(estado, contadorEstados);
        contadorEstados++;
    }
    public HashMap<String,Integer> obtenerEstados()
    {
        return af.obtenerEstados();
    }
    
    public void agregarSimbolo(String simbolo)
    {
        af.agregarSimbolos(simbolo, contadorSimbolos);
        contadorSimbolos++;
    }
    
    public HashMap<String,Integer> obtenerSimbolos()
    {
        return af.obtenerSimbolos();
    }
    
    public void agregarEstadoInicial(String estadoInicial)
    {
        af.agregarEstadoInicial(estadoInicial);
    }
    
    public void agregarEstadoAceptacion(String estadoAceptacion)
    {
        af.agregarEstadoAceptacion(estadoAceptacion);
    }
    
    public void agregarTransicion(String estadoActual,String simbolo,String nuevoEstado)
    {
        af.agregarTransicion(estadoActual, simbolo, nuevoEstado);
    }
    
}
