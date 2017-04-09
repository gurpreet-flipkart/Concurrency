package multithreading.element.barrier.nonrepeatabletask;

import multithreading.element.barrier.IBarrier;
import multithreading.util.ThreadUtil;

public class NonRepeatingBarrierTask implements Runnable {
	static int index = 0;
	private final int id;
	private final IBarrier barrier;

	public NonRepeatingBarrierTask(IBarrier barrier) {
		this.id = index;
		index++;
		this.barrier = barrier;
	}
	private int speed=1000;
	public NonRepeatingBarrierTask(IBarrier barrier, int speed) {
		this.id = index;
		index++;
		this.barrier = barrier;
		this.speed=speed;
	}

	@Override
	public void run() {
		ThreadUtil.execute("Executing Rendevous " + this.id,speed);
		try {
			barrier.waitAtBarrier();
			ThreadUtil.execute("Executing Critical section " + this.id, speed);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}