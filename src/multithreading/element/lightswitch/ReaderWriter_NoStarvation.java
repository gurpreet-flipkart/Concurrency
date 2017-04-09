package multithreading.element.lightswitch;

import multithreading.StatementOrder.Semaphore;

public class ReaderWriter_NoStarvation {
	/*Although the writer thread doesn't starve here. But if the writer updates are important,
	 * then we have a problem here. A reader may occasionally sneak in between writers.
	 * We want to prioritize the writers. If a writer is writing,another writer arrives, 
	 * then no reader should be allowed.*/
	public static void main(String[] args) {
		Semaphore roomLock = new Semaphore(1);
		Semaphore turntile = new Semaphore(1);
		LightSwitch lightSwitch = new LightSwitch(roomLock);
		Reader reader = new Reader(turntile, lightSwitch);
		Thread readerT = new Thread(reader);
		readerT.start();
		Thread writerT = new Thread(new Writer(roomLock, turntile));
		writerT.start();

	}

	static class Reader implements Runnable {
		private final LightSwitch lightSwitch;
		private final Semaphore turntile;

		Reader(Semaphore turntile, LightSwitch lightSwitch) {
			this.lightSwitch = lightSwitch;
			this.turntile = turntile;
		}

		@Override
		public void run() {
			try {
				// this is the characteristics of a turn tile.
				turntile.await();
				turntile.signal();
				this.lightSwitch.on();
				System.out.println("Executing reader critical section");
				this.lightSwitch.off();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	static class Writer implements Runnable {
		private final Semaphore roomEmpty;
		private final Semaphore turntile;

		public Writer(Semaphore roomLock, Semaphore turntile) {
			this.roomEmpty = roomLock;
			this.turntile = turntile;
		}

		@Override
		public void run() {
			try {
				this.turntile.await();
				this.roomEmpty.await();
				System.out.println("Executing writer critical section..");
				this.turntile.signal();
				this.roomEmpty.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
