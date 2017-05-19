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
        estado.setId(estados.size()+1);
        estados.add(estados.size(), estado);

    }

    public void agregarSimbolo(int pos, String simbolo) {
        simbolos.add(pos, simbolo);
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

    public int getSizeEstado() {
        return estados.size();
    }

    public int getSizeSimbolos() {
        return simbolos.size();
    }

    public Estado getEstado(int i) {
        return estados.get(i);
    }
    
    public void imprimirEstados(){
        for(Estado est: estados){
            System.out.print(est.getData()+", ");
        
        }
        
    }
    

}
