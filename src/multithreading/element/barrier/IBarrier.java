package multithreading.element.barrier;

public interface IBarrier {
	public void waitAtBarrier()throws InterruptedException;
	
	public void reset() throws InterruptedException;
}
