/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DEIRY
 */
public abstract class AutomataFinito {

    protected HashMap<String, Integer> estados;
    protected HashMap<String, Integer> simbolos;

    public AutomataFinito() {
        this.estados = new HashMap<>();
        this.simbolos = new HashMap<>();
    }
    
    
    

    public int sizeEstados() {
        return estados.size();
    }

    public int sizeSimbolos() {
        return simbolos.size();
    }

    public int posEstado(String estado) {
        return estados.get(estado);
    }

    public int posSimbolo(String simbolo) {
        return simbolos.get(simbolo);
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
    
    
    public abstract void simplificar();

    public abstract void analizarEstadosInalcanzables();

    public abstract void inicializar();

    public abstract String nuevoEstado(String estado, String simbolo);

    public abstract boolean reconocer(List<String> hilera);
    
    public abstract void agregarEstado(String estado, int posicion);
    
    public abstract void agregarSimbolos(String simbolo, int posicion);
    
    public abstract void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado);
    
    public abstract void agregarEstadoAceptacion(String acep);
    
    public abstract void agregarEstadoInicial(String inicial);
    
    public abstract HashMap<String,Integer> obtenerEstados();
    
    public abstract HashMap<String,Integer> obtenerSimbolos();
    

}
