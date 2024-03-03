<!-- PROJECT LOGO -->
<br />
<div>
  <a href="https://github.com/Ashkan-san/Takedown">
    <img src="composeApp/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_round.png" alt="Logo" width="192" height="192">
  </a>

<h3 align="center">Takedown - Wrestling Companion</h3>
  <p>
    Takedown - Wrestling Companion: Turniere finden, neue Techniken lernen und deinen Progress tracken
</div>


<!-- ABOUT THE PROJECT -->
## Über das Projekt

Takedown ist eine Kotlin Multiplatform/Compose Multiplatform App für Menschen mit der Leidenschaft Wrestling (Freistil/Griechisch-Römisch Ringen).
Sie soll es dem Nutzer vereinfachen lokale Wrestling Turniere zu finden, die Regeln und Punktevergabe zu verstehen und neue Techniken zu erlernen. Weitere geplante Features beinhalten ein Scoreboard, ein persönlicher Account mit Statistiken uvm.


<!--TECHNOLOGIEN -->
## Entwickelt mit

* [![Jetpack Compose][Jetpack-Image]][Jetpack-Website]
* [![Kotlin][Kotlin-Image]][Kotlin-Website]
* [![Android Studio][AndroidStudio-Image]][AndroidStudio-Website]


<!-- AUFBAU -->
## Aufbau der App

WIP

### Bilder der App

WIP

<!-- MEINE LINKS -->

[Jetpack-Website]: https://developer.android.com/jetpack/compose?gclid=Cj0KCQjwhL6pBhDjARIsAGx8D59HFLvsEPK0q1coz93YbJ3k1icM2FN5k0UP3wCPunOPGAeSs8yNT2UaAgU0EALw_wcB&gclsrc=aw.ds
[Kotlin-Website]: https://kotlinlang.org/
[AndroidStudio-Website]: https://developer.android.com/studio?gclid=CjwKCAiAx_GqBhBQEiwAlDNAZlhodtzsGXort6FwWAXJQgR97X5BzLHkYW0Gpu9tgZgNJ-QfY4tQIxoC2ZcQAvD_BwE&gclsrc=aw.ds

<!-- MEINE BILDER -->

[Jetpack-Image]: https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=Jetpack-Compose&logoColor=white
[Kotlin-Image]: https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white
[AndroidStudio-Image]: https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white

## Architektur

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.
