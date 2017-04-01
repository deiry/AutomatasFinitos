/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author DEIRY
 */
public class Estado {
    
    private String nombre;
    private boolean estadoIni;
    private boolean estadoAceptacion;
    private int posicion;

    public Estado(String nombre, boolean estadoIni, boolean estadoAceptacion, int posicion) {
        this.nombre = nombre;
        this.estadoIni = estadoIni;
        this.estadoAceptacion = estadoAceptacion;
        this.posicion = posicion;
    }
    
    Estado(String nombre, int posicion){
    this.posicion = posicion;
     this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstadoIni() {
        return estadoIni;
    }

    public void setEstadoIni(boolean estadoIni) {
        this.estadoIni = estadoIni;
    }

    public boolean isEstadoAceptacion() {
        return estadoAceptacion;
    }

    public void setEstadoAceptacion(boolean estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    
    
}
