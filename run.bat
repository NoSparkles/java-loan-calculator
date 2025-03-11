ff@echo off
setlocal

REM Set the correct JavaFX SDK path
set JAVAFX_PATH="C:\javafx-sdk-23.0.2\lib"

REM Define the JAR file name
set JAR_NAME="\calculator-1.0-SNAPSHOT.jar"

REM Ensure we are in the right directory
cd /d %~dp0

REM Check if the JAR file exists
if not exist "target%JAR_NAME%" (
    echo JAR file not found! Building the project...
    mvn clean package
)

REM Run the JAR with JavaFX modules
if exist "target%JAR_NAME%" (
    echo Running the application...
    java --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml -cp target%JAR_NAME% loan.calculator.App
) else (
    echo Build failed! JAR file is missing.
)

endlocal
pause