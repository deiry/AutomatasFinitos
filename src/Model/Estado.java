/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author DEIRY
 */
public class Estado {

    private String data;
    private boolean estadoAcep, estadoInicial;
    private int particion, posEstado, tamDatos, id;
    private Estado[] transiciones;
    private LinkedList<Estado> listDatos;

    public Estado() {
        estadoAcep = false;
        estadoInicial = false;
        transiciones = new Estado[10];
        listDatos = new LinkedList();
        particion = 0;
        posEstado = 0;
        tamDatos = 1;
        data = "";
        id = 0;
    }

    public void setSimbolo(int i) {
        this.transiciones = new Estado[i];
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void actualizarDatos() {
        Iterator<Estado> it = listDatos.iterator();
        this.data = "";
        this.id = 0;
        if (listDatos.size() > 1) {
            while (it.hasNext()) {
                Estado next = it.next();
                this.data += next.getData();
                this.id += next.getId();
            }
            this.id = this.id * 100;
            tamDatos = listDatos.size();
        } else {
            data = listDatos.getFirst().getData();
            id = listDatos.getFirst().getId();

        }

    }

    public boolean isEstadoAcep() {
        return estadoAcep;
    }

    public void setEstadoAcep(boolean estadoAcep) {
        this.estadoAcep = estadoAcep;
        if (estadoAcep) {
            this.particion = 1;
        }
    }

    public boolean isEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(boolean estadoInicial) {
        this.estadoInicial = estadoInicial;
        if (estadoInicial) {
            this.particion = 0;
        }
    }

    public int getParticion() {
        return particion;
    }

    public void setParticion(int particion) {
        this.particion = particion;
    }

    public int getPosEstado() {
        return posEstado;
    }

    public void setPosEstado(int posEstado) {
        this.posEstado = posEstado;
    }

    public int getTamDatos() {
        return tamDatos;
    }

    public void setTamDatos(int tamDatos) {
        this.tamDatos = tamDatos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransicion(Estado est, int simbolo) {
        transiciones[simbolo] = est;
    }

    public void addTransicion(Estado nEst, int simbolo) {

        Estado tranS = transiciones[simbolo];
        if (tranS != null && nEst != null) {

            transiciones[simbolo] = AF.unionEstados(tranS, nEst);
        } else {
            transiciones[simbolo] = nEst;
        }

    }

    public Estado getTransicion(int simbolo) {
        if (transiciones[simbolo] == null) {
            return null;
        }

        return transiciones[simbolo];

    }

    public Estado[] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Estado[] transiciones) {
        this.transiciones = transiciones;
    }

    public LinkedList<Estado> getListDatos() {
        return listDatos;
    }

    public void setListDatos(LinkedList<Estado> listDatos) {
        this.listDatos = listDatos;
    }

    public Estado copyEstado() {
        Estado copy = new Estado();

        copy.setEstadoAcep(estadoAcep);
        copy.setEstadoInicial(estadoInicial);
        copy.setParticion(particion);
        copy.setTamDatos(tamDatos);
        copy.setTransiciones(transiciones);
        copy.setListDatos(listDatos);
        copy.setId(id);
        return copy;
    }

    public String toStringData(char div) {

        Iterator<Estado> iter = listDatos.iterator();
        Estado node;
        String todo = this.data;
        while (iter.hasNext()) {
            node = iter.next();
            todo += node.getData() + div;
        }
        return todo;
    }

    public void addListData(Estado dato) {

        if (dato.getTamDatos() == 1 && !perteneceListDatos(dato.getId())) {
            listDatos.add(dato);
        } else {
            Iterator<Estado> it = dato.getListDatos().iterator();
            while (it.hasNext()) {
                Estado next = it.next();
                if (!perteneceListDatos(next.getId())) {
                    listDatos.add(next);

                }
            }

        }
        this.actualizarDatos();

    }

    public ArrayList<String> stringListDatos() {
        ArrayList<String> list = new ArrayList<>();
        Iterator<Estado> iter = listDatos.iterator();
        while (iter.hasNext()) {
            Estado next = iter.next();
            list.add(next.getData());

        }
        return list;
    }

    public boolean perteneceListDatos(int id) {
        Iterator<Estado> it = listDatos.iterator();
        while (it.hasNext()) {
            Estado next = it.next();
            if (next.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int getIdList() {
        int idList = 0;
        Iterator<Estado> it = listDatos.iterator();
        while (it.hasNext()) {
            Estado next = it.next();
            idList += next.getId();
        }
        return idList * 100;
    }

    public void addEstado(Estado estado) {

        this.addListData(estado);
        this.setEstadoAcep(estado.isEstadoAcep() == true);

    }

    public boolean isMasTransicion(int simbolo) {
        try {
            return transiciones[simbolo] != null;

        } catch (ArrayIndexOutOfBoundsException excepcion) {
            return false;
        }

    }

    public Estado getDataFirst() {
        return listDatos.getFirst();
    }

    public String getDataList() {
        Iterator<Estado> iter = listDatos.iterator();
        Estado node;
        String todo = this.data;
        while (iter.hasNext()) {
            node = iter.next();
            todo += node.getData();
        }
        return todo;
    }

    public int getParticionToSimbolo(int i) {
        if (this.getTransicion(i) == null) {

        }
        return getTransicion(i).getParticion();
    }

    public String toStringTransicion(int simbolo, char sep, String simb) {
        String tran = this.data;
        if (getTransicion(simbolo) == null) {
            return "";
        }
        tran = tran + sep + simbolo + sep + getTransicion(simbolo).getData();
        return tran;

    }

}
