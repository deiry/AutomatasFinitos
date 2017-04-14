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
public class AFDeterministico extends AutomataFinito {

    private String estadoInicial;
    private List<String> estadoAceptacion;
    private String[][] transiciones;
    private String estadoActual, estadoNuevo;

    public AFDeterministico(HashMap<String, Integer> estados, HashMap<String, Integer> simbolos, String estadoInicial, List<String> estadoAceptacion, String[][] transiciones) {
        this.estados = estados;
        this.simbolos = simbolos;
        this.estadoInicial = estadoInicial;
        this.estadoAceptacion = estadoAceptacion;
        this.transiciones = transiciones;

    }

    public AFDeterministico() {
        super();
        this.estadoAceptacion = new Vector<String>();
        this.estadoInicial = "";        

    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void setEstadoAceptacion(List<String> estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }

    public void setTransiciones(String[][] transiciones) {
        this.transiciones = transiciones;
    }

    public boolean reconocer(List<String> hilera) {
        String estadoActual = estadoInicial;
        String entrada = "";
        for (String temp : hilera) {
            entrada = temp;
            estadoActual = nuevoEstado(estadoActual, entrada);

        }

        if (estadoAceptacion.contains(estadoActual)) {
            return true;
        }
        return false;
    }

    public String nuevoEstado(String actual, String entrada) {
        int posEstado = estados.get(actual);
        int posEntrada = simbolos.get(entrada);
        return transiciones[posEstado][posEntrada];
    }

    @Override
    public void estadosInalcanzables() {
        HashMap<String, Integer> visitado = (HashMap<String, Integer>) estados.clone();
        String estadoActual, nuevoEstado;
        Stack<String> visit = new Stack<>();
        List<String> estadosAlcanzables = new Vector<>();
        int posEstado;
        int pos = 0;

        visitado.replace(estadoInicial, -1);
        estadosAlcanzables.add(estadoInicial);
        visit.add(estadoInicial);
        while (!visit.isEmpty()) {
            estadoActual = visit.pop();

            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String key = entry.getKey();
                Integer posSimbolo = entry.getValue();

                nuevoEstado = nuevoEstado(estadoActual, key);
                if (visitado.get(nuevoEstado) != -1) {
                    visitado.replace(nuevoEstado, -1);
                    visit.add(nuevoEstado);
                    estadosAlcanzables.add(nuevoEstado);
                }
            }
        }
        System.out.println("Estados alcanzables" + estadosAlcanzables.toString());
        actualizarAutomata(estadosAlcanzables);
    }

    public void actualizarAutomata(List<String> estadosAlcanzables) {

        HashMap<String, Integer> nEstados = new HashMap<>();
        String[][] nTransiciones = new String[estadosAlcanzables.size()][simbolos.size()];
        int posEstado;
        String estadoActual, nuevoEstado;

        for (int i = 0; i < nTransiciones.length; i++) {
            estadoActual = estadosAlcanzables.get(i);
            nEstados.put(estadoActual, i);
            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String simbolo = entry.getKey();
                Integer posSimbolo = entry.getValue();
                nTransiciones[i][posSimbolo] = nuevoEstado(estadoActual, simbolo);

            }

        }
        this.setEstados(nEstados);
        this.setTransiciones(transiciones);
    }

    @Override
    public void inicializar() {
        this.transiciones = new String[this.sizeEstados()][this.sizeSimbolos()];
        System.out.println("etados "+estados.toString());
    }

    @Override
    public void simplificar() {
        this.estadosInalcanzables();

    }

    @Override
    public void agregarEstado(String nombre, int posicion) {
        this.estados.put(nombre, posicion);
    }

    @Override
    public void agregarSimbolos(String nombre, int posicion) {
        this.simbolos.put(nombre, posicion);
    }

    @Override
    public void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado) {
        int posEstado = posEstado(estadoActual);
        int posSimbolo = posSimbolo(simbolo);
        transiciones[posEstado][posSimbolo] = nuevoEstado;
    }

    @Override
    public void agregarEstadoAceptacion(String acep) {
        this.estadoAceptacion.add(acep);
    }

    @Override
    public void agregarEstadoInicial(String inicial) {
        this.estadoInicial = inicial;
    }

    @Override
    public HashMap<String, Integer> obtenerEstados() {
        return estados;
    }

    @Override
    public HashMap<String, Integer> obtenerSimbolos() {
        return simbolos;
    }

}
