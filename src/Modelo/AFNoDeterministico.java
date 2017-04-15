/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DEIRY
 */
public class AFNoDeterministico extends AutomataFinito {
    
    private List<String> estadosIniciales;
    private List<String> estadosAceptacion;
    private Object[][] transiciones;
    

    @Override
    public void inicializar() {
        
    }

    @Override
    public void simplificar() {
    }

    @Override
    public void analizarEstadosInalcanzables() {
    }

    @Override
    public String nuevoEstado(String estado, String simbolo) {
        return "";
    }

    @Override
    public boolean reconocer(List<String> hilera) {
        return false;
    }

    public List<String> getEstadosIniciales() {
        return estadosIniciales;
    }

    public void setEstadosIniciales(List<String> estadosIniciales) {
        this.estadosIniciales = estadosIniciales;
    }

    public List<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(List<String> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public Object[][] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Object[][] transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public void agregarEstado(String estado, int posicion) {
        if (estados == null) {
            estados = new HashMap<String,Integer>();
        }
        
        estados.put(estado, posicion);
    }

    @Override
    public void agregarSimbolos(String simbolo, int posicion) {
        if (simbolos == null) {
            simbolos = new HashMap<String,Integer>();
        }

        simbolos.put(simbolo, posicion);
    }

    @Override
    public void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado) {
        if (transiciones == null) {
            transiciones = new Object[obtenerEstados().size()][obtenerSimbolos().size()];
        }
        int posEstado = posEstado(estadoActual);
        int posSimbolo = posSimbolo(simbolo);
        List<String> transicion = (ArrayList<String>) transiciones[posEstado][posSimbolo];
        if (transicion != null) {
            transicion.add(nuevoEstado);
        }
        else
        {
            transicion = new ArrayList<String>();
            transicion.add(nuevoEstado);
        }
        
        transiciones[posEstado][posSimbolo] = transicion;
    }

    @Override
    public void agregarEstadoAceptacion(String acep) {
        if (estadosAceptacion == null)
        {
            estadosAceptacion = new ArrayList<String>();
        }
        estadosAceptacion.add(acep);
    }

    @Override
    public void agregarEstadoInicial(String inicial) {
        if (estadosIniciales == null) 
        {
            estadosIniciales = new ArrayList<String>();
        }
        estadosIniciales.add(inicial);
    }

    @Override
    public HashMap<String, Integer> obtenerEstados() {
        return estados;
    }

    @Override
    public HashMap<String, Integer> obtenerSimbolos() {
        return simbolos;
    }

    @Override
    public Object[][] obtenerTransiciones() {
        return transiciones;
    }

}
