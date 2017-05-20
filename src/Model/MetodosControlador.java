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
     * @param estados
     * @param strEstadoInicial
     * @return 
     */
    public int buscarEstado(ArrayList<Estado> estados,String strEstadoInicial)
    {
        int retorno = 0;
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            if(estado.getData().equals(strEstadoInicial))
            {
                retorno = estado.getPosEstado();
            }
        }
        return retorno;
    }
    
    public int buscarSimbolo(ArrayList<String> simbolos, String simbolo){
        return simbolos.indexOf(simbolo);
    }
}
