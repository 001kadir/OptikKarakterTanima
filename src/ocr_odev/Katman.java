/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr_odev;


import java.util.*;

/**
 *
 * @author dombesz
 */
class Katman {
     
    Vector neurons;
    int size;

    public Katman(String l, int s) {
        String label;
        size = s;
        neurons = new Vector(); // Vektörel yapının kullanılması
        for (int i = 0; i < s; i++) {
            label = new String(l) + String.valueOf(i);
            neurons.addElement(new Noron(label));
        }
    }

    public Noron getNeuron(int i) {
        int j = 0;
        boolean found = false;
        Noron neuron = null;
        Enumeration e = neurons.elements();
        while (e.hasMoreElements()) {
            neuron = (Noron) e.nextElement();
            if (i == j) {
                found = true;
                break;
            } else {
                j++;
            }
        }
        if (found == false) {
            neuron = null;
        }
        return neuron;
    }

    public void computeOutputs() { // çıkışların  hesaplanması
        Noron neuron;
        Enumeration e = neurons.elements();
        while (e.hasMoreElements()) {
            neuron = (Noron) e.nextElement();
            neuron.computeOutput();
        }
    }

    public void computeBackpropDeltas(Vector s) // for output neurons
    {  // Deltanın bulunması
        Noron neuron;
        Enumeration e = neurons.elements();
        Enumeration d = s.elements();
        while (e.hasMoreElements()) {
            neuron = (Noron) e.nextElement();
            neuron.computeBackpropDelta(((Double) d.nextElement()).doubleValue());
        }
    }

    public void computeBackpropDeltas() // for hidden neurons
    {
        Noron neuron;
        Enumeration e = neurons.elements();
        while (e.hasMoreElements()) {
            neuron = (Noron) e.nextElement();
            neuron.computeBackpropDelta();
        }
    }

    public void computeWeights() { //ağırlıkların hesaplanması
        Noron neuron;
        Enumeration e = neurons.elements();
        while (e.hasMoreElements()) {
            neuron = (Noron) e.nextElement();
            neuron.computeWeight();
        }
    }

    public void print() { //yazdırma işleminin gerçekleştirilmesi 
        Noron neuron;
        Enumeration e = neurons.elements();
        while (e.hasMoreElements()) {
            neuron = (Noron) e.nextElement();
            neuron.print();
        }
    }
}

