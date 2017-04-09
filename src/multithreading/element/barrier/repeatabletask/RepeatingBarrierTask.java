package multithreading.element.barrier.repeatabletask;

import multithreading.element.barrier.IBarrier;
import multithreading.util.ThreadUtil;

public class RepeatingBarrierTask implements Runnable {
	private final IBarrier barrier;
	private int speed=1000;
	public RepeatingBarrierTask(IBarrier barrier, int speed) {
		this.barrier = barrier;
		this.speed=speed;
	}
	public RepeatingBarrierTask(IBarrier barrier) {
		this.barrier = barrier;
	}

	@Override
	public void run() {
		int i=1;
		while (true) {
			
			ThreadUtil.execute("Rendezvous "+i, speed);
			try {
				this.barrier.waitAtBarrier();
				ThreadUtil.execute("Critical Section ", speed);
				this.barrier.reset();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}

	}

}
