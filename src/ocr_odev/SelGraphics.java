/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr_odev;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 *
 * @author Kadir
 */
// Grafik ayarlarının yapılması ve ImageCanvas sınıfının kullanılması  
public class SelGraphics extends Canvas {
  /**
   * The initial maximum value for the x-axis.
   */
  final int INIT_MAX_X = 20;
  /**
   * The initial maximum value for the y-axis
   */
  final int INIT_MAX_Y = 11;

    Graphics g;
    Vector  data,data2,xdata,xdata2;

  int ScaleMaxX = INIT_MAX_X;
  int ScaleMaxY = INIT_MAX_Y;

  /**
   * The constructor.
   * 
   */
  public SelGraphics() {
    super();

	// The initial size of the frame
	setSize(400,200);
    data  = new Vector(1,1);
    data.addElement( new Vector(100,20) );
    data2 = new Vector(1,1);
    data2.addElement( new Vector(100,20) );
    xdata = new Vector (1,1);
    xdata.addElement (new Vector (100,20));
    xdata2 = new Vector (1,1);
    xdata2.addElement (new Vector (100,20));
    setVisible(true);
  }
  
  public void update(Graphics g) {
    paint(g);
  }

  public void paintComponents(Graphics g) {  //çizim yapacak kullanıcın her harektinin kontrol edilmesi
    paint(this.getGraphics());
  }

  /**
   * Draws the chart on the screen.
   *
   * @param g	The drawing area
   */
  public void paint(Graphics g) {
	int i;
    int lx = 30;
    int ly = 30;
    double sx,sy;
    Graphics da;
      Rectangle size;
    Rectangle Diagram;
      Enumeration Traces, XValues;
    
    // get bounds
    da = this.getGraphics();
    size = this.getBounds();
    Diagram = new Rectangle(40, 5, size.width - 45, size.height - 25);

    da.setColor(Color.gray);
    da.draw3DRect(0, 0, size.width - 1, size.height - 1, false);
    da.setColor(Color.white);
    da.fillRect(1, 1, size.width - 2, size.height - 2);

    // paint drawing aera
    da.translate(Diagram.x, Diagram.y);
    Color paleyellow = new Color(255, 250, 230);
    da.setColor(paleyellow);
    da.fillRect(0, 0, Diagram.width, Diagram.height);
    da.setColor(Color.red);
    da.drawLine(0, 0, 0, Diagram.height);
    da.drawLine(0, Diagram.height, Diagram.width, Diagram.height);

    sx = ((double) Diagram.width) / ScaleMaxX;
    sy = ((double) Diagram.height) / ScaleMaxY;

 
    ly = 30;

    // draw grids
    da.setFont( new Font("Dialog", Font.PLAIN, 12) );
    
    da.setColor( new Color(255, 200, 100) );

	int x;
    for (i = 1; i < (ScaleMaxX/5); i++) {
	  x = (int)(sx * i * 5);

	  if (x > lx)	{
	    da.setColor(Color.orange);
	    da.drawLine(x, 0, x, Diagram.height);
	    da.setColor(Color.red);
	    da.drawString(Integer.toString(i * 5), x - 10, Diagram.height + 15);
	    lx = x + 30;
	  }
	}

	int y;
    for (i = 0; i <= (ScaleMaxY); i++) {
	  y = (int)(sy * i);

	  if ( y >= ly ) {
	    int ry = Diagram.height - y;

	    da.setColor(Color.orange);
	    da.drawLine(0, ry, Diagram.width, ry);
	    da.setColor(Color.red);
	    da.drawString(Integer.toString(i), -15, ry + 5);
	    ly = y + 30;
	  }
	} 

    // paint traces
    Traces = data.elements();
    XValues = xdata.elements ();
	Double cur;
	Integer curX;
    while ( Traces.hasMoreElements() ) {
	  Enumeration Current_Trace = null, Current_XValues = null;
	  try {
	    Current_Trace=( (Vector) Traces.nextElement()).elements();
	    Current_XValues = ((Vector) XValues.nextElement()).elements();
	  }	catch( NoSuchElementException e ) {}

	  if ( Traces.hasMoreElements() )	
		da.setColor(Color.magenta);
	  else
		da.setColor(Color.black);

	  if (Current_Trace != null) {
	    int nx, ny, ox = 0, oy = -1;
	   
	    for (i = 0; Current_Trace.hasMoreElements(); i++) {
		  cur = (Double)(Current_Trace.nextElement());
		  curX = (Integer) (Current_XValues.nextElement());
		  nx = (int) (sx * curX.intValue());  //(int)(sx * i);
		  ny = Diagram.height - (int)(cur.doubleValue() * sy);
		  if (oy >= 0)
			da.drawLine(ox, oy, nx, ny);
		  else
		        da.drawLine(ox, ny, nx, ny);
		  ox = nx;
		  oy = ny;
		}
	  }
	}
    Traces = data2.elements();
    XValues = xdata2.elements ();
    while ( Traces.hasMoreElements() ) {
	  Enumeration Current_Trace = null, Current_XValues = null;
	  try {
	    Current_Trace=( (Vector) Traces.nextElement()).elements();
	    Current_XValues = ((Vector) XValues.nextElement()).elements();
	  }	catch( NoSuchElementException e ) {}

	  if ( Traces.hasMoreElements() )	
		da.setColor(Color.magenta);
	  else
		da.setColor(Color.green);

	  if (Current_Trace != null) {
	    int nx, ny, ox = 0, oy = -1;
	   
	    for (i = 0; Current_Trace.hasMoreElements(); i++) {
		  cur = (Double)(Current_Trace.nextElement());
		  curX = (Integer) (Current_XValues.nextElement());
		  nx = (int) (sx * curX.intValue());  //(int)(sx * i);
		  ny = Diagram.height - (int)(cur.doubleValue() * sy);
		  if (oy >= 0)
			da.drawLine(ox, oy, nx, ny);
		  else
		        da.drawLine(ox, ny, nx, ny);
		  ox = nx;
		  oy = ny;
		}
	  }
	}
  }
    
  /**
   * Add the data to the graph.
   *
   * @param value            The new value for the graph
   */
  public void add (int inc, double value) {
    // add the value to the storage vector
    int nextX = inc;
    Vector thisXVector = (Vector)xdata.lastElement();
    if (thisXVector.size() != 0)
      nextX = ((Integer)(thisXVector.lastElement())).intValue() + inc;   
    thisXVector.addElement (new Integer (nextX));
    ((Vector)data.lastElement()).addElement( new Double(value) );
    
    if (value > ScaleMaxY) {
	  ScaleMaxY = (int)(2 * value);
	  paint(this.getGraphics());
	}
    else if (((Vector)data.lastElement()).size() == 1){
      	ScaleMaxY = (int) (1.05 * value);
	if (ScaleMaxY <= 1) ScaleMaxY = 2;
	paint (this.getGraphics ());
      }


    if (nextX > ScaleMaxX) {
      ScaleMaxX = nextX + 20;
      //ScaleMaxX = ((Vector)data.lastElement()).size() + 20;
	  paint(this.getGraphics());
	}

    // paint its value in the diagram

    int anz = ((Vector)data.lastElement()).size() - 1;
    {
	  Graphics da = this.getGraphics();
	  Rectangle size = this.getBounds();
	  Rectangle Diagram = new Rectangle(40, 5, size.width - 45,
			   			size.height - 25);
	  da.translate(Diagram.x, Diagram.y);
	  da.setColor(Color.black);
	  double sx = ((double) Diagram.width) / ScaleMaxX;
	  double sy = ((double) Diagram.height) / ScaleMaxY;
	  Double lastValue;
	  Integer lastX;

	  if (anz > 0) {
	    lastValue = (Double)
		(((Vector)data.lastElement()).elementAt(anz - 1));
	    lastX = (Integer)
	      (((Vector)xdata.lastElement()).elementAt(anz-1));
	  }
	  else {
	    lastX = new Integer (0);
	    lastValue = new Double (value);
	  }
	  int ox = (int)(sx * (lastX.intValue())); //(anz - 1));
	  int oy = Diagram.height - (int)(sy * lastValue.doubleValue());
	  int nx = (int)(sx * nextX); //anz);
	  int ny = Diagram.height - (int)(sy * value);
	
	  da.drawLine(ox, oy, nx, ny);
	}

  }  
  public void add2 (int inc, double value) {
    // add the value to the storage vector
    int nextX = inc;
    Vector thisXVector = (Vector)xdata2.lastElement();
    if (thisXVector.size() != 0)
      nextX = ((Integer)(thisXVector.lastElement())).intValue() + inc;   
    thisXVector.addElement (new Integer (nextX));
    ((Vector)data2.lastElement()).addElement( new Double(value) );
    
    if (value > ScaleMaxY) {
	  ScaleMaxY = (int)(2 * value);
	  paint(this.getGraphics());
	}
    else if (((Vector)data2.lastElement()).size() == 1){
      	ScaleMaxY = (int) (1.05 * value);
	if (ScaleMaxY <= 1) ScaleMaxY = 2;
	paint (this.getGraphics ());
      }


    if (nextX > ScaleMaxX) {
      ScaleMaxX = nextX + 20;
      //ScaleMaxX = ((Vector)data2.lastElement()).size() + 20;
	  paint(this.getGraphics());
	}

    // paint its value in the diagram

    int anz = ((Vector)data2.lastElement()).size() - 1;
    {
	  Graphics da = this.getGraphics();
	  Rectangle size = this.getBounds();
	  Rectangle Diagram = new Rectangle(40, 5, size.width - 45,
			   			size.height - 25);
	  da.translate(Diagram.x, Diagram.y);
	  da.setColor(Color.green);
	  double sx = ((double) Diagram.width) / ScaleMaxX;
	  double sy = ((double) Diagram.height) / ScaleMaxY;
	  Double lastValue;
	  Integer lastX;

	  if (anz > 0) {
	    lastValue = (Double)
		(((Vector)data2.lastElement()).elementAt(anz - 1));
	    lastX = (Integer)
	      (((Vector)xdata2.lastElement()).elementAt(anz-1));
	  }
	  else {
	    lastX = new Integer (0);
	    lastValue = new Double (value);
	  }
	  int ox = (int)(sx * (lastX.intValue())); //(anz - 1));
	  int oy = Diagram.height - (int)(sy * lastValue.doubleValue());
	  int nx = (int)(sx * nextX); //anz);
	  int ny = Diagram.height - (int)(sy * value);
	
	  da.drawLine(ox, oy, nx, ny);
	}

  }  

  public void add (double value) {
    add (1,value);

  }

  /**
   * Initialize the graph.
   */
  public void startNewTrace() {
    data.addElement(new Vector(100, 20));
    xdata.addElement (new Vector (100, 20));
    data2.addElement(new Vector(100, 20));
    xdata2.addElement (new Vector (100, 20));
    paint(this.getGraphics());
  }

  /*
   * Clear the graph.
   */
  public void clear() {
    ScaleMaxX = INIT_MAX_X;
    ScaleMaxY = INIT_MAX_Y;
    data = new Vector(1, 1);
    data.addElement( new Vector(100, 20) );
    xdata = new Vector (1,1);
    xdata.addElement (new Vector (100, 20));
    data2 = new Vector(1, 1);
    data2.addElement( new Vector(100, 20) );
    xdata2 = new Vector (1,1);
    xdata2.addElement (new Vector (100, 20));
    paint(this.getGraphics());
  }

}
