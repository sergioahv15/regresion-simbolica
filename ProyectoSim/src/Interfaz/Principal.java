/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaz;

import Genetica.EvaluarFit;
import Genetica.ProgramacionGenetica;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Diego Chapeton and Fabian Sanin
 */
public class Principal extends javax.swing.JFrame {

    public int numeroVariables;
    public XYSeriesCollection dataset;
    public BufferedImage image;
    public XYSeries series,series2;
    public int anchog,altog;
    public boolean inicia=true;
    public Double[][] datos;
    public int numeroEntradas;
    public String rutaArchivo;
    public String rutaGuardar;
    public boolean impVarSal;
    public boolean impVarEnt;
    public boolean impOpeDef;
    public boolean impEvoPro;
    public boolean impTimEje;
    public boolean mosGrafic;

    /** Creates new form Principal */
    public Principal() {
        initComponents();
        jLabel5.setText("Solo se mostrara gráfica para funciones de tipo Y=F(X)");
        jLabel20.setText("");
        dataset = new XYSeriesCollection();
        datos=new Double[100][1000];
        if(inicia){
            anchog=jLabel5.getWidth();
            altog=jLabel5.getHeight();
            inicia=false;
        }
        rutaGuardar="C:"+"\\Resultados.txt";
        jTextField16.setText(rutaGuardar);
        jTextField16.setEditable(false);
        jProgressBar1.setValue(0);
    }

    /** Funcion Utilizada para limpiar los datos**/
    public void LimpiarDatos(){
        jLabel5.setText("");
        jTextArea2.setText("");
        dataset = new XYSeriesCollection();
        datos=new Double[100][1000];
    }

    /** Funcion Utilizada para leer los datos**/
    public void LeerDatos(){
        numeroVariables=Integer.parseInt(jTextField1.getText());
        String s=jTextArea1.getText();
        int tope=0;
        for(int i=0;i<s.length();i++){
            int contador=0;
            while(s.charAt(i)!='\n'){
                while(s.charAt(i)==' ' || s.charAt(i)=='\t')i++;
                if(s.charAt(i)=='\n') break;
                String n="";
                while(s.charAt(i)!=' ' && s.charAt(i)!='\t' && s.charAt(i)!='\n'){
                    n+=s.charAt(i);
                    i++;
                }
                double numero=Double.valueOf(n).doubleValue();
                datos[contador][tope]=numero;
                numeroEntradas=tope+1;
                if(s.charAt(i)==' ' || s.charAt(i)=='\t'){
                    contador++;
                }
            }
            tope++;
        }
    }

    /** Funcion Utilizada para crear la grafica**/
    public void CrearGrafico(){
        jLabel5.setVisible(true);
        series = new XYSeries("XYGraph");
        for(int i=0;i<numeroEntradas;i++){
            series.add(datos[0][i],datos[1][i]);
        }
	dataset.addSeries(series);
	JFreeChart chart = ChartFactory.createXYLineChart(
            "",
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        chart.clearSubtitles();
        if(mosGrafic)
            RenovarLabel(chart);
    }

    /** Funcion Utilizada para pintar o repintar la grafica**/
    public void RenovarImagen(){
        dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "",
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        chart.clearSubtitles();
        RenovarLabel(chart);
    }

    /** Metodo que inicializa parametros de ProgramacionGenetica y ejecuta el Hilo**/
    public void Ejecutar() throws Exception{
        ProgramacionGenetica SR=new ProgramacionGenetica();
        EvaluarFit ef = new EvaluarFit();
        SR.setCols(numeroEntradas);
        ef.numCol =SR.getCols();
        ef.varEntrada =numeroVariables;
        ef.datos=new Double[ef.varEntrada + 1][ef.numCol];
        SR.poblacion=Integer.parseInt(jTextField2.getText());
        SR.minProf=Integer.parseInt(jTextField3.getText());
        SR.maxProf=Integer.parseInt(jTextField4.getText());
        SR.profCruce=Integer.parseInt(jTextField5.getText());
        SR.generaciones=Integer.parseInt(jTextField6.getText());
        SR.maxNodos=Integer.parseInt(jTextField7.getText());
        SR.probFunc=Double.valueOf(jTextField8.getText()).doubleValue();
        SR.probRepro=Float.valueOf(jTextField9.getText()).floatValue();
        SR.probMuta=Float.valueOf(jTextField10.getText()).floatValue();
        SR.probCruce=Double.valueOf(jTextField11.getText()).doubleValue();
        SR.nCromo=Double.valueOf(jTextField12.getText()).doubleValue();
        SR.comIntervalo=Double.valueOf(jTextField13.getText()).doubleValue();
        SR.finIntervalo=Double.valueOf(jTextField14.getText()).doubleValue();
        SR.cParada=Double.valueOf(jTextField15.getText()).doubleValue();

        impVarEnt=jRadioButton12.isSelected();
        impVarSal=jRadioButton11.isSelected();
        impOpeDef=jRadioButton13.isSelected();
        impEvoPro=jRadioButton14.isSelected();
        impTimEje=jRadioButton15.isSelected();
        mosGrafic=jRadioButton16.isSelected();
        
        for (int i = 0; i < ef.varEntrada + 1; i++) {
            for (int j = 0; j < ef.numCol; j++) {
                ef.datos[i][j] = new Double(datos[i][j]);
                datos[i][j] = new Double(datos[i][j]);
            }
        }
        
        String funciones="";
        if(jRadioButton1.isSelected()) funciones+="Add,";
        if(jRadioButton2.isSelected()) funciones+="Subtract,";
        if(jRadioButton3.isSelected()) funciones+="Multiply,";
        if(jRadioButton4.isSelected()) funciones+="Divide,";
        if(jRadioButton5.isSelected()) funciones+="Sqrt,";
        if(jRadioButton6.isSelected()) funciones+="Pow,";
        if(jRadioButton7.isSelected()) funciones+="Log,";
        if(jRadioButton8.isSelected()) funciones+="Sine,";
        if(jRadioButton9.isSelected()) funciones+="Cosine,";
        if(jRadioButton10.isSelected()) funciones+="Exp,";
        SR.functions=funciones.substring(0, funciones.length()-1).split(",");
        if(numeroVariables==1){
            SR.nomVars = "X,Y".split(",");
            jLabel20.setText("X,Y");
            if(impVarSal)
                jTextArea2.setText("Variable de Salida: Y\n");
        }
        else {
            String t="";
            for(int i=0;i<numeroVariables+1;i++){
                t+="V"+i+",";
            }
            jLabel20.setText(t.substring(0, t.length()-1));
            if(impVarSal)
                jTextArea2.setText("Variable de Salida: V"+numeroVariables+"\n");
            SR.nomVars=t.split(",");
        }

        String[] args={""};
        if(mosGrafic && numeroVariables==1) jLabel5.setVisible(true);
            else jLabel5.setVisible(false);
        new Hilo(SR,args,this,0).start();
    }

    /** Metodo que renueva los resultados obtenidos**/
    public void RenovarText(String mas){
        jTextArea2.setText(jTextArea2.getText()+mas);
        jTextArea2.getCaret().setDot( jTextArea2.getText().length() );
        jTextArea2.scrollRectToVisible(jTextArea2.getVisibleRect());
    }

    /** Actualiza la barra de progreso **/
    public void actualizarBarra(int n){
        jProgressBar1.setValue(n);
    }

    /** Metodo que renueva el label donde se pinta la grafica**/
    public void RenovarLabel(JFreeChart ch){
        jLabel5.setText("");
        image = ch.createBufferedImage(anchog,altog);
        jLabel5.setIcon(new ImageIcon(image));
    }

    /** Deshabilita o Habilita el boton mientras termina la ejecucion**/
    public void estadoBoton(boolean b){
        jButton1.setEnabled(b);
        jProgressBar1.setValue(0);
    }

    /** Funcion para guardar los resultados**/
    public void guardarResultados(){
        if(jCheckBox1.isSelected()){
            File file = new File(rutaGuardar);
            try {
                if(!file.exists()) file.createNewFile();
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF8"));
                out.write(jTextArea2.getText());
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jDialog2 = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jRadioButton16 = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jDialog1.setTitle("Ayuda Aplicacion");
        jDialog1.setMinimumSize(new java.awt.Dimension(800, 500));

        jButton3.setText("OK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea3.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea3.setColumns(20);
        jTextArea3.setEditable(false);
        jTextArea3.setRows(5);
        jTextArea3.setText("\tINGRESO DE DATOS:\n\nPrimero debe ingresar el número(M) de variables independientes que desea.\n\nEn el Área de Texto debe ingresar M+1 número de columnas separadas por un espacio, las cuales representan los datos \ncorrespondientes a las M variables y una columna adicional para la variable de salida.\n\nDebe tener en cuenta que la última columna es el resultado de la función.\n\nEjemplo: Para la función Y=X^2 los datos de entrada correspondientes serian:\n\nNúmero Variables independientes: 1\nDatos:\n1 2\n2 4\n3 9\n4 16\n5 25\n\nImportante!! \n\n•\tCuando termine de ingresar los datos por favor deje una línea en blanco al final.\n•\tAdicionalmente usted puede seleccionar las funciones que desea utilizar para que el algoritmo genético \n\tevolucione.\n•\tCabe aclarar que para el estimador es indiferente el uso de las mayúsculas y minúsculas en los nombres \n\tde las funciones, no siendo esto mismo cierto en los nombres de las variables y constantes.\n•\tLos datos con números decimales el punto decimal se representa con un punto “.” Si se separan por una \n\tpueden haber fallos.\n•\tCuando se utilizan funciones trigonométricas, por defecto la aplicación  entiende que los argumentos \n\tque reciben éstas funciones están dados en radianes. Pero si el usuario desea ingresar los valores en \n\tgrados sexagesimales, debe realizar la conversión pertinente multiplicando el ángulo en grados por el \n\tfactor de conversión 0.0174532925, equivalente a π/180.\n•\tLog representa la función logaritmo natural.\n\n\n\n\tLECTURA DE ARCHIVOS:\n\nUsted también puede leer un archivo que tenga en su disco duro, simplemente debe dar clic en el botón \"Cargar Archivo\".\n\nLa primera línea del archivo debe ser \"NumeroVariables:#\" donde # es el número de variables independientes que usted \nva a ingresar.\nLas siguientes líneas son los datos tal cual como usted los ingresaría en el programa.\n\nEjemplo Archivo:\n_______________________________\nNumeroVariables:2\n1 2 3\n2 4 6\n3 8 11\n4 16 20\n5 32 37\n\n===============================\nRecuerde dejar una línea en blanco al final.\n\n\tCONFIGURACION\n\nExisten 3 botones los cuales nos permiten establecer 3 configuraciones por defecto, siendo la configuración 1 la más simple \ny la configuración 3 la más compleja.\nAdicionalmente si usted posee conocimientos en esta area genetica, puede modificar la configuracion deacuerdo a sus \nresultados esperados.\n\nPuede seleccionar los datos que desea ver en el programa, tales datos son: las variables de salida, las variables de entrada, \nlas operaciones definidas, la evolución del proceso, el tiempo de ejecución y la gráfica a mostrar.\n\nAdicionalmente usted tiene la posibilidad de guardar los datos obtenidos por el programa, esto se hace seleccionando la casilla \n“Guardar Datos Obtenidos”, por defecto el programa los guardara en el disco local “C:” en un documento de texto llamado \n“Resultados.txt”, sin embargo el usuario puede seleccionar la ruta donde desea guardar este archivo.");
        jScrollPane3.setViewportView(jTextArea3);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addComponent(jButton3))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jDialog2.setTitle("Acerca de");
        jDialog2.setMinimumSize(new java.awt.Dimension(320, 220));

        jTextArea4.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea4.setColumns(20);
        jTextArea4.setEditable(false);
        jTextArea4.setRows(5);
        jTextArea4.setText("Producto-Versión: Aproximador de Funciones 1.0\n           Autores: Diego Leonardo Chapetón\n                      Joseph Fabián Sanín\n          Materia: Modelamiento y Simulación\n\n           Universidad Nacional de Colombia\n");
        jScrollPane4.setViewportView(jTextArea4);

        jButton4.setText("ok");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jButton4))
                .addContainerGap())
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aproximador de funciones");

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel1.setText("Seleccione las funciones que desea utilziar para la evolucion:");

        jRadioButton1.setSelected(true);
        jRadioButton1.setText("+");

        jRadioButton2.setSelected(true);
        jRadioButton2.setText("-");

        jRadioButton3.setSelected(true);
        jRadioButton3.setText("*");

        jRadioButton4.setSelected(true);
        jRadioButton4.setText("/");

        jRadioButton5.setText("Sqrt");

        jRadioButton6.setText("^");

        jRadioButton7.setText("Log");

        jRadioButton8.setText("Sen");

        jRadioButton9.setText("Cos");

        jRadioButton10.setText("Exp");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel2.setText("Ingrese el numero de variables independientes:");

        jTextField1.setText("1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel3.setText("Ingrese los datos:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("1 1\n2 4\n3 9\n4 16\n5 25\n6 36\n7 49\n8 64\n9 81\n10 100\n");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel4.setText("Resultados:");

        jTextArea2.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel5.setText("jLabel5");

        jButton1.setText("Iniciar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel20.setText("ORDEN VARIABLES");

        jButton2.setText("Cargar Archivo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton9)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton10))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7)
                    .addComponent(jRadioButton8)
                    .addComponent(jRadioButton9)
                    .addComponent(jRadioButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos", jPanel1);

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel6.setText("Tamaño Poblacion:");

        jTextField2.setText("300");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel7.setText("Min. Profundidad Inicial:");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel8.setText("Max. Profundidad Inicial:");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel9.setText("Max. Profundidad de Cruce:");

        jTextField3.setText("2");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setText("6");

        jTextField5.setText("10");

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel10.setText("Numero Evoluciones:");

        jTextField6.setText("3000");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel11.setText("Max. Nodos:");

        jTextField7.setText("25");

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel12.setText("Funcion Probabilidad:");

        jTextField8.setText("0.9");

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel13.setText("Probabilidad de Reproduccion:");

        jTextField9.setText("0.2");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel14.setText("Probabilidad de Mutuacion:");

        jTextField10.setText("0.2");

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel15.setText("Probabilidad de Cruce:");

        jTextField11.setText("0.8");

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel16.setText("Porcentaje de Nuevo Cromosomas:");

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel17.setText("Rango Minimo Aleatorios:");

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel18.setText("Rango Maximo Aleatorios:");

        jTextField12.setText("0.3");

        jTextField13.setText("-10");

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        jLabel19.setText("Fitness de Parada:");

        jTextField14.setText("10");

        jTextField15.setText("0.000001");
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel21.setText("Seleccione los datos que desea observar:");

        jRadioButton11.setSelected(true);
        jRadioButton11.setText("Variable de Salida");

        jRadioButton12.setSelected(true);
        jRadioButton12.setText("Variables de Entrada");

        jRadioButton13.setText("Operaciones Definidas");

        jRadioButton14.setText("Evolucion del proceso");

        jRadioButton15.setSelected(true);
        jRadioButton15.setText("Tiempo de Ejecucion");

        jCheckBox1.setText("Guardar Datos Obtenidos");

        jRadioButton16.setSelected(true);
        jRadioButton16.setText("Mostrar Grafica");

        jLabel22.setText("Ruta:");

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Config 1");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Config 2");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Config 3");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField8)
                                .addComponent(jTextField6))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton11)
                                    .addComponent(jRadioButton12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton13)
                                    .addComponent(jRadioButton14))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton16)
                                    .addComponent(jRadioButton15)))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                            .addComponent(jCheckBox1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField16, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))))
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField10, jTextField11, jTextField12, jTextField13, jTextField14, jTextField15, jTextField2, jTextField3, jTextField4, jTextField5, jTextField6, jTextField7, jTextField8, jTextField9});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton11)
                            .addComponent(jRadioButton13)
                            .addComponent(jRadioButton15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton12)
                            .addComponent(jRadioButton14)
                            .addComponent(jRadioButton16))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel25))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Configuracion", jPanel2);

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Salir");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem2.setText("Ayuda Aplicacion");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Acerca de");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Este Boton incia el algoritmo**/
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        LimpiarDatos();
        LeerDatos();
        if(numeroVariables==1) CrearGrafico();
        else jLabel5.setVisible(false);
        try {
            jButton1.setEnabled(false);
            Ejecutar();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextField1ActionPerformed

    /** Este Boton incia la lectura de un archivo**/
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Frame ventana = new Frame();
        ventana.setResizable(true);
        FileDialog abrir = new FileDialog(ventana,"Seleccione el archivo",FileDialog.LOAD);
        abrir.show();
        rutaArchivo = abrir.getDirectory()+abrir.getFile();
        try {
            BufferedReader inr = new BufferedReader(new FileReader(rutaArchivo));
            try {
                String str;
                jTextArea1.setText("");
                while ((str = inr.readLine()) != null) {
                    str.trim();
                    if(str.contains("#")){
                    }
                    else if(str.contains(":"))
                    {
                        String row[] = str.split(":\\s*");
                        if ("NumeroVariables".equals(row[0])) {
                            jTextField1.setText(row[1]);
                        }
                    }
                    else{
                        jTextArea1.setText(jTextArea1.getText()+str+"\n");
                    }

                }
            } catch (IOException ex) {
                System.out.println("Problema en la lectura del archivo");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No selecciono archivo");
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
        // TODO add your handling code here:]
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1MousePressed

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        // TODO add your handling code here:
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jDialog2.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MousePressed
        // TODO add your handling code here:
        jDialog2.setVisible(true);
    }//GEN-LAST:event_jMenuItem3MousePressed

    // Este boton guarda la ruta para grabar un archivo
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(1);
        int status = fc.showOpenDialog(this);
        if(status == JFileChooser.APPROVE_OPTION){
            java.io.File file = fc.getSelectedFile();
            rutaGuardar=file.getAbsolutePath()+"\\Resultados.txt";
        }
        jTextField16.setText(rutaGuardar);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("100");
        jTextField3.setText("2");
        jTextField4.setText("4");
        jTextField5.setText("8");
        jTextField6.setText("1800");
        jTextField7.setText("21");
        jTextField8.setText("0.9");
        jTextField9.setText("0.1");
        jTextField10.setText("0.1");
        jTextField11.setText("0.9");
        jTextField12.setText("0.3");
        jTextField13.setText("-10");
        jTextField14.setText("10");
        jTextField15.setText("0.0");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("300");
        jTextField3.setText("2");
        jTextField4.setText("6");
        jTextField5.setText("10");
        jTextField6.setText("3000");
        jTextField7.setText("25");
        jTextField8.setText("0.9");
        jTextField9.setText("0.2");
        jTextField10.setText("0.2");
        jTextField11.setText("0.8");
        jTextField12.setText("0.3");
        jTextField13.setText("-10");
        jTextField14.setText("10");
        jTextField15.setText("0.000001");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("1000");
        jTextField3.setText("6");
        jTextField4.setText("12");
        jTextField5.setText("20");
        jTextField6.setText("5000");
        jTextField7.setText("30");
        jTextField8.setText("0.5");
        jTextField9.setText("0.3");
        jTextField10.setText("0.3");
        jTextField11.setText("0.9");
        jTextField12.setText("0.3");
        jTextField13.setText("-20");
        jTextField14.setText("20");
        jTextField15.setText("0.000001");
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

}
