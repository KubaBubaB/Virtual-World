package pojava;

public class Koentarze {
    private static String komentarze_wszystkie = "";

    public static void Dodajtekst(String komentarz) {
        komentarze_wszystkie += komentarz + "\n";
    }

    public static String Get_Komentarz() {
        return komentarze_wszystkie;
    }

    public static void Posprzataj() {
        komentarze_wszystkie = "";
    }
}
