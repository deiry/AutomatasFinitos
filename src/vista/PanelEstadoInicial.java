/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author Alejandro
 */
public class PanelEstadoInicial extends javax.swing.JPanel {

    private controlador.Controlador controlador;
    /**
     * Creates new form PanelEstados
     */
    public PanelEstadoInicial() {
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 2));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(3, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccionar Estado Inicial");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1);

        jp_estados.setBackground(new java.awt.Color(255, 255, 255));
        jp_estados.setLayout(new java.awt.GridLayout(3, 8));

        jButton1.setText("UPCI");
        jp_estados.add(jButton1);

        jButton2.setText("jButton2");
        jp_estados.add(jButton2);

        jButton3.setText("jButton3");
        jp_estados.add(jButton3);

        jButton4.setText("jButton4");
        jp_estados.add(jButton4);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jp_estados;
    // End of variables declaration//GEN-END:variables

    private void actualizarEstados() {
        HashMap<String,Integer> estados = controlador.obtenerEstados();
        jp_estados.removeAll();
        jp_estados.revalidate();
        
        if(estados != null)
        {
            
            jp_estados.setLayout(new GridLayout(1, estados.size()));
            for (Map.Entry<String, Integer> entry : estados.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                JButton btn = new JButton(key);
                ActionListener l = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String estado = btn.getText();
                        controlador.agregarEstadoInicial(estado);
                        btn.setEnabled(false);
                        btn.setBackground(Color.gray);
                        btn.setForeground(new  java.awt.Color(255, 255, 255));
                        //btn.setBackground(new Color(58,171,169));
                    }
                };
                btn.addActionListener(l);
                jp_estados.add(btn);
            } 
        }
        
    }
}
