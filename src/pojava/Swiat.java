package pojava;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;


public class Swiat {
    private final int Szerokosc;
    private final int Wysokosc;
    private int numerTury;
    private Organizm[][] plansza;
    private boolean czyCzlowiekZyje;
    private boolean pauza;
    private final ArrayList<Organizm> organizmy;
    private Czlowiek czlowiek;
    public Interfejs GUI;
    public Koentarze pojemnik_na_stringi;

    public Swiat() {
        this.Szerokosc = 0;
        this.Wysokosc = 0;
        numerTury = 0;
        czyCzlowiekZyje = true;
        pauza = true;
        organizmy = new ArrayList<>();
    }

    public Swiat(int sizeX, int sizeY, Interfejs GUI) {
        this.Szerokosc = sizeX;
        this.Wysokosc = sizeY;
        numerTury = 0;
        czyCzlowiekZyje = true;
        pauza = true;
        this.GUI=GUI;
        plansza = new Organizm[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                plansza[i][j] = null;
            }
        }
        organizmy = new ArrayList<>();
    }


    public void Zapisz_Stan(String Nazwa_Zapisu) {
        try {
            Nazwa_Zapisu += ".txt";
            File file = new File(Nazwa_Zapisu);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(Szerokosc + " ");
            pw.print(Wysokosc + " ");
            pw.print(numerTury + " ");
            pw.print(czyCzlowiekZyje + " " + "\n");
            for (Organizm organizm : organizmy) {
                pw.print(organizm.Get_gatunek() + " ");
                pw.print(organizm.Get_Pozycja().GetX() + " ");
                pw.print(organizm.Get_Pozycja().GetY() + " ");
                pw.print(organizm.Get_Sila() + " ");
                pw.print(organizm.Get_Urodziny() + " ");
                if (organizm.Get_gatunek() == Organizm.Gatunek.CZLOWIEK) {
                    pw.print(czlowiek.Get_Licznik() + " ");
                    pw.print(czlowiek.Get_Cooldown() + " ");
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static Swiat Wczytaj_Swiat(String Nazwa_Zapisu) {
        try {
            Nazwa_Zapisu += ".txt";
            File file = new File(Nazwa_Zapisu);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            Swiat tmpSwiat = new Swiat(sizeX, sizeY, null);
            tmpSwiat.numerTury = Integer.parseInt(properties[2]);
            tmpSwiat.czyCzlowiekZyje = Boolean.parseBoolean(properties[3]);
            tmpSwiat.czlowiek = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organizm.Gatunek gatunek = Organizm.Gatunek.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organizm tmp = Organizm.Urodziny(gatunek, tmpSwiat, new Wspolrzedne(y, x));
                int sila = Integer.parseInt(properties[3]);
                tmp.Set_sila(sila);
                int turaUrodzenia = Integer.parseInt(properties[4]);
                tmp.Set_data_urodzin(turaUrodzenia);

                if (gatunek == Organizm.Gatunek.CZLOWIEK) {
                    tmpSwiat.czlowiek = (Czlowiek) tmp;
                    int licznik = Integer.parseInt(properties[5]);
                    tmpSwiat.czlowiek.Krecenie_Licznika(licznik);
                    int cooldown = Integer.parseInt(properties[6]);
                    tmpSwiat.czlowiek.Set_Cooldown(cooldown);
                }
                tmpSwiat.Dodaj_organizm(tmp);
            }
            scanner.close();
            return tmpSwiat;
        } catch (
                IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public void Zasiej_organizmy() {
        int liczba_org = (int) Math.floor(Szerokosc * Wysokosc / 4);
        czlowiek=(Czlowiek)Organizm.Urodziny(Organizm.Gatunek.CZLOWIEK, this, LosujPole());
        Dodaj_organizm(czlowiek);
        for (int i = 0; i < liczba_org; i++) {
            Wspolrzedne punkt = LosujPole();
            if (!Objects.equals(punkt, new Wspolrzedne(-1, -1))) {
                Dodaj_organizm(Organizm.Urodziny(Organizm.Losuj_gatunek(), this, punkt));
            }
        }
    }

    public Organizm Get_Organizm(int y, int x) {
        return plansza[y][x];
    }

    public void SortujOrganizmy() {
        organizmy.sort((o1, o2) -> {
            if (o1.Get_Inicjatywa() != o2.Get_Inicjatywa())
                return Integer.compare(o2.Get_Inicjatywa(), o1.Get_Inicjatywa());
            else
                return Integer.compare(o1.Get_Urodziny(), o2.Get_Urodziny());
        });
    }

    public Wspolrzedne LosujPole() {
        int stoper = 0;
        while (stoper != 15) {
            Random rand = new Random();
            int y = rand.nextInt(Wysokosc);
            int x = rand.nextInt(Szerokosc);
            if (plansza[y][x] == null) {
                return new Wspolrzedne(y, x);
            } else stoper++;
        }
        return new Wspolrzedne(-1, -1);
    }

    public boolean Sprawdz_Kolizje(Wspolrzedne pole) {
        return (plansza[pole.GetY()][pole.GetX()] == null);
    }

    public void Dodaj_organizm(Organizm organizm) {
        organizmy.add(organizm);
        plansza[organizm.Get_Pozycja().GetY()][organizm.Get_Pozycja().GetX()] = organizm;
    }

    public void Zabij_Organizm(Organizm organizm) {
        plansza[organizm.Get_Pozycja().GetY()][organizm.Get_Pozycja().GetX()] = null;
        organizmy.remove(organizm);
    }

    public int Get_Szerokosc() {
        return Szerokosc;
    }

    public int Get_Wysokosc() {
        return Wysokosc;
    }

    public int Get_Numer_Tury() {
        return numerTury;
    }

    public boolean getCzyCzlowiekZyje() {
        return czyCzlowiekZyje;
    }

    public ArrayList<Organizm> getOrganizmy() {
        return organizmy;
    }

    public Czlowiek getCzlowiek() {
        return czlowiek;
    }

    public boolean isPauza() {
        return pauza;
    }

    public void setPauza(boolean pauza) {
        this.pauza = pauza;
    }

    public void Przesun_Organizm(int y, int x, Organizm.Strona_Swiata kierunek) {
        Organizm tmp = plansza[y][x];
        switch (kierunek) {
            case POLNOC -> plansza[y - 1][x] = tmp;
            case WSCHOD -> plansza[y][x + 1] = tmp;
            case POLUDNIE -> plansza[y + 1][x] = tmp;
            case ZACHOD -> plansza[y][x - 1] = tmp;
        }
        plansza[y][x] = null;
    }

    public void Gra(){
        SortujOrganizmy();
        for (int i=0;i<organizmy.size();i++){
            organizmy.get(i).Akcja();
        }
        numerTury++;
    }
    public void Set_Interfejs(Interfejs i){
        GUI=i;
    }
}
