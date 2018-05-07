#!/bin/sh
sudo modprobe bcm2835-v4l2
echo "Camera configured"
mvn spring-boot:run -Drun.jvmArguments="-Xms10m -Xmx150m -XX:+UseSerialGC" 