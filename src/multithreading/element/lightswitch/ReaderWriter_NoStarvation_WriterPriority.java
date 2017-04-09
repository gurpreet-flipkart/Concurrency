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
		Semaphore noReaders = new Semaphore(1);
		Semaphore noWriters = new Semaphore(1);
		LightSwitch readSwitch = new LightSwitch(noWriters);
		LightSwitch writeSwitch = new LightSwitch(noReaders);
		Thread reader=new Thread(new LowPriorityReader(readSwitch, noReaders));
		reader.start();
		Thread writer=new Thread(new Writer(writeSwitch, noWriters));
		writer.start();
	}

	static class LowPriorityReader implements Runnable {
		private final LightSwitch readSwitch;
		private final Semaphore noReaders;

		LowPriorityReader(LightSwitch readSwitch, Semaphore noReaders) {
			this.readSwitch = readSwitch;
			this.noReaders = noReaders;
		}

		@Override
		public void run() {
			try {
				this.noReaders.await();
				this.readSwitch.on();
				this.noReaders.signal();
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
		private final Semaphore noWriters;

		Writer(LightSwitch writeSwitch, Semaphore noWriters) {
			this.writeSwitch = writeSwitch;
			this.noWriters = noWriters;
		}

		@Override
		public void run() {
			try {
				writeSwitch.on();
				noWriters.await();
				System.out.println("Writer Critical Section");
				noWriters.signal();
				writeSwitch.off();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
