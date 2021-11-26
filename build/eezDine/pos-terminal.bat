@echo off

:loop
java -jar ./pos-terminal.jar

IF %ERRORLEVEL% == 5 goto :loop