#!/bin/bash
#
# Startup script for the Spring Boot application
#


#################################################################
#
# Java Virtual machine general configuration
#

JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home
#JAVA_HOME=/usr/java/jdk1.7.0_21
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME PATH

JAVA_OPTS="-Xms256m -Xmx1024m -XX:+UseParNewGC -XX:MaxPermSize=128M -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=6700  -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

#################################################################
#
# Application files identification
#
# BOOT_HOME -> Base directory where the application is installed
# BOOT_JAR -> Location of Spring Boot application jar
# BOOT_PID -> Location of pid file generated by Spring Boot application
# BOOT_OUT -> Boot process log file
BOOT_HOME=./
BOOT_JAR=$BOOT_HOME/bin/rotary-bootstrap-1.1.1.SNAPSHOT.jar
BOOT_PID=$BOOT_HOME/bin/rotarycep.pid
BOOT_OUT=$BOOT_HOME/logs/rotarycep.out

#################################################################
#
# Spring Boot Specitic configuration
# CONFIG_LOCATION -> Directory where external application.properties is located
# SPRING_OPTS -> Additional spring configuration

CONFIG_LOCATION=/Users/fusedev/install/rotary/

SPRING_OPTS=""

#################################################################
#
# EsperHA Specitic configuration
# ESPERHA_STORAGE -> Directory where esper HA db will be created

ESPERHA_STORAGE=${BOOT_HOME}/esperha-default-store

ESPER_OPTS="-Despertech.esperha.home="$ESPERHA_STORAGE

if [ ! -d "${ESPERHA_STORAGE}" ]
then
mkdir ${ESPERHA_STORAGE}
fi

#
# Funciones para soporte
#
#
function check_if_pid_file_exists {
    if [ ! -f $BOOT_PID ]
        then
        echo "PID file not found: $BOOT_PID"
        exit 1
    fi
}
function check_if_process_is_running {
    if ps -p $(print_process) > /dev/null
        then
            return 0
        else
            return 1
    fi
}

function print_process {
    echo $(<"$BOOT_PID")
}


case "$1" in
status)
echo $" "
echo $"Checking for App information"
echo $" "
check_if_pid_file_exists
if check_if_process_is_running
    then
        echo " Rotary Service PID: $(print_process)"
    else
        echo "Rotary Service stopped"
fi
;;
stop)
echo $" "
echo $"Stopping Rotary "
echo $" "
check_if_pid_file_exists
if ! check_if_process_is_running
    then
        echo -n "Rotary Service stopped"
        echo -ne "\033[0;32m                                [OK] \033[0m"
        echo $" "
        exit 0
fi
kill -TERM $(print_process)
echo $" "
echo $"Checking for Rotary stop correctly."
echo $" "
sleep 5
echo -ne "Waiting for process to stop"
echo
if [ ! -f $BOOT_PID ]
    then
        echo -n "Rotary Stop"
        echo -ne "\033[0;32m                     [OK] \033[0m"
        echo $" "
    else
        echo -n "Rotary Stop"
        echo -ne "\033[0;31m                     [Fallo] \033[0m"
        echo ""
        echo "Process $(print_process) must be stopped by hand."
        echo $" "
        exit 1
fi
echo "Process stopped"
;;
start)
if [ -f $BOOT_PID ] && check_if_process_is_running
    then
    echo "Rotary Service $(print_process) already running"
    kill -TERM $(print_process)


    # Verificamos que se ha realizado correctamente
    VERIFY=$?
    if [ "$?" = "0" ]
        then
            echo -n "Rotary Stop"
            echo -ne "\033[0;32m                     [OK] \033[0m"
            echo $" "
        else
            echo -n "Rotary Stop"
            echo -ne "\033[0;31m                     [Fallo] \033[0m"
            echo ""
        echo "Process $(print_process) must be stopped by hand."
        echo $" "
            exit 1
    fi
fi
java  $JAVA_OPTS  $ESPER_OPTS -jar $BOOT_JAR  --spring.config.location=$CONFIG_LOCATION $SPRING_OPTS >> $BOOT_OUT 2>&1 &

echo $" "
echo -n "Rotary Starting"
echo -ne "\033[0;32m                                            [OK] \033[0m"
echo $" "
;;
restart)
$0 stop
if [ $? = 1 ]
then
exit 1
fi
$0 start
;;
*)
echo "Usage: $0 {start|stop|restart|status}"
exit 1
esac
exit 0
