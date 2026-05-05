package tp.j9_10_11;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyStackWalkerUtil {
	
	/*
	 NB: l'option StackWalker.Option.RETAIN_CLASS_REFERENCE 
	 permet d'effectuer un appel ultérieur à StackWalker.getCallerClass() 
	 et StackFrame.getDeclaringClass()
	 -----------------------
	 l'option StackWalker.Option.SHOW_REFLECT_FRAMES permet de voir 
	 des "frames" spéciales (par défaut pas conservées) liées à des invocations indirectes
	 via l'api java.lang.reflect (ex: Method.invoke() , Contructor.newInstance() )
	 -----------------------
	 l'option StackWalker.Option.SHOW_HIDDEN_FRAMES permet de voir des "frames" spéciales
	 liées aux mécanismes internes de la JVM
	 */
	
	//for walker.walk(MyStackWalkerUtil::stackFrameAsList);
	public static List<StackFrame> stackFrameAsList(Stream<StackFrame> stackFrameStream) {
		String thisUtilityCallName = MyStackWalkerUtil.class.getName();
	    return stackFrameStream
	    		.filter(f -> !f.getClassName().equals(thisUtilityCallName))//remove side effect of this static utility class
	    		.collect(Collectors.toList());
	}
	
	
	
	public static List<StackFrame> getFramesList(StackWalker walker) {
		List<StackFrame> frameList =  walker.walk(MyStackWalkerUtil::stackFrameAsList);
		return frameList;
	}
	
	public static boolean wasCalledByMethod(StackWalker walker,String methodName) {
		List<StackFrame> frameList =  walker.walk(
				stackFrameStream->{
					return stackFrameStream.filter(f -> f.getMethodName().equals(methodName))
	    		                    .collect(Collectors.toList()); 
				} );
		return !frameList.isEmpty();
	}
	
	
	public static StackFrame getLastCallFrame(StackWalker walker) {
		List<StackFrame> frames = getFramesList(walker);
		return frames.get(0);
	}
	
	
	public static StackFrame getFirstCallFrame(StackWalker walker) {
		List<StackFrame> frames = getFramesList(walker);
		return frames.get(frames.size()-1);
	}
	
	public static void displayFrameDetails( StackFrame stackFrame) {
		System.out.println(" \n*******************************************\n");
		System.out.println(" Class name : " + stackFrame.getClassName());
		System.out.println(" File name : " + stackFrame.getFileName());
		System.out.println(" Bytecode index : " + stackFrame.getByteCodeIndex());
		System.out.println(" Line number : " + stackFrame.getLineNumber());
		System.out.println(" Method name : " + stackFrame.getMethodName());
		System.out.println(" Is method native or not? : " + stackFrame.isNativeMethod());
	}
	
	public static void displayFrameDetailsEx( StackFrame stackFrame) {
		displayFrameDetails(stackFrame);
		System.out.println(" Declaring Class name : " + stackFrame.getDeclaringClass());
	}
	

}
