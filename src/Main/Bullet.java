package Main;

import java.awt.Color;
import java.awt.Graphics2D;

// Klasa Bullet odpowiada za pociski
public class Bullet {

    // Deklaracja zmiennych
    private double x, y; // Pozycja pocisku
    private double angle; // Kąt strzału
    private final double speed = 5; // Prędkość pocisku
    private final int diameter = 5; // Średnica pocisku

    // Konstruktor klasy Bullet
    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    // Metoda odpowiedzialna za ruch pocisku
    public void move() {
        x += speed * Math.cos(Math.toRadians(angle)); // Obliczenie nowej pozycji w osi X
        y += speed * Math.sin(Math.toRadians(angle)); // Obliczenie nowej pozycji w osi Y
    }

    // Metoda rysująca pocisk
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED); // Ustawienie koloru na czerwony
        g2d.fillOval((int) x, (int) y, diameter, diameter); // Rysowanie pocisku jako czerwonego koła
    }

    // Metoda zwracająca pozycję X pocisku
    public double getX() {
        return x;
    }

    // Metoda zwracająca pozycję Y pocisku
    public double getY() {
        return y;
    }
}