import javax.swing.*;
import java.awt.event.KeyEvent;

public class ProtectedTextField extends JTextArea {

    public ProtectedTextField() {
        super();
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        // Check if the pressed key is the backspace key and caret position is at the beginning
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && getCaretPosition() == 0) {
            e.consume(); // Consume the event to prevent deletion
        } else {
            super.processKeyEvent(e); // Call the original method for other key events
        }
    }
}