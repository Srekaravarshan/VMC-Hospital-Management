package hospital.java.helpers;
import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Vector;

public class PrintableBook extends Book implements Printable {
    Vector<Printable> pages;// NB: we assume pages are single

    public PrintableBook() {
        super();
        pages = new Vector<>();
    }

    public void add(Printable pp, PageFormat pageFormat) {
        append(pp, pageFormat);
        pages.add(pp);
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex >= pages.size())
            return NO_SUCH_PAGE;
        else {
            Printable pp = pages.elementAt(pageIndex);
            return pp.print(g, pf, 0);
        }
    }
}
