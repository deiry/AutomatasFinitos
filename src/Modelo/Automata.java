/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.AbstractList;
import java.util.List;



/**
 *
 * @author DEIRY
 */
public abstract class Automata {
    
    protected List <Simbolo> alfabeto;
    protected List <Estado> transiciones[][];
    protected List <Estado> estadosInicial;
    protected List <Estado> estadosAceptacion;
    protected List <Estado> estados;
    
    public Automata(){
        
    }

    public List <Simbolo> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(List <Simbolo> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public List <Estado>[][] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(List <Estado>[][] transiciones) {
        this.transiciones = transiciones;
    }

    public List <Estado> getEstadosInicial() {
        return estadosInicial;
    }

    public void setEstadosInicial(List <Estado> estadosInicial) {
        this.estadosInicial = estadosInicial;
    }

    public List <Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(List <Estado> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public List <Estado> getEstados() {
        return estados;
    }

    public void setEstados(List <Estado> estados) {
        this.estados = estados;
    }

   
    
    
    
    
}
