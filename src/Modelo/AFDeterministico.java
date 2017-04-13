/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.*;


/**
 *
 * @author DEIRY
 */
public class AFDeterministico extends AutomataFinito{

    private HashMap<String, Integer> estados;
    private HashMap<String, Integer> simbolos;
    private String estadoInicial;
    private String estadoAceptacion;
    private String[][] transiciones;
    private String estadoActual, estadoNuevo;

    public AFDeterministico(HashMap<String, Integer> estados, HashMap<String, Integer> simbolos, String estadoInicial, String estadoAceptacion, String[][] transiciones) {
        this.estados = estados;
        this.simbolos = simbolos;
        this.estadoInicial = estadoInicial;
        this.estadoAceptacion = estadoAceptacion;
        this.transiciones = transiciones;
    }

    public AFDeterministico() {
    }

    public HashMap<String, Integer> getEstados() {
        return estados;
    }

    public void setEstados(HashMap<String, Integer> estados) {
        this.estados = estados;
    }

    public HashMap<String, Integer> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(HashMap<String, Integer> simbolos) {
        this.simbolos = simbolos;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }



    public void setEstadoAceptacion(String estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }


    public void setTransiciones(String[][] transiciones) {
        this.transiciones = transiciones;
    }
    


    public boolean reconocer(List<String> hilera) {
        String estadoActual = estadoInicial;
        String entrada="";
        for(String temp: hilera){
            entrada = temp;
            estadoActual = nuevoEstado(estadoActual, entrada);
            
        }

        if (estadoActual.equals(estadoAceptacion)) {
            return true;
        }
        return false;
    }

    public String nuevoEstado(String actual, String entrada) {
        int posEstado = estados.get(actual);
        int posEntrada = estados.get(entrada);
        return transiciones[posEstado][posEntrada];
    }
    
 
    
    @Override
    public void estadosInalcanzables(){
        HashMap<String,Integer> visitado = estados;
        visitado.replace(estadoInicial, -1);
        ArrayList <String> visit = new ArrayList<>();
        visit.add(0, estadoInicial);
        int pos=0;
        
       
        for (Map.Entry<String, Integer> simboloEntry : simbolos.entrySet()) {
            String key = simboloEntry.getKey();
            Integer value = simboloEntry.getValue();
            String nuevo = nuevoEstado(visit.get(pos), key);
           
            
            
        }
    }

    @Override
    public void inicializar() {
    }

    @Override
    public void simplificar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
