package multithreading.util;

public class ThreadUtil {
	public static void execute(String msg) {
		try {
			System.out.println("[" + Thread.currentThread().getName() + "] "
					+ msg);
			Thread.sleep((long) ( 100));

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void execute(String msg, long time) {
		try {
			System.out.println("[" + Thread.currentThread().getName() + "] "
					+ msg);
			Thread.sleep(time);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
