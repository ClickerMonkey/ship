#!/bin/bash
java -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -XX:+DisableExplicitGC -Xnoclassgc -Xincgc -Xmx256m -Xms256m -jar mario.jar