package gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import gui.MazeFrame;

public class SetEndBtn extends JButton {
    private MazeFrame frame; // Referencja do głównej ramki labiryntu

    // Konstruktor klasy SetEndBtn, przyjmujący MazeFrame i tekst przycisku jako argumenty
    public SetEndBtn(MazeFrame frame, String text) {
        super(text); // Ustawienie tekstu przycisku
        this.frame = frame; // Przechowanie referencji do MazeFrame
    }

    // Nadpisana metoda fireActionPerformed, która jest wywoływana podczas kliknięcia przycisku
    @Override
    protected void fireActionPerformed(ActionEvent event) {
        // Wywołanie niestandardowej metody ustawiania nowego punktu końcowego w MazeFrame
        frame.setNewEndPoint();

        // Wywołanie metody nadrzędnej, aby zapewnić standardowe zachowanie, takie jak powiadamianie nasłuchiwaczy
        super.fireActionPerformed(event);
    }
}

