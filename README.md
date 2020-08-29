# Game Of Thrones Demo
Game Of Thrones themed Android Jetpack MVVM demo.

## Specifications / Requirements
A simple Android application that lists and displays all the episodes from the popular TV show, GameOfThrones.
- Given the 'game_of_thrones_episodes.txt' file, parse the JSON content and display each of the episode’s name as a list on the left side the app, taking up 35% of the app’s landscape space.  
- Keep the app screen orientation locked in landscape mode.
- When the user taps on an episode from the list, the app will display the following content associated with that episode:
    1. The original image associated with that episode, to be displayed on the top
    2. The episode’s summary at the bottom of the image
- Persist the episode selection across app launches. 

## Built With
* [Kotlin](https://kotlinlang.org/)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)

## Instructions
This demo uses the Gradle build system.

1. Download the demo by cloning this repository.
2. In Android Studio, create a new project and choose the "Import Project" option.
3. Select the root directory that you downloaded with this repository.
4. If prompted for a gradle configuration, accept the default settings. 
- Alternatively use the gradlew build command to build the project directly.

## Issues Encountered
- Use of the DataBinding Library can often require to rebuild the project in order to generate Implementation classes.

## Author
* **James Campbell** - *Android Developer* -

