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
        String estadoU = "";
        int posEstado = posEstado(estado);
        int posSimbolo = posSimbolo(simbolo);
        ArrayList<String> todoEstados = (ArrayList<String>) this.transiciones[posEstado][posSimbolo];
        if (todoEstados.size() > 1) {
            estadoU = unionEstadosTransicion(todoEstados);
        } else {
            estadoU = todoEstados.get(0);
        }
        return estadoU;
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
            estados = new HashMap<String, Integer>();
        }

        estados.put(estado, posicion);
    }

    @Override
    public void agregarSimbolos(String simbolo, int posicion) {
        if (simbolos == null) {
            simbolos = new HashMap<String, Integer>();
        }

        simbolos.put(simbolo, posicion);
    }

    public AFDeterministico convertirAFD() {

        return null;
    }

    public void unirEstados() {
        List<String> transicion;
        HashMap<String, Integer> estadoDet = (HashMap) estados.clone();
        String estadoTran = "";
        Object[][] transCopy = transiciones.clone();

        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            String estado = entry.getKey();
            Integer posEstado = entry.getValue();

            for (Map.Entry<String, Integer> entry1 : simbolos.entrySet()) {
                String simbolo = entry1.getKey();
                Integer posSimbolo = entry1.getValue();

                transicion = (ArrayList<String>) ((ArrayList<String>) (transiciones[posEstado][posSimbolo])).clone();
                if (transicion.size() > 1) {
                    estadoTran = unionEstadosTransicion(transicion);
                    if (!estadoDet.containsKey(estadoTran)) {
                        estadoDet.put(estadoTran, estadoDet.size());
                        transicion.clear();
                        transicion.add(estadoTran);
                    }
                }
            }
        }
        System.out.println("Uniendo Estados " + estadoDet.toString());

        unirTransiciones(estadoDet);
    }

    public void unirTransiciones(HashMap<String, Integer> estadosCompletos) {
        String[][] transicionDet = new String[estadosCompletos.size()][simbolos.size()];
        String estadoN = "";
        List<String> aux;
        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            String estado = entry.getKey();
            Integer posEstado = entry.getValue();

            for (Map.Entry<String, Integer> entry1 : simbolos.entrySet()) {
                String simbolo = entry1.getKey();
                Integer posSimbolo = entry1.getValue();
                aux = (List<String>) transiciones[posEstado][posSimbolo];

                estadoN = unionEstadosTransicion(aux);
                transicionDet[posEstado][posSimbolo] = estadoN;
                
                int posNuevoEst = estadosCompletos.get(estadoN);
                transicionDet[posNuevoEst][posSimbolo] = unirTransicion(aux, simbolo);

            }
        }
        AFDeterministico afd = new AFDeterministico();
        afd.inicializar();
        afd.setTransiciones(transicionDet);
        afd.imprimirTransiciones();
    }

    public String unirTransicion(List<String> estado, String simbolo) {
        String estadoTranciones = "";
        for (int i = 0; i < estado.size(); i++) {
            estadoTranciones += nuevoEstado(estado.get(i), simbolo);
        }
        return estadoTranciones;
    }

    public String unionEstadosTransicion(List<String> lista) {
        String estadoUnion = "";
        for (int i = 0; i < lista.size(); i++) {
            estadoUnion += lista.get(i);
        }

        return estadoUnion;
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
        } else {
            transicion = new ArrayList<String>();
            transicion.add(nuevoEstado);
        }

        transiciones[posEstado][posSimbolo] = transicion;
    }

    @Override
    public void agregarEstadoAceptacion(String acep) {
        if (estadosAceptacion == null) {
            estadosAceptacion = new ArrayList<String>();
        }
        estadosAceptacion.add(acep);
    }

    @Override
    public void agregarEstadoInicial(String inicial) {
        if (estadosIniciales == null) {
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
