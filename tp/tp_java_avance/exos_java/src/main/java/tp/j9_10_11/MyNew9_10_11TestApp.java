package tp.j9_10_11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class MyNew9_10_11TestApp {
	
	public static void test_stream_improvement_since_java9() {
		IntStream.iterate(1, i -> i < 5, i -> i + 1)
		         .forEach(System.out::println);
	}
	
	public static void test_collection_factory_method_of_since_java9(){
		List<String> ls2 = List.of("acb" , "def" , "ghi");//List.of(...) since java 9
		//ls2.add("jkl"); //java.lang.UnsupportedOperationException on java.util.ImmutableCollections
		ls2.stream().forEach(System.out::println);
		
		//IMMUTABLE COLLECTIONS --> .of(...) = BOF if immutable is not wished !!!
		
		Set<Integer> integers = Set.of(1,2,3,4); //List.of(...) since java 9
		//integers.add(5);//java.lang.UnsupportedOperationException / IMMUTABLE !
		integers.stream()
		         .map(i->i+1)
		         .forEach(System.out::println);
	}
	
	public static void test_private_interface_method_since_java9() {
		MyInterface obj = new MyItfImpl();
		System.out.println("obj.f2()="+obj.f2());
		System.out.println("obj.f3()="+obj.f3()); //same private sub method (for defaullt)
	}
	
	public static void test_var_since_java10(){
		List<String> ls1 = new ArrayList<String>(); //since java 5
		List<String> ls2 = new ArrayList<>(); //since java 7 (diamond)
		var ls3 = new ArrayList<String>(); //type inference since java 10 with new keyword "var"
		
		ls3.add("abc"); ls3.add("def");
		ls3.stream().forEach(System.out::println);
		
		//NB "var" (since java 10) is not a reserved word.
		//an old variable may be nammed "var" :
		int var=12; //possible (but NOT ADVISED) .
		System.out.println("var="+var);
	}
	
	public static void test_new_http2_client_since_java9_standard_since_java11() {
		
		//HttpClient client = HttpClient.newHttpClient();
		HttpClient client = HttpClient.newBuilder()
				   //.proxy(ProxySelector.of(new InetSocketAddress("100.78.112.201", 8001)))
				   .connectTimeout(Duration.ofSeconds(10))
				   .build();

		HttpRequest req =
		   HttpRequest.newBuilder(URI.create("http://www.google.com"))
		              .header("User-Agent","Java")
		              .GET()
		              .build();
        /*
        //En mode synchrone :
		try {
			HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
			System.out.println("reponse status:" + resp.statusCode()); //200 for ok
			System.out.println("reponse uri:" + resp.uri().toString()); //http://www.google.com
			System.out.println("reponse type:" + resp.headers().map().get("Content-Type"));//[text/html; charset=ISO-8859-1]
			System.out.println("reponse text:" + resp.body());//texte html  habituellement affich� par navigateur
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} */
		
		//En mode asynchrone , sendAsync retournant un CompletableFuture : 
		client.sendAsync(req, BodyHandlers.ofString())
		      .thenAccept(resp -> {
		    		System.out.println("recuperation r�ponse asynchrone / interpreted by " + Thread.currentThread().getName());
		    	    System.out.println("reponse status:" + resp.statusCode()); 
					System.out.println("reponse uri:" + resp.uri().toString()); 
					System.out.println("reponse type:" + resp.headers().map().get("Content-Type"));
					System.out.println("reponse text size:" + resp.body().length());
		      		});
        System.out.println("suite synchrone interpreted by " + Thread.currentThread().getName());
		try {
			Thread.sleep(2000);//pause ici pour eviter arr�t complet du programme 
			                    // avant la fin des taches de fond asynchrones
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("fin synchrone / interpreted by " + Thread.currentThread().getName());
		
	}
	
   public static void test_new_httpClient_withSubscriber() {
		
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest req =
		   HttpRequest.newBuilder(URI.create("http://www.google.com"))
		              .header("User-Agent","Java")
		              .GET()
		              .build();
		
		MySimplePrintSubscriber subscriber1 = new MySimplePrintSubscriber();
		MyListOfByteBufferSubscriber subscriber2 = new MyListOfByteBufferSubscriber();

		// BodyHandlers.fromLineSubscriber(s) for registering a subscriber that will receive response text line by line
		// the return CompletableFuture contains no body but status and other infos
		//client.sendAsync(req, BodyHandlers.fromLineSubscriber(subscriber1))
		client.sendAsync(req, BodyHandlers.fromSubscriber(subscriber2))
	      .thenApply(HttpResponse::statusCode) //extract statusCode from httpResponse
	      .thenAccept((status) -> {
	          if (status != 200) {
	              System.err.printf("ERROR: %d status received%n", status);
	          }
	      });
		
		
        System.out.println("suite synchrone interpreted by " + Thread.currentThread().getName());
		try {
			Thread.sleep(2000);//pause ici pour eviter arr�t complet du programme 
			                    // avant la fin des taches de fond asynchrones
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("fin synchrone / interpreted by " + Thread.currentThread().getName());
		
	}
   
   public static void oldTryCatchFinallyStyle(){
	   Scanner scanner = null;
	   try {
	       scanner = new Scanner(new File("files/test.txt"));
	       while (scanner.hasNext()) {
	           System.out.println(scanner.nextLine());
	       }
	   } catch (FileNotFoundException e) {
	       e.printStackTrace();
	   } finally {
	       if (scanner != null) {
	           scanner.close();
	       }
	   }
   }
   
   public static void tryWithResourceStyleSinceJava7(){
	   try (Scanner scanner = new Scanner(new File("files/test.txt"))) {
		    while (scanner.hasNext()) {
		        System.out.println(scanner.nextLine());
		    }
		} catch (FileNotFoundException fnfe) {
		    fnfe.printStackTrace();
		}
	   //Automatic close (even without finally) because of 
	   //Scanner implementing java.lang.AutoCloseable
	   
	   //- possible d'implémenter soit meme AutoCloseable.close() sur nouvelle classe
	   //- try(res1=...;res2=...) est possible (avec ressources multiples)
   }
   
   public static void tryWithResourceStyleSinceJava9() throws IOException{
	   final Scanner scanner = new Scanner(new File("files/test.txt"));
	   //scanner can now be declared as final before try(...){ } since java9
	   try (scanner) {
		    while (scanner.hasNext()) {
		        System.out.println(scanner.nextLine());
		    }
		} 
	   //Automatic close (even without finally) because of 
	   //Scanner implementing java.lang.AutoCloseable of java7
   }
   
   public static void test_try_with_resources_improvement_since_java9(){
	   //oldTryCatchFinallyStyle();
	   //tryWithResourceStyleSinceJava7();
	   try {
		tryWithResourceStyleSinceJava9();
	   } catch (IOException e) {
		e.printStackTrace();
	   }
   }

	public static void main(String[] args) {
		
		//test_collection_factory_method_of_since_java9();
		//test_stream_improvement_since_java9();
		//test_private_interface_method_since_java9();
		test_var_since_java10();
		//test_new_http2_client_since_java9_standard_since_java11();
		//test_new_httpClient_withSubscriber();
		//test_try_with_resources_improvement_since_java9();
		//testImprovedSafeVarargs();
	}
	
	//bad V1 with warnings (MyNew9_10_11TestApp.java uses unchecked or unsafe operations ,
	//  Type safety: Potential heap pollution via varargs parameter elements)
	public static <T> T[] unsafeAsArray(T... elements) {
	    return elements; // unsafe! don't ever return a parameterized varargs array ,just returning Object[]
	}
	
	
	//V2 (without warning because of @SafeVarargs on a function with <T> and ...)
	//NB: @SafeVarargs exists since java 7 
	//since java 9, @SafeVarargs can be used on private methods
	@SafeVarargs
	private static <T> Object[] safeAsArray(T... elements) {		
	    return elements; // safe as just returning Object[]
	}
    
	public static <T> T[] badArrayOfThree(T elt) {
	    T[] arrayOf3Elts = unsafeAsArray(elt, elt, elt); // broken! This will be an Object[] no matter what T is
	    return arrayOf3Elts;
	}
	
	public static <T> Object[] goodArrayOfThree(T elt) {
	    Object[] arrayOf3Elts = safeAsArray(elt, elt, elt); // explicitly view as Object[] no matter what T is
	    return arrayOf3Elts;
	}

	public static void threeBadAndGoodXxx() {
	  
	   try {
		String[] threeXxxAsStringArray = badArrayOfThree("xxx"); //cast exception
		   System.out.println("threeXxxAsStringArray:"+threeXxxAsStringArray);
	   } catch (Exception e) {
		    System.err.println(e.getMessage());	//cast exception	
	   }
	   
	   Object[] threeXxxAsObjectArray = goodArrayOfThree("xxx"); 
	   System.out.println("threeXxxAsObjectArray:");
	   for(Object obj : threeXxxAsObjectArray)
		   System.out.println("\t"+obj); //ok , affiche 3 fois xxx
	}

	
	public static void testImprovedSafeVarargs() {
		threeBadAndGoodXxx();
	}
	
}
