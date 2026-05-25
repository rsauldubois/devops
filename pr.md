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


---


## 6. Pourquoi utiliser une CI ?


L’**intégration continue** (_Continuous Integration_) consiste à **automatiser des tâches** comme la compilation, les tests et la validation du code à chaque modification apportée par les développeurs. Cela permet de **détecter rapidement les erreurs**, **d’améliorer la qualité du logiciel** et **d’éviter les problèmes de dernière minute avant la mise en production**. Pour fonctionner, elle nécessite un code source partagé, des mises à jour fréquentes par les développeurs et des tests d’intégration automatisés. Ses principaux avantages sont la détection immédiate des bugs, des notifications rapides en cas de problème et la disponibilité permanente d’une version stable du logiciel.


Dans un contexte DevOps, elle est importante car elle :
- détecte rapidement les erreurs ;
- évite d’intégrer du code cassé ;
- améliore la qualité globale du projet ;
- facilite la collaboration entre plusieurs développeurs ;
- sécurise les pull requests avant fusion.


Elle est donc particulièrement utile pour **garder un projet cohérent et fiable** au fur et à mesure des évolutions.


---


## 7. Fonctionnement de GitHub Actions dans ce projet


GitHub Actions est le système de CI utilisé ici. 
Son fonctionnement repose sur un fichier de workflow YAML placé dans le dépôt GitHub.


### Déclenchement
Le workflow est exécuté automatiquement lorsqu’un événement défini se produit, par exemple :
- un `push` sur une branche ;
- l’ouverture ou la mise à jour d’une `pull request`.


### Définition
Le comportement de la CI est défini dans le fichier de workflow, où l’on précise :
- les événements déclencheurs ;
- les jobs à lancer ;
- l’ordre éventuel des étapes ;
- les commandes à exécuter.


### Déroulement
Lorsqu’un workflow est lancé :
1. GitHub crée un runner ;
2. le code du dépôt est récupéré ;
3. les dépendances sont installées ;
4. les vérifications sont exécutées ;
5. le résultat est affiché dans l’onglet Actions.


Dans ce projet, cela permet de valider automatiquement le backend et le frontend avant intégration.


---


## 8. Autres systèmes de CI


GitHub Actions n’est pas la seule solution de CI. Il existe aussi :
- **GitLab CI/CD** : très intégré à GitLab, avec une configuration dans `.gitlab-ci.yml` ;
- **Jenkins** : très flexible et auto-hébergé, mais souvent plus complexe à administrer ;
- **CircleCI** : solution cloud orientée automatisation rapide ;
- **Travis CI** : historiquement utilisé pour les projets open source.


### Différences principales
Par rapport à GitHub Actions, ces outils peuvent varier sur :
- le niveau d’intégration avec l’hébergeur Git ;
- la simplicité de configuration ;
- la possibilité d’auto-hébergement ;
- la flexibilité des pipelines ;
- le coût et le modèle d’exécution.


GitHub Actions est particulièrement pratique ici car il est directement intégré à GitHub.
---


## 9. Autres outils permettant de mettre en place une CI
En plus des plateformes de CI complètes comme GitHub Actions ou GitLab CI/CD, il existe aussi d’autres outils pouvant être utilisés pour construire une chaîne d’intégration continue.


Par exemple :
**Docker** peut être utilisé pour exécuter les tests dans des environnements isolés et reproductibles ;
**SonarQube** permet d’ajouter une analyse avancée de la qualité et de la sécurité du code ;
**Ansible** peut automatiser certaines étapes de déploiement ou d’exécution ;
**TeamCity** est une solution de CI développée par JetBrains ;
**Bamboo** est un outil CI/CD proposé par Atlassian ;
**Azure DevOps Pipelines** permet également de créer des pipelines d’intégration continue.


Ces outils peuvent être utilisés seuls ou combinés avec une plateforme de CI principale.
Contrairement à GitHub Actions, certains nécessitent davantage de configuration ou d’hébergement, mais offrent parfois plus de personnalisation ou des fonctionnalités avancées adaptées aux grandes infrastructures DevOps.