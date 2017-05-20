/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        int s = -1;
        int[] tranPart;
        boolean cambio = false;
        while (s < simbolos.size()) {
            s++;
            for (int i = 0; i < particiones.size(); i++) {
                ArrayList<Integer> particion = particiones.get(i);
                if (particion.size() > 1) {
                    tranPart = construirVectorTransiciones(i, s);
                    if (validarNuevasParticiones(tranPart, i)) {
                        s = -1;
                        cambio = true;
                        break;

                    }
                    if (cambio) {
                        break;
                    }
                }
                if (cambio) {
                    break;
                }
            }
        }
       

        nuevosEstados = unirEstadosParticiones();

        this.unirTransiciones(nuevosEstados);
        afSimplificado.setEstados(nuevosEstados);
        afSimplificado.setSimbolos(simbolos);
        System.out.println("");

        return afSimplificado;
    }

    public ArrayList<Estado> unirEstadosParticiones() {
        Estado aux;
        ArrayList<Estado> nuevosEstados = new ArrayList<>();
        int posEstado = 0;

        for (ArrayList<Integer> particion : particiones) {
            if (particion.size() > 0) {
                Estado estadoFinal = this.getEstado(particion.get(0));

                for (int i = 1; i < particion.size(); i++) {
                    aux = this.getEstado(particion.get(i));
                    estadoFinal = AF.unionEstados(estadoFinal, aux);

                }
                estadoFinal.setPosEstado(posEstado);
                nuevosEstados.add(posEstado, estadoFinal);
            }
        }
        this.imprimirEstados(nuevosEstados);
        return nuevosEstados;
    }

    public void unirTransiciones(ArrayList<Estado> estado) {

        for (Estado est : estado) {
            for (int i = 0; i < simbolos.size(); i++) {
                Estado tran = AF.unirTransiciones(est, i);
                est.setTransicion(tran, i);
            }
        }
        this.imprimirTransiciones(estado);
    }

    public int numeroParticiones() {
        return particiones.size();
    }

    public ArrayList[] separarParticiones(int[] tran) {
        ArrayList<Integer>[] separar = inicializarArray(numeroParticiones());

        for (int i = 0; i < tran.length; i++) {
            int partS = tran[i];
            separar[partS].add(i);
        }
        return separar;
    }

    public ArrayList<Integer>[] inicializarArray(int numero) {

        ArrayList<Integer>[] separar = new ArrayList[numero];
        for (int i = 0; i < separar.length; i++) {
            separar[i] = new ArrayList<>();
        }
        return separar;
    }

    public boolean validarNuevasParticiones(int[] tranPart, int particion) {
        boolean cambio = false;
        ArrayList<Integer>[] separar = separarParticiones(tranPart);
        int tam0 = separar[0].size();
        int tam1 = separar[1].size();
        int posRef;
        if (tam1 > tam0) {
            posRef = 1;
        } else {
            posRef = 0;
        }

        for (int i = 0; i < separar.length; i++) {
            if (i != posRef && separar[i].size() > 0) {
                cambio = true;
                this.agregarParticion(separar[i]);
                this.eliminarParticion(separar[i], particion);
            }
        }

        return cambio;
    }

    public void eliminarParticion(ArrayList<Integer> nuevaPar, int posParticion) {
        ArrayList<Integer> particion = this.particiones.get(posParticion);

        for (int i = 0; i < nuevaPar.size(); i++) {
            particion.remove(nuevaPar.get(i));
        }
    }

    public void agregarParticion(ArrayList<Integer> nuevaParticion) {
        particiones.add(nuevaParticion);
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
        try {
            if (particion.size() > 1 && !particion.isEmpty()) {
                for (Integer pos : particion) {
                    tranPart[posTran] = super.getEstado(pos).getParticionToSimbolo(simbolo);
                    posTran++;
                }
            } else if(!particion.isEmpty()){
                int pos = particion.get(0);
                tranPart[posTran] = super.getEstado(pos).getParticionToSimbolo(simbolo);

            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Construir vector Tran", "ERROR", JOptionPane.WARNING_MESSAGE);
        }

        return tranPart;
    }

    public void configurarIdDeterministico(ArrayList<Estado> estados) {
        int id = 1;
        for (Estado est : estados) {
            est.setId(id);
            id++;
        }
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
            } else if (est.getParticion() == 1) {
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
