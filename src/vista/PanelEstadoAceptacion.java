/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Model.Estado;
import Model.MetodosControlador;
import controlador.Controlador;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author Alejandro
 */
public class PanelEstadoAceptacion extends javax.swing.JPanel {

    private Controlador controlador;
    /**
     * Creates new form PanelEstados
     */
    public PanelEstadoAceptacion() {
        initComponents();
        controlador = Controlador.getInstance();
        actualizarEstados();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jp_estados = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(1, 2));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(3, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccionar Estado Aceptación");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1);

        jp_estados.setBackground(new java.awt.Color(255, 255, 255));
        jp_estados.setLayout(new java.awt.GridLayout(3, 8));
        jPanel2.add(jp_estados);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 703, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 164, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarEstados() {
        ArrayList<String> estados = controlador.obtenerEstadosString();
        ArrayList<Estado> stdEstados = controlador.obtenerEstados();
        jp_estados.removeAll();
        jp_estados.revalidate();
        
        if(estados != null)
        {
            controlador.boolEstadosAceptacion = new boolean[estados.size()];
            jp_estados.setLayout(new GridLayout(1, estados.size()));
            for (int i=0; i<estados.size();i++) {
                String key = estados.get(i);
                
                controlador.boolEstadosAceptacion[i] = true;
                JButton btn = new JButton(key);
                btn.setForeground(new  java.awt.Color(255, 255, 255));
                btn.setBackground(new Color(58,171,169));
                
                if (stdEstados.get(i).isEstadoAcep()) {
                    btn.setBackground(Color.gray);
                    btn.setForeground(new  java.awt.Color(255, 255, 255));
                    controlador.boolEstadosAceptacion[i] = false;
                }
                
                ActionListener l = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Controlador controlador = Controlador.getInstance();
                        MetodosControlador metodos = new MetodosControlador();
                        String estado = btn.getText();
                        int pos = metodos.buscarEstado(controlador.obtenerEstados(), estado);
                        
                        if (controlador.boolEstadosAceptacion[pos]) {
                            controlador.agregarEstadoInicial(estado);                            
                            btn.setBackground(Color.gray);
                            btn.setForeground(new  java.awt.Color(255, 255, 255));
                            controlador.boolEstadosInciales[pos] = false;      
                        }
                        else
                        {
                            controlador.eliminarEstadoAceptacion(estado);
                            btn.setForeground(new  java.awt.Color(255, 255, 255));
                            btn.setBackground(new Color(58,171,169));
                            controlador.boolEstadosInciales[pos] = true;

                        }
                        
                        //btn.setBackground(new Color(58,171,169));
                    }
                };
                btn.addActionListener(l);
                jp_estados.add(btn);
            }
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jp_estados;
    // End of variables declaration//GEN-END:variables
}
