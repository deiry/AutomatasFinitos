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
public class AFNoDeterministico extends AutomataFinito {
    
    private List<String> estadosIniciales;
    private List<String> estadosAceptacion;
    private List<String>[][] transiciones;
    

    @Override
    public void inicializar() {
        
    }

    @Override
    public void simplificar() {
    }

    @Override
    public void estadosInalcanzables() {
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

    public List<String>[][] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(List<String>[][] transiciones) {
        this.transiciones = transiciones;
    }

}
