package multithreading.element.barrier;

import multithreading.element.barrier.nonrepeatabletask.DeadlockSingleBarrier;
import multithreading.element.barrier.nonrepeatabletask.NonRepeatingBarrierTask;
import multithreading.element.barrier.nonrepeatabletask.NonReusableBarrier;
import multithreading.element.barrier.repeatabletask.DeadlockRepeatBarrier;
import multithreading.element.barrier.repeatabletask.LapFlawedBarrier;
import multithreading.element.barrier.repeatabletask.RepeatingBarrierTask;
import multithreading.element.barrier.repeatabletask.ReusableBarrier;

public class BarrierTestDriver {
	public static void main(String[] args) {
		test_Barrier_Repeating_Task();
	}

	public static void testIncorrectBarrierNonRepeatTask() {
		int allowed = 5;
		IBarrier barrier = new DeadlockSingleBarrier(allowed);
		for (int i = 1; i <= allowed; i++) {
			new Thread(new NonRepeatingBarrierTask(barrier)).start();
		}
	}

	public static void testCorrectBarrierNonRepeatTask() {
		int allowed = 5;
		IBarrier barrier = new NonReusableBarrier(allowed);
		for (int i = 1; i <= allowed; i++) {
			Thread t = new Thread(new NonRepeatingBarrierTask(barrier));
			t.setName("Thread " + i);
			t.start();
		}
	}

	public static void test_InCorrectBarrier_RepeatingTask() {
		int allowed = 3;
		IBarrier barrier = new DeadlockRepeatBarrier(allowed);
		for (int i = 1; i <= allowed; i++) {
			new Thread(new RepeatingBarrierTask(barrier)).start();
		}
	}

	public static void test_InCorrectBarrier_Better_Repeating_Task() {
		int allowed = 3;
		IBarrier barrier = new LapFlawedBarrier(allowed);
		new Thread(new RepeatingBarrierTask(barrier, 100)).start();
		for (int i = 1; i <= allowed - 1; i++) {
			new Thread(new RepeatingBarrierTask(barrier, 1000)).start();
		}
	}

	public static void test_Barrier_Repeating_Task() {
		int allowed = 3;
		IBarrier barrier = new ReusableBarrier(allowed);
		new Thread(new RepeatingBarrierTask(barrier, 100)).start();
		for (int i = 1; i <= allowed - 1; i++) {
			new Thread(new RepeatingBarrierTask(barrier, 1000)).start();
		}
	}

}
