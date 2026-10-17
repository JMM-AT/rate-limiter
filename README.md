# Java Rate Limiter

Bibliothèque de rate limiting en Java pur (sans framework), basée sur l'algorithme **token bucket**.

## Principe
- Un seau par clé (utilisateur, IP, clé d'API), avec une capacité maximale.
- Chaque requête consomme un jeton ; seau vide, la requête est rejetée.
- La recharge est calculée à la demande à partir du temps écoulé (pas de thread en arrière-plan).

## État du projet
- [x] `Bucket` : recharge paresseuse, plafonnement, tests unitaires
- [ ] `TokenBucketRateLimiter` : un seau par clé
- [ ] Sécurité multithread et tests de concurrence
- [ ] Second algorithme (sliding window) et benchmark

## Lancer les tests
```
mvn test
```

## Choix de conception
- Les jetons sont stockés en `double` pour conserver les fractions de jeton.
- Le temps est fourni de l'extérieur, ce qui rend les tests rapides et déterministes.
- Un seau neuf démarre plein.
