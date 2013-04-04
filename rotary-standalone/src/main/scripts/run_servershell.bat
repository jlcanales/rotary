@echo off

if "%JAVA_HOME%" == "" (
  echo.
  echo JAVA_HOME not set
  goto EOF
)

if not exist "%JAVA_HOME%\bin\java.exe" (
  echo.
  echo Cannot find java executable, check JAVA_HOME
  goto EOF
)

echo %JAVA_HOME%
set LIB=..\lib
set EXLIB=..\lib
set IOLIB=..\lib

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;.\config
set CLASSPATH=%CLASSPATH%;%LIB%\*

set MEMORY_OPTIONS=-Xms256m -Xmx256m -XX:+UseParNewGC -XX:MaxPermSize=128M


echo %CLASSPATH%
"%JAVA_HOME%"\bin\java -classpath %CLASSPATH% %MEMORY_OPTIONS% -Dlog4j.configuration=.\config\log4j.xml -Dfile.encoding=UTF-8 com.edcontrol.server.ServerShellMain