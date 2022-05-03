# Intelligence Artificielle

## Commande pour executer le programme

```bash
java -cp devoir-2022.jar:commons-cli-1.4.jar iialib.games.contest.Client -p 4536 -s localhost -c games.icebreaker.MyChallenger
```

## Description de l'algorithme utilisé pour déterminer les coups possibles

Pour chaque position de chaque bateau, on obtient la liste des positions adjacentes filtrées : c'est-à-dire la liste des positions adjacentes pouvant être parcouru (position où il n'y a pas de bateau).

Pour chaque position de le liste des positions adjacentes filtrées, on calcul la distance avec l'iceberg qui lui est le plus proche (utilisation d'un algorithme de parcours en largueur).

Pour les positions adjacentes qui ont la distance minimale avec un iceberg, on ajoute à la liste des mouvements possibles le déplacement du bateau vers la position adjacente en question.
