package multithreading.experiments;

public class Experiment {
//make a thread wait and then sleep...what happens when another wakes him up..
	private static final Object mutex=new Object();
	
	Thread t=new Thread(new Runnable() {
		
		@Override
		public void run() {
			synchronized (mutex) {
			try {
				mutex.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			}
			
		}
	});
}
