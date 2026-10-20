package tp.concurent.basic;

import tp.concurent.task.CallableComputing;

import java.util.concurrent.*;

//CompletionService depuis jdk1.6 avec methode take() retournant un Future
//logique producteur/consommateur
public class EssaiCompletionService {

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(4); //jusqu'à 4 threads en //
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
		
		double[] tabVal = { 4, 9 , 16 , 25, 36, 49 , 64, 81, 100 };
		int taille = tabVal.length;
		for(int i=0;i<taille;i++){
			Callable<String> c = new CallableComputing(tabVal[i]);
			//CompletionService<String> encapsule l'executor et est typé comme Future<T>
			completionService.submit(c); //lancement asynchrone d'un "producteur"
	        	
		}
		for(int i=0;i<taille;i++){
			try {
				Future<String> futureRes = completionService.take(); //attente du PREMIER TERMINE
				System.out.println(futureRes.get()); //consomateur simple 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        System.out.println("fin-main");
        //arrêter l'executor (si besoin en différé):
        executor.shutdown();
        

	}

}
