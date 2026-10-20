Utilisation de wireshark:
1) sélectionner une ou plusieurs interfaces réseaux (loopback ou …)
2) editer le filtre (ex: tcp.port == 9632 ou bien udp.port == 9633 ou autre) 
3) commencer l'enregistrement (premier bouton bleu)
4) déclencher un cycle requêtes/réponses (démarrer appli client après appli serveur)
5) arrêter l'enregistrement (bouton rouge)
6) visualiser les paquets échangés/transmis
=======
On peut voir :
   - le numéro de  port du "serveur" (ex: --->9632) 
   - le numéro de port dynamiquement alloué au client (ex: 56478--> )
   - la longueur des données véhiculées (ex len=64) , 
      sa représentation binaire en hexadécimal et une vision décodée (ex: valeur 816 qui sera considérée comme pair/even).     
======
En mode TCP on voit globalement une connexion qui s'établit avec 3 échanges:
   - SYN (demande de connexion envoyée du client vers le serveur)
   - SYN-ACK (le serveur accuse réception et indique qu'il est prêt à communiquer)
   - ACK (le client accuse réception du paquet SYN-ACK)
   puis ensuite des cycles PUSH/acquittement de type
   - PSH , ACK
   - ACK 
   aussi bien à l'initiative d'un client qui envoie une requête à acquitter qu'à l'initiative d'un serveur qui envoie une réponse à acquiter
====
En mode UDP pas de phase de connexion et des transmission directe sans acquittement .
c'est beaucoup plus simple/performant/rapide mais moins fiable et limité .