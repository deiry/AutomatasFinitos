/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author DEIRY
 */
public class AFD extends AutomataFinito {

    private int estadoInicial;
    private ArrayList<ArrayList<Integer>> particiones;

    public int getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(int estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public AFD simplificar() {
        AFD afSimplificado = new AFD();
        ArrayList<Estado> nuevosEstados;

        int[] visitado = new int[estados.size()];
        analizarEstados(getEstado(estadoInicial), visitado);
        nuevosEstados = eliminarEstadosExtranos(visitado);
        this.crearParticiones(nuevosEstados);
        imprimirParticiones();

        construirVectorTransiciones(0, 0);

        afSimplificado.setEstados(nuevosEstados);
        System.out.println("");
        afSimplificado.imprimirEstados();
        return this;
    }

    public int numeroParticiones() {
        return particiones.size();
    }

    public boolean validarNuevasParticiones(int[] tranPart, int particion) {

        int posReferencia = tranPart[0];
        ArrayList<Integer>[] separar = new ArrayList[numeroParticiones()];
        ArrayList<Integer> aux;
        boolean bandera = false;
        int [] numeroPart = new int[numeroParticiones()];

        for (int i = 1; i < tranPart.length; i++) {
            int tran = tranPart[i];
            if (separar[tran] == null) {
                aux = new ArrayList<>();
                aux.add(i);
                separar[tran] = aux;
            } else {
                aux = separar[tran];
                aux.add(i);
            }
        }
        return bandera;
    }

    public void agregarParticion(int nuevaPart) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(nuevaPart);
        particiones.add(list);

    }

    public void eliminarParticion(int particion, int posParticion) {

        ArrayList<Integer> part = this.particiones.get(particion);
        part.remove(posParticion);
    }

    public int[] construirVectorTransiciones(int postParticiones, int simbolo) {
        ArrayList<Integer> particion = this.particiones.get(postParticiones);
        int[] tranPart;
        tranPart = new int[particion.size()];
        int posTran = 0;

        if (particion.size() > 1) {
            for (Integer pos : particion) {
                tranPart[posTran] = super.getEstado(pos).getParticionToSimbolo(simbolo);
                posTran++;
            }
        } else {
            int pos = particion.get(0);
            tranPart[posTran] = super.getEstado(pos).getParticionToSimbolo(simbolo);

        }
        AF.imprimirVector(tranPart);
        return tranPart;
    }

    public void analizarEstados(Estado estado, int[] visitado) {

        visitado[estado.getPosEstado()] = 1;
        Estado estadoTran;
        for (int i = 0; i < simbolos.size(); i++) {
            estadoTran = estado.getTransicion(i);
            if (estadoTran != null && visitado[estadoTran.getPosEstado()] != 1) {
                analizarEstados(estadoTran, visitado);
            }

        }
    }

    public ArrayList<Estado> eliminarEstadosExtranos(int[] visitado) {
        int nuevaPos = 0;
        ArrayList<Estado> nuevosEstados;
        nuevosEstados = new ArrayList<>();
        for (int i = 0; i < visitado.length; i++) {
            if (visitado[i] == 1) {
                Estado est = this.estados.get(i);
                est.setPosEstado(nuevaPos);
                nuevosEstados.add(nuevaPos, est);
                nuevaPos++;
            }
        }
        return nuevosEstados;
    }

    public void crearParticiones(ArrayList<Estado> estados) {
        particiones = new ArrayList<>();
        ArrayList<Integer> part0 = new ArrayList<>();
        ArrayList<Integer> part1 = new ArrayList<>();
        for (Estado est : estados) {
            if (est.getParticion() == 0) {
                part0.add(est.getPosEstado());
            }else if(est.getParticion() == 1){
                part1.add(est.getPosEstado());
            }
           
        }
        particiones.add(part0);
        particiones.add(part1);

    }

    public void imprimirParticiones() {
        System.out.println("Particiones");
        for (int i = 0; i < particiones.size(); i++) {
            System.out.print(particiones.get(i) + " ");
        }
    }

}
