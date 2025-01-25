package Main;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

// Klasa GamePanel dziedzicząca po JPanel, odpowiada za rysowanie elementów gry
public class GamePanel extends JPanel implements ActionListener {

    // Deklaracja zmiennych
    private ImageIcon backgroundIcon;
    private Image background;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Timer timer;
    private boolean inGame;
    private long startTime;
    private long elapsedTime;

    // Konstruktor klasy GamePanel
    public GamePanel() {
        initPanel();
    }

    // Metoda inicjalizująca panel gry
    private void initPanel() {
        setBackground(Color.BLACK); // Ustawienie koloru tła na czarny
        setFocusable(true); // Ustawienie możliwości skupienia na panelu
        setDoubleBuffered(true); // Włączenie podwójnego buforowania

        backgroundIcon = new ImageIcon("src/assets/background.png"); // Utworzenie obiektu ImageIcon z obrazu tła
        background = backgroundIcon.getImage(); // Pobranie obrazu tła

        player = new Player("src/assets/spaceship.png", 400, 300); // Utworzenie obiektu gracza 
        enemies = new ArrayList<>(); // Utworzenie listy przeciwników
        inGame = true; // Ustawienie zmiennej stanu gry na true
        startTime = System.currentTimeMillis(); // Pobranie czasu rozpoczęcia gry

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double dx = e.getX() - (player.getX() + player.getImage().getWidth(null) / 2); // Obliczenie różnicy w osi X
                double dy = e.getY() - (player.getY() + player.getImage().getHeight(null) / 2); // Obliczenie różnicy w osi Y
                double angle = Math.toDegrees(Math.atan2(dy, dx)); // Obliczenie kąta
                player.setAngle(angle); // Ustawienie kąta gracza
                repaint(); // Odświeżenie panelu
            }
        });

        timer = new Timer(100, this); // Utworzenie timera z opóźnieniem 100 ms
        timer.start(); // Uruchomienie timera
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            drawBackground(g); // Rysowanie tła
            drawPlayer(g); // Rysowanie gracza
            drawEnemies(g); // Rysowanie przeciwników
            drawTimer(g); // Rysowanie timera
        } else {
            drawGameOver(g); // Rysowanie ekranu końca gry
        }
    }

    private void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this); // Rysowanie tła, aby wypełnić panel
    }

    private void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d); // Rysowanie gracza
    }

    private void drawEnemies(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (Enemy enemy : enemies) {
            enemy.draw(g2d); // Rysowanie każdego przeciwnika
        }
    }

    private void drawTimer(Graphics g) {
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Obliczenie upływu czasu w sekundach
        g.setColor(Color.YELLOW); // Ustawienie koloru na żółty
        g.drawString("Time: " + elapsedTime + "s", 10, 20); // Rysowanie timera w lewym górnym rogu
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED); // Ustawienie koloru na czerwony
        g.drawString("Game Over", getWidth() / 2 - 50, getHeight() / 2); // Rysowanie napisu "Game Over" na środku
        g.drawString("Time: " + elapsedTime + "s", getWidth() / 2 - 50, getHeight() / 2 + 20); // Wyświetlenie upływu czasu
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            player.shoot(); // Strzelanie gracza
            player.updateBullets(); // Aktualizacja pocisków
            updateEnemies(); // Aktualizacja przeciwników
            checkCollisions(); // Sprawdzanie kolizji
            spawnEnemies(); // Generowanie przeciwników
            repaint(); // Odświeżenie panelu
        }
    }

    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move(); // Ruch przeciwników
        }
    }

    // Metoda sprawdzająca kolizje
    private void checkCollisions() {
        ArrayList<Bullet> bullets = player.getBullets();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                double dx = bullet.getX() - (enemy.getX() + enemy.getImage().getWidth(null) / 2); // Obliczenie różnicy w osi X
                double dy = bullet.getY() - (enemy.getY() + enemy.getImage().getHeight(null) / 2); // Obliczenie różnicy w osi Y
                double distance = Math.sqrt(dx * dx + dy * dy); // Obliczenie odległości
                if (distance < enemy.getImage().getWidth(null) / 2) {
                    enemies.remove(i); // Usunięcie przeciwnika
                    bullets.remove(j); // Usunięcie pocisku
                    i--; // Dostosowanie indeksu po usunięciu
                    break;
                }
            }
            if (player.checkCollision(enemy)) {
                inGame = false; // Zakończenie gry w przypadku kolizji z graczem
            }
        }
    }

    private void spawnEnemies() {
        Random rand = new Random();
        if (rand.nextInt(100) < 5) { // Ustawienie częstotliwości generowania przeciwników
            enemies.add(new Enemy("src/assets/enemy.png", player.getX() + player.getImage().getWidth(null) / 2, player.getY() + player.getImage().getHeight(null) / 2));
        }
    }
}