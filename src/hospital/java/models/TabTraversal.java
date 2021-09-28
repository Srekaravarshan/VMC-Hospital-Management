package hospital.java.models;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TabTraversal implements EventHandler<KeyEvent> {
    private static final String FOCUS_EVENT_TEXT = "TAB_TO_FOCUS_EVENT";

    @Override
    public void handle(final KeyEvent event)
    {
        if (!KeyCode.TAB.equals(event.getCode()))
        {
            return;
        }

        // handle events where the TAB key or TAB + CTRL key is pressed
        // so don't handle the event if the ALT, SHIFT or any other modifier key is pressed
        if (event.isAltDown() || event.isMetaDown() || event.isShiftDown())
        {
            return;
        }

        if (!(event.getSource() instanceof TextArea))
        {
            return;
        }

        final TextArea textArea = (TextArea) event.getSource();
        if (event.isControlDown())
        {
            // if the event text contains the special focus event text
            // => do not consume the event, and let the default behaviour (= move focus to the next control) happen.
            //
            // if the focus event text is not present, then the user has pressed CTRL + TAB key,
            // then consume the event and insert or replace selection with tab character
            if (!FOCUS_EVENT_TEXT.equalsIgnoreCase(event.getText()))
            {
                event.consume();
                textArea.replaceSelection("\t");
            }
        }
        else
        {
            // The default behaviour of the TextArea for the CTRL+TAB key is a move of focus to the next control.
            // So we consume the TAB key event, and fire a new event with the CTRL + TAB key.

            event.consume();

            final KeyEvent tabControlEvent = new KeyEvent(event.getSource(), event.getTarget(), event.getEventType(), event.getCharacter(),
                    FOCUS_EVENT_TEXT, event.getCode(), event.isShiftDown(), true, event.isAltDown(), event.isMetaDown());
            textArea.fireEvent(tabControlEvent);
        }
    }
}