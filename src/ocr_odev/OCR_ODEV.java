/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr_odev;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Kadir
 */
public class OCR_ODEV {
private static JButton test; // Ekran arayüzlerinin değişkenlerinin tanımlanması
    private static JButton learn;
    private static JButton load;
    private static JPanel rez;
    private static Sonuclar res;
    private static JPanel j=new JPanel();
    private static ImageCanvas imageCanvas;
    private static JPanel parameters;
    private static ErrorGraph errorGraph;
    private static JTextField iterationsTF;
    private static JTextField learningRateTF;
    private static JTextField momentumTF;
    static Algilayici algilayici; // Algilayici sınıfındaki değişkenleri ve metodları kullanmak için oluşturulan nesne
    static int              n_in,n_out;
    static JTextField hiddenTF[] = new JTextField[3];
    /**
     * @param args the command line arguments
     */
  public static void main(String[] args){
        n_in=8*10;
        n_out=10;
        initGui();
        disableBtns(); //butonların başlangıçta 
        
    }
    public static void initGui() { 
        
        JPanel mainPane=new JPanel();
        mainPane.setLayout(new BorderLayout());
        ////////////////////////////////////////////////////////////////////////
        //TOP PANEL                                                           //
        ////////////////////////////////////////////////////////////////////////
        JPanel top=new JPanel();
        //top.setBackground(Color.BLUE);
        top.setBorder(BorderFactory.createEtchedBorder());
	JPanel topPanel = new JPanel();
	topPanel.setLayout(new BorderLayout());
	JPanel controls1 = new JPanel();
	JPanel controls2 = new JPanel();
	JButton init = new JButton("Init");
	ActionListener initListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		
		initPerceptron();
                load.setEnabled(true);
	    }

            
	};
	init.addActionListener(initListener);
	controls1.add("North",init);
	controls1.add("North",new JLabel("H1:"));
	hiddenTF[0] = new JTextField("10",3);
	controls1.add("North",hiddenTF[0]);
	controls1.add("North",new JLabel("H2:"));
	hiddenTF[1] = new JTextField("",3);
	controls1.add("North",hiddenTF[1]);
	controls1.add("North",new JLabel("H3:"));
	hiddenTF[2] = new JTextField("",3);
	controls1.add("North",hiddenTF[2]);
        top.add(controls1);
        mainPane.add(top,BorderLayout.PAGE_START);
        
        ////////////////////////////////////////////////////////////////////////
        //LEFT PANEL                                                          //
        ////////////////////////////////////////////////////////////////////////
        JPanel left=new JPanel();
        //left.setBackground(Color.CYAN);
        left.setBorder(BorderFactory.createEtchedBorder());
        left.setLayout(new BorderLayout());
        JPanel control=new JPanel();
        load=new JButton("Yükle");  
        ActionListener loadListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		setSample();
                learn.setEnabled(true);  
	    }
	};
        load.addActionListener(loadListener);
        learn = new JButton("Öğren"); 
	ActionListener learnListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) { // öğren butonuna badıldığında çalışan metodlar
		disableBtns();
                learn();
                enableBtns();
	    }
	};
	learn.addActionListener(learnListener);
        
        control.setLayout(new FlowLayout(FlowLayout.LEFT));
       // control.setBackground(Color.ORANGE);
        
        control.add(load);
        control.add(learn);
        errorGraph=new ErrorGraph();
        
        parameters = new JPanel();
	parameters.add(new JLabel("Momentum:"));
	momentumTF = new JTextField(String.valueOf(Noron.momentum),4);
	parameters.add(momentumTF);
	parameters.add(new JLabel("Öğrenme Hızı:"));
	learningRateTF = new JTextField(String.valueOf(Noron.learningRate),4);
	parameters.add(learningRateTF);
	parameters.add(new JLabel("Tekrarlamalar:")); //Momentum, öğrenme hızı, iteration'ların bileşenlerinin eklenmesi 
	iterationsTF = new JTextField("10",4);
	parameters.add(iterationsTF);
        left.add(parameters,BorderLayout.SOUTH);
        left.add(control,BorderLayout.NORTH);
        left.add(errorGraph,BorderLayout.CENTER);
        mainPane.add(left,BorderLayout.WEST);
           
        
        ////////////////////////////////////////////////////////////////////////
        //RIGHT PANEL                                                         //
        ////////////////////////////////////////////////////////////////////////
        JPanel right=new JPanel();
        imageCanvas = new ImageCanvas();
        //right.setBackground(Color.GREEN);
        right.setBorder(BorderFactory.createEtchedBorder());
        test = new JButton("Test");
	ActionListener testListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		test();
	    }
	};
        test.addActionListener(testListener);
        JButton clear = new JButton("Temizle");
        
	ActionListener clearListener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		imageCanvas.clear();
	    }
	};
	
        clear.addActionListener(clearListener);
        controls2.setLayout(new FlowLayout(FlowLayout.LEFT));
        controls2.add(test);
        controls2.add(clear);
            
        j.setLayout(new FlowLayout());
        
        imageCanvas.setPreferredSize(new Dimension(130, 180)); //Kullanıcının rakamı girereken fare kullanacağı için ImageCanvas sınıfının arayüz özelliklerinin eklenmesi
        j.add(imageCanvas,FlowLayout.LEFT);
        //j.setBackground(Color.PINK);
        
        right.setLayout(new BorderLayout());
        Vector e=new Vector();
        e.add(0.0);e.add(0.1);e.add(0.2);e.add(0.3);e.add(0.4);
        e.add(0.5);e.add(0.6);e.add(0.7);e.add(0.8);e.add(0.9);
        res=new Sonuclar();
        rez=res.results(e);       
        rez.setLayout(new BoxLayout(rez, BoxLayout.Y_AXIS));
        rez.setSize(400,500);
        j.add(rez,BorderLayout.SOUTH);
        
        right.add(controls2,BorderLayout.NORTH);
        right.add(j,BorderLayout.CENTER);
     
        mainPane.add(right,BorderLayout.CENTER);

        JFrame mainFrame=new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPane);
        mainFrame.setResizable(false);
        mainFrame.setSize(800, 510);
        mainFrame.setLocation(350, 150);
        mainFrame.setVisible(true);
     }
    public static void initPerceptron()
    {
	int hid[] = new int[3];
	int nLayer,i,j,k;
	String text;
       
	algilayici = new Algilayici(n_in,n_out);
	nLayer=0;
	for(i=0;i<3;i++)
	    {
		text = hiddenTF[i].getText();
		if ("".equals(text)) hid[i]=0;
		else hid[i] = (Integer.valueOf(text)).intValue();
		if (hid[i]!=0)
		    {
			String s = "H" + String.valueOf(i) + "|";
			algilayici.addLayer(hid[i],s);
			nLayer++;
		    }
	    }
	for(j=0;j<nLayer;j++)
	    for(i=0;i<hid[j];i++) algilayici.biasConnect(j+1,i);
	algilayici.biasConnect(nLayer+1,0); // for the output

	if (nLayer==0)
	    for(i=0;i<n_in;i++) for(j=0;j<n_out;j++)
		algilayici.connect(0,i,1,j);
	else
	    {
		// connect the inputs to the first hidden layer
		for(i=0;i<hid[0];i++) for(j=0;j<n_in;j++)
		    algilayici.connect(0,j,1,i);
		// connect the hidden layers together 
		for(k=0;k<nLayer-1;k++) for(i=0;i<hid[k];i++) for(j=0;j<hid[k+1];j++)
		    algilayici.connect(k+1,i,k+2,j);
		// connect the last hidden layer to the output
		for(i=0;i<hid[nLayer-1];i++) for(j=0;j<n_out;j++)
		    algilayici.connect(nLayer,i,nLayer+1,j);
	    }algilayici.getLayer(1).getNeuron(1).print();
    }
    public static void setSample()
    {
	Vector iS;
	Vector oS;// Alınan örneklerin işlenmesi
	int i,j,k,l;
	double s;
        Ornek sample=new Ornek();
	for(l=0;l<sample.MAX_RAKAM;l++) {
	    for(k=0;k<sample.MAX_Ornek;k++) {
		iS = new Vector();
		oS = new Vector();
		for(j=0;j<sample.MAX_RAKAM;j++) {
		    if (j==l) oS.addElement(new Double(1.0));
		    else oS.addElement(new Double(0.0));
		}
		for(j=0;j<20;j+=2) {
		    for(i=0;i<15;i+=2) {
			s = sample.input[l][k][i][j] + sample.input[l][k][i][j+1];
                        //System.out.println("("+sample.input[l][k][i][j] +" "+ sample.input[l][k][i][j+1]+")\n");
			if (i!=14) {
			    s += sample.input[l][k][i+1][j] + sample.input[l][k][i+1][j+1];
			    s /= 4.0;
			} else s /= 2.0;
			iS.addElement(new Double(s));
		    }
		}
		algilayici.addSample(iS,oS);
	    }
	}
        
    }
    public static void learn()
    {
	setSample();  //Öğrenme sürecinin gerçekleştiren sınıfların modullerinin kullanılması
	Integer iterations = Integer.valueOf(iterationsTF.getText());
	Double learningRate = Double.valueOf(learningRateTF.getText());
        Double momentum=Double.valueOf(momentumTF.getText());
	Noron.learningRate = learningRate.doubleValue();
        Noron.momentum=momentum.doubleValue();
	int max = iterations.intValue();
	for(int i = 0;i < max; i++)
	    {
		algilayici.ogren(10,0.2);
		if (i%1==0)
		    {
			if (errorGraph==null) errorGraph = new ErrorGraph();
			errorGraph.graph.add(1,algilayici.currentError());
		    }
	    }
	
	if (errorGraph!=null) errorGraph.show();
    }
    public static void test() // Test verileri ve Sonuclar sıfının kullanılması
    {
	Vector iS,oS;
	int i,k;
	double s;
	iS = new Vector();
	for(k=0;k<20;k+=2) for(i=0;i<15;i+=2) {
	    s = imageCanvas.inputSpace[i][k] + imageCanvas.inputSpace[i][k+1];
	    if (i!=14) {
		s += imageCanvas.inputSpace[i+1][k] + imageCanvas.inputSpace[i+1][k+1];
		s /= 4.0;
	    } else s /= 2.0;
	    iS.addElement(new Double(s));
	}
	oS = algilayici.recognize(iS);
	
       rez=res.results(oS);
       rez.setLayout(new BoxLayout(rez, BoxLayout.Y_AXIS));
       rez.setSize(200,500);
       j.remove(1);
       j.add(rez,BorderLayout.SOUTH);
       j.revalidate();
	
    }
    static void disableBtns(){
    load.setEnabled(false);
    learn.setEnabled(false);
    test.setEnabled(false);
    }
    static void enableBtns(){
    load.setEnabled(true);
    learn.setEnabled(true);
    test.setEnabled(true);
    }
    
}
