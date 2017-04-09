package multithreading.element.queue;

import java.util.LinkedList;
import java.util.Queue;

import multithreading.StatementOrder.Semaphore;

public class ConcurrentQ {
	private final Queue<Semaphore> semaphoreQueue = new LinkedList<Semaphore>();
	private final Semaphore mutex = new Semaphore(1);

	public void await() throws InterruptedException {
		mutex.await();
		Semaphore sem = new Semaphore(0);
		semaphoreQueue.add(sem);
		mutex.signal();
		sem.await();

	}

	public void signal() {
		try {
			mutex.await();
			Semaphore sem = semaphoreQueue.remove();
			mutex.signal();
			sem.signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
