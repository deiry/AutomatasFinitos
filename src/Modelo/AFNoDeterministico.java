/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;

/**
 *
 * @author DEIRY
 */
public class AFNoDeterministico {
    
    private HashMap<String, Integer> estados;
    private HashMap<String, Integer> simbolos;
    private String[] estadoInicial;
    private String[] estadoAceptacion;
    private String[][] transiciones;
    private String estadoActual, estadoNuevo;
    
}
