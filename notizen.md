# Notizen für meine KMP Compose App

## TODO

- Wie Markdown anzeigen in IDE? Lohnt nicht, muss jbr runterladen und ist groß
- Debugger lernen
- Logik bezüglich Data Access, Networking usw. sollten in das Shared Module und dann mittels expect actual, später
- ViewModel!!!
- Filtern, sortieren
- Pull Refresh ersetzen durchs offizielle irgendwann

## Shortcuts

### Windows Shortcuts

- Mittlere Maustaste bei Tableiste: öffnet neues Fenster
- Fn + F2: Datei umbenennen
- Shift + Win + Pfeiltaste: Fenster in Bilschrimen verschieben
- Win + Pfeiltaste: Fenster verschieben

### IntelliJ Shortcuts

- Move to File: Markieren und dann Fn + F6
- Comment Line: Ctrl + 7
- Comment Block: Ctrl + Shift + 7

## Allgemein

- run Configuration ist deutlich schneller als die gradle tasks, weil diese alles builden etc.
- TODO später die dependencies in shared module packen
- Run auf Handy nimmt dem Rechner viel Arbeit und ist viel schneller beim builden
- libs.versions.toml ist ein Version Katalog, in welchem die ganzen Dependency Versionen notiert werden sollten
- LogCat ist sehr praktisch, man kann auch Sachen filtern z.B. package:mine nur für meine Sachen, tag:print nur prints etc.
- Unter Settings -> Editor -> Spelling das Dictionary hinzufügen

## Debugger

- Kann während App läuft gestartet werden
- Breakpoints, um dort die App zu pausieren und Fehler zu finden
- Step Over: nächste Zeile ohne in Methode zu gehen
- Step Into: zur ersten Zeile in einer Methode
- Step Out: zur ersten Zeile außerhalb einer Methode
- Links sieht man alle laufenden Threads?

## Icon

- in res Rechtsklick -> new Image Asset und dort kann man dann seine Icons erstellen

## Architektur

- Seperation of Concerns: jede Methode hat seine eigne Aufgabe
- Drive UI from a model: persistente Daten sollen außerhalb von UI kommen
- NIEMALS UI State (data class z.B.) in der UI selber modifizieren
- MutableStateFlow aus Kotlin, mutableStateOf() aus Compose
- Ein ViewModel nur für Composables? -> mutableState

- ViewModel: zum Bearbeiten und Halten der Daten lokal
- Repository: zum

## Compose

- MUSS mutableStateListOf() benutzen für Recomposition
- List: kann keine Elemente hinzufügen, entfernen usw.
- MutableList: kann Elemente hinzufügen, bearbeiten usw.
- MutableStateList aus Compose, nimmt Änderungen für UI wahr (nutzen wenn UI automatisch updaten soll bei Änderungen)
- Recomposition: @Composable, remember, by, key
- remember benutzen, wenn Composable nach Recomposition den selben State behalten soll!

## Coroutines

- Coroutine: asynchroner Thread
- suspend: Funktionen, die Main Thread nicht blockieren. Können pausiert und gestartet werden.
- Dispatchers: stellen verschiedene Thread Pools dar. Default für CPU intensives Zeug, IO für Netzwerk, Main für UI Updates
- launch() startet neue Coroutine
- withContext() zum Ändern des Thread Pools
- GlobalScope (vermeiden) > CoroutineScope (für einzelne Komponenten) >

## Effect

- LaunchedEffect: für coroutine und background tasks
- DisposableEffect: für Resource Kram, wie z.B. bei den Sensoren damals
- SideEffect:

## Gradle

- Gradle ist Build Toolkit zum automatischen Bauen von Anwendungen zu APK
- Unabhängig von Android Studio, muss deshalb separat updated werden
- Build types: z.B. Debug, Release
- Product flavors: verschiedene App Varianten für z.B. free oder paid
- Build variants: Kombi aus den oberen beiden
- Dependency: damit man Libraries nicht selber runterladen muss
- Signing: nötig für App store
- DSL für Kotlin, Groovy für Java


- compile altes Keyword, api ist das selbe. immer implementation benutzen!
- Compose Maven Groups:
- Animation
- Compiler (@Composable transformieren?)
- Foundation (Building Blocks)
- Material, Material3
- Runtime (Model, State Management)
- Ui

## Callback

Callbacks sind Funktionen, welche als Parameter übergeben werden. Sie sind dafür da, damit Funktionen zu einer bestimmten Zeit/Kondition aufgerufen
werden können.

- In meinem Fall nutzen wir sie für Seperate of Concerns. Kleine UI-Cards sollten keine viewmodel oder navigation bekommen, stattdessen kriegen diese
  einen Callback, welcher das gewollte erhält. z.B. viewModel.update() oder navigator.navigate()

## UI

- Spacer nutzen bei Listen wie Rows und Columns
- Padding für Elemente selbst
- Box, wenn ich Elemente übereinander rendern möchte
