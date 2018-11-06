@echo on
@echo =============================================================
@echo $                                                           $
@echo $                      sms-gateway                          $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title sms-gateway version update
@color 0a

rem  Please execute command in local directory.

call mvn -N versions:update-child-modules
call mvn versions:set -DnewVersion=1.0.2
call mvn versions:commit

pause