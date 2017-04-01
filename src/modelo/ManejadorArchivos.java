/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  @author Yaqueline
 * @author Alejandro
 */
public class ManejadorArchivos {

    //separador por default de la clase ManejadorArchivos
    private String separator = ";";

    public ManejadorArchivos(String separador) {
        this.separator = separador;
    }

    /**
     * la siguiente funcion busca si un caracter esta en la linea de un texto
     * que estan separados por ":;" y retorna true si se encuentra alli
     *
     * @param linea linea completa del archivo
     * @param cadena es la cadena que se va buscar en linea
     * @return true si encuentra la cadena, false si nó
     */
    public boolean buscarCandenaUsuarios(String linea, String cadena) {
        boolean validador = false;
        StringTokenizer stringTokenizer = new StringTokenizer(linea, separator);

        while (stringTokenizer.hasMoreElements()) {
            String dato = stringTokenizer.nextToken();
            if (dato.contains(cadena)) {
                validador = true;
                return validador;
            }

        }

        return validador;
    }
/**
 * la siguiente funcion es un contador de lineas de un archivo txt
 * @param nombre ruta del archivo junto con su nombre
 * @return int cantidad de lineas del archivo
 * @throws FileNotFoundException 
 */
    public int getCantidadLineas(String nombre) throws FileNotFoundException {

        int contador = 0;
        FileInputStream fstream = null;
        DataInputStream dstream = null;
        BufferedReader bf = null;

        try {
            fstream = new FileInputStream(nombre);
            dstream = new DataInputStream(fstream);
            bf = new BufferedReader(new InputStreamReader(dstream));

            while (bf.readLine() != null) {
                contador++;
            }

            fstream.close();
            dstream.close();
            bf.close();

        } catch (Exception e2) {
            e2.printStackTrace();

        }

        return contador;
    }

    /**
     * la siguiente funcion recorre todo el texto y lo sube en un arreglo de
     * strings donde cada item son las lineas del archivo txt
     *
     * @param nombre nombre del archivo txt
     * @return vector con todas las entradas del archivo txt
     * @throws FileNotFoundException
     */
    public String[] getLineasTexto(String nombre) throws FileNotFoundException {
        String linea;
        String[] datos = null;
        int contador = 0;
        FileInputStream fstream = null;
        DataInputStream dstream = null;
        BufferedReader bf = null;

        try {
            fstream = new FileInputStream(nombre);
            dstream = new DataInputStream(fstream);
            bf = new BufferedReader(new InputStreamReader(dstream));

            while ((linea = bf.readLine()) != null) {
                contador++;
            }
            datos = new String[contador];

            fstream.close();
            dstream.close();
            bf.close();

            fstream = new FileInputStream(nombre);
            dstream = new DataInputStream(fstream);
            bf = new BufferedReader(new InputStreamReader(dstream));

            for (int j = 0; j < contador; j++) {
                datos[j] = bf.readLine();
            }

            fstream.close();
            dstream.close();
            bf.close();

        } catch (Exception e2) {
            e2.printStackTrace();

        }
        return datos;
    }

    /**
     * la siguiente funcion abre un archivo txt e inserta una linea al final del
     * documento txt
     *
     * @param linea contiene Sting a insertar en el archivo
     * @param nombre es el nombre y la ruta del archivo que se va escribir
     */
    void escribirArchivoAlFinal(String linea, String nombre) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {

            fichero = new FileWriter(nombre, true);
            pw = new PrintWriter(fichero);

            pw.println(linea);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
/**
 * este mentodo borra la linea n  e inserta una nueva linea en la linea n del
 * archivo de texto
 * @param n linea n a borrar
 * @param linea linea que se inserta en la posicion n
 * @param nombre es el nombre y la ruta del archivo que se va escribir
 */
    public void reemplazarLinea(int n, String linea, String nombre) {
        File f;

        try {
            String[] s = getLineasTexto(nombre);

            f = new File(nombre);
            f.delete();

            for (int i = 0; i < s.length; i++) {
                if (i != n) {
                    escribirArchivoAlFinal(s[i], nombre);
                } else {
                    escribirArchivoAlFinal(linea, nombre);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String buscarPalabra(String cadena, String nombre)
    {
        String linea = null;
        String[] lineas = null;
        
        try {
            
            lineas = getLineasTexto(nombre);
            
            for (int i = 0; i < lineas.length; i++) {
                if (buscarCandenaUsuarios(lineas[i], cadena)) {
                    linea = lineas[i];
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return linea;
    }
    
    public void borrarArchivo(String nombre)
    {
        File f = new File(nombre);
        f.delete();
    }

}