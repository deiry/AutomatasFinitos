/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author DEIRY
 */
public class AFNoDeterministico extends AutomataFinito {

    private List<String> estadosIniciales;
    private List<String> estadosAceptacion;
    private Object[][] transiciones;

    @Override
    public void inicializar() {

    }

    @Override
    public void simplificar() {
    }

    @Override
    public void analizarEstadosInalcanzables() {
    }

    @Override
    public String nuevoEstado(String estado, String simbolo) {
        String estadoU = "";
        int posEstado = posEstado(estado);
        int posSimbolo = posSimbolo(simbolo);
        ArrayList<String> todoEstados = (ArrayList<String>) this.transiciones[posEstado][posSimbolo];
        if (todoEstados.size() > 1) {
            estadoU = unionEstadosTransicion(todoEstados);
        } else {
            estadoU = todoEstados.get(0);
        }
        return estadoU;
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

    public Object[][] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Object[][] transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public void agregarEstado(String estado, int posicion) {
        if (estados == null) {
            estados = new HashMap<String, Integer>();

        }

        estados.put(estado, posicion);
        if (transiciones != null) {
            if (transiciones.length < obtenerEstados().size()) {
                reconstruirTransiciones();
            }
        }
    }

    @Override
    public void agregarSimbolos(String simbolo, int posicion) {
        if (simbolos == null) {
            simbolos = new HashMap<String, Integer>();
        }

        simbolos.put(simbolo, posicion);
        if (transiciones != null) {
            if (transiciones.length < obtenerSimbolos().size()) {
                reconstruirTransiciones();
            }
        }

    }

    public AFDeterministico convertirAFD() {

        return null;
    }

    /**
     * Hashmap todosEstados ahi se van agregando los nuevos estados que van
     * saliendo HashMap nuevosEstados se guarda la union de los estados en un
     * string y su arrayList los ejemplo: String q1q2, arrayList q1,q2 HashMap
     * transicionesNuevosEstados cuando busco con el string de la union de los
     * estados obtengo la transcion que hace cuando de cada simbolo
     */
    public void convertirAFNDtoAFD() {
        ArrayList<String> estadosCompletos = convertirHashMaptoArray(estados);
        ArrayList<String> simbolosList = convertirHashMaptoArray(simbolos);

        HashMap<String, List<String>> nuevosEstados = new HashMap();
        HashMap<String, List<List<String>>> transicionesNuevosEstados = new HashMap();
        List<List<String>> vector = new ArrayList<>();

        for (int i = 0; i < estadosCompletos.size(); i++) {
            String estado = estadosCompletos.get(i);
            Integer posEstado = i;
            for (int j = 0; j < simbolosList.size(); j++) {
                Integer posSimbolo = j;

                if (posEstado < estados.size()) {
                    ArrayList<String> estadosNDet = (ArrayList<String>) ((ArrayList<String>) this.transiciones[posEstado][posSimbolo]).clone();

                    if ((estadosNDet.size() > 1) && !nuevosEstados.containsValue(estadosNDet)) {
                        String estadoTran = unionEstadosTransicion(estadosNDet);
                        estadosCompletos.add(estadosCompletos.size(), estadoTran);
                        nuevosEstados.put(estadoTran, estadosNDet);
                        List<String> nVector = new Vector();
                        transicionesNuevosEstados.put(estadoTran, new Vector(nVector));
                    }
                } else {

                    ArrayList<String> nuevoE = (ArrayList<String>) nuevosEstados.get(estado);
                    ArrayList<String> transicionU = unirTransicion(nuevoE, posSimbolo);
                    if (transicionU.size() > 1 && (!nuevosEstados.containsValue(transicionU))) {
                        vector = transicionesNuevosEstados.get(estado);
                        vector.add(posSimbolo, transicionU);
                        transicionesNuevosEstados.put(estado, vector);

                        String transU = unionEstadosTransicion(transicionU);
                        estadosCompletos.add(estadosCompletos.size(), transU);
                        nuevosEstados.put(transU, transicionU);
                        List<String> nVector = new Vector();
                        transicionesNuevosEstados.put(transU, new Vector(nVector));
                    } else {
                        vector = new ArrayList<>();
                        vector = transicionesNuevosEstados.get(estado);
                        vector.add(posSimbolo, transicionU);
                        transicionesNuevosEstados.replace(estado, vector);

                    }

                }
            }
        } 
        System.out.println("Estdos finales " + estadosCompletos.toString());
        System.out.println("transiciones finales "+ transicionesNuevosEstados.toString());
        AFDeterministico afd = new AFDeterministico();
        afd.setSimbolos(simbolos);
        afd.construirEstados(estadosCompletos);
        afd.contruirTransiciones(transiciones, transicionesNuevosEstados);
    }
    
    
    
        
    public boolean validarRepetidos(HashMap<String, List<String>> nuevoEstados, ArrayList transicionU){
        for (Map.Entry<String, List<String>> entry : nuevoEstados.entrySet()) {
            List<String> arrayEstados = entry.getValue();
            String estado = "";
            for (int i = 0; i < arrayEstados.size(); i++) {
                estado = arrayEstados.get(i);
                for (int j = 0; j < transicionU.size(); j++) {
                    
                }
            }
        }
    return false;
    }


    /**
     * con la lista de estados buscar las transiones de cada uno de los estados
     * y guardarlos en arrayList SIN REPETIR porque se caga todo :v
     *
     * @param estado
     * @param simbolo
     * @return
     */
    public ArrayList<String> unirTransicion(List<String> estado, int simbolo) {

        ArrayList<String> unirTodo = (ArrayList<String>) ((ArrayList<String>) this.transiciones[posEstado(estado.get(0))][simbolo]).clone();
        for (int i = 1; i < estado.size(); i++) {
            ArrayList<String> aux = (ArrayList<String>) ((ArrayList<String>) this.transiciones[posEstado(estado.get(i))][simbolo]).clone();
            if (aux.size() > 1) {
                for (int j = 0; j < aux.size(); j++) {
                    if (!unirTodo.contains(aux.get(j))) {
                        unirTodo.add(aux.get(j));
                    }
                }
            } else {
                String estadoString = aux.get(0);
                if (!unirTodo.contains(estadoString)) {
                    unirTodo.add(estadoString);
                }
            }
        }
        unirTodo = ordenarTransicion(unirTodo);
        return unirTodo;
    }


    @Override
    public void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado) {
        if (transiciones == null) {
            transiciones = new Object[obtenerEstados().size()][obtenerSimbolos().size()];
        }
        int posEstado = posEstado(estadoActual);
        int posSimbolo = posSimbolo(simbolo);
        List<String> transicion = (ArrayList<String>) transiciones[posEstado][posSimbolo];
        if (transicion != null) {
            transicion.add(nuevoEstado);
        } else {
            transicion = new ArrayList<String>();
            transicion.add(nuevoEstado);
        }
        
        

        transiciones[posEstado][posSimbolo] = ordenarTransicion((ArrayList<String>)transicion);
    }
    
    private ArrayList<String> ordenarTransicion (ArrayList<String> estadosSiguiente)
    {
        HashMap<String,Integer> estados = getEstados();
        String[] strEstadosSig = new String[estados.size()];
        for (int i = 0; i < estadosSiguiente.size(); i++)
        {
            String get = estadosSiguiente.get(i);
            strEstadosSig[estados.get(get)] = get;
        }
        estadosSiguiente = new ArrayList<>();
        for (int i = 0; i < strEstadosSig.length; i++) {
            if(strEstadosSig[i] != null)
            {
                estadosSiguiente.add(strEstadosSig[i]);
            }        
        }
        return estadosSiguiente;
    }

    @Override
    public void agregarEstadoAceptacion(String acep) {
        if (estadosAceptacion == null) {
            estadosAceptacion = new ArrayList<String>();
        }
        estadosAceptacion.add(acep);
    }

    @Override
    public void agregarEstadoInicial(String inicial) {
        if (estadosIniciales == null) {
            estadosIniciales = new ArrayList<String>();
        }
        estadosIniciales.add(inicial);
    }

    @Override
    public HashMap<String, Integer> obtenerEstados() {
        return estados;
    }

    @Override
    public HashMap<String, Integer> obtenerSimbolos() {
        return simbolos;
    }

    @Override
    public Object[][] obtenerTransiciones() {
        return transiciones;
    }

    @Override
    public void agregarTransiciones(Object[][] transiciones) {
        this.transiciones = transiciones;
    }

    @Override
    public ArrayList<String> obtenerEstadoInicial() {
        return (ArrayList<String>) estadosIniciales;
    }

    @Override
    public ArrayList<String> obtenerEstadoAceptacion() {
        return (ArrayList<String>) estadosAceptacion;
    }

    private void reconstruirTransiciones() {
        Object[][] mat = new Object[obtenerEstados().size()][obtenerSimbolos().size()];

        for (int i = 0; i < transiciones.length; i++) {
            for (int j = 0; j < transiciones[i].length; j++) {
                mat[i][j] = transiciones[i][j];
            }
        }
        transiciones = mat;
    }

    @Override
    public void unirEstados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
