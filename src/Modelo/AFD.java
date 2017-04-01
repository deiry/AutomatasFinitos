/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;

/**
 *
 * @author DEIRY
 */
public class AFD extends Automata{

    public Estado estadoInicial, estadoActual,nuevoEstado, estadoAceptacion;
    public Simbolo simboloEntrada;
    public AFD() {
      this.estadoInicial = estadosInicial.get(0);
      this.estadoAceptacion = estadosAceptacion.get(0);
    }
    
    public boolean reconocer(List<Simbolo> hilera){
     estadoActual = this.estadoInicial;
     simboloEntrada = hilera.get(0);
     int pos =1;
     
        while (!hilera.isEmpty()) {            
            nuevoEstado = f(estadoActual, simboloEntrada); 
            simboloEntrada = hilera.get(pos);
            pos++;
        }
        
        if (nuevoEstado == estadoAceptacion ) {
            return true;
        }
     return false;
    
    }
    
    public Estado f(Estado actual, Simbolo entrada){
        return this.transiciones[actual.getPosicion()][entrada.getPosicion()].get(0);
    }
    
}
