package pojava;

import java.awt.*;

public class Trawa extends Roslina{
    private static final int SILA=0;
    public Trawa(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.TRAWA, SILA,0, punkt, swiat);
        SetKolor(new Color(133, 247, 57));
    }
}
