package fproyecto2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * Clase para utilizar el metodo de move, e-closure y simulacion de
 * un automata
 * Incluye también un método para generar archivos DOT
 */
public class Simulacion {
    
    private String resultado;
    
    public Simulacion(){
        
    }
    
    public Simulacion(Automata afn_simulacion, String regex){
        simular(regex,afn_simulacion);
    }
    
    public HashSet<Estado> eClosure(Estado eClosureEstado){
        Stack<Estado> pilaClosure = new Stack();
        Estado actual = eClosureEstado;
        actual.getTransiciones();
        HashSet<Estado> resultado = new HashSet();
        
        pilaClosure.push(actual);
        while(!pilaClosure.isEmpty()){
            actual = pilaClosure.pop();
           
            for (Transicion t: (ArrayList<Transicion>)actual.getTransiciones()){
                
                if (t.getSimbolo().equals(Init.EPSILON)&&!resultado.contains(t.getFin())){
                    resultado.add(t.getFin());
                    pilaClosure.push(t.getFin());
                }
            }
        }
        resultado.add(eClosureEstado); //la operacion e-Closure debe tener el estado aplicado
        return resultado;
    }
    
    public HashSet<Estado> move(HashSet<Estado> estados, String simbolo){
       
        HashSet<Estado> alcanzados = new HashSet();
        Iterator<Estado> iterador = estados.iterator();
        while (iterador.hasNext()){
            
            for (Transicion t: (ArrayList<Transicion>)iterador.next().getTransiciones()){
                Estado siguiente = t.getFin();
                String simb = (String) t.getSimbolo();
                if (simb.equals(simbolo)){
                    alcanzados.add(siguiente);
                }
                
            }
            
        }
        return alcanzados;
        
    }
    
    public Estado move(Estado estado, String simbolo){
        ArrayList<Estado> alcanzados = new ArrayList();
           
        for (Transicion t: (ArrayList<Transicion>)estado.getTransiciones()){
            Estado siguiente = t.getFin();
            String simb = (String) t.getSimbolo();
            
            if (simb.equals(simbolo)&&!alcanzados.contains(siguiente)){
                alcanzados.add(siguiente);
            }

        }
       
        return alcanzados.get(0);
    }
    
    /**
     * Método para simular un automata sin importar si es determinista o no deterministas
     * 
     * @param regex recibe la cadena a simular 
     * @param automata recibe el automata a ser simulado
     */
    public boolean simular(String regex, Automata automata)
    {
        Estado inicial = automata.getEstadoInicial();
        ArrayList<Estado> estados = automata.getEstados();
        ArrayList<Estado> aceptacion = new ArrayList(automata.getEstadosAceptacion());
        
        HashSet<Estado> conjunto = eClosure(inicial);
        for (Character ch: regex.toCharArray()){
            conjunto = move(conjunto,ch.toString());
            HashSet<Estado> temp = new HashSet();
            Iterator<Estado> iter = conjunto.iterator();
            
            while (iter.hasNext()){
               Estado siguiente = iter.next();
               /**
                * En esta parte es muy importante el metodo addAll
                * porque se tiene que agregar el eClosure de todo el conjunto
                * resultante del move y se utiliza un hashSet temporal porque
                * no se permite la mutacion mientras se itera
                */
                temp.addAll(eClosure(siguiente)); 
               
            }
            conjunto=temp;
            
            
        }
        
        
        boolean res = false;
        
        for (Estado estado_aceptacion : aceptacion){
            if (conjunto.contains(estado_aceptacion)){
                res = true;
            }
        }
        if (res){
            //System.out.println("Aceptado");
            //this.resultado = "Aceptado";
            return true;
        }
        else{
            //System.out.println("NO Aceptado");
            // this.resultado = "No Aceptado";
            return false;
        }
    }

    public String getResultado() {
            return resultado;
        }
    
        
        /**
         * Método para crear el archivo DOT para después generar PNG
         * @param nombreArchivo
         * @param automataFinito
         * @return String con el comando a ejecutar.
         * Es necesario tener la librería de Graphiz instalada para correr
         * el comando
         * DOT: Graph description language
         * http://www.graphviz.org/
         * https://en.wikipedia.org/wiki/DOT_(graph_description_language)
         * http://rich-iannone.github.io/DiagrammeR/graphviz.html
         * http://www.graphviz.org/doc/info/shapes.html
         */
    public String generarDOT(String nombreArchivo, Automata automataFinito){
        String texto = "digraph automata_finito {\n";

        texto +="\trankdir=LR;"+"\n";
        
        texto += "\tgraph [label=\""+nombreArchivo+"\", labelloc=t, fontsize=20]; \n";
        texto +="\tnode [shape=doublecircle, style = filled,color = mediumseagreen];";
        //listar estados de aceptación
        for(int i=0;i<automataFinito.getEstadosAceptacion().size();i++){
            texto+=" "+automataFinito.getEstadosAceptacion().get(i);
        }
        //
        texto+=";"+"\n";
        texto +="\tnode [shape=circle];"+"\n";
        texto +="\tnode [color=midnightblue,fontcolor=white];\n" +"	edge [color=red];"+"\n";
       
        texto +="\tsecret_node [style=invis];\n" + "	secret_node -> "+automataFinito.getEstadoInicial()+" [label=\"inicio\"];" + "\n";
	
        Graphviz gp = new Graphviz();
        
        gp.addln(texto);
        
        //transiciones
        for(int i=0;i<automataFinito.getEstados().size();i++){
            ArrayList<Transicion> t = automataFinito.getEstados().get(i).getTransiciones();
            for (int j = 0;j<t.size();j++){
                texto+="\t"+t.get(j).DOT_String()+"\n";
                gp.add("\t"+t.get(j).DOT_String()+"\n");
            }
            
        }
      //  System.out.println(texto);
      //  texto+="}";
        gp.addln(gp.end_graph());
       
        String type = "png";
        File out = new File("Automata." + type);
        gp.writeGraphToFile(gp.getGraph(gp.getDotSource(), type), out);
        System.out.println(gp.getPath());
        graficaHTML(gp.getPath());
//        File dummy = new File("");
//        String path = dummy.getAbsolutePath();
//        path+="/";
//        String archivo =nombreArchivo+".dot";
//        new File(path+"/GeneracionAutomatas/").mkdirs();
//        String pathImagen = path+"GeneracionAutomatas/PNG/";
//        path+="GeneracionAutomatas/DOT/";
//        File TextFile = new File("/GeneracionAutomatas/DOT/"+nombreArchivo+".dot");
//        FileWriter TextOut;
//    
//        try {
//            TextOut = new FileWriter(path+nombreArchivo+".dot");
//            TextOut.write(texto);
//           
//            TextOut.close();
//        } catch (IOException ex) {
//          
//        }
        
       
        /*String[] cmdArray = new String[2];
        cmdArray[0] = "/opt/local/bin/dot";
        cmdArray[1] = "-Tpng "+path+archivo + " > "+path+nombreArchivo+".png";*/
//        String comando = "dot -Tpng "+path+archivo + " > "+pathImagen+nombreArchivo+".png";
//        try
//        {
//            ProcessBuilder pbuilder;
//
//            /*
//             * Realiza la construccion del comando    
//             * en la linea de comandos esto es: 
//             * dot -Tpng -o archivo.png archivo.dot
//             */
//            pbuilder = new ProcessBuilder( "dot", "-Tpng", "-o",pathImagen+nombreArchivo+".png",path+archivo );
//            pbuilder.redirectErrorStream( true );
//            //Ejecuta el proceso
//            pbuilder.start();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//           /* Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable. */
//        }
//        //System.out.println("Ejecute el siguiente comando");
//        //System.out.println(comando);
//        
//        return comando;


           return "";
    }
    
    private void graficaHTML(String rutaGrafo) {
        String encabezadoHTML = "", reporte = "";

        int contador = 1;
       
        encabezadoHTML = "<!DOCTYPE html>\n"
                + "<html lang='en'>\n"
                + "<head>\n"
                + "    <meta charset='UTF-8'>\n"
                + "    <meta name='viewport' content=width=device-width, initial-scale=1.0\">\n"
                + "    <meta http-equiv='X-UA-Compatible' content='ie=edge'>\n"
                + "    <title>Grafico </title>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div>\n"
                
                + "<center>\n"
                + "<h2>Grafo</h2>\n"
                +"<br>\n"
                +"<br>\n"
                +" <img src='"+rutaGrafo+"' alt='pensum' style='background-size: auto'>\n"
                
                + "    </center>\n"
                + "    \n"
                + "</body>\n"
                + "</html>";
        File f;
        FileWriter w;
        BufferedWriter bw;
        PrintWriter wr;
        String ruta = "";
        try {
            f = new File("Grafico.html");
            w = new FileWriter(f);
            bw = new BufferedWriter(w);
            wr = new PrintWriter(bw);

            wr.write(encabezadoHTML);

            wr.close();
            bw.close();

            ruta = f.getAbsolutePath();
            //  System.out.println(ruta);

        } catch (Exception e) {
        }

       // System.out.println(ruta);

        try {
            // create a process and execute google-chrome 
            Process process = Runtime.getRuntime().exec("/usr/bin/firefox "+ruta);
       //     System.out.println("Google Chrome successfully started");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   

}
