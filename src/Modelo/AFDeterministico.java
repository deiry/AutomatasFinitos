/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author DEIRY
 */
public class AFDeterministico {

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

    public String getEstadoAceptacion() {
        return estadoAceptacion;
    }

    public void setEstadoAceptacion(String estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }

    public String[][] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(String[][] transiciones) {
        this.transiciones = transiciones;
    }
    
    public int sizeEstados(){
        return estados.size();
    }
    
    public int sizeSimbolos(){
        return simbolos.size();
    }
    
    public int posEstado(String estado){
        return estados.get(estado);
    }
    
    public int posSimbolo(String simbolo){
        return simbolos.get(simbolo);
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
    

}
