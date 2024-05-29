package gui.buttons;

import gui.*;
import mazeLogic.MazeParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoadButtonTXT implements ActionListener {
    private MazeFrame mazeFrame; // Referencja do głównej ramki labiryntu

    // Konstruktor klasy LoadButtonTXT, przyjmujący MazeFrame jako argument
    public LoadButtonTXT(MazeFrame mazeFrame) {
        this.mazeFrame = mazeFrame;
    }

    // Metoda wywoływana po naciśnięciu przycisku
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(".");// Tworzenie okna wyboru pliku
        int returnValue = fileChooser.showOpenDialog(null);// Pokazanie okna wyboru pliku i zapisanie wyniku wyboru
        // Sprawdzanie, czy użytkownik zatwierdził wybór pliku
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();// Pobranie wybranego pliku
            try {
                MazeParser parser = new MazeParser(selectedFile);// Utworzenie obiektu MazeParser na podstawie wybranego pliku
                mazeFrame.loadMaze(parser);// Załadowanie labiryntu do MazeFrame
            } catch (Exception ex) {
                ex.printStackTrace();
                // Wyświetlenie komunikatu o błędzie w przypadku problemów z załadowaniem pliku
                JOptionPane.showMessageDialog(null, "Error loading maze file!");
            }
        }
    }
}
