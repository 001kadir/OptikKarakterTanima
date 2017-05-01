package ocr_odev;

import java.util.*;

/**
 *
 * @author dombesz
 */
class Sinaps
{
    //Synapse'ler Sinir hücreleri arasındaki bağlantılardır.
    //Random ağrılık verilerek bunlar oluşturulmuştur.
  double agirlik;
  double veri;
  Noron from;
  Noron to;
  static Random random = new Random();

  Sinaps(Noron f,Noron t)
  {
    from   = f;
    to     = t;
    agirlik = random.nextDouble() / 5.0;
    veri   = 0.0;
    f.outlinks.addElement(this);
    t.inlinks.addElement(this);
  }
  public double getWeight()
  {
    return agirlik;
  }
}
