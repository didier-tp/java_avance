La classe AtomicLong en Java (située dans le paquet java.util.concurrent.atomic)
permet de manipuler une valeur de type long de manière sûre dans un environnement multithread,
sans utiliser de verrous explicites (synchronized ou Lock).

Principaux intérêts:

• Thread-safety sans verrou (Lock-free) : Elle garantit que les opérations de lecture,
 modification et écriture sont atomiques (indivisibles).
 Elle évite ainsi les race conditions (conditions de course).

• Meilleures performances : Contrairement à l'utilisation du mot-clé synchronized qui bloque les threads concurrents,
 AtomicLong s'appuie en interne sur des instructions matérielles de type CAS (Compare-And-Swap) via la machine virtuelle,
  ce qui offre une exécution beaucoup plus rapide et fluide en cas de forte concurrence.

• Opérations atomiques prêtes à l'emploi : Elle fournit des méthodes utilitaires sécurisées telles que :
	• incrementAndGet() / getAndIncrement()
	• decrementAndGet() / getAndDecrement()
	• addAndGet(long delta) / getAndAdd(long delta)
	• compareAndSet(long expect, long update)

• Générateurs de séquences et métriques : Elle est idéale pour compter des accès,
générer des identifiants uniques ou suivre des statistiques partagées entre plusieurs threads
(par exemple un compteur de requêtes HTTP).

=====
En comparant les temps d'execution avec AtomicLong et synchronized on obtient (sur un cas ordinaire) des performances
a peu près identiques (néanmoins ça peut dépendre des optimisations matérielles).