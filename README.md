# Flashback Bypass


Flashback Bypass is a lightweight Fabric client mod for Minecraft 1.21.8 that unlocks Flashback replay viewing even when the Concurrent Chunk Management Engine (C2ME) optimization mod is installed.

## C2ME Compatibility Fixes

This mod not only removes the UI block, but also implements workarounds for the technical issues that caused Flashback to block replays with C2ME:

- **Forces synchronous chunk loading** in replay worlds, preventing race conditions and world corruption.
- **Disables C2ME's async region file IO** for replays, running all region IO on the main thread to avoid data loss.
- These workarounds are only active during replay playback and do not affect normal gameplay.

If you encounter issues with other C2ME features during replays, please report them!

## Features

- Removes Flashback's hard-coded block on launching replays while C2ME is loaded.
- Leaves Flashback's remaining safety checks intact so you still see registry mismatch warnings and version prompts.
- Works automatically on the client; no configuration required.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) 0.17.2 or newer for Minecraft 1.21.8.
2. Install the latest [Fabric API](https://modrinth.com/mod/fabric-api).
3. Place the Flashback mod, the C2ME mod, and this mod's JAR into your `mods` folder.
4. Launch the game. Flashback's replay browser will now open normally without requiring the Shift override.

> **Note:** Flashback may still display registry mismatch warnings if your mod list changed between recording and playback. Review these carefully before continuing.

## Building from Source

The project uses [Fabric Loom](https://github.com/FabricMC/fabric-loom) with Gradle.

```powershell
./gradlew.bat build
```

The compiled JAR will be located at `build/libs/flashback-bypass-<version>.jar`.

If you want to compile against the actual Flashback and C2ME mod jars locally, drop them into the `libs/` directory and uncomment the relevant dependency stanzas inside `build.gradle`.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE.md) for details.
