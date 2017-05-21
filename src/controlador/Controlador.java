/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

//import Modelo.AFDeterministico;
//import Modelo.AFNoDeterministico;
//import Modelo.AutomataFinito;
import Model.AFD;
import Model.AFND;
import Model.AutomataFinito;
import Model.Estado;
import Model.MetodosControlador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.runtime.JSType.isString;
import vista.VistaPrincipal;

/**
 *
 * @author alejandro
 */
public class Controlador {

//    private AFDeterministico afd;
//    private AFNoDeterministico afnd;
    private int contadorEstados;
    private int contadorSimbolos;
    private int selector;
    private AutomataFinito af;
    private AutomataFinito af2;
    private int posEstadoA = 1;
    private int posEstadoB = 1;
    private int posSimboloA = 1;
    private int posSimboloB = 1;
    private static Controlador instance = null;
    private MetodosControlador metodos;
    public boolean[] boolEstadosInciales;
    public boolean[] boolEstadosAceptacion;

    protected Controlador() {
        VistaPrincipal vp = new VistaPrincipal();
        vp.setVisible(true);
        this.contadorEstados = 0;
        this.contadorSimbolos = 0;
        this.selector = 1;
        af = null;
        af2 = null;
        metodos = new MetodosControlador();
    }

    public static Controlador getInstance() {
        if (instance == null) {
            instance = new Controlador();
        }
        return instance;
    }

    public void inicializarAF() {
        if (selector == 1) {
            if (af == null) {
                af = new AFND();
            }

        } else if (selector == 2) {
            if (af2 == null) {
                af2 = new AFND();
            }

        }

    }

    /**
     * crea el automata a partir de una hilera ejemplo:
     * {[Q0,Q1,Q2,Q3][a1,a2][(Q0,a1,Q1)(Q1,a2,Q3)][Q0][Q3]}
     *
     * @param stringAutomata
     */
    public void construirAtomata(String stringAutomata) {

        //af = new AFNoDeterministico();
        AutomataFinito af = new AFND();
        ArrayList<String> componentesAF = obtenerCompontesAtomata(stringAutomata);

        if (!componentesAF.isEmpty()) {

            ArrayList<Estado> estados = obtenerEstados(componentesAF.get(0));
            af.setEstados(estados);

            ArrayList<String> simbolos = obtenerLenguaje(componentesAF.get(1));
            af.setSimbolos(simbolos);

            ArrayList<String> estadosIniciales = obtenerEstadosIniciales(componentesAF.get(3));
            for (int i = 0; i < estadosIniciales.size(); i++) {
                String estado = estadosIniciales.get(i);
                int id = metodos.buscarEstado(af.getEstados(), estado);
                af.addEstadoInicial(id);
            }

            ArrayList<String> estadosAceptacion = obtenerEstadosAceptacion(componentesAF.get(4));
            for (int i = 0; i < estadosAceptacion.size(); i++) {
                String estado = estadosAceptacion.get(i);
                int id = metodos.buscarEstado(af.getEstados(), estado);
                af.addEstadoAceptacion(id);
            }
            construirTransiciones(componentesAF.get(2), estados, af);
            if ( estadosIniciales.size() == 1 && af.isAFDeterministco()) {
                AFD afd = new AFD();
                afd.setEstados(af.getEstados());
                afd.setSimbolos(simbolos);
                afd.asignarEstadoInicial();
                af = afd;
            }

            System.out.println("Automata Contruido");
            contadorEstados = estados.size();
            contadorSimbolos = simbolos.size();
            selectorAF(af);
        } else {
            error();
        }

        selectorAF(af);
    }

    private void error() {
        System.out.println("ERROR");
        JOptionPane.showMessageDialog(null, "ERROR", "", JOptionPane.ERROR_MESSAGE);
            
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
     * separado por comas, devolviendo un ArrayList de estados
     *
     * @param strEstados
     * @return
     */
    private ArrayList<Estado> obtenerEstados(String strEstados) {
        ArrayList array = subStringComa(strEstados, 1);
        ArrayList<Estado> retorno = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Estado estado = new Estado();
            estado.setId(i + 1);
            estado.setPosEstado(i);
            estado.setData((String) array.get(i));
            retorno.add(i, estado);
        }
        return retorno;
    }

    /**
     * obtiene los simbolos a partir de un substring donde cada simbolo esta
     * separado por comas, devolviendo un HashMap
     *
     * @param strLenguaje
     * @return
     */
    private ArrayList<String> obtenerLenguaje(String strLenguaje) {
        return subStringComa(strLenguaje, 1);
    }

    /**
     * obtiene los estados iniciales a partir de un substring donde cada estado
     * inicial estan separado por comas, devolviendo un HashMap
     *
     * @param strEstadosIniciales
     * @return
     */
    private ArrayList<String> obtenerEstadosIniciales(String strEstadosIniciales) {
        ArrayList<String> array = subStringComa(strEstadosIniciales, 1);
        return array;
    }

//    /**
//     * obtiene los estados de aceptacion a partir de un substring donde cada
//     * estado esta separado por comas, devolviendo un HashMap
//     *
//     * @param strEstadosAceptacion
//     * @return
//     */
//    private HashMap obtenerEstadosAceptacion(String strEstadosAceptacion) {
//        return subStringComa(strEstadosAceptacion);
//    }
    /**
     * obtiene los estados de aceptacion a partir de un substring donde cada
     * estado esta separado por comas, devolviendo un ArrayList de Estados
     *
     * @param strEstadosAceptacion
     * @return
     */
    private ArrayList<String> obtenerEstadosAceptacion(String strEstadosAceptacion) {
        ArrayList<String> array = subStringComa(strEstadosAceptacion, 1);

        return array;
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


    private void construirTransiciones(String hilera, ArrayList<Estado> estados, AutomataFinito af) {
        ArrayList<String> transiciones = splitTransiciones(hilera);
        for (int i = 0; i < transiciones.size(); i++) {
            ArrayList<String> transicionArrayList = subStringComa(transiciones.get(i), 0);
            Estado estadoAct;
            String estadoActual = transicionArrayList.get(0);
            int pos = metodos.buscarEstado(estados, estadoActual);
            estadoAct = af.getEstado(pos);

            Estado estadoSig;
            String estadoSiguiente = transicionArrayList.get(2);
            pos = metodos.buscarEstado(estados, estadoSiguiente);
            estadoSig = af.getEstado(pos);

            String simbolo = transicionArrayList.get(1);
            pos = metodos.buscarSimbolo(af.getSimbolos(), simbolo);
            estadoAct.addTransicion(estadoSig, pos);
           
        }
      

    }

    public Object[][] obtenerMatrizTransiciones(int value) {
        if (value == 1) {
            return metodos.obtenerTransiciones(af);
        } else if (value == 2) {
            return metodos.obtenerTransiciones(af2);
        } else {
            return null;
        }
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

    public void agregarEstado(String stEstado) {
        Estado estado = new Estado();
        if (selector == 1) {
            estado.setId(posEstadoA);
            estado.setData(stEstado);
            posEstadoA++;
            af.agregarEstado(estado);
        } else if (selector == 2) {
            estado.setId(posEstadoB);
            estado.setData(stEstado);
            posEstadoB++;
            af2.agregarEstado(estado);
        }

    }

    public ArrayList<Estado> obtenerEstados() {
        if (selector == 1) {
            return af.getEstados();
        } 
        else if (selector == 2) {
            return af2.getEstados();
        } else {
            return null;
        }

    }

    public ArrayList<String> obtenerEstadosString() {

        if (selector == 1) {
            return af.obtenerEstadosString();
        } else if (selector == 2) {
            return af2.obtenerEstadosString();
        } else {
            return null;
        }
    }

    public ArrayList<Estado> obtenerEstados2() {
        if (af2 != null) {
            return af2.getEstados();
        } else {
            return null;
        }
    }

    public void agregarSimbolo(String simbolo) {
        if (selector == 1) {
            af.agregarSimbolo(simbolo);
            posSimboloA++;
        } else if (selector == 2) {
            af2.agregarSimbolo(simbolo);
            posSimboloB++;
        }
    }

    public ArrayList<String> obtenerSimbolos() {
        if (af != null) {
            return af.getSimbolos();
        } else {
            return null;
        }

    }

    public ArrayList<String> obtenerSimbolos2() {
        if (af2 != null) {
            return af2.getSimbolos();
        } else {
            return null;
        }
    }

    public boolean agregarEstadoInicial(String estadoInicial) {
        if (selector == 1) {
            if (af instanceof AFD) {
                if (af.tamEstadosIniciales() == -1) {
                    int posicion = metodos.buscarEstado(af.getEstados(), estadoInicial);
                    af.addEstadoInicial(posicion);
                    return true;

                } else {
                    JOptionPane.showMessageDialog(null, "No puedo agregar más estados iniciales");
                    return false;
                }

            } else if (af instanceof AFND) {
                int posicion = metodos.buscarEstado(af.getEstados(), estadoInicial);
                af.addEstadoInicial(posicion);
                return true;
            } else {
                return false;
            }

        } else if (selector == 2) {

            if (af2 instanceof AFD) {
                if (af2.tamEstadosIniciales() == -1) {
                    int posicion = metodos.buscarEstado(af2.getEstados(), estadoInicial);
                    af2.addEstadoInicial(posicion);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No puedo agregar más estados iniciales");
                    return false;
                }

            } else if (af2 instanceof AFND) {
                int posicion = metodos.buscarEstado(af2.getEstados(), estadoInicial);
                af2.addEstadoInicial(posicion);

                return true;
            } else {
                return true;

            }
        } else {
            return false;
        }

    }

    public void agregarEstadoAceptacion(String estadoAceptacion) {
        if (selector == 1) {
            int posicion = metodos.buscarEstado(af.getEstados(), estadoAceptacion);
            af.addEstadoAceptacion(posicion);
        } else if (selector == 2) {
            int posicion = metodos.buscarEstado(af2.getEstados(), estadoAceptacion);
            af2.addEstadoAceptacion(posicion);
        }
    }

    public void agregarTransicion(String estadoActual, String simbolo, String nuevoEstado) {

        if (selector == 1) {
            Estado estadoAct = af.getEstado(metodos.buscarEstado(af.getEstados(), estadoActual));
            Estado nuevoEst = af.getEstado(metodos.buscarEstado(af.getEstados(), nuevoEstado));
            int posSimbolo = metodos.buscarSimbolo(af.getSimbolos(), simbolo);
            estadoAct.addTransicion(nuevoEst, posSimbolo);

        } else if (selector == 2) {
            Estado estadoAct = af2.getEstado(metodos.buscarEstado(af2.getEstados(), estadoActual));
            Estado nuevoEst = af2.getEstado(metodos.buscarEstado(af2.getEstados(), nuevoEstado));
            int posSimbolo = metodos.buscarSimbolo(af2.getSimbolos(), simbolo);
            estadoAct.addTransicion(nuevoEst, posSimbolo);

        }
        // af.agregarTransicion(estadoActual, simbolo, nuevoEstado);
    }

    public void convertirAF() {
        if (selector == 1) {
            if (af instanceof AFND) {
                af = af.convertir(false);
            } else {
                JOptionPane.showMessageDialog(null, "Ya es un automata deterministico", "X", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            if (af2 instanceof AFND) {
                af = af2.convertir(false);
            } else {
                JOptionPane.showMessageDialog(null, "Ya es un automata deterministico", "X", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    public void simplificar() {

        af.simplificar();
    }

    public String obtenerAutomata() {
        AutomataFinito afAux = null;
        String automata = null;
        if (selector == 2) {
            afAux = this.af;
            this.af = af2;
        }
        if (af != null) {
            automata = "{";

            ArrayList<Estado> estados = af.getEstados();
            ArrayList<String> simbolos = af.getSimbolos();
            //        

            automata = automata + "[";
            automata = automata + af.toStringEstados(',');
        
            automata = automata + "][";

            automata = automata + af.toStringSimbolos(',');
            automata = automata + "][";

            automata = automata + af.toStringTransiciones('(', ')');

            automata = automata + "][";
            for (int i = 0; i < estados.size(); i++) {
                Estado estado = estados.get(i);
                if (estado.isEstadoInicial()) {
                    automata = automata + estados.get(i).getData();
                    if (i < estados.size() - 1) {
                        automata = automata + ",";
                    }
                }
            }

            automata = automata + "][";
            for (int i = 0; i < estados.size(); i++) {
                Estado estado = estados.get(i);
                if (estado.isEstadoAcep()) {
                    automata = automata + estados.get(i).getData();
                    if (i < estados.size() - 1) {
                        automata = automata + ",";
                    }
                }

            }
            automata = automata + "]}";
            if (selector == 2) {
                this.af = afAux;
            }
        }
        return automata;
    }

    public ArrayList<String> obtenerEstadosAceptacion() {
        ArrayList<Estado> estados = af.getEstados();
        ArrayList<String> estadosAceptacion = new ArrayList<>();

        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            if (estado.isEstadoAcep()) {
                estadosAceptacion.add(estado.getData());
            }
        }
        return estadosAceptacion;
    }

    public ArrayList<String> obtenerEstadosInicial() {
        ArrayList<Estado> estados = af.getEstados();
        ArrayList<String> estadosIniciales = new ArrayList<>();

        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            if (estado.isEstadoInicial()) {
                estadosIniciales.add(estado.getData());
            }
        }
        return estadosIniciales;
    }

    public ArrayList<String> obtenerEstadosAceptacion2() {
        if (af2 != null) {
            ArrayList<Estado> estados = af2.getEstados();
            ArrayList<String> estadosAceptacion = new ArrayList<>();

            for (int i = 0; i < estados.size(); i++) {
                Estado estado = estados.get(i);
                if (estado.isEstadoAcep()) {
                    estadosAceptacion.add(estado.getData());
                }
            }
            return estadosAceptacion;
        } else {
            return null;
        }
    }

    public ArrayList<String> obtenerEstadosInicial2() {
        if (af2 != null) {
            ArrayList<Estado> estados = af2.getEstados();
            ArrayList<String> estadosIniciales = new ArrayList<>();

            for (int i = 0; i < estados.size(); i++) {
                Estado estado = estados.get(i);
                if (estado.isEstadoInicial()) {
                    estadosIniciales.add(estado.getData());
                }
            }
            return estadosIniciales;
        } else {
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
        return true;
    }

    public String identificar() {
        AutomataFinito afAux = null;
        if (selector == 2) {
            afAux = af;
            af = af2;
        }
        if (af != null) {

            if (af instanceof AFD) {
                if (selector == 2) {
                    af = afAux;
                }

                return "AFD";
            } else if (af instanceof AFND) {
                if (selector == 2) {
                    af = afAux;
                }
                return "AFND";
            } else {
                if (selector == 2) {
                    af = afAux;
                }
                return "-";
            }

        } else {
            if (selector == 2) {
                af = afAux;
            }
            return "-";
        }

    }

    public void seleccionarAfA() {
        if (selector != 1) {
            this.selector = 1;
        }
    }

    public void seleccionarAfB() {
        if (selector != 2) {
            this.selector = 2;
        }
    }

    /**
     * metodo que valida cual automata esta seleccionado para contruir el
     * automata
     *
     * @param af
     */
    private void selectorAF(AutomataFinito af) {
        if (this.selector == 1) {
            this.af = af;
        } else if (this.selector == 2) {
            this.af2 = af;
        } else {
            this.af = null;
        }
    }

    public int getSelector() {
        return this.selector;
    }

    public AutomataFinito getAutomataFinitoA() {
        return af;
    }

    public AutomataFinito getAutomataFinitoB() {
        return af2;
    }

    public void reiniciarVariables() {
        this.contadorEstados = 0;
        this.contadorSimbolos = 0;
        this.selector = 1;
        af = null;
        posEstadoA = 1;
        posEstadoB = 2;
    }

    public String identificarA() {
        return identificar();
    }

    public String identificarB() {

        if (af2 != null) {
            if (af2 instanceof AFD) {
                return "AFD";
            } else if (af2 instanceof AFND) {
                return "AFND";
            } else {
                return "-";
            }
        } else {
            return "-";
        }

    }

    public void eliminarEstadoInicial(String estado) {
        if (selector == 1) {
            af.eliminarEstadoInicial((metodos.buscarEstado(af.getEstados(), estado)));
        } else {
            af2.eliminarEstadoInicial((metodos.buscarEstado(af2.getEstados(), estado)));
        }
    }

    public void eliminarEstadoAceptacion(String estado) {
        if (selector == 1) {
            af.eliminarEstadoAceptacion((metodos.buscarEstado(af.getEstados(), estado)));
        } else {
            af2.eliminarEstadoAceptacion((metodos.buscarEstado(af2.getEstados(), estado)));
        }
    }

    public boolean unirAutomatas() {
        if(af != null && af2 != null)
        {
           return true;
        }
        else
        {
            return false;
        }
    }

    public boolean intersectar() {
        if(af != null && af2 != null)
        {
           return true;
        }
        else
        {
            return false;
        }
    }

    public void generarImagen() throws IOException {
        metodos.generarImagen(obtenerAutomata());    
    }
}
