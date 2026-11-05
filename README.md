# Proyecto intermodular II - Reto 2

Segundo reto del curso 2026/2027 de la asignatura de Proyecto Intermodular II en 2º de DAM.

El proyecto sirve de iniciación al scraping utilizando la librería JSoup. Para esta introducción, el programa realiza una sencilla tarea de scraping:

- Se accede a la página web de MotoGP y se obtienen los datos del calendario de la temporada 2027 a través de su HTML.
- Se obtienen una serie de datos de cada GP: `nombre`, `circuito`, `ciudad` (si la hay), y `fechas` de inicio y final.
- Se guarda la información en un formato ordenado en un [archivo de texto](src/main/java/resources/calendario.txt).
- Se lee e imprime dicho archivo de texto por consola.