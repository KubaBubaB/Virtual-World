package pojava;

import java.awt.*;

public class Jagodki extends Roslina{
    private static final int SILA=99;
    public Jagodki(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.WILCZE_JAGODY, SILA,0, punkt, swiat);
        SetKolor(new Color(171, 48, 219));
    }
}
