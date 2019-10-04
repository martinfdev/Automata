package fproyecto2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class ExpresionRegular {

    private String expresion;

    public ExpresionRegular(String expresion) {
        this.expresion = expresion;
   
    }

    public void comprobar(String cadena) {
        Init auto = new  Init();
        
        try {
            Pattern pat = Pattern.compile(this.expresion);
            Matcher mat = pat.matcher(cadena);
           
            if (mat.matches()) {
                auto.iniciarAutomata(this.expresion, cadena);
                JOptionPane.showMessageDialog(null, "La cadena pertence al lenguaje");
                
                
            } else {

                JOptionPane.showMessageDialog(null, "La cadena NO pertence al lenguaje");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la entrada de la expresion regular");
        }

    }

}
