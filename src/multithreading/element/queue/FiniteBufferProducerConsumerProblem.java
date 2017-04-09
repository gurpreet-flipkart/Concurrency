package multithreading.element.queue;

import multithreading.StatementOrder.Semaphore;

public class FiniteBufferProducerConsumerProblem {
	public static void main(String[] args) {
		Semaphore mutex = new Semaphore(1);
		Semaphore items = new Semaphore(0);
		Buffer buffer = new Buffer();
		Semaphore spaces = new Semaphore(4);
		for (int i = 1; i <= 10; i++) {
			Thread producer = new Thread(new Producer(mutex, items, spaces,
					buffer));
			producer.setName("Thread " + i);
			producer.start();
		}
		Thread consumer = new Thread(new Consumer(mutex, items, spaces, buffer));
		consumer.setName("Manmeet");
		consumer.start();

	}

	static class Buffer {
		private int val;

		public void set(int val) {
			this.val = val;
		}

		public int get() {
			return this.val;
		}
	}

	static class Producer extends Worker implements Runnable {

		Producer(Semaphore mutex, Semaphore items, Semaphore spaces,
				Buffer buffer) {
			super(mutex, items, spaces, buffer);
		}

		@Override
		public void run() {
			while (true) {
				try {
					spaces.await();
					mutex.await();
					System.out.println(Thread.currentThread().getName()
							+ " Produced");
					buffer.set((int) (Math.random() * 1000));
					mutex.signal();
					items.signal();
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	static class Worker {
		protected Semaphore mutex, items, spaces;
		protected Buffer buffer = null;

		Worker(Semaphore mutex, Semaphore items, Semaphore spaces, Buffer buffer) {
			this.mutex = mutex;
			this.items = items;
			this.spaces = spaces;
			this.buffer = buffer;
		}
	}

	static class Consumer extends Worker implements Runnable {

		Consumer(Semaphore mutex, Semaphore items, Semaphore spaces,
				Buffer buffer) {
			super(mutex, items, spaces, buffer);
		}

		@Override
		public void run() {
			while (true) {
				try {
					items.await();
					mutex.await();
					int data = buffer.get();
					System.out.println(Thread.currentThread().getName()
							+ " Consumed :" + data);
					mutex.signal();
					spaces.signal();
					// do something with the data here...
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
