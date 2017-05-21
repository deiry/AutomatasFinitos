/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author DEIRY
 */
public abstract class AutomataFinito {

    protected ArrayList<Estado> estados;
    protected ArrayList<String> simbolos;

    public AutomataFinito() {
        estados = new ArrayList<>();
        simbolos = new ArrayList<>();
    }

    public void agregarEstado(int pos, Estado estado) {
        estados.add(pos, estado);
    }

    public void agregarEstado(Estado estado) {
        estado.setPosEstado(estados.size());
        estado.setId(estados.size() + 1);
        estados.add(estados.size(), estado);

    }

    public void agregarSimbolo(int pos, String simbolo) {
        simbolos.add(pos, simbolo);
    }

    public void agregarSimbolo(String simbolo) {
        simbolos.add(simbolo);
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public ArrayList<String> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(ArrayList<String> simbolos) {
        this.simbolos = simbolos;
    }

    public int getSizeEstados() {
        return estados.size();
    }

    public int getSizeSimbolos() {
        return simbolos.size();
    }

    public Estado getEstado(int i) {
        return estados.get(i);
    }

    public void imprimirEstados(ArrayList<Estado> estados) {
        for (Estado est : estados) {
            System.out.print(est.getData() + ", ");

        }

    }

    public void imprimirTransiciones(ArrayList<Estado> estados) {
        System.out.println("");
        Iterator<Estado> it = estados.iterator();
        while (it.hasNext()) {
            Estado next = it.next();
            String data = next.getData();
            String tran1 = "", tran2 = "";
            if (next.getTransicion(0) != null) {
                tran1 = next.getTransicion(0).getData();
            }
            if (next.getTransicion(1) != null) {
                tran2 = next.getTransicion(1).getData();
            }
            System.out.println(data + "| " + tran1 + "| " + tran2 + "| " + next.isEstadoAcep()
                    + " " + next.isEstadoInicial() + " " + next.getParticion() + " " + next.getId() + " " + next.getTamDatos());
        }
    }

    public String toStringEstados(char sep) {
        String stringEst = this.getEstado(0).getData();
        for (int i = 1; i < estados.size(); i++) {
            stringEst = stringEst + sep + estados.get(i).getData();
        }
        return stringEst;
    }

    public String toStringSimbolos(char sep) {
        String sim = simbolos.get(0);
        for (int s = 1; s < simbolos.size(); s++) {
            sim = sim + sep + simbolos.get(s);
        }
        return sim;
    }

    public String toStringTransiciones(char sepInicial, char sepFinal) {
        String transicionString = "";
        char sep = ',';

        for (Estado est : estados) {
            for (int i = 0; i < simbolos.size(); i++) {
                if (est.getTransicion(i) != null) {
                    transicionString = sepInicial + est.toStringTransicion(i, ',', simbolos.get(i)) + sepFinal + transicionString;

                }
            }
        }

        return transicionString;
    }

    ;
    
    public ArrayList<String> obtenerEstadosString() {

        ArrayList<String> estadosString = new ArrayList<>();
        for (Estado estado : estados) {
            estadosString.add(estado.getData());
        }
        return estadosString;
    }

    public void eliminarEstadoAceptacion(int posEstado) {

        Estado aceptacion = this.getEstado(posEstado);
        aceptacion.setEstadoAcep(false);
    }

    public abstract void addEstadoInicial(int posEstado);

    public abstract void addEstadoAceptacion(int posEstado);

    public abstract void eliminarEstadoInicial(int posEstado);

    public abstract int tamEstadosIniciales();

}
