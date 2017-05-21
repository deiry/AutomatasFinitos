/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author Alejandro
 */
public class MetodosControlador {

    public MetodosControlador() {
    }

    /**
     * Busca a que estado le pertenece ese dato y lo retorna el id del estado
     *
     * @param estados
     * @param strEstadoInicial
     * @return
     */
    public int buscarEstado(ArrayList<Estado> estados, String strEstadoInicial) {
        int retorno = 0;
        for (int i = 0; i < estados.size(); i++) {
            Estado estado = estados.get(i);
            if (estado.getData().equals(strEstadoInicial)) {
                retorno = estado.getPosEstado();
            }
        }
        return retorno;
    }

    public int buscarSimbolo(ArrayList<String> simbolos, String simbolo) {
        return simbolos.indexOf(simbolo);
    }

    public Object[][] obtenerTransiciones(AutomataFinito automata) {
        ArrayList<Estado> estados = automata.getEstados();
        ArrayList<String> simbolos = automata.getSimbolos();
        Object[][] matriz = new Object[estados.size()][simbolos.size()+1];
        for (int i = 0; i < estados.size(); i++) {
            Estado aux = estados.get(i);
            for (int j = 0; j < simbolos.size(); j++) {
                if(j==0)
                {
                    matriz[i][j] = estados.get(i).getData();
                }
                if (aux.getTransicion(j) != null) {
                    matriz[i][j+1] = aux.getTransicion(j).getData();
                }
                else{
                    matriz[i][j+1] = " ";
                }
            }
        }
        return matriz;
    }
    
    public void generarImagen(String text) throws FileNotFoundException, IOException
    {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 48);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.CYAN);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File("C:\\temp\\Text.png"));
            Desktop.getDesktop().open(new File("C:\\temp\\Text.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //Desktop.getDesktop().open(file);
    }
}
