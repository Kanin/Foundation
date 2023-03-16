<a href="https://bit.ly/3GHdIQI">
  <img src="https://i.imgur.com/AeprAug.jpg" />
</a>

### Foundation is a Spigot/Paper API library to develop highly-customized Minecraft plugins.
[![](https://jitpack.io/v/kangarko/Foundation.svg)](https://jitpack.io/#kangarko/Foundation)
[![](https://javadoc.jitpack.io/v/kangarko/Foundation.svg)](https://javadoc.jitpack.io/#kangarko/Foundation)

Foundation enables people develop Minecraft plugins faster, saving boilerplate code and thus focusing on what matters the most, putting your ideas out there.

Some features include:

- GUI menu APIs
- Compatibility wrappers: Example: Send title/animations/packets on multiple MC versions with a single call
- Advanced command system without the need of editing plugin.yml
- Localization support
- Auto-updating configuration with comments support
- Time-saving wrappers: Example: Spawn holograms, custom head skulls, hook into third party plugins without importing them (i.e. Citizens, ProtocolLib), and so much more! 

Thousands of servers are running on Foundation since 2013. It has been battle tested and proven in plugins ChatControl, Boss, CoreArena, Confiscate, AutoPlay, Puncher, Winter, AnimeX and others.

Check out https://mineacademy.org/plugins for our official list of plugins that use Foundation.

### Sample usage

Please see [this link](https://github.com/kangarko/plugintemplate) for a sample plugin demostrating different Foundation abilities.

### Compatibility

We aim to provide broad compatibility layer enabling the below Minecraft versions to work:

- 1.2.5 (from 2012) - See mineacademy.org/oldmcsupport for setup instructions.
- 1.3.2, 1.4.7, 1.5.2, 1.6.4 - Many APIs are missing due to lack of features/access.
- 1.7.10
- 1.8.8
- 1.9.x, 1.10.x, 1.11.x, 1.12.x
- 1.13.x, 1.14.x, 1.15.x, 1.16.x, 1.17.x, 1.18.x, 1.19.x
- We continously update for newer versions but sometimes forget to update it here, but it does not mean that the library is incompatible!

### Compiling and using

We use JitPack to automatically compile and host the latest release of Foundation for you.

#### a) Alternative A: If you don't have Foundation on your computer:

To install Foundation with Maven, open your pom.xml, locate the `<repositories>` section and place this repository within it:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Then locate the `<dependencies>` section of your pom.xml and place the following inside of it. Replace the "REPLACE_WITH_LATEST_VERSION" string with the latest version from: https://github.com/kangarko/Foundation/releases

```xml
<dependency>
    <groupId>com.github.kangarko</groupId>
    <artifactId>Foundation</artifactId>
    <version>REPLACE_WITH_LATEST_VERSION</version>
</dependency>
```

For more information, including how to use Foundation with other tools than Maven, please visit: https://jitpack.io/#kangarko/Foundation/

#### b) Alternative B: If you have Foundation on your computer:

If you downloaded Foundation to your disk, do not place any repository to your pom.xml file, instead, only place the following dependency. Notice the groupId is different. You can use the LATEST keyword to automatically synchronize changes you make to your local copy of Foundation with your plugin source code (now that's fast!).

```xml
<dependency>
    <groupId>org.mineacademy</groupId>
    <artifactId>Foundation</artifactId>
    <version>LATEST</version>
</dependency>
```

### Important Licencing Information

2013 - 2021 © MineAcademy.org

If you are a paying student of MineAcademy.org then you are granted full
unlimited licence to use, modify and reproduce Foundation both commercially
and non-commercially, for yourself, your team or network. You can also
modify the library however you like and include it in your plugins you publish
or sell without stating that you are using this library.

If you are not a paying student of MineAcademy.org then you may
use this library for non-commercial purposes only. You are allowed
to make changes to this library however as long as those are only
minor changes you must clearly attribute that you are using Foundation
in your software.

For both parties, do not sell or claim any part of this library as your own.
All infringements will be prosecuted.

No guarantee - this software is provided AS IS, without any guarantee on its
functionality. We made our best efforts to make Foundation an enterprise-level
solution for anyone looking to accelerate his coding however we are not
taking any responsibility for the success or failure you achieve using it.

**A tutorial on how to use this library is a part of our Project Orion training available at https://mineacademy.org**
