package tp.j15_16_17;

public class TestPatternMatchingSwitchPreviewApp {
	
/*
	public static void main(String[] args) {
		System.out.println(getDoubleUsingSwitch("12.5"));
		System.out.println(getDoubleUsingSwitch(12.6));
		System.out.println(getDoubleUsingSwitch(12.7f));
		System.out.println(getDoubleUsingSwitch(12));
		System.out.println(getDoubleUsingSwitch(null));
		
		System.out.println(getTypeAnimalDomesticAsString(new Chat()));
		System.out.println(getTypeAnimalDomesticAsString(new Chien()));
		System.out.println(getTypeAnimalDomesticAsString(new ChienFou()));
		
		
		System.out.println(getTypeVivantFromRecord(new Vivant.Invertebre("limace","pas rapide")));
		System.out.println(getTypeVivantFromRecord(new Vivant.Vertebre("homme","intelligent","pas toujours sage")));
	}
	
	
	//NB: still in "preview mode"  in java 17-LTS !!!
	//java --enable-preview --source 17 ....
	static double getDoubleUsingSwitch(Object o) {
	    return switch (o) {
	        case Integer i -> i.doubleValue();
	        case Float f -> f.doubleValue();
	        case Double d -> d.doubleValue();
	        case String s -> Double.parseDouble(s);
	        case null -> 0d;
	        default -> 0d;
	    };
	}
	
	//NB: still in "preview mode"  in java 17-LTS !!!
	//java --enable-preview --source 17 ....
	//ou bien tester avec le jdk21 en mode normal (sans preview)
	static String getTypeAnimalDomesticAsString(AnimalDomestique a) {
		    return switch (a) {
		        case Chat chat -> "chat";
		        case Chien chien -> "chien";
		        case Oiseau oiseau -> "oiseau";
		        //default -> "ni chat , ni chien, ni oiseau";
		    };
		    
		  
		     //Remarque importante :
		     //Ce switch/case avec pattern-matching de type 
		     //nécessite absolument une partie "default" si
		     //AnimalDomestique n'est pas scellé (sans le mot clef sealed et ...)
		     //et ne nécessite pas de partie "default" si AnimalDomestique n'est pas scellé
		   
		     //Autrement dit l'intéret principal des classes scéllées tient dans la possibilité
		     //d'utilisiser directement les différents types de classes concrètes 
		     //(dérivant d'un même type abstrait scellé) au sein d'un switch/case sans default
		     //sans avoir besoin de gérer en parallèle une énumération à valeurs possibles fixes/finies.
		    
	}
	
	static String getTypeVivantFromRecord(Vivant v) {
	    return switch (v) {
	        case Vivant.Invertebre ive -> "invertebre";
	        case Vivant.Vertebre ve -> "vertebre";
	        //with default if Vivant is not sealed
	    };
	}

 */
}
