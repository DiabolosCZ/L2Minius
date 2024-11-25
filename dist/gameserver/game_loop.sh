#!/bin/bash

while :;
do
java -server -Dfile.encoding=UTF-8 -Xmx5G -cp config:./../libs/* l2mv.gameserver.GameServer > log/stdout.log 2>&1

        [ $? -ne 2 ] && break
        sleep 30;
done
