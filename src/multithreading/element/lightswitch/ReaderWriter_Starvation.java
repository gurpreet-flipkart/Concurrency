package multithreading.element.lightswitch;

import multithreading.StatementOrder.Semaphore;

public class ReaderWriter_Starvation {
	//here the writer is likely to starve as the readers continue to come and go.
	/*
	 * The solution should be :
	 * If the writer asks for a lock..no further readers should be allowed.
	 * When the writer asks for a lock, it locks access to further readers.
	 * No new readers can then proceed.
	 * This solution is in ReaderWriterProblemBetter.*/
	public static void main(String[] args) {
		Semaphore roomLock = new Semaphore(1);
		LightSwitch lightSwitch = new LightSwitch(roomLock);
		Reader reader = new Reader(lightSwitch);
		Thread readerT = new Thread(reader);
		readerT.start();
		Thread writerT = new Thread(new Writer(roomLock));
		writerT.start();

	}

	static class Reader implements Runnable {
		private final LightSwitch lightSwitch;

		Reader(LightSwitch lightSwitch) {
			this.lightSwitch = lightSwitch;
		}

		@Override
		public void run() {
			try {
				this.lightSwitch.on();
				System.out.println("Executing reader critical section");
				this.lightSwitch.off();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	static class Writer implements Runnable {
		private final Semaphore roomLock;

		public Writer(Semaphore roomLock) {
			this.roomLock = roomLock;
		}

		@Override
		public void run() {
			try {
				this.roomLock.await();
				System.out.println("Executing writer critical section..");
				this.roomLock.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
