# 💊 Pharmacy Management – Gestion de pharmacie

Application de bureau en **Java (Swing)** pour gérer une pharmacie : médicaments, stock, ventes, commandes et rapports. Elle propose deux profils d'utilisateurs, **pharmacien** et **gestionnaire**, avec une base de données **Oracle**.

## ✨ Fonctionnalités

**Authentification**
- Connexion sécurisée avec saisie du mot de passe masquée
- Interfaces séparées selon le rôle (pharmacien / gestionnaire)
- Consultation du profil utilisateur

**Gestion des médicaments et du stock**
- Ajout de nouveaux médicaments
- Modification des quantités en stock
- Liste des médicaments en **stock critique**
- **Historique** des mouvements de stock

**Ventes**
- Enregistrement d'une vente
- Annulation d'une vente
- Consultation de la liste des ventes (vue pharmacien et vue gestionnaire)

**Commandes et suivi**
- Gestion des commandes
- Envoi de notifications
- Rapports et statistiques pour le gestionnaire

## 🛠️ Technologies

| Domaine | Outils |
|---|---|
| Langage | Java |
| Interface graphique | Swing |
| Base de données | Oracle (XE) |
| Accès aux données | JDBC, classes DAO |
| IDE | Eclipse |
| Versionnement | Git et GitHub |

## 🗂️ Structure du projet

```
src/
├── module-info.java
└── pharmacie/
    ├── Connexion.java            # Connexion à la base Oracle
    ├── Authentification.java     # Gestion de la connexion utilisateur
    ├── Medicament.java, Vente.java, Commande.java, Client.java ...   # Classes métier
    ├── MedicamentDAO.java, VenteDAO.java                             # Accès aux données
    ├── Pharmacien.java, Gestionnaire.java                            # Profils
    └── Interface*.java, Fenetre*.java                                # Écrans de l'application
```

## 🚀 Installation

### Prérequis
- JDK 17 ou supérieur
- Eclipse IDE (ou un autre IDE Java)
- Oracle Database XE
- Le driver JDBC Oracle (`ojdbc`) ajouté au projet

### Étapes
1. Cloner le dépôt :
   ```bash
   git clone https://github.com/Maram1503/Pharmacy-Management.git
   ```
2. Importer le projet dans Eclipse : **File > Import > Existing Projects into Workspace**.
3. Ajouter le driver Oracle JDBC dans le *Build Path* du projet.
4. Créer l'utilisateur et les tables dans votre base Oracle.
5. Ouvrir `src/pharmacie/Connexion.java` et renseigner vos identifiants :
   ```java
   private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
   private static final String USER = "pharmacie";
   private static final String PASSWORD = "votre_mot_de_passe";
   ```
6. Lancer l'application depuis la classe principale de l'interface de connexion (`InterfaceConnexion`).

## 📸 Aperçu

<!-- Ajoutez ici vos captures d'écran, par exemple : -->
<!-- ![Connexion](docs/connexion.png) -->

## 👩‍💻 Auteure

**Maram Othmani**
- GitHub : [@Maram1503](https://github.com/Maram1503)
- LinkedIn : [votre profil](https://www.linkedin.com/in/votre-nom)

## 📄 Licence

Projet réalisé dans un cadre pédagogique.
