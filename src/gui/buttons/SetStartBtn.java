package gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import gui.MazeFrame;

public class SetStartBtn extends JButton {
    private MazeFrame frame; // Referencja do głównej ramki labiryntu

    // Konstruktor klasy SetStartBtn, przyjmujący MazeFrame i tekst przycisku jako argumenty
    public SetStartBtn(MazeFrame frame, String text) {
        super(text); // Ustawienie tekstu przycisku
        this.frame = frame; // Przechowanie referencji do MazeFrame
    }

    // Nadpisana metoda fireActionPerformed, która jest wywoływana podczas kliknięcia przycisku
    @Override
    protected void fireActionPerformed(ActionEvent event) {
        // Wywołanie niestandardowej metody ustawiania nowego punktu startowego w MazeFrame
        frame.setNewStartPoint();

        // Wywołanie metody nadrzędnej, aby zapewnić standardowe zachowanie, takie jak powiadamianie nasłuchiwaczy
        super.fireActionPerformed(event);
    }
}
