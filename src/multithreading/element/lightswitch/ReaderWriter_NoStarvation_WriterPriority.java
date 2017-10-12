package multithreading.element.lightswitch;

import multithreading.StatementOrder.Semaphore;

public class ReaderWriter_NoStarvation_WriterPriority {
    /*
     * Although the writer thread doesn't starve here. But if the writer updates
     * are important, then we have a problem here. A reader may occasionally
     * sneak in between writers. We want to prioritize the writers. If a writer
     * is writing,another writer arrives, then no reader should be allowed.
     */
    public static void main(String[] args) {
        Semaphore readerLock = new Semaphore(1);
        Semaphore writerLock = new Semaphore(1);
        LightSwitch readSwitch = new LightSwitch(writerLock);
        LightSwitch writeSwitch = new LightSwitch(readerLock);
        Thread reader = new Thread(new LowPriorityReader(readSwitch, writerLock));
        reader.start();
        Thread writer = new Thread(new Writer(writeSwitch, readerLock));
        writer.start();
    }

    static class LowPriorityReader implements Runnable {
        private final LightSwitch readSwitch;
        private final Semaphore readerLock;

        LowPriorityReader(LightSwitch readSwitch, Semaphore readerlock) {
            this.readSwitch = readSwitch;
            this.readerLock = readerlock;
        }

        @Override
        public void run() {
            try {
                this.readerLock.await();
                this.readSwitch.on();
                this.readerLock.signal();
                System.out.println("Reader critical section.");
                this.readSwitch.off();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    static class Writer implements Runnable {
        private final LightSwitch writeSwitch;
        private final Semaphore writerLock;

        Writer(LightSwitch writeSwitch, Semaphore writerLock) {
            this.writeSwitch = writeSwitch;
            this.writerLock = writerLock;
        }

        @Override
        public void run() {
            try {
                writeSwitch.on();
                writerLock.await();
                System.out.println("Writer Critical Section");
                writerLock.signal();
                writeSwitch.off();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
