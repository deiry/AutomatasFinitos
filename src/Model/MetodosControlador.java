/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class MetodosControlador {

    public MetodosControlador() {
    }

    /**
     * Busca a que estado le pertenece ese dato y lo retorna el id del estado
     *
     * @param estados
     * @param strEstadoInicial
     * @return
     */
    public int buscarEstado(ArrayList<Estado> estados, String strEstadoInicial) {
        int retorno = 0;
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            if (estado.getData().equals(strEstadoInicial)) {
                retorno = estado.getPosEstado();
            }
        }
        return retorno;
    }

    public int buscarSimbolo(ArrayList<String> simbolos, String simbolo) {
        return simbolos.indexOf(simbolo);
    }

    public Object[][] obtenerTransiciones(AutomataFinito automata) {
        ArrayList<Estado> estados = automata.getEstados();
        ArrayList<String> simbolos = automata.getSimbolos();
        Object[][] matriz = new Object[estados.size()][simbolos.size()+1];
        for (int i = 0; i < estados.size(); i++) {
            Estado aux = estados.get(i);
            for (int j = 0; j < simbolos.size(); j++) {
                if(j==0)
                {
                    matriz[i][j] = estados.get(i).getData();
                }
                if (aux.getTransicion(j) != null) {
                    matriz[i][j+1] = aux.getTransicion(j).getData();
                }
                else{
                    matriz[i][j+1] = " ";
                }
            }
        }
        return matriz;
    }
}
