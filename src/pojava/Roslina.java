package pojava;

import java.util.Random;

public abstract class Roslina extends Organizm{
    public Roslina(Gatunek gatunek, int sila, int inicjatywa, Wspolrzedne punkt, Swiat swiat){
        super(gatunek, sila,inicjatywa, punkt, swiat);
        this.inicjatywa=0;
    }
    @Override
    public void Akcja() {
        Random rand = new Random();
        int szansa = rand.nextInt(100);
        if (szansa < 10) {
            Rozmnazanie(this);
        }
    }
}
