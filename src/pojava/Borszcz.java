package pojava;

import java.awt.*;
import java.util.Random;

public class Borszcz extends Roslina{
    private static final int SILA=10;
    public Borszcz(Swiat swiat, Wspolrzedne punkt, int turaUrodzenia){
        super(Gatunek.BARSZCZ_SOSNOWSKIEGO, SILA,0, punkt, swiat);
        SetKolor(new Color(123, 148, 33));
    }

    public void Akcja() {
        Random rand = new Random();
        int szansa = rand.nextInt(100);
        Organizm tmp;
        if (szansa < 10) {
            Rozmnazanie(this);
        }
        if (this.punkt.GetY() != 0) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(this.punkt.GetY() - 1, punkt.GetX()))) {
                tmp = swiat.Get_Organizm(this.punkt.GetY() - 1, punkt.GetX());
                if (ToKill_or_NotToKill(tmp)) {
                    String pierwszy=Gatunek_to_string(swiat.Get_Organizm(this.punkt.GetY() - 1, punkt.GetX()));
                    int y=tmp.Get_Pozycja().GetY();
                    int x=tmp.Get_Pozycja().GetX();

                    swiat.pojemnik_na_stringi.Dodajtekst(pierwszy+"["+y+"]["+x+"] umiera od zatrucia barszczem"
                            +"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"]");
                    swiat.Zabij_Organizm(swiat.Get_Organizm(this.punkt.GetY() - 1, punkt.GetX()));
                }
            }
        }
        if (this.punkt.GetX() != swiat.Get_Szerokosc() - 1) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(this.punkt.GetY(), punkt.GetX() + 1))) {
                tmp = swiat.Get_Organizm(this.punkt.GetY(), punkt.GetX() + 1);
                if (ToKill_or_NotToKill(tmp)) {
                    String pierwszy=Gatunek_to_string(swiat.Get_Organizm(this.punkt.GetY(), punkt.GetX()+1));
                    int y=tmp.Get_Pozycja().GetY();
                    int x=tmp.Get_Pozycja().GetX();

                    swiat.pojemnik_na_stringi.Dodajtekst(pierwszy+"["+y+"]["+x+"] umiera od zatrucia barszczem"
                            +"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"]");
                    swiat.Zabij_Organizm(swiat.Get_Organizm(this.punkt.GetY(), punkt.GetX()+1));
                }
            }
        }
        if (this.punkt.GetY() != swiat.Get_Wysokosc() - 1) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne (this.punkt.GetY() + 1, punkt.GetX()))) {
                tmp = swiat.Get_Organizm(this.punkt.GetY() + 1, punkt.GetX());
                if (ToKill_or_NotToKill(tmp)) {
                    String pierwszy=Gatunek_to_string(swiat.Get_Organizm(this.punkt.GetY() + 1, punkt.GetX()));
                    int y=tmp.Get_Pozycja().GetY();
                    int x=tmp.Get_Pozycja().GetX();

                    swiat.pojemnik_na_stringi.Dodajtekst(pierwszy+"["+y+"]["+x+"] umiera od zatrucia barszczem"
                            +"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"]");
                    swiat.Zabij_Organizm(swiat.Get_Organizm(this.punkt.GetY() + 1, punkt.GetX()));
                }
            }
        }
        if (this.punkt.GetX() != 0) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne (this.punkt.GetY(), punkt.GetX() - 1))) {
                tmp = swiat.Get_Organizm(this.punkt.GetY(), punkt.GetX() - 1);
                if (ToKill_or_NotToKill(tmp)) {
                    String pierwszy=Gatunek_to_string(swiat.Get_Organizm(this.punkt.GetY(), punkt.GetX()-1));
                    int y=tmp.Get_Pozycja().GetY();
                    int x=tmp.Get_Pozycja().GetX();

                    swiat.pojemnik_na_stringi.Dodajtekst(pierwszy+"["+y+"]["+x+"] umiera od zatrucia barszczem"
                            +"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"]");
                    swiat.Zabij_Organizm(swiat.Get_Organizm(this.punkt.GetY(), punkt.GetX()-1));
                }
            }
        }
    }

    private boolean ToKill_or_NotToKill(Organizm schrodinger) {
        Gatunek kota = schrodinger.Get_gatunek();
        if (kota == Organizm.Gatunek.CZLOWIEK || kota == Organizm.Gatunek.WILK ||
                kota == Organizm.Gatunek.OWCA || kota == Organizm.Gatunek.LIS || kota == Organizm.Gatunek.ANTYLOPA ||
                kota == Organizm.Gatunek.ZOLW) {
            return true;
        } else return false;
    }
}
