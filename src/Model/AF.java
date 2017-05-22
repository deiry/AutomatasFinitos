/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author DEIRY
 */
public class AF {

    public static Estado unirTransiciones(Estado estado, int simbolo) {
        Estado nuevaTran = new Estado();
        Iterator<Estado> iter = estado.getListDatos().iterator();
        Estado auxTran;
        while (iter.hasNext()) {
            Estado node = iter.next();
            auxTran = node.getTransicion(simbolo);
            if (auxTran != null) {
                nuevaTran.addEstado(auxTran);
            }
        }
        return nuevaTran;
    }

    public static Estado unionEstados(Estado this1, Estado estado2) {
        Estado estadoU = new Estado();
        if (this1.isEstadoAcep() || estado2.isEstadoAcep()) {
            estadoU.setEstadoAcep(true);
        }

        estadoU.addListData(this1);
        estadoU.addListData(estado2);

        return estadoU;
    }

    public static Estado interseccionEstados(Estado this1, Estado estado2) {
        Estado estadoU = new Estado();
        if (this1.isEstadoAcep() && estado2.isEstadoAcep()) {
            estadoU.setEstadoAcep(true);
        }

        estadoU.addListData(this1);
        estadoU.addListData(estado2);

        return estadoU;
    }

    public static boolean perteneceA(ArrayList<Estado> estadosfinal, Estado nuevoE) {
     
         int idList = nuevoE.getId();

        for (int i = 0; i < estadosfinal.size(); i++) {
            Estado est = estadosfinal.get(i);
            if (est.getId() == idList) {
                return true;
            }
        }
        return false;
    }

    public static String validarRepetidosData(String data) {
        char[] arraycar = data.toCharArray();
        for (int i = 0; i < arraycar.length; i++) {
            for (int j = 0; j < arraycar.length - 1; j++) {
                if (i != j) {
                    if (arraycar[i] == arraycar[j]) {
                        arraycar[j] = ' ';
                    }
                }
            }
        }
        String s = new String(arraycar);
        s = s.replace(" ", "");

        return s;
    }

    public static Estado igualEstado(ArrayList<Estado> estadosfinal, int idNuevo) {
        Estado est;
        for (int i = 0; i < estadosfinal.size(); i++) {
            est = estadosfinal.get(i);
            if (est.getId() == idNuevo) {
                return est;
            }
        }
        return null;
    }

    public static int buscarEstadoInicial(ArrayList<Estado> estados) {

        for (Estado estado : estados) {
            if (estado.isEstadoInicial()) {
                return estado.getPosEstado();
            }
        }

        return 0;
    }

    public static void igualTransiciones(ArrayList<Estado> estados, int simbolos) {
        Estado estadoList;
        for (Estado estado : estados) {
            for (int i = 0; i < simbolos; i++) {
                estadoList = estado.getTransicion(i);

                if (estadoList != null) {
                    estadoList = AF.igualEstado(estados, estadoList.getId());
                    if (estadoList != null) {
                        estado.setTransicion(estadoList, i);
                    }

                }

            }
        }

    }

    public static void imprimirVector(int[] vec) {

        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + ",");
        }
    }

    public static AFND unionAutomatas(AutomataFinito afd1, AutomataFinito afd2) {
        AFND afnd = new AFND();
        int i = 0;
        for (int j = 0; j < afd1.getSizeEstados(); j++) {
            Estado est = afd1.getEstado(j);
            est.setPosEstado(i);
            afnd.agregarEstado(i, est);
            i++;
            if (est.isEstadoInicial()) {
                afnd.addEstadoInicial(j);
            }
        }

        for (int j = 0; j < afd2.getSizeEstados(); j++) {
            Estado est = afd2.getEstado(j);
            est.setPosEstado(i);
            afnd.agregarEstado(i, est);
            i++;
            if (est.isEstadoInicial()) {
                afnd.addEstadoInicial(j);
            }
        }

        afnd.imprimirEstados(afnd.getEstados());
        afnd.setSimbolos(afd1.getSimbolos());

        return afnd;
    }

    public static AFND interseccionAutomatas(AutomataFinito afd1, AutomataFinito afd2) {
        AFND afnd = new AFND();
        int i = 0;
        for (int j = 0; j < afd1.getSizeEstados(); j++) {
            Estado est = afd1.getEstado(j);
            est.setPosEstado(i);
            afnd.agregarEstado(i, est);
            i++;
            if (est.isEstadoInicial()) {
                afnd.addEstadoInicial(j);
            }
        }

        for (int j = 0; j < afd2.getSizeEstados(); j++) {
            Estado est = afd2.getEstado(j);
            est.setPosEstado(i);
            afnd.agregarEstado(i, est);
            i++;
            if (est.isEstadoInicial()) {
                afnd.addEstadoInicial(j);
            }
        }

        afnd.imprimirEstados(afnd.getEstados());
        afnd.setSimbolos(afd1.getSimbolos());
        return afnd;
    }
}
