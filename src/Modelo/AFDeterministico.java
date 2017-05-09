/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 *
 * @author DEIRY
 */
public class AFDeterministico extends AutomataFinito {

    private String estadoInicial;
    private List<String> estadoAceptacion;
    private String[][] transiciones;
    private ArrayList<List> particiones;
    private String estadoActual, estadoNuevo;
    private Vector<String> tranUnion;

    public AFDeterministico(HashMap<String, Integer> estados, HashMap<String, Integer> simbolos, String estadoInicial, List<String> estadoAceptacion, String[][] transiciones) {
        this.estados = estados;
        this.simbolos = simbolos;
        this.estadoInicial = estadoInicial;
        this.estadoAceptacion = estadoAceptacion;
        this.transiciones = transiciones;

    }

    public AFDeterministico() {
        super();
        this.estadoAceptacion = new Vector<String>();
        this.estadoInicial = "";

    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void setEstadoAceptacion(List<String> estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }

    public void setTransiciones(String[][] transiciones) {
        this.transiciones = transiciones;
    }

    public boolean reconocer(List<String> hilera) {
        String estadoActual = estadoInicial;
        String entrada = "";
        for (String temp : hilera) {
            entrada = temp;
            estadoActual = nuevoEstado(estadoActual, entrada);

        }

        if (estadoAceptacion.contains(estadoActual)) {
            return true;
        }
        return false;
    }

    public String nuevoEstado(String actual, String entrada) {
        int posEstado = estados.get(actual);
        int posEntrada = simbolos.get(entrada);
        return transiciones[posEstado][posEntrada];
    }

    @Override
    public void analizarEstadosInalcanzables() {
        HashMap<String, Integer> visitado = (HashMap<String, Integer>) estados.clone();
        String estadoActual, nuevoEstado;
        Stack<String> alcanzable = new Stack<>();
        List<String> estadosAlcanzables = new Vector<>();

        visitado.replace(estadoInicial, -1);
        estadosAlcanzables.add(estadoInicial);
        alcanzable.add(estadoInicial);
        while (!alcanzable.isEmpty()) {
            estadoActual = alcanzable.pop();

            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String key = entry.getKey();
                nuevoEstado = nuevoEstado(estadoActual, key);
                if (visitado.get(nuevoEstado) != -1) {
                    visitado.replace(nuevoEstado, -1);
                    alcanzable.add(nuevoEstado);
                    estadosAlcanzables.add(nuevoEstado);
                }
            }
        }
        this.eliminarEstados(visitado);
        actualizarAutomata(estadosAlcanzables);
    }

    public void eliminarEstados(HashMap<String, Integer> vist) {
        List<String> estInalcanzable = new Vector();
        for (Map.Entry<String, Integer> entry : vist.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value != -1) {
                estInalcanzable.add(key);
            }
        }

        for (int i = 0; i < estInalcanzable.size(); i++) {
            String aux = estInalcanzable.get(i);
            if (this.estadoAceptacion.contains(aux)) {
                this.estadoAceptacion.remove(aux);
            }
        }

    }

    public void actualizarAutomata(List<String> estadosAlcanzables) {

        HashMap<String, Integer> nEstados = new HashMap<>();
        String[][] nTransiciones = new String[estadosAlcanzables.size()][simbolos.size()];
        int posEstado;
        String estadoActual, nuevoEstado;

        for (int i = 0; i < nTransiciones.length; i++) {
            estadoActual = estadosAlcanzables.get(i);
            nEstados.put(estadoActual, i);
            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String simbolo = entry.getKey();
                Integer posSimbolo = entry.getValue();
                nTransiciones[i][posSimbolo] = nuevoEstado(estadoActual, simbolo);
            }
        }
        this.setEstados(nEstados);
        this.setTransiciones(nTransiciones);
    }

    @Override
    public void inicializar() {
        this.transiciones = new String[this.sizeEstados()][this.sizeSimbolos()];
    }

    @Override
    public void simplificar() {
        this.analizarEstadosInalcanzables();
        System.out.println("Transiciones despues de estados inalcanzables");
        imprimirTransiciones();
        this.particiones = creacionParticiones();
        String nuevoE;
        int partIni;
        List<String> particion;
        boolean cambio = false;
        int i = 0;
        while (i < particiones.size()) {

            particion = particiones.get(i);
            if (particion.size() > 1) {
                for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                    String simbolo = entry.getKey();
                    nuevoE = nuevoEstado(particion.get(0), simbolo);
                    partIni = obtenerParticion(nuevoE);
                    for (int k = 1; k < particion.size(); k++) {
                        String estadoActual = particion.get(k);

                        nuevoE = nuevoEstado(estadoActual, simbolo);
                        int partNuevo = obtenerParticion(nuevoE);

                        if (partNuevo != partIni) {
                            List<String> nuevaParticion = new Vector();
                            nuevaParticion.add(estadoActual);
                            particiones.add(nuevaParticion);
                            particion.remove(estadoActual);
                            cambio = true;
                            break;
                        }

                    }
                    if (cambio) {
                        break;
                    }
                }

            }
            if (cambio) {
                i = 0;
                cambio = false;
            } else {
                i++;
            }
        }
        System.out.println("Particiones " + particiones.toString().toString());
        unirTransiciones();
    }

      public void imprimirTransiciones() {
        System.out.println("Transiciones");
        for (int i = 0; i < transiciones.length; i++) {
            for (int j = 0; j < transiciones[i].length; j++) {
                System.out.print(transiciones[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int obtenerParticion(String estado) {
        for (int p = 0; p < particiones.size(); p++) {
            if (particiones.get(p).contains(estado)) {
                return p;
            }
        }
        return 0;
    }

    public ArrayList creacionParticiones() {

        ArrayList<List> particiones = new ArrayList<>();
        int cont = 0;
        Vector<String> particionN = new Vector<>();
        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (!estadoAceptacion.contains(key)) {
                particionN.add(cont, key);
                cont++;
            }
        }

        particiones.add(0, particionN);
        particiones.add(1, estadoAceptacion);
        return particiones;
    }

    public void unirTransiciones() {
        HashMap<String, Integer> nuevosEstados = unirEstados();
        Vector<String> aux = new Vector();
        String nuevoE = "";
        String estadosU = "";
        String[][] tran = new String[particiones.size()][this.sizeSimbolos()];

        for (int j = 0; j < particiones.size(); j++) {
            List particion = particiones.get(j);

            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String simbolo = entry.getKey();
                Integer posSimbolo = entry.getValue();

                if (particion.size() > 1) {
                    String soloMirar = (String) particion.get(0);
                    nuevoE = nuevoEstado((String) particion.get(0), simbolo);
                    aux.add(nuevoE);
                    estadosU = nuevoE;

                    for (int i = 1; i < particion.size(); i++) {
                        nuevoE = nuevoEstado((String) particion.get(i), simbolo);
                        if (!aux.contains(nuevoE)) {
                            aux.add(nuevoE);
                            estadosU += nuevoE;
                        }
                    }

                } else {
                    estadosU = nuevoEstado((String) particion.get(0), simbolo);
                    estadosU = validarNuevoEstado(estadosU);
                }
                //int posEstado = nuevosEstados.get(estadosU);
                tran[j][posSimbolo] = estadosU;

            }
        }

        this.setTransiciones(tran);
        imprimirTransiciones();
        this.setEstados(nuevosEstados);
        this.setEstadoInicial(validarNuevoEstado(estadoInicial));
        this.setEstadoAceptacion(validarNuevoEstadosAceptacion());

    }

    public List validarNuevoEstadosAceptacion() {
        List<String> nuevosEstadosAceptacion = new ArrayList<>();
        String estadoAcep;
        for (int i = 0; i < estadoAceptacion.size(); i++) {
            estadoAcep = validarNuevoEstado(estadoAceptacion.get(i));
            if (!nuevosEstadosAceptacion.contains(estadoAcep)) {
                nuevosEstadosAceptacion.add(estadoAcep);

            }
        }
        return nuevosEstadosAceptacion;
    }

    public String validarNuevoEstado(String estadoU) {
        String transicionN = "";
        for (int i = 0; i < particiones.size(); i++) {
            List particion = particiones.get(i);
            if (particion.size() > 1) {
                for (int j = 0; j < particion.size(); j++) {
                    if (particion.get(j).equals(estadoU)) {
                        transicionN = tranUnion.get(i);
                        return transicionN;
                    }
                }
            } else {
                if (particion.get(0).equals(estadoU)) {
                    transicionN = estadoU;
                }
            }

        }
        return transicionN;
    }

    public HashMap<String, Integer> unirEstados() {
        HashMap<String, Integer> union = new HashMap<>();
        tranUnion = new Vector();
        String estadoN = "";
        int pos = 0;
        Iterator<List> it = particiones.iterator();
        while (it.hasNext()) {
            List particion = it.next();
            if (particion.size() > 1) {
                for (int i = 0; i < particion.size(); i++) {
                    estadoN += particion.get(i);
                }
            } else {
                estadoN = (String) particion.get(0);
            }

            union.put(estadoN, pos);
            tranUnion.add(pos, estadoN);
            estadoN = "";
            pos++;
        }
        return union;

        //  this.setEstados(union);
        // System.out.println("Estados " + estados.toString());
    }

    public void imprimirArray(List<String> list) {
        Iterator<String> it = list.iterator();
        System.out.println("");
        while (it.hasNext()) {
            String next = it.next();
            System.out.print(next + " -");
        }
    }

    public void contruirTransiciones(Object[][] transicionesOld, HashMap<String, List<List<String>>> transicionesNuevas) {

        List<String> aux;
        String[][] transicionFinal = new String[sizeEstados()][simbolos.size()];

        //Segunda opcion
        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            String estado = entry.getKey();
            Integer posEstado = entry.getValue();
            for (Map.Entry<String, Integer> entry1 : simbolos.entrySet()) {
                String simbolo = entry1.getKey();
                Integer posSimbolo = entry1.getValue();

                if (posEstado < transicionesOld.length) {
                    String unionArray = unionEstadosTransicion((List) transicionesOld[posEstado][posSimbolo]);
                    transicionFinal[posEstado][posSimbolo] = unionArray;
                } else {
                    aux = transicionesNuevas.get(estado).get(posSimbolo);
                    String unionString = unionEstadosTransicion(aux);
                    transicionFinal[posEstado][posSimbolo] = unionString;
                }

            }
        }
        this.setTransiciones(transicionFinal);
        imprimirTransiciones();
    }

    public void construirEstados(ArrayList<String> estadosList) {
        HashMap<String, Integer> estadoMap = new HashMap<>();
        for (int i = 0; i < estadosList.size(); i++) {
            estadoMap.put(estadosList.get(i), i);
        }
        this.setEstados(estadoMap);
    }

    @Override
    public void agregarEstado(String nombre, int posicion) {
        this.estados.put(nombre, posicion);
    }

    @Override
    public void agregarSimbolos(String nombre, int posicion) {
        this.simbolos.put(nombre, posicion);
    }

    @Override
    public void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado) {
        int posEstado = posEstado(estadoActual);
        int posSimbolo = posSimbolo(simbolo);
        transiciones[posEstado][posSimbolo] = nuevoEstado;
    }

    @Override
    public void agregarEstadoAceptacion(String acep) {
        this.estadoAceptacion.add(acep);
    }

    @Override
    public void agregarEstadoInicial(String inicial) {
        this.estadoInicial = inicial;
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
        Object[][] transiciones = this.transiciones;
        return transiciones;
    }

    @Override
    public void agregarTransiciones(Object[][] transiciones) {
        this.transiciones = (String[][]) this.transiciones;
    }

    @Override
    public ArrayList<String> obtenerEstadoInicial() {
        ArrayList<String> estadoInicial = new ArrayList<String>();
        estadoInicial.add(this.estadoInicial);
        return estadoInicial;
    }

    @Override
    public ArrayList<String> obtenerEstadoAceptacion() {
        return (ArrayList<String>) estadoAceptacion;
    }

    @Override
    public AFDeterministico convertirAFNDtoAFD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
