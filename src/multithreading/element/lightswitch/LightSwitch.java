package multithreading.element.lightswitch;

import multithreading.StatementOrder.Semaphore;

public class LightSwitch {
	private final Semaphore resSemaphore;
	private int members = 0;
	private final Semaphore mutex = new Semaphore(1);

	public LightSwitch(Semaphore resSemaphore) {
		this.resSemaphore = resSemaphore;
	}

	public void on() throws InterruptedException {
		mutex.await();
		members += 1;
		if (members == 1) {
			resSemaphore.await();
		}
		mutex.signal();
	}

	public void off() throws InterruptedException {
		mutex.await();
		members -= 1;
		if (members == 0) {
			resSemaphore.signal();
		}
		mutex.signal();
	}
}
