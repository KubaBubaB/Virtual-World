package pojava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Interfejs extends JFrame implements ActionListener,KeyListener {
    private JMenu menu;
    private final JMenuItem NowaGra;
    private final JMenuItem Wczytaj;
    private final JMenuItem Zapisz;
    private Swiat swiat;
    private final JFrame jFrame;
    private double Wielkosc_Kwadracikow=25;
    private final Canvas canvas;
    private JPanel boczniak;
    private final Opisy okienko;
    private int sizeX=400,sizeY=400;

    public Interfejs() {
        jFrame = new JFrame("PO_JAVA");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1200,860);
        jFrame.setResizable(false);
        jFrame.setLayout(null);
        jFrame.setFocusable(true);
        JMenuBar menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        NowaGra = new JMenuItem("Nowa Gra");
        Wczytaj = new JMenuItem("Wczytaj");
        Zapisz =new JMenuItem("Zapisz");
        NowaGra.addActionListener(this);
        Wczytaj.addActionListener(this);
        Zapisz.addActionListener(this);
        menu.add(NowaGra);
        menu.add(Wczytaj);
        menu.add(Zapisz);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);

        jFrame.addKeyListener(this);
        canvas = new Canvas();
        canvas.setBounds(0,0,800,800);
        canvas.setBackground(Color.DARK_GRAY);
        jFrame.add(canvas);
        jFrame.setVisible(true);
        okienko= new Opisy();
        jFrame.add(okienko);
        boczniak = new Lista_Organizmow();
        jFrame.add(boczniak);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == NowaGra) {
            sizeX = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Podaj szerokosc(max 200)", "20"));
            sizeY = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Podaj wysokosc(max 200)", "20"));
            while(sizeX>201 || sizeY>201){
                sizeX = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                        "Podaj szerokosc(max 200)", "20"));
                sizeY = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                        "Podaj wysokosc(max 200)", "20"));
            }
            if (sizeX>sizeY){
                Wielkosc_Kwadracikow=Math.floor(800/sizeX);
            }
            else{
                Wielkosc_Kwadracikow=Math.floor(800/sizeY);
            }
            swiat = new Swiat(sizeX, sizeY, this);
            swiat.Zasiej_organizmy();
            Wyczysc_organizmy();
            Wypisz_organizmy();
        }
        if (e.getSource() == Wczytaj){
            String Nazwa_Pliku = JOptionPane.showInputDialog(jFrame, "Podaj nazwe pliku", "Moj_Swiat");
            swiat = Swiat.Wczytaj_Swiat(Nazwa_Pliku);
            swiat.Set_Interfejs(this);
            Wyczysc_organizmy();
            Wypisz_organizmy();
            Koentarze.Posprzataj();
            okienko.Update_Opis();
        }
        if (e.getSource() == Zapisz){
            String Nazwa_Pliku = JOptionPane.showInputDialog(jFrame, "Podaj nazwe pliku", "Moj_Swiat");
            swiat.Zapisz_Stan(Nazwa_Pliku);
            Wypisz_organizmy();
        }
        Wypisz_organizmy();
    }

    public void Wypisz_organizmy(){
        ArrayList<Organizm> organizmy=swiat.getOrganizmy();
        for(int i=0;i<organizmy.size();i++){
            int x=organizmy.get(i).Get_Pozycja().GetX();
            int y=organizmy.get(i).Get_Pozycja().GetY();

            Graphics g=canvas.getGraphics();
            g.setColor(organizmy.get(i).GetKolor());
            g.fillRect(x*(int)Wielkosc_Kwadracikow,y*(int)Wielkosc_Kwadracikow,(int)Wielkosc_Kwadracikow,(int)Wielkosc_Kwadracikow);
        }
    }

    public void Wyczysc_organizmy(){
        Graphics g=canvas.getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,800,800);
    }

    @Override
    public void keyPressed(KeyEvent e){
        if (swiat!=null && swiat.isPauza()){
            int klawisz=e.getKeyCode();
            if(swiat.getCzyCzlowiekZyje()){
                switch(klawisz){
                    case KeyEvent.VK_UP:
                        swiat.getCzlowiek().set_Strona(Organizm.Strona_Swiata.POLNOC);
                        swiat.setPauza(false);
                        break;
                    case KeyEvent.VK_DOWN:
                        swiat.getCzlowiek().set_Strona(Organizm.Strona_Swiata.POLUDNIE);
                        swiat.setPauza(false);
                        break;
                    case KeyEvent.VK_LEFT:
                        swiat.getCzlowiek().set_Strona(Organizm.Strona_Swiata.ZACHOD);
                        swiat.setPauza(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        swiat.getCzlowiek().set_Strona(Organizm.Strona_Swiata.WSCHOD);
                        swiat.setPauza(false);
                        break;
                    case KeyEvent.VK_P:
                        if(!swiat.getCzlowiek().Get_AktywnosSkilla())swiat.getCzlowiek().Aktywuj_Umiejke();
                        break;
                }
            }
            swiat.setPauza(false);
            Koentarze.Posprzataj();
            swiat.Gra();
            this.Wyczysc_organizmy();
            this.Wypisz_organizmy();
            okienko.Update_Opis();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    private static class Lista_Organizmow extends JPanel {
        private final int ILOSC_TYPOW = 12;
        private JButton[] jButtons;

        public Lista_Organizmow() {
            super();
            setBounds(800,0,400,860);
            setBackground(Color.BLACK);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            jButtons = new JButton[ILOSC_TYPOW];
            jButtons[0] = new JButton("Barszczyk");
            jButtons[0].setBackground(new Color(123, 148, 33));

            jButtons[1] = new JButton("Guarana");
            jButtons[1].setBackground(new Color(224, 99, 99));

            jButtons[2] = new JButton("Mlecz");
            jButtons[2].setBackground(new Color(227, 227, 30));

            jButtons[3] = new JButton("Trawa");
            jButtons[3].setBackground(new Color(133, 247, 57));

            jButtons[4] = new JButton("Wilcze jagody");
            jButtons[4].setBackground(new Color(171, 48, 219));

            jButtons[5] = new JButton("Antylopa");
            jButtons[5].setBackground(new Color(204,132,49));

            jButtons[6] = new JButton("Czlowiek");
            jButtons[6].setBackground(new Color(32, 160, 186));

            jButtons[7] = new JButton("Lis");
            jButtons[7].setBackground(new Color(179, 39, 14));

            jButtons[8] = new JButton("Owca");
            jButtons[8].setBackground(new Color(138, 127, 125));

            jButtons[9] = new JButton("Wilk");
            jButtons[9].setBackground(new Color(82, 75, 74));

            jButtons[10] = new JButton("Zolw");
            jButtons[10].setBackground(new Color(29, 71, 28));

            jButtons[11] = new JButton("Strzalki - sterowanie // P - umiejetnosc");
            jButtons[11].setBackground(Color.BLACK);

            for (int i = 0; i < ILOSC_TYPOW; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }
        }
    }

    private static class Opisy extends JPanel {
        private String tekst;
        private final JTextArea textArea;
        public Opisy() {
            super();
            setBounds(800, 200, 400, 660);
            tekst = Koentarze.Get_Komentarz();
            textArea = new JTextArea(tekst);
            textArea.setEditable(false);
            setLayout(new CardLayout());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }
        public void Update_Opis() {
            tekst = Koentarze.Get_Komentarz();
            textArea.setText(tekst);
        }
    }
}

