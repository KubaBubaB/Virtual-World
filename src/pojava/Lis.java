package pojava;

import java.awt.*;
import java.util.Random;

public class Lis extends Zwierze{
    private static final int SILA=3;
    private static final int INICJATYWA=7;
    public Lis(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.LIS, SILA, INICJATYWA, punkt, swiat);
        SetKolor(new Color(179, 39, 14));
    }
    @Override
    public void Akcja() {
        boolean polnoc = true;
        boolean poludnie = true;
        boolean wschod = true;
        boolean zachod = true;
        int y = punkt.GetY();
        int x = punkt.GetX();
        if (y == 0) {
            polnoc = false;
        }
        if (x == swiat.Get_Szerokosc() - 1) {
            wschod = false;
        }
        if (y == swiat.Get_Wysokosc() - 1) {
            poludnie = false;
        }
        if (x == 0) {
            zachod = false;
        }
        if (polnoc) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y - 1, x))) {
                int sila_drugiego = swiat.Get_Organizm(y - 1, x).Get_Sila();
                if (this.sila < sila_drugiego) {
                    polnoc = false;
                }
            }
        }
        if (wschod) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y, x + 1))) {
                int sila_drugiego = swiat.Get_Organizm(y, x + 1).Get_Sila();
                if (this.sila < sila_drugiego) {
                    wschod = false;
                }
            }
        }
        if (zachod) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y, x - 1))) {
                int sila_drugiego = swiat.Get_Organizm(y, x - 1).Get_Sila();
                if (this.sila < sila_drugiego) {
                    zachod = false;
                }
            }
        }
        if (poludnie) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y + 1, x))) {
                int sila_drugiego = swiat.Get_Organizm(y + 1, x).Get_Sila();
                if (this.sila < sila_drugiego) {
                    poludnie = false;
                }
            }
        }
        if (!poludnie && !polnoc && !wschod && !zachod) {
            return;
        }
        boolean tmp = true;
        while (tmp) {
            Random rand = new Random();
            int random = rand.nextInt(100);
            if (random < 25) {//idz na polnoc
                if (polnoc) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() - 1, punkt.GetX())) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() - 1, punkt.GetX())) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY() - 1, punkt.GetX())))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLNOC);
                        punkt.SetY(punkt.GetY() - 1);
                    }
                    tmp = false;
                }
            } else if (random < 50) {//idz na wschod
                if (wschod) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() + 1)) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() + 1)) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY(), punkt.GetX() + 1)))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.WSCHOD);
                        punkt.SetX(punkt.GetX() + 1);
                    }
                    tmp = false;
                }
            } else if (poludnie) {//idz na poludnie
                if (punkt.GetY() != swiat.Get_Wysokosc() - 1) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() + 1, punkt.GetX())) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() + 1, punkt.GetX())) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY() + 1, punkt.GetX())))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLUDNIE);
                        punkt.SetY(punkt.GetY() + 1);
                    }
                    tmp = false;
                }
            } else {//idz na zachod
                if (zachod) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() - 1)) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() - 1)) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY(), punkt.GetX() - 1)))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.ZACHOD);
                        punkt.SetX(punkt.GetX() - 1);
                    }
                    tmp = false;
                }
            }
        }
    }
}
