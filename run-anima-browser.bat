@echo off
setlocal enabledelayedexpansion

echo ================================================
echo         Anima Browser - Secure Web Browser
echo ================================================
echo.

REM Check Java installation
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not found in PATH
    echo Please install Java 21 or later from: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo Starting Anima Browser...
echo.

REM Try to launch with simple classpath approach
java -Dfile.encoding=UTF-8 -cp target\anima-browser.jar it.r2u.animar2u.AnimaApplication

if errorlevel 1 (
    echo.
    echo Error: Failed to start Anima Browser
    echo JavaFX components may not be properly included in the JAR.
    echo.
)

pause
