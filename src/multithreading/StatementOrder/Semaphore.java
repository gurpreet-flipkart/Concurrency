package multithreading.StatementOrder;

public class Semaphore {
	private final java.util.concurrent.Semaphore semaphore;

	public Semaphore(int permits) {
		this.semaphore = new java.util.concurrent.Semaphore(permits);
	}

	public void await() throws InterruptedException {
		this.semaphore.acquire();
	}

	public void signal() {
		this.semaphore.release();
	}
	
	public void signal(int n){
		this.semaphore.release(n);
	}
	
	public  int getQueueCount(){
		return this.semaphore.getQueueLength();
	}
}
