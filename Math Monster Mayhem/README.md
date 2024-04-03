Monster Mayhem - Group 25
====================

## Authors

Fahmid Abdullah

Sameer Mehboob Bugti

Adam Murdock Gale

Saad Meshaal Koker

Jesse Titus

## Description

Team25 is proud to present our new educational game: Monster Mayhem. Monster Mayhem is a game designed to be used in classrooms for students to improve their math and logic skills. We have applied the software devleopment practices from class material and used several tools such as Jira and Confluence during the production of this game. We hope you enjoy! 

Created for CS2212B - instructor Daniel Servos.

A copy of the github repo has been uploaded to OWL. 

In the github zip, the executable is included as "MonsterMayhem.jar" in the parent folder.

The Javadoc output can be found in "target\site".

The readme (hint: you are reading it) can be found in the parent folder.

Presentation video: https://www.youtube.com/watch?v=tIjyfY0uiU0

## Functional Requirements

The game has been designed to meet the functional requirements outlined by the assignment specifications. These include:

- User Interface
- Main Menu
- Instructions/Tutorials
- Game Play/Mechanics
- Sale/Load Game State
- High Score Table
- Multiple Players
- Instructor Dashboard
- Debug Mode/Level Selection
- House-keeping/Error Handling
- Challenge Mode
- Level Maker
- Character Customization
- Powerups

## Non-functional Requirements

The game also meets the Non-Functional requirements outlined by the assignment specifications:

- Consistent Development Environment
- Object-Oriented Design
- Education Content
- Graphical User Interface
- Local Data Storage
- Third-Party Libraries
- Version Control and Documentation
- Code Documentation and Commenting
- Unit Testing
- Coding Conventions
- Platform Compatibility
- File System Integrity
- User Feedback and Error Handling
- File Size
- Efficiency and Resource Usage
- Accessibility
- Maintainability and Reusability
- Language and Communication
- Software Engineering Principles

## Required Libraries and Third Party Tools

Java, JavaFX, and Maven are required in order to compile/run Monster Mayhem.

Java 17.0+ is required:
The JDK can be installed by following the tutorial at https://docs.oracle.com/en/java/javase/22/install/overview-jdk-installation.html.

JavaFX 21.0.2 is required:
The JavaFX dependencies can be installed by following the tutorial at https://openjfx.io/openjfx-docs/.

Maven 3.9.6 is requied:
Maven dependencies can be installed by following the tutorial at https://maven.apache.org/install.html.

## How to Play

If there are no user profiles created, the user will be prompted to create a profile upon opening the game. The user can then explore the game options from the main menu. For a first-time user, we recommend a user to click "New Game" and jump straight in! Once you're in the game scene, look at the center of the screen for the questions, and either click or press 1-4 on the keyboard to answer the question. If the answer is correct, the enemy will takes damage and the player will progess towards the end of the level. If the answer is incorrect, the player will take damage and will not earn progression towards the next level. If the player reaches 0 HP before the question set has been completed, the game is lost. 

### Build Guide

Ensure that Java, JavaFX, and Maven are installed by following the tutorial in the "Required Libraries and Third Party Tools" section. In VSCode, you can use the "export JAR" feature to compile and create an executable jar file to run the game. This file is also included in our .zip for ease of use.

### How to Access the Admin Dashboard

Click the "Dashboard" option in the main menu and login. The password is "password".

Once you are logged in, you can begin editing user attributes.

Logging in also makes it so you can press the "Q" key in Normal mode to automatically answer a question correctly.

## Things Helpful for the TA

We used VSCode/Maven to generate the jar file, but I'm sure there an easier (harder?) way to do it via commandline if this tooling is not available to you. It was quite a struggle on our end to get all of us up and running with Maven and JavaFX. Some common issues we ran into: Not correctly setting the environment path variables, not restarting vscode after editing paths or installing packages, ensuring that the correct VScode extensions were running.

## Presentation Video

https://www.youtube.com/watch?v=tIjyfY0uiU0
