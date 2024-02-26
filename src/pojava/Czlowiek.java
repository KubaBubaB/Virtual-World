package pojava;

import java.awt.*;
import java.util.Random;

import static pojava.Organizm.Strona_Swiata.BRAK;

public class Czlowiek extends Zwierze{
    private static final int SILA=5;
    private static final int INICJATYWA=4;
    private Organizm.Strona_Swiata strona=BRAK;
    public int licznik = 0;
    public int cooldown = 0;
    boolean AktywnoscSkilla = false;
    public Czlowiek(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.CZLOWIEK, SILA, INICJATYWA, punkt, swiat);
        SetKolor(new Color(32, 160, 186));
    }
    @Override
    public void Akcja(){
        if (cooldown > 0) {
            cooldown--;
        }
        if (AktywnoscSkilla && licznik < 5) {
            licznik++;
        }
        if (licznik == 5) {
            AktywnoscSkilla = false;
            cooldown = 5;
            licznik = 0;
        }
        else if(licznik>0){
            AktywnoscSkilla=true;
        }
        if (AktywnoscSkilla) {
            Random rand = new Random();
            int szansa = rand.nextInt(100);
            if (szansa < 50 || licznik < 3) {
                Ruch();
                Ruch();
            } else Ruch();
        } else {
            Ruch();
        }
        set_Strona(BRAK);
    }
    public int Get_Cooldown(){return cooldown;}
    public int Get_Licznik(){return licznik;}
    public void Set_Cooldown(int cool){
        this.cooldown=cool;
    }
    public void Krecenie_Licznika(int numerek){
        this.licznik=numerek;
    }

    @Override
    public void Ruch(){
        switch (strona){
            case POLNOC:
                if (punkt.GetY() != 0) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() - 1, punkt.GetX())) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() - 1, punkt.GetX())) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY() - 1, punkt.GetX())))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLNOC);
                        punkt.SetY(punkt.GetY() - 1);
                    }
                }
                break;
            case WSCHOD:
                if (punkt.GetX() != swiat.Get_Szerokosc() - 1) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() + 1)) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() + 1)) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY(), punkt.GetX() + 1)))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.WSCHOD);
                        punkt.SetX(punkt.GetX() + 1);
                    }
                }
                break;
            case POLUDNIE:
                if (punkt.GetY() != swiat.Get_Wysokosc() - 1) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() + 1, punkt.GetX())) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() + 1, punkt.GetX())) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY() + 1, punkt.GetX())))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLUDNIE);
                        punkt.SetY(punkt.GetY() + 1);
                    }
                }
                break;
            case ZACHOD:
                if (punkt.GetX() != 0) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() - 1)) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() - 1)) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY(), punkt.GetX() - 1)))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.ZACHOD);
                        punkt.SetX(punkt.GetX() - 1);
                    }
                }
                break;
            default:
                System.out.println("Czlowiek sie nie ruszyl");
        }
    }

    public void set_Strona(Organizm.Strona_Swiata strona){this.strona=strona;}
    public boolean Get_AktywnosSkilla(){return AktywnoscSkilla;}
    public void Aktywuj_Umiejke(){
            AktywnoscSkilla=true;
    }
}
