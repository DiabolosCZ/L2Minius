@echo off
title LII MultVerso [GAME SERVER]
:start
echo Starting [GAME SERVER].
echo.

set JAVA_OPTS=%JAVA_OPTS% -Xmn512m
set JAVA_OPTS=%JAVA_OPTS% -Xms5096m
set JAVA_OPTS=%JAVA_OPTS% -Xmx5096m

"C:\Program Files\Java\jdk-17\bin\java.exe" -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -server -Dfile.encoding=UTF-8 -cp config;./../libs/* l2mv.gameserver.GameServer

if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo Server restarted ...
echo.
goto start
:error
echo.
echo Server terminated abnormaly ...
echo.
:end
echo.
echo Server terminated ...
echo.

pause
