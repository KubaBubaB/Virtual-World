package pojava;

import java.awt.*;

public class Wilk extends Zwierze{
    private static final int SILA=9;
    private static final int INICJATYWA=5;
    public Wilk(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.WILK, SILA, INICJATYWA, punkt, swiat);
        SetKolor(new Color(82, 75, 74));
    }
}
