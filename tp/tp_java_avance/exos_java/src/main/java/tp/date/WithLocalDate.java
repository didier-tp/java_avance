package tp.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class WithLocalDate {

	public static void main(String[] args) {
		// Manipulations basiques de dates (via des nouveautees java 8):
		
		
		/* A FAIRE EN Tp:
		 1. afficher telle quelle une instance de LocalDate correspondant a la date d'aujourd'hui
		 2. afficher cette meme instance au format francais jour/mois/annee
		    en utilisant la methode .format() de la classe LocalDate
		    et DateTimeFormatter.ofPattern("....",Locale.FRENCH)
		 3. afficher la Date du premier pas sur la lune 21/07/1969
		 */
		
        
	   //Instant (timeStamp) from LocalDateTime :
	   LocalDateTime now = LocalDateTime.now();
       System.out.println("basic/default display of LocalDateTime.now() :" + now);
       Instant instantT =  now.atZone(ZoneId.systemDefault()).toInstant();
       long nbMsSinceFirstJanuary1970GMT = instantT.toEpochMilli();
       System.out.println("instantT.toEpochMilli() , timestamp , nb ms since 1970-01-01 00:00:00 GMT=" + nbMsSinceFirstJanuary1970GMT);
		
		// Manipulations plus elaborees de Dates et Heures (avec nouveautees java 8):
	   LocalTime nowTime = LocalTime.now();	 System.out.println("now is " + nowTime);
	   /* A FAIRE EN Tp:
		 - effectuer des manipulations de dates et d'heures :
		 ex: maintenant + 1 semaine , maintenant + 1 mois , ...
		 */
	}

}
