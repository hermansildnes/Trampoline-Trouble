# <u>Trampoline Trouble</u>

## <u>Concept</u>

A 2d platform-like shooter game built with Java using City Engine, a wrapper around JBox2D. The game is inspired by the game [Vikings on Trampolines](https://store.steampowered.com/app/748810/Vikings_On_Trampolines/) by the Norwegian developer D-Pad Studio. The player must survive waves of enemies by jumping between trampolines and using weapons to defeat enemies.


## <u>Gameplay</u>

This platform-like game features a player who must stay above the deadly ground by jumping between trampolines. Waves of enemies spawn, and as they attempt to distract the player from survival, the player collects power-ups in an attempt to defeat them.


## <u>How to Play</u>

### <u>Controls</u>
- **A**: Move left
- **D**: Move right
- **S**: Fast fall
- **Mouse**: Aim
- **Left Click**: Use equipped weapon

### <u>Game Mechanics</u>
- Stay above ground by using trampolines
- Collect equipment such as laser guns to defeat enemies
- Health packs restore lost health
- Enemies will chase you and try to knock you to the ground

## <u>Requirements</u>
- Java 21 or higher
- City Engine framework

## <u>Installation and Running</u>
1. Make sure you have Java 21 installed
2. Clone this repository
3. Ensure the City Engine library is in your classpath
4. Compile and run the `Game.java` class:
```
javac src/game/Game.java
java -cp src game.Game
```

## <u>Project Structure</u>

- `src/game/`: Main game code
    - `player/`: Player character implementation and controls
    - `enemy/`: Enemy behavior and controllers
    - `worlds/`: Level design and wave management
    - `environment/`: Environmental elements like trampolines and collectibles
- `data/assets/`: Game assets (sprites, images)

## <u>Credits</u>
- Player sprites: Graphics created by Penzilla Design
- Background image: Graphics by vnitti (https://vnitti.itch.io/glacial-mountains-parallax-background)
- Enemy sprites: CC BY 4.0 (https://lucky-loops.itch.io/character-satyr)