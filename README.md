# Proyecto de grado - Espejo inteligente

## Tecnologias utilizadas

1. Java 8

    ![alt text](https://vignette.wikia.nocookie.net/mcmodhelp/images/c/c1/Java8_logo.png/revision/latest?cb=20150425172559)

2. Maven 3.3.9

    ![alt text](http://programaenlinea.net/wp-content/uploads/2017/11/maven-build-manager-logo-xx-by-xx.png)

3. Spring boot 2.0

  ![alt text](http://www.opencodez.com/wp-content/uploads/2016/09/Spring-Logo.png)

4. Apache common
    
    ![alt text](https://commons.apache.org/images/commons-logo.png)
5. OpenCv 3.4

    ![alt text]
    (https://i.stack.imgur.com/ez8QV.png)


## Instalación y configuración

1. Descargar el repositoria en un directorio.

    ```
    git clone https://github.com/mcadac/degree_project.git
    ```

2. Instalar Java 8

    ```
    http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html
    ```

3. Configurar la variable de entorno JAVA_HOME. 

    ```
    setx JAVA_HOME="{directorio_java_bin}"
    ```

4. Instalar maven 3.3.9 o una versión mas nueva.

    ```
    https://maven.apache.org/docs/3.3.9/release-notes.html
    ```

5. Configurar la variables de entorno MVN_HOME

    ```
    setx MVN_HOME={directorio_maven}
    ```

6. Configurar la variabale de entorno M2.

    ```
    setx M2="{directorio_repositorio_maven}"
    ```

6. Instalar Opencv 3.4 en el dispositivo que se desea ejecutar el sistema.

    ```
    https://github.com/opencv-java/opencv-java-tutorials/blob/master/docs/source/01-installing-opencv-for-java.rst
    ```

## Ejecución

1. Ir al proyecto **face-recognition-mirror**

2. Ejcutar el archivo **face_run.sh**
    ```
    sh face_run.sh
    ```

    ![Alt text](images/face.png?raw=true "Face running")

3. Ir al proyecto **gui-mirror**

4. Ejecutar el siguiente comando
    ```
    mvn spring-boot:run
    ```
    
    ![Alt text](images/gui-mirror.png?raw=true "Face running")

5. Ir al proyecto **recommendations-mirror**

6. Ejecutar el siguiente comando
    ```
    mvn spring-boot:run
    ```




