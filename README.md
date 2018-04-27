# Proyecto de grado - Espejo inteligente

## Tecnologias utilizadas

1. Java 8
2. Maven 3.3.9
3. Spring boot 2.0
4. Apache common
5. OpenCv 3.4

## Instalación y configuración

sudo modprobe bcm2835-v4l2

will "enable" the camera for opencv automatically.

make sure you have the camera enabled from the raspberry config, either gui or raspi-config. the above loads the necessary drivers to handle everything automatically, i.e. loads the appropriate interfaces (v4l2 drivers) for the raspberry camera.

works out of the box on raspbian jessie. other releases might include the drivers by default, but the link below contains info on compiling the drivers in your worst case. so you should be able to get this to work with pidora as well.
