/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr_odev;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
/**
 *
 * @author Kadir
 */
class ErrorGraph extends Panel {

    SelGraphics graph;//Arayüz bileşenlerinin tanımlanması
    TextField error;
    JButton clear;
    /**
     * The name of the clear button.
     */
    protected final static String CLEAR = "Temizle"; //Arayüz üzerinde görülen grafiğin kodlanması;

    /**
     * The name of the close button.
     */
    //protected final static String CLOSE = "Close";
    ErrorGraph() {

        graph = new SelGraphics();

        setLayout(new BorderLayout());
        add("North", new Label("  Error Graph")); //Grağin adının tanımlanması
        add("Center", graph);
        Panel pSouth = new Panel();
        clear = new JButton(CLEAR);
        ActionListener clearListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                clearPerformed();
            }
        };
        clear.addActionListener(clearListener);
        pSouth.add(clear);
        add("South", pSouth);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();//Boyutların ayarlanmsı
        this.setLocation(Math.abs(dimension.width - this.getSize().width), Math.abs(dimension.height - this.getSize().height));
    }

    public void clearPerformed() {  //Arayüz bileşindeki temizelene tuşuna basıldığında çalışan metod 
        graph.clear();
    }
}





