@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo Compiling source code first...
call mvn clean compile -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo Source compilation failed!
    exit /b %ERRORLEVEL%
)
echo Source code compiled successfully!
echo Now compiling tests...
call mvn test-compile
if %ERRORLEVEL% NEQ 0 (
    echo Test compilation failed!
    exit /b %ERRORLEVEL%
)
echo All compilation successful!

