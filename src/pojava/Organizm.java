package pojava;

import java.awt.*;
import java.util.Random;

public abstract class Organizm {
    public abstract void Akcja();
    public enum Gatunek {
        CZLOWIEK,
        WILK,
        OWCA,
        LIS,
        ZOLW,
        ANTYLOPA,
        TRAWA,
        MLECZ,
        GUARANA,
        WILCZE_JAGODY,
        BARSZCZ_SOSNOWSKIEGO
    }

    public enum Strona_Swiata{
        POLNOC(0),
        WSCHOD(1),
        POLUDNIE(2),
        ZACHOD(3),
        BRAK(4);

        private final int value;

        Strona_Swiata(int value) {
            this.value = value;
        }
    }
    Gatunek gatunek;
    protected int sila;
    protected int inicjatywa;
    protected Swiat swiat;
    protected Color kolorek;
    Wspolrzedne punkt;
    int data_urodzin;

    public Organizm(Gatunek gatunek, int sila, int inicjatywa, Wspolrzedne punkt, Swiat swiat) {
        this.gatunek = gatunek;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.punkt = punkt;
        this.swiat = swiat;
        this.data_urodzin = swiat.Get_Numer_Tury();
    }

    public Wspolrzedne Get_Pozycja() {
        return punkt;
    }

    public int Get_Urodziny() {
        return data_urodzin;
    }

    public int Get_Inicjatywa(){
        return inicjatywa;
    }

    public int Get_Sila(){
        return sila;
    }

    public Gatunek Get_gatunek() {
        return gatunek;
    }

    public static Gatunek Losuj_gatunek() {
        Random rand = new Random();
        int komora_losujaca = rand.nextInt(10);
        return switch (komora_losujaca) {
            case 0 -> Gatunek.WILK;
            case 1 -> Gatunek.OWCA;
            case 2 -> Gatunek.LIS;
            case 3 -> Gatunek.ZOLW;
            case 4 -> Gatunek.ANTYLOPA;
            case 5 -> Gatunek.TRAWA;
            case 6 -> Gatunek.MLECZ;
            case 7 -> Gatunek.GUARANA;
            case 8 -> Gatunek.WILCZE_JAGODY;
            default -> Gatunek.BARSZCZ_SOSNOWSKIEGO;
        };
    }

    public static Organizm Urodziny(Gatunek rodzic, Swiat swiat, Wspolrzedne punkt) {
        return switch (rodzic) {
            case WILK -> new Wilk(swiat, punkt, swiat.Get_Numer_Tury());
            case OWCA -> new Owca(swiat, punkt, swiat.Get_Numer_Tury());
            case LIS -> new Lis(swiat, punkt, swiat.Get_Numer_Tury());
            case ZOLW -> new Zolw(swiat, punkt, swiat.Get_Numer_Tury());
            case ANTYLOPA -> new Antylopa(swiat, punkt, swiat.Get_Numer_Tury());
            case CZLOWIEK -> new Czlowiek(swiat, punkt, swiat.Get_Numer_Tury());
            case TRAWA -> new Trawa(swiat, punkt, swiat.Get_Numer_Tury());
            case MLECZ -> new Mlecyk(swiat, punkt, swiat.Get_Numer_Tury());
            case GUARANA -> new Guarana(swiat, punkt, swiat.Get_Numer_Tury());
            case WILCZE_JAGODY -> new Jagodki(swiat, punkt, swiat.Get_Numer_Tury());
            default -> new Borszcz(swiat, punkt, swiat.Get_Numer_Tury());
        };
    }

    protected void Rozmnazanie(Organizm rodzic) {
        int y = punkt.GetY();
        int x = punkt.GetX();
        boolean polnoc = true;
        boolean wschod = true;
        boolean poludnie = true;
        boolean zachod = true;

        if (swiat.Get_Numer_Tury() - rodzic.Get_Urodziny() < 3) {
            return;
        }

        if (y != 0) {
            if (!swiat.Sprawdz_Kolizje(new Wspolrzedne(y - 1, x))) polnoc = false;
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

        if (!polnoc && !wschod && !poludnie && !zachod) {
            return;
        }
        else {
            while (true) {
                Random rand = new Random();
                int komora_losujaca = rand.nextInt(4);
                if (polnoc && komora_losujaca == 0) {
                    Organizm nowy = Urodziny(rodzic.Get_gatunek(), swiat, new Wspolrzedne(y-1, x));
                    swiat.Dodaj_organizm(nowy);
                    return;
                } else if (wschod && komora_losujaca == 1) {
                    Organizm nowy = Urodziny(rodzic.Get_gatunek(), swiat, new Wspolrzedne(y, x+1));
                    swiat.Dodaj_organizm(nowy);
                    return;
                } else if (poludnie && komora_losujaca == 2) {
                    Organizm nowy = Urodziny(rodzic.Get_gatunek(), swiat, new Wspolrzedne(y+1, x));
                    swiat.Dodaj_organizm(nowy);
                    return;
                } else if (zachod) {
                    Organizm nowy = Urodziny(rodzic.Get_gatunek(), swiat, new Wspolrzedne(y, x-1));
                    swiat.Dodaj_organizm(nowy);
                    return;
                }
            }
        }
    }

    public boolean Specjalna_Akcja() {
        return false;
    }

    public void Set_sila(int sila) {
        this.sila = sila;
    }

    public void Set_data_urodzin(int data) {
        data_urodzin = data;
    }

    public String Gatunek_to_string(Organizm organizm) {
        Organizm.Gatunek tmp = organizm.Get_gatunek();
        if (tmp == Gatunek.CZLOWIEK) {
            return "Czlowiek";
        } else if (tmp == Gatunek.ZOLW) {
            return "Zolw";
        } else if (tmp == Gatunek.WILK) {
            return "Wilk";
        } else if (tmp == Gatunek.WILCZE_JAGODY) {
            return "Jagody";
        } else if (tmp == Gatunek.LIS) {
            return "Lis";
        } else if (tmp == Gatunek.TRAWA) {
            return "Trawa";
        } else if (tmp == Gatunek.ANTYLOPA) {
            return "Antylopa";
        } else if (tmp == Gatunek.GUARANA) {
            return "Guarana";
        } else if (tmp == Gatunek.OWCA) {
            return "Owca";
        } else if (tmp == Gatunek.BARSZCZ_SOSNOWSKIEGO) {
            return "Barszcz";
        } else{
            return "Mlecyk";
        }
    }

    protected void SetKolor(Color kolor) {
        this.kolorek = kolor;
    }
    public Color GetKolor(){return kolorek;}
}