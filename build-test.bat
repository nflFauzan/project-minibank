@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
set "PATH=C:\Program Files\Java\jdk-21\bin;%PATH%"
call mvnw.cmd clean test -Dtest=TellerPassbookControllerTest,PassbookRestControllerTest,PassbookServiceTest,NavigationRoutingControllerTest

