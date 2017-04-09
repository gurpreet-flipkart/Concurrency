package multithreading.element.queue;

import multithreading.StatementOrder.Semaphore;

public class ProducerConsumerProblem {
	public static void main(String[] args) {
		Semaphore mutex = new Semaphore(1);
		Semaphore items = new Semaphore(0);
		Buffer buffer = new Buffer();
		Thread producer = new Thread(new Producer(mutex, items, buffer));
		Thread consumer = new Thread(new Consumer(mutex, items, buffer));
		producer.start();
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

		Producer(Semaphore mutex, Semaphore items, Buffer buffer) {
			super(mutex, items, buffer);
		}

		@Override
		public void run() {
			while (true) {
				try {
					mutex.await();
					System.out.println("Produced");
					buffer.set((int) (Math.random() * 1000));
					mutex.signal();
					items.signal();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	static class Worker {
		protected Semaphore mutex, items;
		protected Buffer buffer = null;

		Worker(Semaphore mutex, Semaphore items, Buffer buffer) {
			this.mutex = mutex;
			this.items = items;
			this.buffer = buffer;
		}
	}

	static class Consumer extends Worker implements Runnable {

		Consumer(Semaphore mutex, Semaphore items, Buffer buffer) {
			super(mutex, items, buffer);
		}

		@Override
		public void run() {
			while (true) {
				try {
					items.await();
					mutex.await();
					int data = buffer.get();
					System.out.println("Consumed :"+data);
					mutex.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
