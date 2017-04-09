package multithreading.element.barrier.repeatabletask;

import multithreading.StatementOrder.Semaphore;
import multithreading.element.barrier.IBarrier;

public class LapFlawedBarrier implements IBarrier {
	private final int n;
	private int count = 0;

	public LapFlawedBarrier(int n) {
		this.n = n;
	}

	private final Semaphore mutex = new Semaphore(1);
	protected final Semaphore turntile = new Semaphore(0);

	@Override
	public void waitAtBarrier() throws InterruptedException {
		mutex.await();
		count += 1;
		//Thread.sleep(1000);
		if (count == n) {
			System.out.println("signalling.."
					+ Thread.currentThread().getName());
			turntile.signal();
		}
		mutex.signal();

		turntile.await();
		turntile.signal();
	}

	@Override
	public void reset() throws InterruptedException {
		mutex.await();
		count -= 1;
		//Thread.sleep(1000);
		// deadlock prone region.
		if (count == 0) {
			System.out.println("Waiting.." + Thread.currentThread().getName());
			turntile.await();
		}
		mutex.signal();

	}
}
