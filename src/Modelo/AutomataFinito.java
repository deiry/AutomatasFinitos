/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String unionEstadosTransicion(List<String> lista) {
        
        String estadoUnion = "";
        String estados;
        for (int i = 0; i < lista.size(); i++) {
            estados = lista.get(i);         
            estadoUnion += estados;
        }

        return estadoUnion;
    }

    public ArrayList<String> convertirHashMaptoArray(HashMap<String, Integer> map) {

        ArrayList<String> aux = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            String key = entry.getKey();
            Integer value = entry.getValue();
            if (key != null && value != null) {
                aux.add(value, key);
            }
        }

        return aux;
    }
    
    public  List<List<String>> convertirMaptoList(HashMap<String, List<List<String>>> map){
        List<List<String>> lista = new ArrayList<>();
        for (Map.Entry<String, List<List<String>>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<List<String>> value = entry.getValue();
            lista.add(posEstado(key),(List)value);
        }
    return lista;
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

    public abstract HashMap<String, Integer> obtenerEstados();

    public abstract HashMap<String, Integer> obtenerSimbolos();

    public abstract Object[][] obtenerTransiciones();

    public abstract void agregarTransiciones(Object[][] transiciones);

    public abstract ArrayList<String> obtenerEstadoInicial();

    public abstract ArrayList<String> obtenerEstadoAceptacion();

    public abstract HashMap<String, Integer> unirEstados();

    public abstract AFDeterministico convertirAFNDtoAFD();

}
