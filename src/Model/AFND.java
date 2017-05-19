/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author DEIRY
 */
public class AFND extends AutomataFinito {

    private final ArrayList<Integer> estadosInciales;
    private final ArrayList<Estado> estadosFinalesAFD;

    public AFND() {
        estadosInciales = new ArrayList<>();
        estadosFinalesAFD = new ArrayList<>();

    }

    public void addEstadoInicial(int posEstado) {
        estadosInciales.add(posEstado);
    }

    public Estado getEstadoInicial(int posEstado) {

        return estados.get(estadosInciales.get(posEstado));
    }

    @Override
    public void agregarEstado(int posEstado, Estado estado) {
        super.agregarEstado(posEstado, estado);
        if (estado.isEstadoInicial()) {
            addEstadoInicial(posEstado);
        }
    }

    public AFD convertir() {
        Estado estadoN = this.getEstadoInicial(0);
        estadoN.setPosEstado(0);
        estadosFinalesAFD.add(0, estadoN);
        agregarNuevasTransiciones(estadoN);
        AFD afd = new AFD();

        AF.igualTransiciones(estadosFinalesAFD, simbolos.size());
        this.convertirEstadosDeterministico(estadosFinalesAFD);
        afd.setEstados(estadosFinalesAFD);
        afd.setSimbolos(simbolos);
        afd.setEstadoInicial(AF.buscarEstadoInicial(estadosFinalesAFD));
        return afd;
    }

    public void agregarNuevasTransiciones(Estado estado) {
        Estado nuevoE;
        for (int simbolo = 0; simbolo < simbolos.size(); simbolo++) {
            if (estado.getTamDatos() == 1) {
                Estado tran = estado.getTransicion(simbolo);
                if (tran != null) {
                    nuevoE = tran;
                } else {
                    nuevoE = null;
                }
            } else {
                nuevoE = AF.unirTransiciones(estado, simbolo);
            }

            if ((nuevoE != null) && !AF.perteneceA(estadosFinalesAFD, nuevoE)) {
                int pos = estadosFinalesAFD.size();
                nuevoE.setPosEstado(pos);
                estadosFinalesAFD.add(pos, nuevoE);
                agregarNuevasTransiciones(nuevoE);
            } else if (nuevoE != null) {
                nuevoE = AF.igualEstado(estadosFinalesAFD, nuevoE.getId());
            }

            estado.addTransicion(nuevoE, simbolo);
        }
    }

    public void convertirEstadosDeterministico(ArrayList<Estado> estadosAFD) {
        int idE = 1;
        for (Estado estado : estadosAFD) {
            estado.setListDatos(new LinkedList<>());
            estado.setTamDatos(1);
            estado.setId(idE);
            idE++;

        }
    }

    public void addEstadosFinalesAFD(Estado estado) {
        estadosFinalesAFD.add(estadosFinalesAFD.size(), estado);
    }

}
