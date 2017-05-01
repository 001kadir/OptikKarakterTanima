/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ocr_odev;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 //Verilen giriş ve çıkışlara bakılarak sonuçların yüzdelik dilimlerinin ekrana yazdırılması
/**
 *
 * @author dombesz
 */
class Sonuclar extends JPanel {

       public JPanel results(Vector o){
        JPanel textPanel = new JPanel();
        textPanel.setBounds(new Rectangle(0, 0, 500, 500));
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       
	Double[] v = new Double[10];
	String[] plot = new String[10];
	double max = 0.0,vv;
	int j=0;
	Enumeration e = o.elements();
	for(int i=0;i<10;i++) {
	    v[i]=(Double)e.nextElement();
	    vv = v[i].doubleValue();
            
	    if      (vv < 0.1) plot[i]=new String("                               ");
	    else if (vv < 0.2) plot[i]=new String("O                          ");
	    else if (vv < 0.3) plot[i]=new String("OO                       ");
	    else if (vv < 0.4) plot[i]=new String("OOO                    ");
	    else if (vv < 0.5) plot[i]=new String("OOOO                 ");
	    else if (vv < 0.6) plot[i]=new String("OOOOO              ");
	    else if (vv < 0.7) plot[i]=new String("OOOOOO           ");
	    else if (vv < 0.8) plot[i]=new String("OOOOOOO        ");
	    else if (vv < 0.9) plot[i]=new String("OOOOOOOO     ");
	    else               plot[i]=new String("OOOOOOOOO  ");

	    if (v[i].doubleValue()>max) {
		j=i;
		max = v[i].doubleValue();
                
	    }
	}
        //JOptionPane.showMessageDialog(null, max);
	for(int i=0;i<10;i++) {
	   JLabel label = new JLabel("             "+String.valueOf(i)+": "+plot[i]
                   +new Integer((int)(v[i].doubleValue()*100))+"%             ");
	    label.setFont(new Font("helvetica",Font.BOLD,15));
	    if (i==j) label.setForeground(Color.red);
            
	    textPanel.add(label);
            
        }
	
	return textPanel;
}
}