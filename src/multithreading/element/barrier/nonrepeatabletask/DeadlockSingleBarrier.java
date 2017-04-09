package multithreading.element.barrier.nonrepeatabletask;

import multithreading.StatementOrder.Semaphore;
import multithreading.element.barrier.IBarrier;

 public class DeadlockSingleBarrier implements IBarrier{
	private final int n;
	private int count=0;
	public DeadlockSingleBarrier(int n) {
		this.n=n;
	}
	
	private final Semaphore mutex=new Semaphore(1);
	protected final Semaphore barrier=new Semaphore(0);
	
	public void waitAtBarrier() throws InterruptedException{
		this.mutex.await();
		count+=1;
		this.mutex.signal();
		if(count==n){
			this.barrier.signal();
		}
		
		this.barrier.await();
	}

	@Override
	public void reset() throws InterruptedException {
		throw new UnsupportedOperationException();
	}
	
}
