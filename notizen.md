# Notizen für meine KMP Compose App

## TODO
- Wie Markdown anzeigen in IDE? Lohnt nicht, muss jbr runterladen und ist groß
- Debugger lernen
- Logik bezüglich Data Access, Networking usw. sollten in das Shared Module und dann mittels expect actual, später
- ViewModel!!!

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