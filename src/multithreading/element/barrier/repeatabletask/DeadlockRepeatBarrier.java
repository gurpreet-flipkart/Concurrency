package multithreading.element.barrier.repeatabletask;

import multithreading.StatementOrder.Semaphore;
import multithreading.element.barrier.IBarrier;

public class DeadlockRepeatBarrier implements IBarrier {
	private final int n;
	private int count = 0;
	private boolean hitZero = false;

	public DeadlockRepeatBarrier(int n) {
		this.n = n;
	}

	private final Semaphore mutex = new Semaphore(1);
	protected final Semaphore turntile = new Semaphore(0);
	private boolean hitN = false;

	@Override
	public void waitAtBarrier() throws InterruptedException {
		mutex.await();
		count += 1;
		mutex.signal();
		Thread.sleep(1000);
		if (count == n) {
			System.out.println("signalling.."
					+ Thread.currentThread().getName());
			turntile.signal();
		}
		turntile.await();
		turntile.signal();
	}

	@Override
	public void reset() throws InterruptedException {
		mutex.await();
		count -= 1;
		mutex.signal();
		Thread.sleep(1000);
		//deadlock prone region.
		if (count == 0) {
			System.out.println("Waiting.." + Thread.currentThread().getName());
			turntile.await();
		}
	}
}
