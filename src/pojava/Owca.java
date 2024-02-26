package pojava;

import java.awt.*;

public class Owca extends Zwierze{
    private static final int SILA=4;
    private static final int INICJATYWA=4;
    public Owca(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.WILK, SILA, INICJATYWA, punkt, swiat);
        SetKolor(new Color(138, 127, 125));
    }
}
