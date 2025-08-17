# crud-spring-thymleaf-docker

Spring MVC + Thymeleaf + Spring Data JPA + MySQL (Docker)

Application Spring Boot (Java 17) avec Thymeleaf et Spring Data JPA, livrée avec un environnement Docker Compose (MySQL + phpMyAdmin).

Démarrage rapide (Docker)
Prérequis

Docker Desktop (ou Docker Engine)

Git

1) Cloner le projet
git clone https://github.com/<votre-user>/springmvc-thymeleaf-springdata.git
cd springmvc-thymeleaf-springdata

2) Lancer l’environnement
docker compose up -d --build

3) Accéder aux services

Application : http://localhost:8080

phpMyAdmin : http://localhost:8081

Serveur : db

Utilisateur : root

Mot de passe : root

4) Se connecter à MySQL depuis l’hôte (facultatif)

Host : localhost

Port : 3307

User : root / Password : root

Base par défaut : springthymleaf

