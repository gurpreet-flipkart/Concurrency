package multithreading.StatementOrder;

import multithreading.util.ThreadUtil;

public class StatementExecutionOrderDemo {
	private static final Semaphore aArrived = new Semaphore(0);
	private static final Semaphore bArrived = new Semaphore(0);
	static Thread a = new Thread(new Runnable() {

		@Override
		public void run() {
			ThreadUtil.execute("A1");
			aArrived.signal();
			try {
				bArrived.await();
				ThreadUtil.execute("A2");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	});

	static Thread b = new Thread(new Runnable() {

		@Override
		public void run() {
			ThreadUtil.execute("B1");
			bArrived.signal();
			try {
				aArrived.await();
				ThreadUtil.execute("B2");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	});

	public static void main(String[] args) throws InterruptedException {
		b.start();
		Thread.sleep(1000);
		a.start();
	}

}
