package pojava;

import java.awt.*;
import java.util.Random;

public class Mlecyk extends Roslina{
    private static final int SILA=0;
    public Mlecyk(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.MLECZ, SILA,0, punkt, swiat);
        SetKolor(new Color(227, 227, 30));
    }
    @Override
    public void Akcja() {
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int szansa = rand.nextInt(100);
            if (szansa < 10) {
                Rozmnazanie(this);
            }
        }
    }
}
