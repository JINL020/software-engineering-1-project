[[_TOC_]]

# Wofür wird GIT verwendet?

In dieser LV werden Ihre Übungsausarbeitungen, sofern nicht explizit anders angeführt, über GitLab abgegeben bzw. eingereicht. Wichtig hierbei ist, dass wir den letzten gepushten Commit Ihres Masterbranches als maßgeblich für die Bewertung und die Bestimmung der von Ihnen gewählten Deadline ansehen. Die jeweiligen Punkte und Prüfungsergebnisse sind weiterhin in Moodle ersichtlich. 

**Ändern Sie nicht den Namen des Masterbranches**: Dieser muss den Namen `master` tragen. Nur Daten, die vor der Abgabedeadline im Masterbranch liegen (daher Commit samt Push *vor* der Deadline) werden während der Abgabegespräche und Bewertung berücksichtigt.

# Wie erhalte ich lokalen Zugriff auf dieses Repository?

Um optimal mit diesem Repository zu arbeiten sollten Sie es auf Ihr lokales Arbeitsgerät spiegeln. Verwenden Sie hierzu den Befehl `git clone URLIhresRepositories`. Die URL Ihres Repositories finden Sie im Kopf dieser Webseite rechts vom Namen des Repositories. Um diese zu erhalten drücken Sie auf den blauen mit `Clone` beschrifteten Knopf. Wählen Sie die mittels `Clone with HTTPS` bereitgestellte URL. Diese sollte vergleichbar sein zu `https://git01lab.cs.univie.ac.at/.....`. 

**Probleme mit den Zertifikaten**: Falls Sie beim clonen Ihres Git Repositories Probleme gemeldet bekommen, die mit der Prüfung der Zertifikate in Verbindung stehen ist es eine schnelle Lösung diese abzuschalten. Hierzu kann folgender Befehl verwendet werden:  `git config --global http.sslVerify false`

# Wie nütze ich dieses Repository?

Clonen Sie hierzu dieses Repository wie oben angegeben. Danach können Sie mit `git add`, `commit`, `push`, etc. damit arbeiten. Optimalerweise legen Sie hierzu nach dem initialen clone Ihren Namen (echten Namen, kein Nickname) und Ihre E-Mail-Adresse (E-Mail-Adresse der Universität Wien) fest sodass alle Commits Ihnen direkt zugeordnet werden können. Verwenden Sie hierzu folgende Befehle:

> `git config --global user.name "Mein Name"`

> `git config --global user.email a123456@univie.ac.at`

Weitere Informationen über dem Umgang mit GIT sind in den hierzu passenden Folien auf Moodle erhältlich bzw. wurden während dem Git & GitLab Screencast besprochen. Für weiterführende Informationen lohnt sich ein Blick in das Pro Git Handbuch: https://git-scm.com/book/de/v2 Besonders für das Thema branching empfiehlt sich außerdem: https://learngitbranching.js.org/

# Welche Inhalte sind vorgegeben und wofür sind diese gedacht?

Es wurden mehrere **Ordner** sowie **.gitignore** Dateien vorgegeben. Letztere dienen dazu Ihr Repository nicht mit "unnötigen" Dateien zu befüllen, welche es erschweren würden Ihr Projekt während der Abgabegespräche in die Entwicklungsumgebungen der Lektoren zu importieren (temporäre Dateien, etc.). Ändern Sie diese Dateien daher nicht bzw. nur sehr behutsam. 

Die vorgegebenen Ordner sind wie folgt zu verwenden:
* **Dokumentation** - Nützen Sie diesen Ordner, um Ihre Dokumentation abzulegen bzw. abzugeben. Dies ist für Teilaufgabe 1 relevant da Sie so Ihre Ausarbeitung (das zu erstellende PDF) hier hinterlegen können, um diese abzugeben. Eine Vorlage für die Ausarbeitung von Teilaufgabe 1 finden Sie in Moodle. Falls notwendig können Sie in diesem Ordner auch SVGs ablegen (für Klassen- und Sequenzdiagramme). Achten Sie darauf, dass die abgegebenen Inhalte lesbar sind. Für Teilaufgabe 2 und 3 können Sie hier die verlangte ReadeMe ablegen um deutlich zu machen welche Ideen und Konzepte in welchen Teilen Ihrer Ausarbeitung umgesetzt wurden. Auch die für diese Aufgaben durchzuführende Reflexion und die dabei entstehenden kurzen Zusammenfassungen dieser können hier abgelegt werden.
* **Executables** - Hinterlegen Sie hier die finalen kompilierten Abgaben Ihrer Implementierung von Teilaufgabe 2 und Teilaufgabe 3. Diese sollten .jar Dateien seien, welche sich zumindest mit `java -jar <NameDerJarDatei.jar>` exekutieren lassen. Prüfen Sie ob dies der Fall ist! Genauere Informationen zu den zusätzlich anzuwendenden Parametern finden Sie auf Moodle und auf der Evaluierungsplattform. 
Tipps dazu wie die notwendigen Jar Dateien erstellt werden können finden Sie ebenfalls Moodle. Die Jar Dateien dienen zu Ihrer **Sicherheit** und sind nicht zwingend notwendig. Sollte sich während der Abgabegespräche Ihr Projekt nicht importieren oder bauen lassen wird auf die hier hinterlegten Jar Dateien zurückgegriffen (sollte dies notwendig werden kann dies zu Punkteabzügen führen). 
* **Source** - Nützen Sie diesen Ordner, um die Implementierung von Teilaufgabe 2 und Teilaufgabe 3 abzulegen (daher Sourcecode, Konfigurationen, etc.). Wählen Sie hierzu diesen Ordner als Eclipse Workspace und erstellen die passenden Gradle-Projekte. Binden Sie die notwendigen Bibliotheken nicht manuell ein, sondern nutzen Sie hierzu Build-Management-Tools, welche diese automatisch herunterladen und einbinden da es sonst zu Problemen während der Abgabegespräche kommen kann (z.B. durch nicht mehr passende Pfade). Dies ist auch der normalerweise im professionellen Entwickleralltag eingesetzte Weg, um mit Abhängigkeiten umzugehen. Tipps hierzu gibt es während des  Client bzw. Server Tutorials. 

# Was gilt es während der Implementierung zu beachten? 

* **Beispielprojekte**: Sehen Sie sich die auf Moodle bereitgestellten Beispielprojekte an (für Teilaufgabe 2 und 3). Sie können diese mittels Eclipse einfach in den Eclipse-Workspace als Gradle-Projekt importieren und direkt mit der Implementierung beginnen. Um die Beispielprojekte einfach in Git bzw. GitLab zu integrieren zuerst das Repository clonen und anschließend, vor dem Eclipse-Import, in den passenden Source-Unterordner kopieren.
* **Vor einer Deadline**: Während der Abgabegespräche und den Bewertungen wird der Inhalt des Ordners Source von den LV Leitern in Eclipse importiert, gebaut, etc. und das hierbei entstehende Jar zur Bewertung herangezogen. Prüfen Sie daher sicherheitshalber ob dies fehlerfrei möglich ist indem Sie dieses Repository neu klonen, in einen neuen Eclipse Workspace importieren und anschließen Ihre Projekte bauen bzw. mittels Gradle in ein ausführbares Jar exportieren. Prüfen Sie anschließend dieses Jar mehrfach mit den bereitgestellten Lösungen zur Selbstevaluation.
* **Während der Bearbeitung**: Erstellen Sie keine zusätzlichen Ordner im Wurzelverzeichnis dieses Repositories und verändern Sie nicht die Namen, etc. der vorgegebenen Ordner. Zusätzliche Ordner, etc. können Sie als Unterordner in den vorgegebenen Ordnern erstellen. Stellen Sie sicher, dass nicht mehrere unterschiedliche/widersprüchliche Versionen Ihrer Abgaben in diesem Repository enthalten sind. Es muss während der Bewertung schnell und einfach möglich sein zu erkennen wo sich Ihre Abgabe befindet und welche Inhalte für die jeweilige Teilaufgabe relevant sind. Andernfalls kann eine Situation entstehen, bei welcher versehentlich, beispielsweise, eine veraltete Kopie zur Bewertung herangezogen wird und Sie deshalb nicht alle Punkte erhalten. Eine spätere Korrektur und individuelle Nachfragen von unserer Seite sind Aufgrund der hohen Zahl an Studierenden organisatorisch nicht möglich.    

# Welche Funktionen sollen nicht genutzt werden?

GitLab ist eine mächtige Software, die es erlaubt zahlreiche Einstellungen anzupassen. Wir würden dazu raten diese Möglichkeiten nicht unüberlegt zu nützen da unbedachte Aktionen (z.B. das Löschen des Masterbranches) hierbei auch negative Auswirkungen haben können da GitLab teilweise nicht nachfragt, sondern Aktionen einfach ausgeführt (*Think before you click!*). Verwenden Sie daher optimalerweise einfach die vorgegebenen Einstellungen. Zur Sicherheit wurden, soweit möglich, unnötige Funktionen von uns bereits deaktiviert.
