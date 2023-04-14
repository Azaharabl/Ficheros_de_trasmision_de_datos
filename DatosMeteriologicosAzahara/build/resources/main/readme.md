# Realizacion del ejercicio: Datos Meteoralógicos

## 1. Leer los tres ficheros y combinarlos en uno solo completando los datos e incluyendo el campo fecha
Primero obtenermos la path de Resuces que es donde tenemos nuestros ficheros, y los filtramos para solo escojer
los ficheors ".csv".

Despues creamos el modelo que usaremos para el fichero inclullendo los datos de dia que obtendremos del nombre del fichero
y parseando los datos como decimales u horas.

Una vez listos los datos los pasamos a un fichero con formato csv, para ello necesitamos un directorio que en nuestro
caso hemos llamdo data. Comprobaremos si exite el fichero peara sobreescribirlo, y si no crearlo.

## 2. Exportar el resultado a Json y XML

estos datos tambien los pasaremos a xml y a Json

Para pasarlo a xml importaremos la librería se simpleXml
crearemos un dto del objeto a resializar con todos los campos string y con la anotaciones necesarias
tambien crearemos una colecion para usarla de listacon las antoaciones

Para pasarlo a json usaremosla librería de Moshi


## 3. Realizar las siguientes consultas:
- Temperatura máxima por día y lugar
- Temperatura mínima por día y lugar
- Temperatura máxima por provincia (día, lugar, valor y momento)
- Temperatura mínima por provincia (día, lugar, valor y valor)
- Temperatura media por provincia (día, lugar y valor)
- Listado de precipitación media por día y provincia
- Número de lugares en el que llovío por día y provincia
- Temperatura média de la provincia de Madrid
- Media de temperatura máxima total
- Media de temprratura minima total
- Lugares donde la máxima ha sido antes de las 15:00 por día
- Lugares donde la mínima ha sido después de las 17:30 por día

## 4.- Para la privincia de Madrid exportar en JSON y XML el siguiente informe
- Por cada día:
    - Temperatura media
    - Temperatura máxima (Lugar y momento)
    - Temperatura mínima (Lugar y momento
    - Si hubo precipitación (sí/no) y valor de la misma.   
- lo haremos de tres modos, coleciones, Stream y Dataframe
