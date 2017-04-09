package multithreading.element.barrier.repeatabletask;

import multithreading.StatementOrder.Semaphore;
import multithreading.element.barrier.IBarrier;

public class ReusableBarrier implements IBarrier{
	private final Semaphore mutex=new Semaphore(1);
	private final Semaphore turntile=new Semaphore(0);
	private final Semaphore turntile2=new Semaphore(1);
	private final int n;
	private int count=0;
	
	public ReusableBarrier(int permits) {
		this.n=permits;
	}
	@Override
	public void waitAtBarrier() throws InterruptedException {
		mutex.await();
		count+=1;
		if(count==n){
			turntile2.await();//lock the turn tile 2
			turntile.signal();//open the turn tile 1
		}
		mutex.signal();
		
		turntile.await();
		turntile.signal();
	}

	@Override
	public void reset() throws InterruptedException {
		mutex.await();
		count-=1;
		if(count==0){
			turntile.await();//lock the turn tile 
			turntile2.signal(); //open the turn tile 2
		}
		mutex.signal();
		turntile2.await();
		turntile2.signal();
	}

}
