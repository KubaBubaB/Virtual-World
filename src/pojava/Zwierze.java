package pojava;

import java.util.Random;

public abstract class Zwierze extends Organizm {
    public Zwierze(Gatunek gatunek, int sila, int inicjatywa, Wspolrzedne punkt, Swiat swiat){
        super(gatunek, sila, inicjatywa, punkt, swiat);
    }
    protected boolean Kolizja(Organizm other) {
        int sila_other = other.Get_Sila();
        int sila_ruszajacego = Get_Sila();
        Random rand = new Random();
        int szansa = rand.nextInt(100);
        Gatunek gatunek = other.Get_gatunek();
        String pierwszy = Gatunek_to_string(this);
        String drugi = Gatunek_to_string(other);
        if (other.Get_gatunek() == Get_gatunek()) {
            if (((swiat.Get_Numer_Tury() - Get_Urodziny()) > 3) &&
                    ((swiat.Get_Numer_Tury() - other.Get_Urodziny()) > 3)) {
                Rozmnazanie(other);
            }
            return false;
        } else if (Czy_odbija_atak(other, this)) {
            Koentarze.Dodajtekst(drugi+"["+other.punkt.GetY()+"]["+other.punkt.GetX()+"] odbija atak "+
                    pierwszy+"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"]");
            return false;
        } else if (gatunek == Gatunek.ANTYLOPA && szansa > 50) {
            if (other.Specjalna_Akcja()) {
                return true;
            }
        }
        if (sila_ruszajacego == sila_other || sila_other < sila_ruszajacego) {
            if (gatunek == Gatunek.GUARANA) {
                this.sila = this.Get_Sila() + 3;
            }
            Koentarze.Dodajtekst(pierwszy+"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"] atakuje i zwycierza nad "+
                    drugi+"["+other.punkt.GetY()+"]["+other.punkt.GetX()+"]");
            swiat.Zabij_Organizm(other);
            return true;
        } else {
            Koentarze.Dodajtekst(pierwszy+"["+this.punkt.GetY()+"]["+this.punkt.GetX()+"] atakuje ale umiera od ran "+
                    drugi+"["+other.punkt.GetY()+"]["+other.punkt.GetX()+"]");
            swiat.Zabij_Organizm(this);
            return false;
        }
    }
    @Override
    public void Akcja() {
        Ruch();
    }

    protected void Ruch() {
        boolean tmp = true;
        while (tmp) {
            Random rand = new Random();
            int random = rand.nextInt(100);
            if (random < 25) {//idz na polnoc
                if (punkt.GetY() != 0) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() - 1, punkt.GetX())) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY() - 1, punkt.GetX())) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY() - 1, punkt.GetX())))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.POLNOC);
                        punkt.SetY(punkt.GetY() - 1);
                    }
                    tmp = false;
                }
            } else if (random < 50) {//idz na wschod
                if (punkt.GetX() != swiat.Get_Szerokosc() - 1) {
                    if (swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() + 1)) ||
                            (!swiat.Sprawdz_Kolizje(new Wspolrzedne(punkt.GetY(), punkt.GetX() + 1)) &&
                                    Kolizja(swiat.Get_Organizm(punkt.GetY(), punkt.GetX() + 1)))) {
                        swiat.Przesun_Organizm(punkt.GetY(), punkt.GetX(), Strona_Swiata.WSCHOD);
                        punkt.SetX(punkt.GetX() + 1);
                    }
                    tmp = false;
                }
            } else if (random < 75) {//idz na poludnie
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
                if (punkt.GetX() != 0) {
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

    public boolean Czy_odbija_atak(Organizm odbijajacy, Zwierze Atakujacy) {
        return (odbijajacy.Get_gatunek() == Gatunek.ZOLW) && (Atakujacy.Get_Sila() < 5);
    }
}
