/*
 * MainFrame.java
 *
 * 
 */

package edb.gui;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import edb.alggen.Poblacion;
import edb.alggen.Resultado;
import edb.gui.SymFocus;
import edb.gui.VentanaAlterna;

/**
 * 
 *
 */


public class MainFrame extends javax.swing.JFrame
{

    private static final long serialVersionUID = 1L;
    public Poblacion pb;

    //Se necsita una instancia de la clase VentanaAlterna para enviarla al constructor de la clase Poblacion
    public VentanaAlterna ventana;
    
     //PANELES
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel PanelP;
 
    
    
    //PESTAÑAS
    public javax.swing.JTabbedPane TabFolders;
    
     //ELEMENTOS DE LA PESTAÑA 1:
    
    //ETIQUETAS
    private javax.swing.JLabel jEtiq1;
    private javax.swing.JLabel jEtiq2;
    private javax.swing.JLabel jEtiq3;
    private javax.swing.JLabel jEtiq4;
    private javax.swing.JLabel jEtiq5;
    
    
    //AREA DE TEXTO PARA LOS scroll INICIALES
     public javax.swing.JTextArea vectores;
     private javax.swing.JScrollPane scroll;
     
     public javax.swing.JEditorPane help;
     public javax.swing.JScrollPane scroll2;
    
    //BOTONES
    private javax.swing.JButton jButton1;   				
    private javax.swing.JButton jButton2;
   
    //CAJAS DE TEXTO
    public javax.swing.JTextField texpresion;
    public javax.swing.JTextField tconstantes;
    public javax.swing.JTextField tvariables;
    
    
    
    //ELEMENTOS DE LA PESTAÑA 2
    
    public javax.swing.JProgressBar barra;
    
    public javax.swing.JButton bEvolucion;

    private javax.swing.JRadioButton rArchivo;

    private javax.swing.JRadioButton rAutomatico;

    private javax.swing.JRadioButton rECM;

    private javax.swing.JRadioButton rKS;

    private javax.swing.JTextField tConstantes;

    private javax.swing.JTextField tCruce;

    private javax.swing.JTextField tDelta;

    private javax.swing.JTextField tEntrada;

    private javax.swing.JTextField tErrormax;

    private javax.swing.JTextField tErrormin;

    private javax.swing.JTextField tExpresion;

    private javax.swing.JTextField tGeneraciones;

    private javax.swing.JTextField tKS;

    private javax.swing.JTextField tMutacion;

    private javax.swing.JTextField tPoblacion;

    private javax.swing.JTextField tSalida;

    private javax.swing.JTextField tValarchivo;

    private javax.swing.JTextField tValmax;

    private javax.swing.JTextField tValmin;

    private javax.swing.JTextField tVariables;

    private javax.swing.ButtonGroup buttonGroup1;

    private javax.swing.ButtonGroup buttonGroup2;

    private javax.swing.JLabel Label1;

    private javax.swing.JLabel Label10;

    private javax.swing.JLabel Label11;

    private javax.swing.JLabel Label12;

    private javax.swing.JLabel Label13;

    private javax.swing.JLabel Label14;

    private javax.swing.JLabel Label15;

    private javax.swing.JLabel Label16;

    private javax.swing.JLabel Label17;

    private javax.swing.JLabel Label2;

    private javax.swing.JLabel Label3;

    private javax.swing.JLabel Label4;

    private javax.swing.JLabel Label5;

    private javax.swing.JLabel Label6;

    private javax.swing.JLabel Label7;

    private javax.swing.JLabel Label8;

    private javax.swing.JLabel Label9;

    //private javax.swing.JPanel PanelP;
    public javax.swing.JLabel Etiq;

    private javax.swing.JPanel Panel2;

    public MainFrame()
    {
        initComponents();
    }

    private void initComponents()
    {// GEN-BEGIN:initComponents
        
           //COMPONENTES DE LA PESTAÑA 1
        jPanel1 = new javax.swing.JPanel();
        PanelP  = new javax.swing.JPanel();
        
        jButton1 = new javax.swing.JButton(); 		// linea 2
        jButton2 = new javax.swing.JButton();
        
        jEtiq1 = new javax.swing.JLabel();
        jEtiq2 = new javax.swing.JLabel();
        jEtiq3 = new javax.swing.JLabel();
        jEtiq4 = new javax.swing.JLabel();
        jEtiq5 = new javax.swing.JLabel();
        
       //Se crea el scroll para el área de texto de los vectores
        vectores = new javax.swing.JTextArea(null, 0, 0);
        help=new javax.swing.JEditorPane();
        help.setEditable(false);

        scroll=new javax.swing.JScrollPane(vectores);
        scroll2=new javax.swing.JScrollPane(help);
    
        
        
        tvariables = new javax.swing.JTextField();
        texpresion = new javax.swing.JTextField();
        tconstantes = new javax.swing.JTextField();
        
        TabFolders= new javax.swing.JTabbedPane();
         
         
         
        //COMPONENTES DE LA PESTAÑA 2
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        PanelP = new javax.swing.JPanel();
        Label1 = new javax.swing.JLabel();
        Label2 = new javax.swing.JLabel();
        Label3 = new javax.swing.JLabel();
        tConstantes = new javax.swing.JTextField();
        tVariables = new javax.swing.JTextField();
        tExpresion = new javax.swing.JTextField();
        Panel2 = new javax.swing.JPanel();
        Label4 = new javax.swing.JLabel();
        Etiq = new javax.swing.JLabel();
        Label5 = new javax.swing.JLabel();
        Label6 = new javax.swing.JLabel();
        Label7 = new javax.swing.JLabel();
        rECM = new javax.swing.JRadioButton();
        rKS = new javax.swing.JRadioButton();
        Label8 = new javax.swing.JLabel();
        Label9 = new javax.swing.JLabel();
        Label10 = new javax.swing.JLabel();
        Label11 = new javax.swing.JLabel();
        Label12 = new javax.swing.JLabel();
        Label13 = new javax.swing.JLabel();
        Label14 = new javax.swing.JLabel();
        rAutomatico = new javax.swing.JRadioButton();
        rArchivo = new javax.swing.JRadioButton();
        Label15 = new javax.swing.JLabel();
        Label16 = new javax.swing.JLabel();
        tCruce = new javax.swing.JTextField();
        tMutacion = new javax.swing.JTextField();
        tPoblacion = new javax.swing.JTextField();
        tGeneraciones = new javax.swing.JTextField();
        tEntrada = new javax.swing.JTextField();
        tSalida = new javax.swing.JTextField();
        tValmin = new javax.swing.JTextField();
        tValmax = new javax.swing.JTextField();
        tValarchivo = new javax.swing.JTextField();
        Label17 = new javax.swing.JLabel();
        tErrormin = new javax.swing.JTextField();
        tErrormax = new javax.swing.JTextField();
        tDelta = new javax.swing.JTextField();
        tKS = new javax.swing.JTextField();
        bEvolucion = new javax.swing.JButton();
        barra = new javax.swing.JProgressBar();
        
        
        
        //UBICACION DE LOS COMPONENTES DE LA PRIMERA PESTAÑA
          getContentPane().setLayout(null);
     //getContentPane().setBackground(new java.awt.Color(255,195,140));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parámetros de Entrada Para el Simulador");
        setResizable(false);
        
        
        jPanel1.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(100, 100,100)));
        jEtiq1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12));
        jEtiq1.setText("Función :");
        jPanel1.add(jEtiq1);
        jEtiq1.setBounds(30, 45, 70, 13);

        jEtiq2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12));
        jEtiq2.setText("Variables: ");
        jPanel1.add(jEtiq2);
        jEtiq2.setBounds(30, 79, 70, 13);

        jEtiq3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 11));
        jEtiq3.setText("Constantes: ");
        jPanel1.add(jEtiq3);
        jEtiq3.setBounds(25, 109, 72, 19);
        
        jEtiq4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12));
        jEtiq4.setText("  Vectores");
        jPanel1.add(jEtiq4);
        jEtiq4.setBounds(30, 145, 80, 13);
        
        jEtiq5.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12));
        jEtiq5.setText("  Iniciales:");
        jPanel1.add(jEtiq5);
        jEtiq5.setBounds(30, 165, 80, 13);

        tconstantes.setFont(new java.awt.Font("Arial", 1, 13));
        tconstantes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        jPanel1.add(tconstantes);
        tconstantes.setBounds(100, 110, 240, 23);
        tconstantes.setText("");

        tvariables.setFont(new java.awt.Font("Arial", 1, 13));
        tvariables.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        jPanel1.add(tvariables);
        tvariables.setBounds(100, 75, 240, 23);
        tvariables.setText("");

        texpresion.setFont(new java.awt.Font("Arial", 1, 11));
        texpresion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        jPanel1.add(texpresion);
        texpresion.setBounds(100, 40, 240, 23);
        texpresion.setText("");
        
        vectores.setFont(new java.awt.Font("Arial", 1, 14));
        scroll.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        jPanel1.add(scroll);
        scroll.setBounds(100, 145, 240, 193);
        vectores.setText("");
        
        //AREA DONDE SE LE MUESTRAN LOS COMENTARIOS DE AYUDA AL USUARIO
        help.setFont(new java.awt.Font("Times New Roman", 2, 13));
        help.setBackground(new java.awt.Color(240, 240, 240));
        help.setBounds(390,52,204,294);
        scroll2.setBackground(new java.awt.Color(240, 240, 240));
        scroll2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        scroll2.setAutoscrolls(true);
//        scroll2.setCorner(this,1);
        jPanel1.add(scroll2);
        scroll2.setBounds(360,40,206,298);
        letrero_inicial();
       
        //getContentPane().add(jPanel1);
        
        //DEFINE LAS DIMENSIONES DEL PANEL: x,y,ancho,altura
        //jPanel1.setBounds(40, 20, 340, 320);
        

         TabFolders.setBackground(new java.awt.Color(225, 225, 225));
         TabFolders.add(" Parámetros de Entrada ",jPanel1);

        
        
        //UBICACION DE LOS COMPONENTES DE LA SEGUNDA PESTAÑA
        //getContentPane().setLayout(null);
        PanelP.setLayout(null);
        PanelP.setBackground(new java.awt.Color(240, 240, 240));
        PanelP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(100, 100,100)));
        
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Estimador de parámetros para funciones no lineales por simulación de algoritmos evolutivos");
        setResizable(false);

        Label4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label4.setText("Probabilidad Cruce:");
        PanelP.add(Label4);
        Label4.setBounds(40, 20, 110, 13);

        Label5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label5.setText("Probabilidad de mutaci\u00f3n:");
        PanelP.add(Label5);
        Label5.setBounds(40, 50, 130, 13);

        Label6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label6.setText("Cantidad de poblaci\u00f3n:");
        PanelP.add(Label6);
        Label6.setBounds(40, 80, 120, 13);

        Label7.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label7.setText("N\u00famero de generaciones:");
        PanelP.add(Label7);
        Label7.setBounds(40, 110, 130, 13);

        rECM.setBackground(new java.awt.Color(240, 240, 240));
        rECM.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        rECM.setText("ECM");
        rECM.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                RECMActionPerformed(evt);
            }
        });

        PanelP.add(rECM);
        rECM.setBounds(312, 40, 49, 21);

        rKS.setBackground(new java.awt.Color(240, 240, 240));
        rKS.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        rKS.setText("Kolmogorov - Smirnov");
        rKS.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                RKSActionPerformed(evt);
            }
        });

        PanelP.add(rKS);
        rKS.setBounds(310, 60, 130, 21);

        Label8.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label8.setText("Error m\u00ednimo:");
        PanelP.add(Label8);
        Label8.setBounds(300, 120, 80, 13);

        Label9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label9.setText("Error m\u00e1ximo:");
        PanelP.add(Label9);
        Label9.setBounds(300, 150, 70, 13);

        Label9.setVisible(false);

        Label10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label10.setText("Delta de error:");
        PanelP.add(Label10);
        Label10.setBounds(300, 180, 80, 13);

        Label10.setVisible(false);

        Label11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label11.setText("Porcentaje de puntos (K-S):");
        PanelP.add(Label11);
        Label11.setBounds(300, 150, 130, 13);

        /*Label12.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label12.setText("Archivo de entrada:");
        PanelP.add(Label12);
        Label12.setBounds(40, 140, 92, 13);*/

        Label13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label13.setText("Archivo de salida:");
        PanelP.add(Label13);
        Label13.setBounds(40, 140, 100, 13);

        Label14.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
        Label14.setText("Calcular pobaci\u00f3n inicial ");
        PanelP.add(Label14);
        Label14.setBounds(20, 210, 220, 13);

        rAutomatico.setBackground(new java.awt.Color(240, 240, 240));
        rAutomatico.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        rAutomatico.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        rAutomatico.setText("Autom\u00e1ticamente:");
        PanelP.add(rAutomatico);
        rAutomatico.setBounds(50, 230, 130, 21);

        rArchivo.setBackground(new java.awt.Color(240, 240, 240));
        rArchivo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        rArchivo.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        rArchivo.setText("Valores dados de archivo:");
        PanelP.add(rArchivo);
        rArchivo.setBounds(50, 300, 150, 21);

        Label15.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label15.setText("Valores entre:");
        PanelP.add(Label15);
        Label15.setBounds(60, 260, 80, 13);

        Label16.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        Label16.setText("y");
        PanelP.add(Label16);
        Label16.setBounds(210, 260, 20, 13);

        tCruce.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tCruce.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tCruce);
        tCruce.setBounds(140, 20, 110, 18);
        tCruce.setText("0.5");

        tMutacion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tMutacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tMutacion);
        tMutacion.setBounds(170, 50, 80, 18);
        tMutacion.setText("0.025");

        tPoblacion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tPoblacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tPoblacion);
        tPoblacion.setBounds(160, 80, 90, 18);
        tPoblacion.setText("200");

        tGeneraciones.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tGeneraciones.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tGeneraciones);
        tGeneraciones.setBounds(170, 110, 80, 18);
        tGeneraciones.setText("100");

        tEntrada.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        PanelP.add(tEntrada);
        tEntrada.setBounds(240, 140, 110, 18);
        tEntrada.setText("Entrada.txt");
        tEntrada.setVisible(false);
        
        tSalida.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tSalida.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tSalida);
        tSalida.setBounds(130, 140, 120, 18);
        tSalida.setText("Salida.txt");

        tValmin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tValmin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tValmin);
        tValmin.setBounds(140, 260, 60, 18);
        tValmin.setText("-100.0");

        tValmax.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tValmax.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tValmax);
        tValmax.setBounds(230, 260, 60, 18);
        tValmax.setText("100.0");

        tDelta.setVisible(false);

        tValarchivo.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tValarchivo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tValarchivo);
        tValarchivo.setBounds(70, 330, 220, 18);
        tValarchivo.setText("dataDefault.txt");

        Label17.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
        Label17.setText("C\u00e1lculo del error:");
        PanelP.add(Label17);
        Label17.setBounds(300, 20, 100, 13);

        tErrormin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tErrormin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tErrormin);
        tErrormin.setBounds(440, 120, 80, 18);
        tErrormin.setText("0.0");

        tErrormax.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tErrormax.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        PanelP.add(tErrormax);
        tErrormax.setBounds(390, 150, 80, 18);
        tErrormax.setText("0.01");

        tErrormax.setVisible(false);

        tDelta.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        PanelP.add(tDelta);
        tDelta.setBounds(390, 180, 80, 18);
        tDelta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));
        tDelta.setText("0.1");

        tKS.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
        tKS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,100,200)));
        PanelP.add(tKS);
        tKS.setBounds(440, 150, 80, 18);
        tKS.setText("0.8");

        bEvolucion.setText("Iniciar!");
        bEvolucion.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
        bEvolucion.setBackground(new java.awt.Color(210, 210, 210));
        bEvolucion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(100, 100,100)));
        
        bEvolucion.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                BEvolucionActionPerformed(evt);
            }
        });
        
        tExpresion.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                TExpresionActionPerformed(e);
            }
        });
        

        PanelP.add(bEvolucion);
        bEvolucion.setBounds(370, 280, 150, 70);
        
        Etiq.setFont(new java.awt.Font("Microsoft Sans Serif",1, 10));
        Etiq.setBounds(345, 183, 190, 70);
        Etiq.setText("Evolución de las Generaciones:");
        PanelP.add(Etiq);
        Etiq.setVisible(false);
        
        
        barra.setBounds(370, 240, 150,20);
     
        barra.setMinimum(0);
        barra.setMaximum(100);
        barra.setVisible(false);
        barra.setBackground(new java.awt.Color(240,240,240));
        barra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,0,0)));
        PanelP.add(barra);

        buttonGroup1.add(rECM);
        buttonGroup1.add(rKS);
        rECM.setSelected(true);
        rECM.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100,200)));

        buttonGroup2.add(rArchivo);
        buttonGroup2.add(rAutomatico);
        rAutomatico.setSelected(true);

    
         TabFolders.setEnabled(true);
         TabFolders.add(" Ejecución de la Simulación ",PanelP);
         TabFolders.setBounds(40, 20, 600, 400);
         getContentPane().add(TabFolders, java.awt.BorderLayout.CENTER);
         
         //Se adicionan los "escuchadores" de foco a los campos de texto de la primera pestaña
         
         SymFocus aSymFocus = new SymFocus(this);
	 texpresion.addFocusListener(aSymFocus);
	 tconstantes.addFocusListener(aSymFocus);
	 tvariables.addFocusListener(aSymFocus);
         vectores.addFocusListener(aSymFocus);      

         


        pack();

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 925) / 2, (screenSize.height - 582) / 2, 675, 482);
    }

    
    
    private void TExpresionActionPerformed(java.awt.event.ActionEvent e)
    {
        try {
            help.setText(help.getText()+"\nmenu para la expresion");
            
            
            
        }catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Se ha generado un error en tiempo de ejecución. Por favor reiniciar el programa", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    public void BeforeBEvolucion(String funcion, String variables, String constantes, String vec){
        texpresion.setText(funcion);
        tvariables.setText(variables);
        tconstantes.setText(constantes);
        vectores.setText(vec);
    }

    public Resultado BEvolucion2()
    {
            double probabilidadCruce = Double.parseDouble(tCruce.getText());
            double probabilidadMutacion = Double.parseDouble(tMutacion.getText());
            int cantidadPoblacion = Integer.parseInt(tPoblacion.getText());
            int numeroGeneraciones = Integer.parseInt(tGeneraciones.getText());
            String archivoEntrada = tEntrada.getText();
            String archivoSalida = tSalida.getText();
            boolean autoPoblacionInicial = rAutomatico.isSelected();
            double desde = Double.parseDouble(tValmin.getText());
            double hasta = Double.parseDouble(tValmax.getText());
            String archivoPoblacionInicial = tValarchivo.getText();
            int tipoCalculoError = (rKS.isSelected()) ? 1 : 2;
            double errorMinimo = Double.parseDouble(tErrormin.getText());
            double errorMaximo = Double.parseDouble(tErrormax.getText());
            double deltaError = Double.parseDouble(tDelta.getText());
            double porcPuntosks = Double.parseDouble(tKS.getText());
            String funcion = texpresion.getText();
            String variables = tvariables.getText();
            String constantes = tconstantes.getText();
            String vect = vectores.getText();

            if(funcion == "" || variables=="" || constantes =="" || vect=="")
            {
              TabFolders.setEnabled(false);
              JOptionPane.showMessageDialog(this, "Error en algun parametro..", "ERROR!", JOptionPane.ERROR_MESSAGE);

            }else{

//                texpresion.setEnabled(false);
//                tvariables.setEnabled(false);
//                tconstantes.setEnabled(false);
//                vectores.setEnabled(false);
//                letrero_inicial();


            Poblacion pb = new Poblacion(this,funcion,variables,constantes,vect,probabilidadCruce, probabilidadMutacion, cantidadPoblacion,
                    numeroGeneraciones, archivoEntrada, archivoSalida, autoPoblacionInicial, desde, hasta,
                    archivoPoblacionInicial, tipoCalculoError, errorMinimo, errorMaximo, deltaError, porcPuntosks,ventana);

            bEvolucion.setEnabled(false);
            //barra.setVisible(true);

            return pb.start();

            }
    return new Resultado("Error",0);
    }

    private void BEvolucionActionPerformed(java.awt.event.ActionEvent evt)
    {

        try
        {
            double probabilidadCruce = Double.parseDouble(tCruce.getText());
            double probabilidadMutacion = Double.parseDouble(tMutacion.getText());
            int cantidadPoblacion = Integer.parseInt(tPoblacion.getText());
            int numeroGeneraciones = Integer.parseInt(tGeneraciones.getText());
            String archivoEntrada = tEntrada.getText();
            String archivoSalida = tSalida.getText();
            boolean autoPoblacionInicial = rAutomatico.isSelected();
            double desde = Double.parseDouble(tValmin.getText());
            double hasta = Double.parseDouble(tValmax.getText());
            String archivoPoblacionInicial = tValarchivo.getText();
            int tipoCalculoError = (rKS.isSelected()) ? 1 : 2;
            double errorMinimo = Double.parseDouble(tErrormin.getText());
            double errorMaximo = Double.parseDouble(tErrormax.getText());
            double deltaError = Double.parseDouble(tDelta.getText());
            double porcPuntosks = Double.parseDouble(tKS.getText());
            String funcion = texpresion.getText();
            String variables = tvariables.getText();
            String constantes = tconstantes.getText();
            String vect = vectores.getText();
            
            if(funcion == "" || variables=="" || constantes =="" || vect=="")
            { 
              TabFolders.setEnabled(false);
              JOptionPane.showMessageDialog(this, "Error en algun parametro..", "ERROR!", JOptionPane.ERROR_MESSAGE);
              return;  
            }else{
                
//                texpresion.setEnabled(false);
//                tvariables.setEnabled(false);
//                tconstantes.setEnabled(false);
//                vectores.setEnabled(false);
//                letrero_inicial();
    
            
            pb = new Poblacion(this,funcion,variables,constantes,vect,probabilidadCruce, probabilidadMutacion, cantidadPoblacion,
                    numeroGeneraciones, archivoEntrada, archivoSalida, autoPoblacionInicial, desde, hasta,
                    archivoPoblacionInicial, tipoCalculoError, errorMinimo, errorMaximo, deltaError, porcPuntosks,ventana);

            bEvolucion.setEnabled(false);
            //barra.setVisible(true);
            
            pb.start();
              };

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error en algun parametro..", "ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public void setEstado(String name)
    {
        bEvolucion.setText(name);
    }
    
    public void activar()
    {
        bEvolucion.setEnabled(true);
        bEvolucion.setText("Iniciar");
    }
    
    
    
    public void Habilitar()
    {
       barra.setVisible(false);
       barra.setValue(0);
       Etiq.setVisible(false);
       texpresion.setEnabled(true);
       tvariables.setEnabled(true);
       tconstantes.setEnabled(true);
       vectores.setEnabled(true);
        
    }
    
    
    public void letrero_inicial()
    {
        help.setFont(new java.awt.Font("Arial",0, 12));
        help.setText("               MENU DE AYUDA\n\n"); 
        help.setText(help.getText()+" El estimador de parámetros es\n");
        help.setText(help.getText()+" una aplicación diseñada para\n");
        help.setText(help.getText()+" encontrar la mejor aproximación\n");
        help.setText(help.getText()+" para las constantes de una\n");
        help.setText(help.getText()+" función de varias variables y\n");
        help.setText(help.getText()+" en general no lineal.\n\n");
        help.setText(help.getText()+" En las casillas de texto ubicadas\n");
        help.setText(help.getText()+" en la parte izquierda se deben\n");
        help.setText(help.getText()+" introducir los valores que deben\n");
        help.setText(help.getText()+" corresponder a dicha función, las\n");
        help.setText(help.getText()+" variables de la expresión,las\n");
        help.setText(help.getText()+" constantes a encontrar y los\n");
        help.setText(help.getText()+" vectores iniciales de las\n");
        help.setText(help.getText()+" soluciones.El procedimiento para\n");
        help.setText(help.getText()+" hallar las constantes es a través\n");
        help.setText(help.getText()+" de un algoritmo evolutivo.");
        
    }

    private void RECMActionPerformed(java.awt.event.ActionEvent evt)
    {
        // TODO add your handling code here:
    }

    private void RKSActionPerformed(java.awt.event.ActionEvent evt)
    {
        // TODO add your handling code here:
    }
    
    //PUNTO DE ENTRADA MAIN DE LA APLICACIÒN

    public static void main(String args[])
    {

        try
        {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
        {
        }

        Thread tt = new Thread(new Runnable()
        {
            public void run()
            {
               
                //new JEntrada().setVisible(true);
                new MainFrame().setVisible(true);
//                int p=2;
//                new VentanaAlterna(p).setVisible(true); 
            }
        });

        tt.setPriority(Thread.MIN_PRIORITY);

        tt.start();

    }

    // -----------------------------------------------


}