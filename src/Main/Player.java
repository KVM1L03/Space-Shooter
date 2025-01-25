package Main;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Klasa Player odpowiada za gracza
public class Player {

    // Deklaracja zmiennych
    private Image image; // Obraz gracza
    private double x, y; // Pozycja gracza
    private double angle; // Kąt obrotu gracza
    private ArrayList<Bullet> bullets; // Lista pocisków
    private Timer shootTimer; // Timer do kontrolowania częstotliwości strzałów
    private boolean canShoot; // Flaga określająca, czy gracz może strzelać

    // Konstruktor klasy Player
    public Player(String imagePath, double x, double y) {
        ImageIcon icon = new ImageIcon(imagePath); // Utworzenie obiektu ImageIcon z obrazu gracza
        this.image = icon.getImage(); // Pobranie obrazu gracza
        this.x = x; // Ustawienie pozycji X gracza
        this.y = y; // Ustawienie pozycji Y gracza
        this.angle = 0; // Ustawienie kąta obrotu na 0
        this.bullets = new ArrayList<>(); // Utworzenie listy pocisków
        this.canShoot = true; // Ustawienie flagi canShoot na true

        shootTimer = new Timer(300, new ActionListener() { // Utworzenie timera z opóźnieniem 300 ms
            @Override
            public void actionPerformed(ActionEvent e) {
                canShoot = true; // Ustawienie flagi canShoot na true po upływie czasu
            }
        });
        shootTimer.start(); // Uruchomienie timera
    }

    // Metoda ustawiająca kąt obrotu gracza
    public void setAngle(double angle) {
        this.angle = angle;
    }

    // Metoda odpowiedzialna za strzelanie
    public void shoot() {
        if (canShoot) { // Sprawdzenie, czy gracz może strzelać
            bullets.add(new Bullet(x + image.getWidth(null) / 2, y + image.getHeight(null) / 2, angle)); // Dodanie nowego pocisku do listy
            canShoot = false; // Ustawienie flagi canShoot na false
        }
    }

    // Metoda aktualizująca pozycje pocisków
    public void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.move(); // Ruch pocisku
            if (bullet.getX() < 0 || bullet.getX() > 800 || bullet.getY() < 0 || bullet.getY() > 600) {
                bullets.remove(i); // Usunięcie pocisku, jeśli jest poza ekranem
                i--; // Dostosowanie indeksu po usunięciu
            }
        }
    }

    // Metoda rysująca gracza i pociski
    public void draw(Graphics2D g2d) {
        AffineTransform backup = g2d.getTransform(); // Zapisanie aktualnej transformacji
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle), x + image.getWidth(null) / 2, y + image.getHeight(null) / 2); // Obrót gracza
        trans.translate(x, y); // Przesunięcie gracza
        g2d.transform(trans); // Zastosowanie transformacji
        g2d.drawImage(image, 0, 0, null); // Rysowanie obrazu gracza
        g2d.setTransform(backup); // Przywrócenie oryginalnej transformacji

        for (Bullet bullet : bullets) {
            bullet.draw(g2d); // Rysowanie każdego pocisku
        }
    }

    // Metoda sprawdzająca kolizję z przeciwnikiem
    public boolean checkCollision(Enemy enemy) {
        double dx = (x + image.getWidth(null) / 2) - (enemy.getX() + enemy.getImage().getWidth(null) / 2); // Obliczenie różnicy w osi X
        double dy = (y + image.getHeight(null) / 2) - (enemy.getY() + enemy.getImage().getHeight(null) / 2); // Obliczenie różnicy w osi Y
        double distance = Math.sqrt(dx * dx + dy * dy); // Obliczenie odległości
        return distance < (image.getWidth(null) / 2 + enemy.getImage().getWidth(null) / 2); // Sprawdzenie, czy odległość jest mniejsza niż suma promieni
    }

    // Metoda zwracająca listę pocisków
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    // Metoda zwracająca pozycję X gracza
    public double getX() {
        return x;
    }

    // Metoda zwracająca pozycję Y gracza
    public double getY() {
        return y;
    }

    // Metoda zwracająca obraz gracza
    public Image getImage() {
        return image;
    }
}