# Continuous Integration, Static Analysis & Test Automation
## Romane Sauldubois, Elnaz Shamseddini, Paul Potier et Abtin Rashedi
Cette Pull Request explique la mise en place d'une chaîne complète de **Continuous Integration (CI)** incluant la compilation, l’analyse statique et l’exécution automatisée des tests pour le backend et le frontend.


## 1. Continuous Integration avec GitHub Actions

### Rôle
GitHub Actions est utilisé pour automatiser les vérifications du projet.

### Fonctionnement
Le pipeline est déclenché automatiquement à chaque :
- push
- pull request

Il exécute les étapes suivantes :
- installation des dépendances
- compilation
- analyse statique
- exécution des tests

Il couvre deux parties du projet :
- le backend Java
- le frontend TypeScript

---

## 2. Backend Java

### 2.1 Build et gestion avec Maven

#### Rôle
Maven est utilisé pour :
- compiler le projet Java
- gérer les dépendances
- exécuter les tests
- intégrer les outils de qualité


### 2.2 Analyse statique avec Checkstyle

#### Rôle
Checkstyle permet de :
- vérifier le respect des conventions de code Java
- détecter les problèmes de qualité sans exécuter l’application

#### Intégration
Il est exécuté automatiquement via Maven dans le pipeline CI.


### 2.3 Tests automatisés avec JUnit

#### Rôle
JUnit est utilisé pour :
- exécuter les tests unitaires
- valider le comportement du code backend

Les tests sont lancés automatiquement via Maven dans la CI.

---

## 3. Frontend TypeScript / Angular

### 3.1 Environnement avec Node.js et npm

#### Rôle
Ces outils permettent :
- d’installer les dépendances du projet
- d’exécuter les scripts frontend


### 3.2 Analyse statique avec Angular

#### Outil
La commande de lint Angular

#### Rôle
Elle permet :
- de détecter les erreurs de style
- d’identifier certaines erreurs de code TypeScript


### 3.3 Tests automatisés avec Karma

#### Rôle
Karma est utilisé pour :
- exécuter les tests unitaires du frontend
- simuler un environnement navigateur

#### Spécificité CI
Les tests sont exécutés en mode ChromeHeadless, ce qui permet un lancement sans interface graphique dans le pipeline.

---

## 4. Résumé du pipeline CI

- Backend :
  - build avec Maven
  - analyse statique avec Checkstyle
  - tests avec JUnit

- Frontend :
  - gestion via Node.js et npm
  - analyse statique avec Angular lint
  - tests avec Karma en ChromeHeadless

---

## 5. Objectif

Cette configuration permet :
- une détection automatique des erreurs
- une amélioration de la qualité du code
- une validation continue des contributions
- une sécurisation des pull requests

