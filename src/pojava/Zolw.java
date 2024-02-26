package pojava;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze{
    private static final int SILA=2;
    private static final int INICJATYWA=1;
    public Zolw(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.ZOLW, SILA, INICJATYWA, punkt, swiat);
        SetKolor(new Color(29, 71, 28));
    }
    @Override
    public void Akcja() {
        Random rand = new Random();
        int szansa = rand.nextInt(100);
        if (szansa >= 75) {
            Ruch();
        }
    }
}
