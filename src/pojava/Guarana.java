package pojava;

import java.awt.*;

public class Guarana extends Roslina{
    private static final int SILA=0;
    public Guarana(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.GUARANA, SILA,0, punkt, swiat);
        SetKolor(new Color(224, 99, 99));
    }
}
