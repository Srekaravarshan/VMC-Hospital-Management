package hospital.java.helpers;


import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;

public class PrintJobWatcher implements PrintJobListener {

    Boolean done = false;
    Integer status = 0;

    public PrintJobWatcher() {
        System.out.println("PrintJobWatcher()");
    }

    @Override
    public void printDataTransferCompleted(PrintJobEvent pje) {
        this.done(PrintJobEvent.DATA_TRANSFER_COMPLETE);
        System.out.println("DATA_TRANSFER_COMPLETE");
    }

    @Override
    public void printJobCompleted(PrintJobEvent pje) {
        this.done(PrintJobEvent.JOB_COMPLETE);
        System.out.println("JOB_COMPLETE");
    }

    @Override
    public void printJobFailed(PrintJobEvent pje) {
        this.done(PrintJobEvent.JOB_FAILED);
        System.out.println("JOB_FAILED");
    }

    @Override
    public void printJobCanceled(PrintJobEvent pje) {
        this.done(PrintJobEvent.JOB_CANCELED);
        System.out.println("JOB_CANCELED");
    }

    @Override
    public void printJobNoMoreEvents(PrintJobEvent pje) {
        this.done(PrintJobEvent.NO_MORE_EVENTS);
        System.out.println("NO_MORE_EVENTS");
    }

    @Override
    public void printJobRequiresAttention(PrintJobEvent pje) {
        this.done(PrintJobEvent.REQUIRES_ATTENTION);
        System.out.println("REQUIRES_ATTENTION");
    }

    private synchronized void done(Integer status) {
        System.out.println("DONE !");
        this.status = status;
        this.done = true;
        notifyAll();
    }

    synchronized void waitForDone() throws InterruptedException {
        System.out.println("AVANT : IMPRESSION EN COURS...");
        try {
            while (!this.done
                    || ((this.status != PrintJobEvent.JOB_COMPLETE) || (this.status != PrintJobEvent.JOB_FAILED))) {
                System.out.println("IMPRESSION EN COURS...");
                wait();
            }
        } catch (InterruptedException e) {
        }
    }
}