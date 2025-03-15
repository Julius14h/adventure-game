# Text-Based Adventure Game (JavaFX)

## Overview
This is a text-based adventure game developed in JavaFX as part of a course project. The game allows players to explore a story through text input and features interactive UI elements. This repository contains the source code and resources for the project.

## My Contribution
In this project, I was responsible for designing and implementing the following features:
- **Zoom Functionality**: Used the Decorator design pattern to create decorator classes that enable zoom-in and zoom-out features for the game’s text display, enhancing accessibility and user experience.
- **Game File Management**: Implemented save, load, and delete functionality to allow players to persist their progress to files and retrieve or remove them as needed.

These features were built to integrate seamlessly with the core game logic developed by my team.

## Technologies Used
- **JavaFX**: For the game’s UI and event handling.
- **Java**: Core programming language.
- **Decorator Pattern**: Applied to extend zoom functionality dynamically.

## My Contributions
- although part-1 was an individual submission, part-2 was a group effort where I worked with three other students to enhance the game from part-1.
- **Zoom Feature**: Players can increase or decrease the text size using the zoom-in/zoom-out controls, implemented via decorator classes that wrap the base text component.
- **Save/Load/Delete**: Where the original game only supported the ability to load and save files, I added the functionality for it to delete files.
- **My Files**: The files I contributed to were AdventureGameViewAbstract, AdventureGameViewDecorator, EnhancedAdventureGameViewDecorator, DeleteView in part-2/assignment2/views as well as DeleteSaveCommand, and ZoomCommands
