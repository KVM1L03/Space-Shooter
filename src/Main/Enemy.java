package Main;

import java.awt.Image;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.util.Random;

// Klasa Enemy odpowiada za przeciwników
public class Enemy {
    
    // Deklaracja zmiennych
    private Image image; // Obraz przeciwnika
    private double x, y; // Pozycja przeciwnika
    private double speed = 2; // Prędkość przeciwnika
    private double targetX, targetY; // Cel przeciwnika

    // Konstruktor klasy Enemy
    public Enemy(String imagePath, double targetX, double targetY) {
        ImageIcon icon = new ImageIcon(imagePath); // Utworzenie obiektu ImageIcon z obrazu przeciwnika
        this.image = icon.getImage(); // Pobranie obrazu przeciwnika
        this.targetX = targetX; // Ustawienie celu przeciwnika w osi X
        this.targetY = targetY; // Ustawienie celu przeciwnika w osi Y
        spawnAtBorder(); // Wywołanie metody spawnAtBorder
    }

    // Metoda spawnAtBorder odpowiada za losowe pojawienie się przeciwnika na krawędziach panelu
    private void spawnAtBorder() {
        Random rand = new Random(); // Utworzenie obiektu Random
        int side = rand.nextInt(4); // Losowanie jednej z czterech krawędzi
        switch (side) {
            case 0: // Górna krawędź
                x = rand.nextInt(800); // Losowanie pozycji X
                y = 0; // Ustawienie pozycji Y na 0
                break;
            case 1: // Dolna krawędź
                x = rand.nextInt(800); // Losowanie pozycji X
                y = 600; // Ustawienie pozycji Y na 600
                break;
            case 2: // Lewa krawędź
                x = 0; // Ustawienie pozycji X na 0
                y = rand.nextInt(600); // Losowanie pozycji Y
                break;
            case 3: // Prawa krawędź
                x = 800; // Ustawienie pozycji X na 800
                y = rand.nextInt(600); // Losowanie pozycji Y
                break;
        }
    }

    // Metoda move odpowiada za ruch przeciwnika w kierunku celu
    public void move() {
        double angle = Math.atan2(targetY - y, targetX - x); // Obliczenie kąta ruchu
        x += speed * Math.cos(angle); // Obliczenie nowej pozycji w osi X
        y += speed * Math.sin(angle); // Obliczenie nowej pozycji w osi Y
    }

    // Metoda draw rysuje przeciwnika
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x, (int) y, null); // Rysowanie obrazu przeciwnika
    }

    // Metoda zwracająca pozycję X przeciwnika
    public double getX() {
        return x;
    }

    // Metoda zwracająca pozycję Y przeciwnika
    public double getY() {
        return y;
    }

    // Metoda zwracająca obraz przeciwnika
    public Image getImage() {
        return image;
    }
}