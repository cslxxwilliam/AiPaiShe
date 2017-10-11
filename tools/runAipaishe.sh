#!/bin/bash

case "$1" in
start)
   if [ -e /var/aipaishe/aipaishe.pid ]; then
     echo "Aipaishe is aleady running, pid=`cat /var/aipaishe/aipaishe.pid`"
     exit 1
   else
     export SENDGRID_API_KEY=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
     /usr/bin/java -Dsun.misc.URLClassPath.disableJarChecking=true -jar /var/aipaishe/aipaishe-0.0.1.jar>/var/aipaishe/aipaishe.log &
     echo $!>/var/aipaishe/aipaishe.pid
     echo "Aipaishe started, pid=`cat /var/aipaishe/aipaishe.pid`"
   fi
   ;;
stop)
   if [ -e /var/aipaishe/aipaishe.pid ]; then
     kill `cat /var/aipaishe/aipaishe.pid`
     rm /var/aipaishe/aipaishe.pid
     echo "Aipaishe is stopped"
   else
     echo "Aipaishe is NOT running"
     exit 1
   fi
   ;;
restart)
   $0 stop
   $0 start
   ;;
status)
   if [ -e /var/aipaishe/aipaishe.pid ]; then
      echo "Aipaishe is running, pid=`cat /var/aipaishe/aipaishe.pid`"
   else
      echo "Aipaishe is NOT running"
      exit 1
   fi
   ;;
*)
   echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0