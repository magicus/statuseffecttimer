# Status Effect Timer

This is a Minecraft mod that overlays a timer on the Vanilla status effect HUD icons.

This mod requires Minecraft 1.16.5-1.18.2 and the Fabric loader.

This mod overlays the number of seconds left of the status effect, or the number of minutes (followed by "m") if it is more than 60 seconds, on the vanilla status effect indicator. If the effect has an amplifier (as in "Haste II"), the amplifier is also overlaid. That's it. This is a very minimalistic mod. No settings are required nor provided.

I created this since the vanilla user experience of going into the inventory, and closing the recipe book, to check the remaining time of status effects was .. not ideal.
There are some other mods that tried to achieve this, but most are bloated replacement for large parts of vanilla code, and the remaining still did not keep the vanilla basics.

## Screenshot

This is what it looks like when you are using the mod.

![Screenshot](screenshot.png?raw=true)

![Animation](animation.gif?raw=true)

## Download

The latest version is 1.1.1. 

Direct download links for Minecraft 1.18.1-1.18.2:

* Download from GitHub: [statuseffecttimer-1.1.1+1.18.1.jar](https://github.com/magicus/statuseffecttimer/releases/download/v1.1.1-1.18.1/statuseffecttimer-1.1.1+1.18.1.jar)
* Download from Modrinth: [statuseffecttimer-1.1.1+1.18.1.jar](https://cdn.modrinth.com/data/T9FDHbY5/versions/1.1.1+1.18.1/statuseffecttimer-1.1.1%2B1.18.1.jar)
* Download from CurseForge: [statuseffecttimer-1.1.1+1.18.1.jar](https://www.curseforge.com/minecraft/mc-mods/status-effect-timer/files/3754930)

For all other Minecraft releases, check these download sites:
* [GitHub releases](https://github.com/magicus/statuseffecttimer/releases)
* [Modrinth versions](https://modrinth.com/mod/statuseffecttimer/versions)
* [CurseForge](https://www.curseforge.com/minecraft/mc-mods/status-effect-timer/files)

## Installation

Install this as you would any other Fabric mod. (Personally, I recommend MultiMC as Minecraft launcher for modded Minecraft.)
[I still need help installing this mod.](https://lmgtfy.app/?q=how+to+install+minecraft+fabric+mods)

## Support

Do you have any problems with the mod? Please open an issue here on Github.

Currently Minecraft versions 1.16.5 to 1.18.2 are supported, but it would probably be trivial to add support for other versions.
If you want support for another version, please open an issue and state the requested version.

## Known Incompatibilities

This mod does not work if the mod ['Slight' Gui Modifications](https://github.com/shedaniel/slight-gui-modifications) is installed and the configuration `fluidStatusEffects` is enabled.

This mod conflicts with [Giselbaer's Durability Viewer](https://github.com/gbl/DurabilityViewer), since that mod also draws a timer on the status effect icon. You need to disable the effect time feature in Durability Viewer to avoid clutter. 
