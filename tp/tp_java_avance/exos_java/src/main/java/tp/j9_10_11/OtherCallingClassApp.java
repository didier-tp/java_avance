package tp.j9_10_11;

public class OtherCallingClassApp {
	public static void main(String[] args) {
		StackWalkingDemoApp swda = new StackWalkingDemoApp();
		swda.m1();//calling m2 calling m3
	}
}
