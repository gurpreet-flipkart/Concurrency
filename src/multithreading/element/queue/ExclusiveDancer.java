package multithreading.element.queue;

import multithreading.StatementOrder.Semaphore;

public class ExclusiveDancer {

	private static int followers = 0;
	private static int leaders = 0;

	public static void main(String[] args) throws InterruptedException {
		Semaphore mutex = new Semaphore(1);
		Semaphore leadersQ = new Semaphore(0);
		Semaphore followersQ = new Semaphore(0);
		while (true) {
			Thread.sleep(7000);
			boolean choice = (int) (Math.random() + 0.5) == 1 ? true : false;
			System.out.println(leadersQ.getQueueCount() + ":"
					+ followersQ.getQueueCount());
			Thread thread = null;
			if (choice) {
				System.out.println("Leader");
				thread = new Thread(new Leader(mutex, leadersQ, followersQ));
			} else {
				System.out.println("Follower");
				thread = new Thread(new Follower(mutex, leadersQ, followersQ));
			}
			thread.start();
		}
	}

	static void dance() {
		System.out.println("Dance");
	}

	static class Leader implements Runnable {
		private Semaphore mutex;
		private Semaphore leadersQ;
		private Semaphore followersQ;

		public Leader(Semaphore mutex, Semaphore leadersQ, Semaphore followersQ) {
			this.mutex = mutex;
			this.leadersQ = leadersQ;
			this.followersQ = followersQ;
		}

		@Override
		public void run() {
			try {
				mutex.await();
				if (followers > 0) {
					System.out.println("Follower exists.");
					followers--;
					followersQ.signal();
				} else {
					leaders++;
					mutex.signal();
					System.out.println("waiting for follower");
					leadersQ.await();
				}
				dance();
				mutex.signal();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	static class Follower implements Runnable {
		private Semaphore mutex;
		private Semaphore leadersQ;
		private Semaphore followersQ;

		public Follower(Semaphore mutex, Semaphore leadersQ,
				Semaphore followersQ) {
			this.mutex = mutex;
			this.leadersQ = leadersQ;
			this.followersQ = followersQ;
		}

		@Override
		public void run() {
			try {
				mutex.await();
				if (leaders > 0) {
					System.out.println("leader exists");
					leaders--;
					leadersQ.signal();
				} else {
					followers++;
					mutex.signal();
					System.out.println("Waiting for leader.");
					followersQ.await();
				}
				dance();
				mutex.signal();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
