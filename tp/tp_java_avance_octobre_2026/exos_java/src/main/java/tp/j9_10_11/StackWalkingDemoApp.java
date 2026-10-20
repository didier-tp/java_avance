package tp.j9_10_11;

import java.lang.StackWalker.StackFrame;
import java.util.List;

public class StackWalkingDemoApp {

	
	
	public static void main(String[] args) {
		StackWalkingDemoApp thisApp = new StackWalkingDemoApp();
		thisApp.m1();//calling m2 calling m3
		thisApp.mx();//calling mxy
		thisApp.my();//calling mxy
	}
	
	public void m1() {
		m2();
	}
	
	public void m2() {
		m3a();
		m3b();
		m3b2();
		m3b3();
	}
	
     public void mx() {
    	 System.out.println("mx() calling mxy()");
		 mxy();
	}
     
     public void my() {
    	System.out.println("my() calling mxy()");
 		mxy();
 	}
	
	public void mxy() {
		//may be called by mx() or my()
		StackWalker stackWalker = StackWalker.getInstance();
		if(MyStackWalkerUtil.wasCalledByMethod(stackWalker, "mx"))
			System.out.println("mxy() was called by mx()");
		else
			System.out.println("mxy() was not called by mx()");
		
		if(MyStackWalkerUtil.wasCalledByMethod(stackWalker, "my"))
			System.out.println("mxy() was called by my()");
		else
			System.out.println("mxy() was not called by my()");
	}
	
    public void m3a() {
    	List<StackFrame> stackTrace = MyStackWalkerUtil.getFramesList(StackWalker.getInstance());
    	System.out.println("stackTrace as List<StackFrame> : " +stackTrace + "\n");
    	//[tp.j9_10_11.StackWalkingDemoApp.m3a(StackWalkingDemoApp.java:26), tp.j9_10_11.StackWalkingDemoApp.m2(StackWalkingDemoApp.java:20), tp.j9_10_11.StackWalkingDemoApp.m1(StackWalkingDemoApp.java:16), tp.j9_10_11.StackWalkingDemoApp.main(StackWalkingDemoApp.java:12)]
	}
    
    
    public void m3b() {
    	StackWalker stackWalker = StackWalker.getInstance();
    	//stackWalker.forEach(System.out::println);
    	stackWalker.walk(stackFrameStream->{stackFrameStream.forEach(System.out::println); return null;});
    	/*
    	tp.j9_10_11.StackWalkingDemoApp.m3b(StackWalkingDemoApp.java:33)
		tp.j9_10_11.StackWalkingDemoApp.m2(StackWalkingDemoApp.java:21)
		tp.j9_10_11.StackWalkingDemoApp.m1(StackWalkingDemoApp.java:16)
		tp.j9_10_11.StackWalkingDemoApp.main(StackWalkingDemoApp.java:12)
    	 */
	}
    
    public void m3b2() {
    	StackWalker stackWalker = StackWalker.getInstance();
    	stackWalker.forEach(MyStackWalkerUtil::displayFrameDetails);
    	/*
    	StackWalker stackWalkerEx = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    	stackWalkerEx.forEach(MyStackWalkerUtil::displayFrameDetailsEx);
    	*/
	}
    
    public void m3b3() {
    	Class<?> caller = StackWalker
    		      .getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
    		      .getCallerClass();
    	System.out.println("getClassCaller()=" + caller.getCanonicalName());
    	StackFrame lastCallFrame = MyStackWalkerUtil.getLastCallFrame(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE));
    	System.out.println("last call: " + lastCallFrame.getMethodName() + " on " + lastCallFrame.getClassName());
    	//last call: m3b3 on tp.j9_10_11.StackWalkingDemoApp
    	StackFrame firstCallFrame = MyStackWalkerUtil.getFirstCallFrame(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE));
    	System.out.println("first call:" + firstCallFrame.getMethodName()+ " on " + firstCallFrame.getClassName());
    	//first call:main on tp.j9_10_11.StackWalkingDemoApp
	}
    

	

}
