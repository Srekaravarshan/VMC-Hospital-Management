package hospital.java.helpers;

import javax.swing.JEditorPane;
import javax.swing.RepaintManager;

import java.awt.*;
import java.awt.print.*;

public class PrintableEditorPane extends JEditorPane implements Printable {
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);

        RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
        Dimension d = this.getSize();
        double panelWidth = d.width;
        double panelHeight = d.height;
        double pageWidth = pf.getImageableWidth();
        double pageHeight = pf.getImageableHeight();
        double scale = pageWidth / panelWidth;
        int totalNumPages = (int) Math.ceil(scale * panelHeight / pageHeight);

        // Check for empty pages
        if (pageIndex >= totalNumPages)
            return Printable.NO_SUCH_PAGE;

        g2.translate(pf.getImageableX(), pf.getImageableY());
        g2.translate(0f, -pageIndex * pageHeight);
        g2.scale(scale, scale);
        this.paint(g2);

        return Printable.PAGE_EXISTS;
    }

}
