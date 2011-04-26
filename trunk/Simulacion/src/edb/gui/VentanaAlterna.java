/*
 * VentanaAlterna.java
 *
 *
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package edb.gui;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import edb.alggen.Poblacion;



/**
 *
 * 
 */
public class VentanaAlterna extends javax.swing.JFrame{
    
    private javax.swing.JLabel jEtiqconst;
    private javax.swing.JLabel[] jEtiqerror;
    private javax.swing.JLabel[] jEtiqconstantes;
    private javax.swing.JLabel jEtiqtipoerror;
    
    private javax.swing.JPanel jPanelconst;
 
    
    private javax.swing.JEditorPane[] tconstantes;
    public javax.swing.JEditorPane error;
    
    int numConstantes;
    
    
    
    /** Creates a new instance of VentanaAlterna */
    public VentanaAlterna(int constantes,String nombres[]) {
         initComponents(constantes,nombres);
    }

    private void initComponents(int constantes,String nombres[])
    {// GEN-BEGIN:
        setTitle("Resultados Parciales");
        numConstantes = constantes;
        tconstantes = new javax.swing.JEditorPane[numConstantes];
        jEtiqerror = new javax.swing.JLabel[2];
        jEtiqconstantes= new javax.swing.JLabel[numConstantes];
        error = new javax.swing.JEditorPane();
        jEtiqerror[0] = new javax.swing.JLabel();
        jEtiqerror[1] = new javax.swing.JLabel();
        jEtiqconst = new javax.swing.JLabel();
        jEtiqtipoerror= new javax.swing.JLabel();
        
        jPanelconst = new javax.swing.JPanel();
        
        
        for(int i=0;i<numConstantes;i++)
        {
            jEtiqconstantes[i]= new javax.swing.JLabel();
            tconstantes[i] = new javax.swing.JEditorPane();
        }
        
        getContentPane().setLayout(null);
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        
        jPanelconst.setLayout(null);
        int limite=(43*numConstantes)+109;

        jPanelconst.setBackground(new java.awt.Color(240, 240, 240));
        jPanelconst.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,100,200)));
        jPanelconst.setBounds(15,15,198,limite);
        int y=45;
        
        
         jEtiqerror[0].setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
         jEtiqerror[1].setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
         jEtiqconst.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
         
         jEtiqconst.setText("Variación de los Parámetros :");
         jEtiqerror[0].setText("Variación del Error :");
         jEtiqerror[1].setText("Error =");
         
         jEtiqconst.setBounds(15,10,180,18);
         jPanelconst.add(jEtiqerror[0]);
         jPanelconst.add(jEtiqerror[1]);
         jPanelconst.add(jEtiqconst);
         jPanelconst.add(error);
       
        
        for(int i=0;i<numConstantes;i++)
        {
            
            jEtiqconstantes[i].setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
            jEtiqconstantes[i].setText(nombres[i]+" = ");
            tconstantes[i].setFont(new java.awt.Font("Microsoft Sans Serif", 0, 10));
            tconstantes[i].setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,100,200)));
            jPanelconst.add(jEtiqconstantes[i]);
            jPanelconst.add(tconstantes[i]);
            jEtiqconstantes[i].setBounds(10,y,70,13);
            tconstantes[i].setEditable(false);
            tconstantes[i].setBounds(40,y,140,18);
            tconstantes[i].setBackground(new java.awt.Color(240, 240, 240));
            tconstantes[i].setText("");
            y+=43;
 
        }
         
         jEtiqerror[0].setBounds(35,y,180,18);
         jEtiqerror[1].setBounds(10,y+30,70,13);
         error.setEditable(false);
         error.setBackground(new java.awt.Color(240, 240, 240));
         error.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,100,200)));
         error.setBounds(50,y+30,130,18);
         error.setText("");
        
        // Coordenada x, coordenada y, ancho, altura
        
        
        pack();
        getContentPane().add(jPanelconst, java.awt.BorderLayout.CENTER);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width + 465) / 2, (screenSize.height - 582) / 2,235,limite+62);
        
        
    }
    
     public void setActualizar(double cromo[],double errorp)
    {
         //cromo[0]=3.26541E-9;
         
         String valores="";
         for(int i=0;i<numConstantes;i++)
          {
              valores+=cromo[i];
              tconstantes[i].setText(valores);
              valores="";
          }
          
          valores=""+errorp;
          error.setText(valores);
    }
 
     
}
