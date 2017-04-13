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

    private HashMap<String, Integer> estados;
    private HashMap<String, Integer> simbolos;
    
    
    

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

    public abstract void estadosInalcanzables();

    public abstract void inicializar();

    public abstract String nuevoEstado(String estado, String simbolo);

    public abstract boolean reconocer(List<String> hilera);

}
