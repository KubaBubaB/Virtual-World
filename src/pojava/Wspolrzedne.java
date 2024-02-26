package pojava;

public class Wspolrzedne {
    private int x;
    private int y;

    public Wspolrzedne(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Wspolrzedne() {
        x = 0;
        y = 0;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int y) {
        this.y = y;
    }
}