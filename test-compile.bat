@echo off
echo Testing compilation of refactored code...

REM Check if classes directory exists
if not exist "target\classes" mkdir "target\classes"

REM Try to compile main application class
javac -cp "target\dependency\*" -d "target\classes" "src\main\java\it\r2u\animar2u\AnimaApplication.java"

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
) else (
    echo Compilation failed with error level %ERRORLEVEL%
)

pause
