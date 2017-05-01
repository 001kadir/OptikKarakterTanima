package ocr_odev;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author dombesz
 */
class Ornek {
// Yapay sinir ağının  eğitilmesi için gereklis input.dat uzantılı dosyanın açılması 
    public double input[][][][];
    public final int MAX_Ornek = 10;
    public final int MAX_RAKAM = 10;
    InputStream in;
    InputStreamReader isr;
    BufferedReader file;
    String line;

    public Ornek() {
        input = new double[MAX_RAKAM][MAX_Ornek][15][20];
        openSampleFile();
    }

    void openSampleFile()
    {
        int i = 0, j = 0, k = 0, l = 0;
        byte b[];
        char c;

        b = new byte[1];


        try {
            in = new BufferedInputStream(new FileInputStream("input.dat"));
            isr = new InputStreamReader(in);

            file = new BufferedReader(isr);

            for (l = 0; l < MAX_RAKAM; l++) {
                for (k = 0; k < MAX_Ornek; k++) {
                    for (j = 0; j < 20; j++) {
                        line = file.readLine();
                        // System.out.println(line);
                        for (i = 0; i < 15; i++) {
                            c = line.charAt(i);
                            if (c == 'X') {
                                input[l][k][i][j] = 1.0;
                            } else if (c == '.') {
                                input[l][k][i][j] = 0.0;
                            }
                        }
                    }
                    line = file.readLine();
                }
                line = file.readLine();
            }
        } catch (Exception e) {
            System.err.println("Error when loading file input.dat: " + e+"lkij="+l+" "+k+" "+i+" "+j);
        }


    }
}

