package multithreading.element.queue;

import multithreading.StatementOrder.Semaphore;

public class DancerProblem {
	private static int dance=0;
	public static void dance() {
		synchronized (DancerProblem.class) {
			dance++;
			System.out.println("Dancing "+dance);
			
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Semaphore leaderQ = new Semaphore(0);
		Semaphore followerQ = new Semaphore(0);
		while (true) {
			Thread.sleep(1000);
			boolean choice = (int) (Math.random() + 0.5) == 1 ? true : false;
			System.out.println(leaderQ.getQueueCount()+":"+followerQ.getQueueCount());
			Thread thread=null;
			if (choice) {
				System.out.println("Leader");
				thread = new Thread(new Leader(leaderQ, followerQ));
			} else {
				System.out.println("Follower");
				thread = new Thread(new Follower(leaderQ, followerQ));
			}
			thread.start();
		}
	}

	static class Leader implements Runnable {
		private final Semaphore leaderQueue;
		private final Semaphore followerQueue;

		Leader(Semaphore leaderQueue, Semaphore followerQueue) {
			this.leaderQueue = leaderQueue;
			this.followerQueue = followerQueue;
		}

		@Override
		public void run() {
			followerQueue.signal();
			try {
				this.leaderQueue.await();
				dance();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static class Follower implements Runnable {
		private final Semaphore leaderQueue;
		private final Semaphore followerQueue;

		Follower(Semaphore leaderQueue, Semaphore followerQueue) {
			this.leaderQueue = leaderQueue;
			this.followerQueue = followerQueue;
		}

		@Override
		public void run() {
			leaderQueue.signal();
			try {
				this.followerQueue.await();
				dance();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
