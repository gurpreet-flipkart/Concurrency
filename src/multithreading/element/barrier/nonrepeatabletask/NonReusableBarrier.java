package multithreading.element.barrier.nonrepeatabletask;

public class NonReusableBarrier extends DeadlockSingleBarrier {

	public NonReusableBarrier(int allowed) {
		super(allowed);
	}
	
	@Override
	public void waitAtBarrier() throws InterruptedException {
		// TODO Auto-generated method stub
		super.waitAtBarrier();
		super.barrier.signal();
		System.out.println("Barrier crossed");
	}

}
