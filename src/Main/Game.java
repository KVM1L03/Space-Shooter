package Main;

import javax.swing.JFrame;

// Główna klasa Game uruchamiająca grę

public class Game extends JFrame {
    public Game() {
        init();
    }

    private void init() {
        add(new GamePanel()); // Dodanie panelu gry
        setTitle("Space Shooter"); // Ustawienie tytułu okna
        setSize(800, 600); // Ustawienie rozmiaru okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ustawienie domyślnej operacji zamknięcia
        setLocationRelativeTo(null); // Wyśrodkowanie okna
    }

    public static void main(String[] args) {
        Game game = new Game(); // Utworzenie instancji gry
        game.setVisible(true); // Ustawienie widoczności okna
    }
}