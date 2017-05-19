/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.AFDeterministico;
import Modelo.AFNoDeterministico;
import Modelo.AutomataFinito;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static jdk.nashorn.internal.runtime.JSType.isString;
import vista.VistaPrincipal;

/**
 *
 * @author alejandro
 */
public class Controlador {

    private AFDeterministico afd;
    private AFNoDeterministico afnd;
    private int contadorEstados;
    private int contadorSimbolos;
    private int selector;
    private AutomataFinito af;
    private AutomataFinito af2;
    private static Controlador instance = null;

    protected Controlador() {
        VistaPrincipal vp = new VistaPrincipal();
        vp.setVisible(true);
        this.contadorEstados = 0;
        this.contadorSimbolos = 0;
        this.selector = 1;
        af = new AFNoDeterministico();

//        af.agregarEstado("q1", 0);
//        af.agregarEstado("q2", 1);
//        af.agregarEstado("q3", 2);
//        af.agregarSimbolos("0", 0);
//        af.agregarSimbolos("1", 1);
    }

    public static Controlador getInstance() {
        if (instance == null) {
            instance = new Controlador();
        }
        return instance;
    }

    /**
     * crea el automata a partir de una hilera ejemplo:
     * {[Q0,Q1,Q2,Q3][a1,a2][(Q0,a1,Q1)(Q1,a2,Q3)][Q0][Q3]}
     *
     * @param stringAutomata
     */
    public void construirAtomata(String stringAutomata) {
       
        af = new AFNoDeterministico();
        ArrayList<String> componentesAF = obtenerCompontesAtomata(stringAutomata);

        if (!componentesAF.isEmpty()) {
            HashMap<String, Integer> estados = obtenerEstados(componentesAF.get(0));
            HashMap<String, Integer> simbolos = obtenerLenguaje(componentesAF.get(1));
            HashMap<String, Integer> estadosIniciales = obtenerEstadosIniciales(componentesAF.get(3));
            HashMap<String, Integer> estadosAceptacion = obtenerEstadosAceptacion(componentesAF.get(4));
            Object[][] transiciones = obtenerTransiciones(componentesAF.get(2), estados, simbolos);

            af.setEstados(estados);
            af.setSimbolos(simbolos);
            af.agregarTransiciones(transiciones);

            for (Map.Entry<String, Integer> entry : estadosIniciales.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                agregarEstadoInicial(key);
            }

            for (Map.Entry<String, Integer> entry : estadosAceptacion.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                agregarEstadoAceptacion(key);
            }
            System.out.println("Automata Contruido");
            contadorEstados = estados.size();
            contadorSimbolos = simbolos.size();
            if (identificar().equals("AFD")) {
                construirAFD();
            }
        } else {
            error();
        }
        
        selectorAF(af);
    }

    private void construirAFD() {
        AFDeterministico afd = new AFDeterministico();
        afd.setSimbolos(af.getSimbolos());
        afd.setEstados(af.getEstados());
        afd.setEstadoInicial(af.obtenerEstadoInicial().get(0));
        afd.setEstadoAceptacion(af.obtenerEstadoAceptacion());
        Object[][] transiciones = af.obtenerTransiciones();
        String[][] transicionesAFD = new String[transiciones.length][transiciones[0].length];
        ArrayList<String> listTran;
        for (int i = 0; i < transiciones.length; i++) {
            for (int j = 0; j < transiciones[i].length; j++) {
                listTran = (ArrayList) transiciones[i][j];
                if (listTran != null) {
                    transicionesAFD[i][j] = listTran.get(0);
                }
            }
        }

        afd.setTransiciones(transicionesAFD);
        af = afd;
    }

    private void error() {
        System.out.println("ERROR");
    }

    /**
     * divide el automata por componentes pero es un subestring
     *
     * @param stringAutomata
     * @return
     */
    private ArrayList obtenerCompontesAtomata(String stringAutomata) {
        ArrayList<String> automata = new ArrayList<>();
        //elimimando los espacios
        stringAutomata = stringAutomata.replaceAll("\\s", "");
        int size = stringAutomata.length();
        if (stringAutomata.charAt(0) == '{' && stringAutomata.charAt(size - 1) == '}') {
            int i = 0;
            while (i < size) {
                if (stringAutomata.charAt(i) == '[') {
                    i++;
                    String s = "";
                    while (stringAutomata.charAt(i) != ']') {
                        s = s.concat(String.valueOf(stringAutomata.charAt(i)));
                        i++;
                    }
                    automata.add(s);
                }
                i++;
            }
        } else {
            error();
        }

        return automata;
    }

    /**
     * obtiene los estados a partir de un substring donde cada estado esta
     * separado por comas, devolviendo un HashMap
     *
     * @param strEstados
     * @return
     */
    private HashMap obtenerEstados(String strEstados) {
        return subStringComa(strEstados);
    }

    /**
     * obtiene los simbolos a partir de un substring donde cada simbolo esta
     * separado por comas, devolviendo un HashMap
     *
     * @param strLenguaje
     * @return
     */
    private HashMap obtenerLenguaje(String strLenguaje) {
        return subStringComa(strLenguaje);
    }

    /**
     * obtiene los estados iniciales a partir de un substring donde cada estado
     * inicial estan separado por comas, devolviendo un HashMap
     *
     * @param strEstadosIniciales
     * @return
     */
    private HashMap obtenerEstadosIniciales(String strEstadosIniciales) {
        return subStringComa(strEstadosIniciales);
    }

    /**
     * obtiene los estados de aceptacion a partir de un substring donde cada
     * estado esta separado por comas, devolviendo un HashMap
     *
     * @param strEstadosAceptacion
     * @return
     */
    private HashMap obtenerEstadosAceptacion(String strEstadosAceptacion) {
        return subStringComa(strEstadosAceptacion);
    }

    /**
     * devuelve un HashMap una clave String y un valor entero, obteniendo las
     * claves a partir de dividir la hilera que tiene cada clave separada por
     * comas
     *
     * @param hilera
     * @return
     */
    private HashMap subStringComa(String hilera) {
        HashMap<String, Integer> retorno = new HashMap<>();
        int size = hilera.length();

        int i = 0;
        int j = 0;
        while (i < size) {
            String s = "";
            while (i < size && hilera.charAt(i) != ',') {
                s = s.concat(String.valueOf(hilera.charAt(i)));
                i++;
            }
            retorno.put(s, j);
            i++;
            j++;
        }

        return retorno;
    }

    /**
     * devuelve un ArrayList una clave String y un valor entero, obteniendo las
     * claves a partir de dividir la hilera que tiene cada clave separada por
     * comas
     *
     * @param hilera
     * @param a
     * @return
     */
    private ArrayList<String> subStringComa(String hilera, int a) {
        ArrayList<String> retorno = new ArrayList<>();
        int size = hilera.length();

        int i = 0;
        int j = 0;
        while (i < size) {
            String s = "";
            while (i < size && hilera.charAt(i) != ',') {
                s = s.concat(String.valueOf(hilera.charAt(i)));
                i++;
            }
            retorno.add(s);
            i++;
            j++;
        }

        return retorno;
    }

    /**
     *
     * @param hilera
     * @param estados
     * @param lenguaje
     * @return
     */
    private Object[][] obtenerTransiciones(String hilera, HashMap<String, Integer> estados, HashMap<String, Integer> lenguaje) {
        Object[][] matriz = new Object[estados.size()][lenguaje.size()];
        ArrayList<String> transiciones = splitTransiciones(hilera);
        for (int i = 0; i < transiciones.size(); i++) {
            ArrayList<String> transicionArrayList = subStringComa(transiciones.get(i), 0);

            String estadoActual = transicionArrayList.get(0);
            String estadoSiguiente = transicionArrayList.get(2);
            String simbolo = transicionArrayList.get(1);

            int fila = estados.get(estadoActual);
            int columna = lenguaje.get(simbolo);

            ArrayList<String> posicionArrayList = (ArrayList<String>) matriz[fila][columna];
            if (posicionArrayList != null) {
                posicionArrayList.add(estadoSiguiente);
            } else {
                posicionArrayList = new ArrayList<>();
                posicionArrayList.add(estadoSiguiente);
            }

            matriz[fila][columna] = posicionArrayList;
        }

        return matriz;
    }

    private ArrayList<String> splitTransiciones(String hilera) {

        ArrayList<String> retorno = new ArrayList<>();
        int size = hilera.length();

        int i = 0;
        int j = 0;
        while (i < size) {
            String s = "";
            if (hilera.charAt(i) == '(') {
                i++;
            }
            while (i < size && hilera.charAt(i) != ')') {
                s = s.concat(String.valueOf(hilera.charAt(i)));
                i++;
            }
            retorno.add(s);
            i++;
            j++;
        }
        return retorno;
    }

    public void agregarEstado(String estado) {
        af.agregarEstado(estado, contadorEstados);
        contadorEstados++;
    }

    public HashMap<String, Integer> obtenerEstados() {
        return af.obtenerEstados();
    }
    public HashMap<String, Integer> obtenerEstados2() {
        if (af2 != null) {
            return af.obtenerEstados();
        }
        else{
            return null;
        }
    }

    public void agregarSimbolo(String simbolo) {
        af.agregarSimbolos(simbolo, contadorSimbolos);
        contadorSimbolos++;
    }

    public HashMap<String, Integer> obtenerSimbolos() {
        return af.obtenerSimbolos();
    }
    
    public HashMap<String, Integer> obtenerSimbolos2() {
        if (af2 != null) {
        return af2.obtenerSimbolos();
        }
        else{
            return null;
        }
        
    }

    public void agregarEstadoInicial(String estadoInicial) {
        af.agregarEstadoInicial(estadoInicial);
    }

    public void agregarEstadoAceptacion(String estadoAceptacion) {
        af.agregarEstadoAceptacion(estadoAceptacion);
    }

    public void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado) {
        af.agregarTransicion(estadoActual, simbolo, nuevoEstado);
    }

    public void convertirAF() {
        if (identificar().equals("AFND")) {
            af = af.convertirAFNDtoAFD();
        }

    }

    public void simplificar() {
        af.simplificar();
    }

    public Object[][] obtenerTransiciones() {
        Object[][] mat = af.obtenerTransiciones();
        if (mat != null) {
            Object[][] matOut = new Object[mat.length + 1][mat[0].length + 1];
            matOut[0][0] = "Estados|Simbolos";
            HashMap<String, Integer> estados = obtenerEstados();
            HashMap<String, Integer> simbolos = obtenerSimbolos();
            for (Map.Entry<String, Integer> entry : estados.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                matOut[value + 1][0] = key;
            }
            for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                matOut[0][value + 1] = key;
            }
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    if (isString(mat[i][j])) {
                        String s = mat[i][j].toString();
                        matOut[i + 1][j + 1] = s;
                    } else {
                        ArrayList<String> array = (ArrayList<String>) mat[i][j];
                        if (array != null) {
                            String s = new String();
                            for (int k = 0; k < array.size(); k++) {
                                s = s + array.get(k);
                                if (k < array.size() - 1) {
                                    s = s + ", ";
                                }
                            }
                            matOut[i + 1][j + 1] = s;
                        } else {
                            matOut[i + 1][j + 1] = "";
                        }
                    }
                }
            }
            return matOut;
        } else {
            return null;
        }
    }

    public String obtenerAutomata() {
        AutomataFinito afAux = null;
        if (selector == 2) {
            afAux = this.af;
            this.af = af2;
        }
        String automata = "{";

        HashMap<String, Integer> estados = af.getEstados();
        HashMap<String, Integer> simbolos = af.getSimbolos();
        String[] strEstados = new String[estados.size()];
        String[] strSimbolos = new String[simbolos.size()];
        ArrayList<String> estadoInicial = af.obtenerEstadoInicial();
        ArrayList<String> estadoAcpetacion = af.obtenerEstadoAceptacion();
        Object[][] transiciones = af.obtenerTransiciones();

        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            strEstados[value] = key;
        }

        for (Map.Entry<String, Integer> entry : simbolos.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            strSimbolos[value] = key;
        }
        automata = automata + "[";
        for (int i = 0; i < strEstados.length; i++) {
            automata = automata + strEstados[i];
            if (i < strEstados.length - 1) {
                automata = automata + ",";
            }
        }
        automata = automata + "][";
        for (int i = 0; i < strSimbolos.length; i++) {
            automata = automata + strSimbolos[i];
            if (i < strSimbolos.length - 1) {
                automata = automata + ",";
            }
        }
        automata = automata + "][";

        for (int i = 0; i < transiciones.length; i++) {
            for (int j = 0; j < transiciones[i].length; j++) {
                if (isString(transiciones[i][j])) {
                    String get = (String) transiciones[i][j];
                    automata = automata
                            + "("
                            + strEstados[i] + ","
                            + strSimbolos[j] + ","
                            + get
                            + ")";
                } else {
                    ArrayList<String> estadosSig = (ArrayList) transiciones[i][j];
                    if (estadosSig != null) {
                        for (int k = 0; k < estadosSig.size(); k++) {
                            String get = estadosSig.get(k);
                            automata = automata
                                    + "("
                                    + strEstados[i] + ","
                                    + strSimbolos[j] + ","
                                    + get
                                    + ")";

                        }
                    }
                }
            }
        }

        automata = automata + "][";
        for (int i = 0; i < estadoInicial.size(); i++) {
            automata = automata + estadoInicial.get(i);
            if (i < estadoInicial.size() - 1) {
                automata = automata + ",";
            }
        }
        automata = automata + "][";
        for (int i = 0; i < estadoAcpetacion.size(); i++) {
            automata = automata + estadoAcpetacion.get(i);
            if (i < estadoAcpetacion.size() - 1) {
                automata = automata + ",";
            }
        }
        automata = automata + "]}";
        if (selector == 2) {
            this.af = afAux;
        }
        return automata;
    }

    public ArrayList<String> obtenerEstadosAceptacion() {
        return af.obtenerEstadoAceptacion();
    }

    public ArrayList<String> obtenerEstadosInicial() {
        return af.obtenerEstadoInicial();
    }

    public ArrayList<String> obtenerEstadosAceptacion2() {
        if (af2 != null) {
            return af2.obtenerEstadoAceptacion();
        }
        else
        {
            return null;
        }
    }

    public ArrayList<String> obtenerEstadosInicial2() {
        if (af2 != null) {
            return af2.obtenerEstadoInicial();
        }
        else
        {
            return null;
        }
    }

    
    public AutomataFinito obtenerAutomataFinito() {
        return af;
    }

    public void setAutomataFinito(AutomataFinito af) {
        this.af = af;
    }

    public boolean reconocer(String hilera) {
        AutomataFinito afCopy = af;
        if (identificar().equals("AFND")) {
            afCopy = af.convertirAFNDtoAFD();
        }
        List<String> listHilera = new ArrayList<>();
        for (int i = 0; i < hilera.length(); i++) {
            listHilera.add(String.valueOf(hilera.charAt(i)));
        }
        boolean rta = afCopy.reconocer(listHilera);
        return rta;
    }

    public String identificar() {
        String respuesta = "AFD";
        Object[][] transicion = af.obtenerTransiciones();
        for (int i = 0; i < transicion.length; i++) {
            for (int j = 0; j < transicion[i].length; j++) {
                if (isString(transicion[i][j])) {
                    respuesta = "AFD";
                    return respuesta;
                } else {
                    ArrayList<String> list = (ArrayList) transicion[i][j];
                    if (list != null) {
                        if (list.size() > 1) {
                            respuesta = "AFND";
                            return respuesta;
                        }
                    }
                }
            }
        }
        return respuesta;
    }

    public void seleccionarAfA() 
    {
        if(selector != 1)
        {
            this.selector = 1;
        }
    }

    public void seleccionarAfB() 
    {
        if(selector != 2)
        {
            this.selector = 2;
        }
    }

    /**
     * metodo que valida cual automata esta seleccionado para contruir el automata
     * @param af 
     */
    private void selectorAF(AutomataFinito af) {
        if (this.selector == 1) {
            this.af = af;
        }
        else if(this.selector == 2)
        {
            this.af2 = af;
        }
        else
        {
            this.af = null;
        }
    }
    
    public int getSelector()
    {
        return this.selector;
    }
}