#!/bin/bash

cd `dirname $0`
cd ..

DEPLOY_DIR=`pwd`
#修改这里
APP_MAINCLASS="io.github.pleuvoir.migration.Launcher"

if [ ! -n "$1" ] ;then
	echo "you have not input a command!"
	echo "eg:./start.sh [baseline/migrate/repair] [environment]"
	exit
fi

if [ ! -n "$2" ] ;then
	echo "you have not input a environment!"
	echo "eg:./start.sh [baseline/migrate/repair] [environment]"
	exit
fi

command=$1
environment=$2

LIB_DIR=$DEPLOY_DIR/lib

LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

#启动
java $JAVA_OPTS -classpath $LIB_JARS $APP_MAINCLASS -m $command -e $environment
