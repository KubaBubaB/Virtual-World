package pojava;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze{
    private static final int SILA=4;
    private static final int INICJATYWA=4;
    Antylopa(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.ANTYLOPA, SILA, INICJATYWA, punkt, swiat);
        SetKolor(new Color(204,132,49));
    }

    public boolean Specjalna_Akcja() {
        int y = punkt.GetY();
        int x = punkt.GetX();
        boolean polnoc = true;
        boolean wschod = true;
        boolean poludnie = true;
        boolean zachod = true;

        if (y != 0) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y-1, x))) polnoc = false;
        } else polnoc = false;
        if (x != swiat.Get_Szerokosc() - 1) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y, x + 1))) wschod = false;
        } else wschod = false;
        if (y != swiat.Get_Wysokosc() - 1) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y + 1, x))) poludnie = false;
        } else poludnie = false;
        if (x != 0) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y, x - 1))) zachod = false;
        } else zachod = false;

        if (!polnoc && !wschod && !poludnie && !zachod) return false;
        else {
            while (true) {
                Random rand = new Random();
                int szansa = rand.nextInt(100);
                if (polnoc && szansa < 25) {
                    swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLNOC);
                    punkt.SetY(punkt.GetY() - 1);
                    return true;
                } else if (wschod && szansa < 50) {
                    swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.WSCHOD);
                    punkt.SetX(punkt.GetX() + 1);
                    return true;
                } else if (poludnie && szansa < 75) {
                    swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLUDNIE);
                    punkt.SetY(punkt.GetY() + 1);
                    return true;
                } else if (zachod) {
                    swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.ZACHOD);
                    punkt.SetX(punkt.GetX() - 1);
                    return true;
                }
            }
        }
    }
    @Override
    public void Akcja() {
        Ruch();
        Ruch();
    }
}
