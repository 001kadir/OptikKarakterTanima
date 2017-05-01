package ocr_odev;
import java.util.*;

/**
 *
 * @author dombesz
 */


public class Algilayici
{
  Vector           katmanlar;
  Vector           girisOrnekleri;
  Vector           cikisOrnekleri;
  public Katman     inputLayer;
  public Katman     outputLayer;
  public double    error;
  //Perceptonların oluşturulması 
  public Algilayici(int i,int o)
  {
    katmanlar          = new Vector();
    girisOrnekleri    = new Vector();
    cikisOrnekleri   = new Vector();
    inputLayer      = new Katman("I",i+1); // plus the bias
    outputLayer     = new Katman("O",o);
    katmanlar.addElement(inputLayer);
    katmanlar.addElement(outputLayer);
    error = 0.0;
  } 
  
  public void addLayer(int n,String name)
  {
    katmanlar.insertElementAt(new Katman(name,n),katmanlar.size()-1);
  }
  public Katman getLayer(int i)
  {
    int         j=0;
    boolean     found=false;
    Katman       layer=null;
    Enumeration e = katmanlar.elements();
    while(e.hasMoreElements())
    {
      layer = (Katman)e.nextElement();
      if (i==j)
      {
        found = true;
        break;
      } else j++;
    }
    if (found==false) layer = null;
    return layer;
  }
  
  public void connect(int sourceLayer,int sourceNeuron,
		      int destLayer,int destNeuron)
  {
    new Sinaps(getLayer(sourceLayer).getNeuron(sourceNeuron),
                getLayer(destLayer).getNeuron(destNeuron));
  }
  public void biasConnect(int destLayer,int destNeuron)
  {
    new Sinaps(inputLayer.getNeuron(inputLayer.size-1),
                getLayer(destLayer).getNeuron(destNeuron));
  }
  public void removeSamples()
  {
    girisOrnekleri.removeAllElements();
    cikisOrnekleri.removeAllElements();
  }
  public void addSample(Vector i,Vector o)
  {
    girisOrnekleri.addElement(i);
    cikisOrnekleri.addElement(o);
  }
  public void printSamples()
  {
    System.out.println(girisOrnekleri+"->"+cikisOrnekleri);
  }
  public Vector recognize(Vector iS)
  {
    initInputs(iS);
    propagate();
    Vector oS = getOutput();
    return oS;
  }
  public void ogren(int iterations,double globalError)
  {
    Enumeration iS;
    Enumeration oS;
    error = 0.0;
    int i=0;
    do
    {
      error = 0.0;
      iS = girisOrnekleri.elements();
      oS = cikisOrnekleri.elements();
      while(iS.hasMoreElements()) {
       Vector temp = (Vector) oS.nextElement ();
       learnPattern((Vector)iS.nextElement(),temp);
       error += computeError (temp);
      }
      i++;

    }while(i<iterations&&globalError<error);
  }
  void learnPattern(Vector iS, Vector oS)
  {
    initInputs(iS);
    propagate();
    bpAdjustWeights(oS);
  }
  void initInputs(Vector iS)
  {
    Noron neuron;
    Enumeration e = inputLayer.neurons.elements();
    Enumeration eS = iS.elements();
    while (eS.hasMoreElements())
    {
      neuron = (Noron)e.nextElement();
      neuron.output = ((Double)eS.nextElement()).doubleValue();
    }
    neuron = (Noron)e.nextElement(); // bias;
    neuron.output = 1.0;
  }
  void propagate()
  {
    Katman layer;
    Enumeration e = katmanlar.elements();
    e.nextElement(); // skip the input layer
    while(e.hasMoreElements())
    {
      layer = (Katman)e.nextElement();
      layer.computeOutputs();
    }
  }
  public Vector getOutput()
  {
    Vector oS = new Vector();
    Noron neuron;
    Enumeration e = outputLayer.neurons.elements();
    while(e.hasMoreElements())
    {
      neuron = (Noron) e.nextElement();
      oS.addElement(new Double(neuron.getOutput()));
    }
    return oS;
  }
  double computeError(Vector oS)
  {
    Noron neuron;
    double sum=0.0;
    double tmp;
    Enumeration e = outputLayer.neurons.elements();
    Enumeration eS = oS.elements();
    while (e.hasMoreElements())
    {
      neuron = (Noron)e.nextElement();
      tmp = ((Double)eS.nextElement()).doubleValue() - neuron.getOutput();
      sum += tmp * tmp;
    }
    return sum/2.0;
  }
  double currentError() {
    return error;
  }//compute:hesaplama
  //adjust:ayarlama
  void bpAdjustWeights(Vector oS)
  {
    outputLayer.computeBackpropDeltas(oS);
    for(int i=katmanlar.size()-2; i>=1; i--)
     ((Katman)katmanlar.elementAt(i)).computeBackpropDeltas();
    outputLayer.computeWeights();
    for(int i=katmanlar.size()-2; i>=1; i--)
     ((Katman)katmanlar.elementAt(i)).computeWeights();
  }
  void print()
  {
    Katman layer;
    Enumeration e = katmanlar.elements();
    while(e.hasMoreElements())
    {
      layer = (Katman)e.nextElement();
      layer.print();
    }
  }
}
