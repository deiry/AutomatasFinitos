/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Controlador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Alejandro
 */
public class VistaPrincipal extends javax.swing.JFrame {

    private Controlador controlador;
    /**
     * Creates new form VistaPrincipal
     */
    public VistaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jp_modificar_automata = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        btn_vista_estados = new javax.swing.JButton();
        btn_vista_simbolos = new javax.swing.JButton();
        btn_vista_estado_incial = new javax.swing.JButton();
        btn_vista_estado_acpetacion = new javax.swing.JButton();
        btn_vista_transiciones = new javax.swing.JButton();
        jp_operaciones_automata = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        Convertir = new javax.swing.JButton();
        jp_contenedor_principal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jp_modificar_automata.setLayout(new java.awt.GridLayout(6, 1));

        jButton6.setText("Automata");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jp_modificar_automata.add(jButton6);

        btn_vista_estados.setText("Estados");
        btn_vista_estados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vista_estadosActionPerformed(evt);
            }
        });
        jp_modificar_automata.add(btn_vista_estados);

        btn_vista_simbolos.setText("Simbolos");
        btn_vista_simbolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vista_simbolosActionPerformed(evt);
            }
        });
        jp_modificar_automata.add(btn_vista_simbolos);

        btn_vista_estado_incial.setText("Estado Inicial");
        btn_vista_estado_incial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vista_estado_incialActionPerformed(evt);
            }
        });
        jp_modificar_automata.add(btn_vista_estado_incial);

        btn_vista_estado_acpetacion.setText("Estado Aceptación");
        btn_vista_estado_acpetacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vista_estado_acpetacionActionPerformed(evt);
            }
        });
        jp_modificar_automata.add(btn_vista_estado_acpetacion);

        btn_vista_transiciones.setText("Transiciones");
        btn_vista_transiciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vista_transicionesActionPerformed(evt);
            }
        });
        jp_modificar_automata.add(btn_vista_transiciones);

        jp_operaciones_automata.setLayout(new java.awt.GridLayout(1, 6));

        jButton1.setText("Abrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jp_operaciones_automata.add(jButton1);

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jp_operaciones_automata.add(jButton2);

        jButton3.setText("Identificar");
        jp_operaciones_automata.add(jButton3);

        jButton4.setText("Reconocer");
        jp_operaciones_automata.add(jButton4);

        jButton5.setText("Simplificar");
        jp_operaciones_automata.add(jButton5);

        Convertir.setText("Convertir");
        Convertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConvertirActionPerformed(evt);
            }
        });
        jp_operaciones_automata.add(Convertir);

        jp_contenedor_principal.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jp_modificar_automata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_operaciones_automata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_contenedor_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jp_operaciones_automata, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_modificar_automata, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addComponent(jp_contenedor_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_vista_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vista_estadosActionPerformed
        PanelEstados panelEstados = new PanelEstados();
        mostrarPanel(jp_contenedor_principal, panelEstados);
    }//GEN-LAST:event_btn_vista_estadosActionPerformed

    private void btn_vista_simbolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vista_simbolosActionPerformed
        PanelSimbolos panelSimbolos = new PanelSimbolos();
        mostrarPanel(jp_contenedor_principal, panelSimbolos);
    }//GEN-LAST:event_btn_vista_simbolosActionPerformed

    private void btn_vista_estado_incialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vista_estado_incialActionPerformed
        PanelEstadoInicial panelEstadoInicial = new PanelEstadoInicial();
        mostrarPanel(jp_contenedor_principal, panelEstadoInicial);
    }//GEN-LAST:event_btn_vista_estado_incialActionPerformed

    private void btn_vista_estado_acpetacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vista_estado_acpetacionActionPerformed
        PanelEstadoAceptacion panelEstadoAceptacion = new PanelEstadoAceptacion();
        mostrarPanel(jp_contenedor_principal, panelEstadoAceptacion);
    }//GEN-LAST:event_btn_vista_estado_acpetacionActionPerformed

    private void btn_vista_transicionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vista_transicionesActionPerformed
        PanelTransiciones panelTransiciones = new PanelTransiciones();
        mostrarPanel(jp_contenedor_principal, panelTransiciones);
    }//GEN-LAST:event_btn_vista_transicionesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String texto = abrirArchivo();
        controlador = Controlador.getInstance();
        controlador.construirAtomata(texto);
        PanelAutomata panelAutomata = new PanelAutomata();
        mostrarPanel(jp_contenedor_principal, panelAutomata);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String automata = controlador.obtenerAutomata();
        guardarArchivo(automata);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        PanelAutomata panelAutomata = new PanelAutomata();
        mostrarPanel(jp_contenedor_principal, panelAutomata);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void ConvertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConvertirActionPerformed
        
        Controlador ctrl = Controlador.getInstance();
        ctrl.convertirAF();
    }//GEN-LAST:event_ConvertirActionPerformed


    /**
     * metodo que sobrescribe el contendio de un panel para hacer las vistas
     * dinamicas
     * @param contenedor contenedor al que se le inyecta el nuevo panel
     * @param nuevoPanel panel a insertar
     */
    private void mostrarPanel(JPanel contenedor,JPanel nuevoPanel)
    {
        contenedor.removeAll();
        contenedor.revalidate();
        contenedor.add(nuevoPanel);
        nuevoPanel.setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPrincipal().setVisible(true);
            }
        });
    }
    
    private String abrirArchivo() 
    {
        String aux="";   
        String texto="";
        try
        {
         /**llamamos el metodo que permite cargar la ventana*/
         JFileChooser file=new JFileChooser();
         file.showOpenDialog(this);
         /**abrimos el archivo seleccionado*/
         File abre = file.getSelectedFile();

         /**recorremos el archivo, lo leemos para plasmarlo
         *en el area de texto*/
         if(abre!=null)
         {     
            FileReader archivos=new FileReader(abre);
            BufferedReader lee=new BufferedReader(archivos);
            while((aux=lee.readLine())!=null)
            {
               texto+= aux+ "\n";
            }
               lee.close();
          }    
         }
         catch(IOException ex)
         {
           JOptionPane.showMessageDialog(null,ex+"" +
                 "\nNo se ha encontrado el archivo",
                       "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
          }
        return texto;//El texto se almacena en el JTextArea
    }
    
    private void guardarArchivo(String areaDeTexto) 
    {
        try
        {
         String nombre="";
         JFileChooser file=new JFileChooser();
         file.showSaveDialog(this);
         File guarda =file.getSelectedFile();

         if(guarda !=null)
         {
          /*guardamos el archivo y le damos el formato directamente,
           * si queremos que se guarde en formato doc lo definimos como .doc*/
           FileWriter  save=new FileWriter(guarda+".txt");
           save.write(areaDeTexto);
           save.close();
           JOptionPane.showMessageDialog(null,
                "El archivo se a guardado Exitosamente",
                    "Información",JOptionPane.INFORMATION_MESSAGE);
           }
        }
         catch(IOException ex)
         {
          JOptionPane.showMessageDialog(null,
               "Su archivo no se ha guardado",
                  "Advertencia",JOptionPane.WARNING_MESSAGE);
         }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Convertir;
    private javax.swing.JButton btn_vista_estado_acpetacion;
    private javax.swing.JButton btn_vista_estado_incial;
    private javax.swing.JButton btn_vista_estados;
    private javax.swing.JButton btn_vista_simbolos;
    private javax.swing.JButton btn_vista_transiciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jp_contenedor_principal;
    private javax.swing.JPanel jp_modificar_automata;
    private javax.swing.JPanel jp_operaciones_automata;
    // End of variables declaration//GEN-END:variables

    
}
