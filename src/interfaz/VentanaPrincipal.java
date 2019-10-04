
package interfaz;
import fproyecto2.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VentanaPrincipal extends javax.swing.JFrame {
    String path;
    ExpresionRegular ep;
    
    
    public VentanaPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Fabiola");
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtEtradaExpresion = new javax.swing.JTextField();
        btnCargarArchivo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCadena = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        btnComprobarCadena = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtEtradaExpresion.setText("(a|b)*abb");
        txtEtradaExpresion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEtradaExpresionFocusLost(evt);
            }
        });

        btnCargarArchivo.setText("CARGAR ARCHIVO DE TEXTO");
        btnCargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarArchivoActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(txtCadena);

        jLabel1.setText("Expresion Regular");

        btnComprobarCadena.setText("COMPROBAR CADENA");
        btnComprobarCadena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarCadenaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEtradaExpresion)
                        .addGap(18, 18, 18)
                        .addComponent(btnCargarArchivo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnComprobarCadena)
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEtradaExpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCargarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnComprobarCadena, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarArchivoActionPerformed
        File archivo;
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        file.addChoosableFileFilter(new FileNameExtensionFilter("Archivo tipo txt", "txt"));
        file.setAcceptAllFileFilterUsed(true);
        int opcion = file.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {

             archivo = file.getSelectedFile();
              path = file.getSelectedFile().getPath();
          //  System.out.println(archivo+" "+path);
            if (archivo.canRead()) {
                if (!"".equals(txtEtradaExpresion.getText())) {
                    
                }else{
                    
                    txtEtradaExpresion.setText(leerArchivo(archivo));
                    ep = new ExpresionRegular(txtEtradaExpresion.getText());
                    
                }  
            }
        } 
    }//GEN-LAST:event_btnCargarArchivoActionPerformed

    private void btnComprobarCadenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarCadenaActionPerformed
        String cadena;
        cadena = txtCadena.getText();
        if (txtCadena.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No hay texto que comprobar");
        }else{
             ep.comprobar(cadena);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnComprobarCadenaActionPerformed

    private void txtEtradaExpresionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEtradaExpresionFocusLost
         ep = new ExpresionRegular(txtEtradaExpresion.getText());
        //System.out.println("hola");
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEtradaExpresionFocusLost

    private String leerArchivo(File archivo) {
        FileInputStream entrada;
        InputStreamReader ent;
        String documento = "";
        try {
            entrada = new FileInputStream(archivo);
         ent = new InputStreamReader(entrada, "UTF-8");
                 
            int ascci;

            while ((ascci = ent.read()) != -1) {
                char caracter = (char) ascci;
                documento += caracter;

            }

        } catch (Exception e) {
        }
        return documento;   
    }
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnComprobarCadena;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txtCadena;
    private javax.swing.JTextField txtEtradaExpresion;
    // End of variables declaration//GEN-END:variables
}
