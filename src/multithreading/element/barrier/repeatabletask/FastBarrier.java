package multithreading.element.barrier.repeatabletask;

import multithreading.StatementOrder.Semaphore;
import multithreading.element.barrier.IBarrier;

public class FastBarrier implements IBarrier {
	private final Semaphore mutex = new Semaphore(1);
	private final Semaphore turntile = new Semaphore(0);
	private final Semaphore turntile2 = new Semaphore(1);
	private final int n;
	private int count = 0;

	public FastBarrier(int permits) {
		this.n = permits;
	}

	@Override
	public void waitAtBarrier() throws InterruptedException {
		mutex.await();
		count += 1;
		if (count == n) {
			turntile.signal(n);// open the turn tile 1 for n threads.
		}
		mutex.signal();

		turntile.await();
	}

	@Override
	public void reset() throws InterruptedException {
		mutex.await();
		count -= 1;
		if (count == 0) {
			turntile2.signal(n); // open the turn tile 2 for n threads.
		}
		mutex.signal();
		turntile2.await();
	}

}
