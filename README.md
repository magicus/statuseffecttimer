# Status Effect Timer

[![Modrinth](https://img.shields.io/modrinth/dt/statuseffecttimer?logo=modrinth)](https://modrinth.com/mod/statuseffecttimer)
[![CurseForge](https://img.shields.io/curseforge/dt/484738?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/status-effect-timer)
[![GitHub](https://img.shields.io/github/downloads/magicus/statuseffecttimer/total?logo=github)](https://github.com/magicus/statuseffecttimer/releases)

This is a Minecraft mod for Fabric that overlays a timer on the Vanilla status effect HUD icons.

This mod overlays the time left of the status effect on the vanilla indicator. If the effect has an amplifier (as in "Haste II"), the amplifier is also overlaid. That's it. This is a very minimalistic mod. No settings are required nor provided.

The time is presented as seconds remaining. Durations longer than 60 seconds are presented as minutes remaining (followed by "m"), and durations longer than 60 minutes are presented as hours remaining (followed by "h").

Prior to 1.19.4, status effects longer than 32147 ticks (roughly 25 minutes) where considered "infinite", and are marked as "**" in the mod. Starting with 1.19.4, status effects can be properly infinite, which is marked by a "∞" symbol, and the 32147 tick limit has been removed.

I created this since the vanilla user experience of going into the inventory, and closing the recipe book, to check the remaining time of status effects was .. not ideal. There are some other mods that tried to achieve this, but most are bloated replacement for large parts of vanilla code, and the remaining still did not keep the vanilla basics.

## Screenshot

This is what it looks like when you are using the mod.

![Screenshot](screenshot.png?raw=true)

![Animation](animation.gif?raw=true)

## Download

You can download the mod from any of these sites:

* [GitHub releases](https://github.com/magicus/statuseffecttimer/releases)
* [Modrinth versions](https://modrinth.com/mod/statuseffecttimer/versions)
* [CurseForge](https://www.curseforge.com/minecraft/mc-mods/status-effect-timer/files)

## Installation

Install this as you would any other Fabric mod. (I recommend using [Prism Launcher](https://prismlauncher.org/) as Minecraft launcher for modded Minecraft.)

## Support

Do you have any problems with the mod? Please open an issue here on Github.

## Known Incompatibilities

This mod does not work if the mod ['Slight' Gui Modifications](https://github.com/shedaniel/slight-gui-modifications) is installed and the configuration `fluidStatusEffects` is enabled.

This mod conflicts with [Giselbaer's Durability Viewer](https://github.com/gbl/DurabilityViewer), since that mod also draws a timer on the status effect icon. You need to disable the effect time feature in Durability Viewer to avoid clutter. 
