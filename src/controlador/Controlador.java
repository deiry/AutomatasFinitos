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
import java.util.Map;
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
        
//        af.agregarEstado("q1", 0);
//        af.agregarEstado("q2", 1);
//        af.agregarEstado("q3", 2);
//        af.agregarSimbolos("0", 0);
//        af.agregarSimbolos("1", 1);
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
        af = new AFNoDeterministico();
        ArrayList<String> componentesAF = obtenerCompontesAtomata(stringAutomata);
        
        if (!componentesAF.isEmpty()) 
        {
            HashMap<String,Integer> estados = obtenerEstados(componentesAF.get(0));
            HashMap<String,Integer> lenguaje =obtenerLenguaje(componentesAF.get(1));
            HashMap<String,Integer> estadosIniciales =obtenerEstadosIniciales(componentesAF.get(3));
            HashMap<String,Integer> estadosAceptacion = obtenerEstadosAceptacion(componentesAF.get(4));
            Object[][] transiciones = obtenerTransiciones(componentesAF.get(2),estados,lenguaje);
            
            af.setEstados(estados);
            af.setSimbolos(lenguaje);
            af.agregarTransiciones(transiciones);
            
            for (Map.Entry<String, Integer> entry : estadosIniciales.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                agregarEstadoInicial(key);
            }
            
            for (Map.Entry<String, Integer> entry : estadosAceptacion.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                agregarEstadoAceptacion(key);
            }
            System.out.println("Automata Contruido");
            
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

    private Object[][] obtenerTransiciones(String hilera, HashMap<String,Integer> estados, HashMap<String,Integer> lenguaje) 
    {
        Object[][] matriz = new Object[estados.size()][lenguaje.size()];
        ArrayList<String> transiciones = splitTransiciones(hilera);
        for (int i = 0; i < transiciones.size(); i++) {
            ArrayList<String> transicionArrayList = subStringComa(transiciones.get(i),0);
            
            String estadoActual = transicionArrayList.get(0);
            String estadoSiguiente = transicionArrayList.get(2);
            String simbolo = transicionArrayList.get(1);
            
            int fila = estados.get(estadoActual);
            int columna = lenguaje.get(simbolo);
            
            ArrayList<String> posicionArrayList = (ArrayList<String>)matriz[fila][columna];
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
    
    public Object[][] obtenerTransiciones()
    {   
        Object[][] mat = af.obtenerTransiciones();
        if (mat != null) {
            Object[][] matOut = new Object[mat.length+1][mat[0].length+1];
            matOut[0][0] = "Estados|Simbolos";
            HashMap<String,Integer> estados = obtenerEstados();
            HashMap<String,Integer> simbolos = obtenerSimbolos();
            for (Map.Entry<String, Integer> entry : estados.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                matOut[value+1][0] = key;
            }
            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                matOut[0][value+1] = key;
            }
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    ArrayList<String> array = (ArrayList<String>) mat[i][j];
                    if(array != null)
                    {
                        String s = new String();
                        for (int k = 0; k < array.size(); k++) {
                            s = s + array.get(k);
                            if (k < array.size()-1)
                            {
                                s = s +", ";
                            }
                        }
                        matOut[i+1][j+1] = s;
                    }
                    else
                    {
                        matOut[i+1][j+1] = "";
                    }
                }
            }
            return matOut;
        }
        else{
            return null;
        }    
    }

    public String obtenerAutomata() {
        String automata = "{";
        
        HashMap<String,Integer> estados = af.getEstados();
        HashMap<String,Integer> simbolos = af.getSimbolos();
        String[] strEstados = new String[estados.size()];
        String[] strSimbolos = new String[simbolos.size()];
        ArrayList<String> estadoInicial = af.obtenerEstadoInicial();
        ArrayList<String> estadoAcpetacion = af.obtenerEstadoAceptacion();
        Object[][] transiciones = af.obtenerTransiciones();
        
        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            strEstados[value] = key;
        }
        
        for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            strSimbolos[value] = key;
        }
        automata = automata + "[";
        for (int i = 0; i < strEstados.length; i++) 
        {
            automata = automata + strEstados[i];
            if(i < strEstados.length-1)
            {
                automata = automata + ",";
            }
        }
        automata = automata + "][";
        for (int i = 0; i < strSimbolos.length; i++) 
        {
            automata = automata + strSimbolos[i];
            if(i < strSimbolos.length-1)
            {
                automata = automata + ",";
            }
        }
        automata = automata + "][";
        
        for (int i = 0; i < transiciones.length; i++) {
            for (int j = 0; j < transiciones[i].length; j++) {
                ArrayList<String> estadosSig = (ArrayList)transiciones[i][j];
                if (estadosSig != null)
                {
                    for (int k = 0; k < estadosSig.size(); k++) {
                        String get = estadosSig.get(k);
                        automata = automata 
                        + "("
                        + strEstados[i]+","
                        + strSimbolos[j]+","
                        + get
                        + ")";
                        
                    }
                }
                
            }
        }
        
        
        automata = automata + "][";
        for (int i = 0; i < estadoInicial.size(); i++) {
            automata = automata + estadoAcpetacion.get(i);
            if(i < estadoInicial.size()-1)
            {
                automata = automata + ",";
            }
        }
        automata = automata + "][";
        for (int i = 0; i < estadoAcpetacion.size(); i++) {
            automata = automata + estadoAcpetacion.get(i);
            if(i < estadoAcpetacion.size()-1)
            {
                automata = automata + ",";
            }
        }
        automata = automata + "]}";
        return automata;
    }
    
    
    
}
