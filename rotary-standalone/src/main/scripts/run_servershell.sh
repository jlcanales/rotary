#!/bin/sh

# Script to run CEP Server
#

JAVA_HOME=/usr

#Only needed for EsperHA activation with Berkeley DB
CEP_HOME=/apps/yell01_correlador
ESPERHA_STORAGE=${CEP_HOME}/esperha-default-store


if [ -z "${JAVA_HOME}" ]
then
  echo "JAVA_HOME not set"
  exit 0
fi

if [ ! -x "${JAVA_HOME}/bin/java" ]
then
  echo Cannot find java executable, check JAVA_HOME
  exit 0
fi

if [ ! -d "${ESPERHA_STORAGE}" ]
then
mkdir ${ESPERHA_STORAGE}
fi


LIB=../lib
EXLIB=../lib
IOLIB=../lib


unset CLASSPATH
CLASSPATH=.
CLASSPATH=$CLASSPATH:./*
CLASSPATH=$CLASSPATH:./config
CLASSPATH=$CLASSPATH:$LIB/*
CLASSPATH=$CLASSPATH:$CEP_HOME


if [[ $OSTYPE == "cygwin" ]]; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$JRE_HOME" ] && JRE_HOME=`cygpath --unix "$JRE_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# For Cygwin, switch paths to Windows format before running java
if [[ $OSTYPE == "cygwin" ]]; then
  JAVA_HOME=`cygpath --absolute --windows "$JAVA_HOME"`
  JRE_HOME=`cygpath --absolute --windows "$JRE_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

#export CLASSPATH="$CLASSPATH"

MEMORY_OPTIONS="-Xms256m -Xmx1024m -XX:+UseParNewGC -XX:MaxPermSize=128M"

echo ${CLASSPATH}

$JAVA_HOME/bin/java -classpath $CLASSPATH $MEMORY_OPTIONS -Dlog4j.configuration=./log4j.xml -Dfile.encoding=UTF-8 org.rotarysource.standalone.ServerShellMain
