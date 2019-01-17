#!/bin/sh

#请修改此处
APP_MAINCLASS="io.github.pleuvoir.Launcher"
#请修改此处
appname="sms-channel-service"

PID_FILE="/tmp/project/$appname-pid.log"


function rudekill()
{
    pidapp=`jps -l |grep $appname |grep -v "grep" | awk '{print $1}'`
    echo $pidapp
    if [ ! "x$pidapp" = "x" ]; then
        kill -15 $pidapp
    fi
    
    sleep 3
    #try again for make sure
    pidapp=`jps -l|grep $appname |grep -v "grep" | awk '{print $1}'`    
    if [ ! "x$pidapp" = "x" ]; then
        kill -9 $pidapp
        echo "rudekill second time."
    fi
}


if [ -f $PID_FILE ]; then
    PID=$(cat $PID_FILE)
	echo "pid:"$PID
    if [ "X$PID" = "X" ]; then
        echo "!!!WARN!!! pid file exists but pid is null, exit 1"
        rm -rf $PID_FILE
        exit 1
    else
        ln=`ps -p $PID | wc -l`

        if [ $ln -gt 1 ]; then
            kill -15 $PID

            sleep=0
            RETVAL=1
            while [ $sleep -lt 60 -a $RETVAL -eq 1 ]; do
                
                echo "waiting for processes to stop";
                sleep 1
                sleep=`expr $sleep + 1`

                n=`ps -p $PID | wc -l`

                if [ "x$n" = "x1" ]; then
                	RETVAL=0
                else
                	kill -9 $PID
                fi
                
            done
        fi


 

        n=`ps -p $PID | wc -l`

        if [ "x$n" = "x1" ]; then
            echo "stop success!"
            rm -rf $PID_FILE
        else
            echo "!!!WARN!!! $PID not shutdown normally ,PLease check it manual!!!"
        fi

     fi


else
    echo "!!!WARN!!! NO pid file of $PID_FILE! begin rude kill."
    #there are some unknown reason, when this branch happened which cause duplicate process bug
    #fix it with rude kill
    rudekill
    
fi
