BASES DE DATOS II
Trabajo Practico 4
Bases de Datos NoSQL con Redis
El objetivo de esta práctica es introducir el uso de Redis como base de datos NoSQL de tipo
clave-valor. Se comenzará con una serie de preguntas conceptuales sobre el motor y sus
características para luego trabajar directamente con sus principales tipos de datos: Strings,
Listas, Conjuntos, Conjuntos Ordenados, Hashes y soporte Geoespacial.
Para la resolución pueden optar por utilizar una instalación local de Redis Stack (disponible en
redis.io/downloads) o alguna versión online como try.redis.io.
Sección 1 - Introducción a Redis
Redis (Remote Dictionary Server) es una base de datos NoSQL en memoria, de tipo clave-valor,
ampliamente utilizada como caché, broker de mensajes y motor de estructuras de datos en
tiempo real. Responder las siguientes preguntas antes de comenzar con los ejercicios prácticos.
1. ¿Qué tipo de base de datos es Redis? ¿En qué se diferencia de una base de datos
relacional y de otras bases de datos NoSQL como MongoDB?
2. ¿Dónde almacena los datos Redis? ¿Qué implicancias tiene esto en términos de
velocidad y de persistencia?
3. ¿Qué tipos de datos soporta Redis? Listar y describir brevemente cada uno.
4. Enunciar las características principales de Redis.
5. Comparar Redis con los RDBMS: ¿en qué casos conviene usar Redis en lugar de una
base de datos relacional y en cuáles no?
6. ¿Redis tiene soporte para transacciones? ¿Cómo funcionan? ¿Qué garantías ofrecen y
qué limitaciones tienen respecto de las transacciones ACID?
7. ¿Redis tiene persistencia? Describir los mecanismos disponibles (RDB y AOF) e indicar
las diferencias entre ellos.
8. ¿Cuáles son los principales casos de uso de Redis en aplicaciones reales?
Sección 2 - Manejo de Strings
2.1 Valores de texto
9. Agregar una clave package con el valor "Bariloche 3 days".

10. Agregar una clave user con el valor "Turismo BD2". Obtener el valor de la clave user.
11. Obtener todas las claves almacenadas actualmente.
12. Agregar una clave user con el valor "Cronos Turismo". ¿Cuál es el valor actual de la clave
user?
13. Concatenar " S.A." a la clave user. ¿Cuál es el valor actual de la clave user?
14. Eliminar la clave user. ¿Qué valor retorna si se intenta obtener la clave user luego de
eliminarla?
2.2 Valores numericos
15. Verificar si existe la clave visits.
16. Agregar una clave visits con el valor 0.
17. Incrementar en 1 la clave visits. ¿Cuál es el valor actual?
18. Incrementar en 5 la clave visits. ¿Cuál es el valor actual?
19. Decrementar en 1 la clave visits. ¿Cuál es el valor actual?
20. Incrementar en 2 la clave visits. ¿Cuál es el valor actual?
21. Agregar una clave "value package" con el valor 539789.32.
22. Incrementar en 20000 la clave "value package". ¿Cuál es el valor actual?
23. ¿Cual es el tipo de datos de "value package", visits y user?
Sección 3 - Manejo de Claves
24. Obtener todas las claves que empiecen con "v".
25. Obtener todas las claves que contengan la letra "t".
26. Obtener todas las claves que terminan con "age".
27. Renombrar la clave "package" por "bariloche package".
28. ¿Qué comando se utiliza para renombrar una clave solo si el nombre destino no existe
aún?
29. Eliminar todas las claves.
Sección 4 - Expiración de Claves
30. Agregar una clave agency con el valor "Cronos Tours".
31. ¿Cuál es el tiempo de vida (TTL) de la clave agency?
32. Agregar una expiración de 30 segundos a la clave agency.
33. ¿Cuál es el tiempo de vida de la clave agency luego de agregar la expiración?

34. Pasados los 30 segundos: ¿cuál es el TTL de agency? ¿Que retorna si se solicita el valor
de agency?
35. Agregar una clave agency con el valor "Cronos Tours" que expire en 20 segundos desde
su creación.
Sección 5 - Listas
36. Insertar una lista llamada pets con el valor "dog".
37. ¿Qué sucede si se ejecuta el comando GET sobre pets? ¿Cómo se obtienen los valores
de una lista?
38. Agregar a la lista pets el valor "cat" por la izquierda.
39. Agregar a la lista pets el valor "fish" por la derecha.
40. ¿Qué tipo de dato es el valor de pets?
41. Eliminar el valor del extremo izquierdo de la lista.
42. Eliminar el valor del extremo derecho de la lista.
43. Agregar a una clave "vuelo:ar389" los valores: aep, mdz, brc, nqn y mdq.
44. Ordenar los valores de la lista "vuelo:ar389". ¿Qué sucede si se solicitan todos los
valores de la lista luego de ordenarla?
45. Insertar el valor "fte" inmediatamente después de "brc".
46. Insertar el valor "ush" inmediatamente antes de "fte".
47. Modificar el último elemento de la lista por "sla".
48. Obtener la cantidad de elementos de "vuelo:ar389".
49. Obtener el tercer valor de "vuelo:ar389".
50. Eliminar el valor "aep" de "vuelo:ar389".
51. Quedarse únicamente con los valores de las posiciones 3 a 5 de "vuelo:ar389".
52. Agregar en "vuelo:ar389" el valor "fte". ¿Cuántas veces aparece ahora en la lista?
Sección 6 - Conjuntos (Sets)
53. Agregar un conjunto llamado airports con los siguientes valores:
eze aep nqn mdz mdq ush fte sla aep nqn brc cpc juj aep tuc
eqs
54. ¿Cuántos valores tiene el conjunto? ¿Por qué puede diferir de la cantidad de valores
ingresados?
55. Listar los valores del conjunto airports.
56. Quitar el valor "cpc" del conjunto airports.
57. Quitar un valor aleatorio del conjunto airports.

58. ¿Qué cantidad de valores tiene airports ahora?
59. Comprobar si "cpc" es miembro del conjunto airports.
60. Mover los valores "sla" y "juj" a un nuevo conjunto denominado noa_airports.
61. Obtener la unión de los conjuntos airports y noa_airports. ¿Modifica los conjuntos
originales?
62. Realizar la unión de airports y noa_airports y almacenar el resultado en un nuevo
conjunto llamado total_airports.
63. Realizar la intersección entre total_airports y noa_airports.
64. Realizar la diferencia entre total_airports y noa_airports.
Sección 7 - Conjuntos Ordenados (Sorted Sets)
65. Agregar a un conjunto ordenado llamado passengers los siguientes datos (score
nombre):
2.5 federico 4 alejandra 3 julian 1 ivan 2 tomas 2 luciana 2.4
natalia
66. Obtener los valores del conjunto passengers.
67. Actualizar el score de luciana a 2.7.
68. Agregar al conjunto passengers a silvia con score 5.1.
69. Incrementar en 2 el score de alejandra.
70. Obtener los valores del conjunto passengers con sus scores.
71. Obtener los valores del conjunto passengers con sus scores en orden inverso.
72. Obtener la cantidad de elementos del conjunto passengers.
73. Obtener la cantidad de elementos que tienen scores entre 2 y 3.
74. Obtener el ranking de julian en el conjunto passengers.
75. Obtener el score de tomas en el conjunto passengers.
76. Extraer el elemento con menor score del conjunto passengers.
77. Extraer el elemento con mayor score del conjunto passengers.
78. Eliminar del conjunto passengers al valor silvia.
Sección 8 - Hashes
79. Agregar a un hash llamado user:cronos los siguientes campos:
"razon social" "cronos s.a"
domicilio "47 236 La Plata"
"telefono" 2215556677

80. Agregar el campo mail con el valor info@cronos.com.ar al hash user:cronos.
81. Obtener todos los campos y valores de user:cronos.
82. Obtener únicamente el valor del campo mail de user:cronos.
83. Eliminar el campo teléfono de user:cronos.
84. Obtener la cantidad de campos de user:cronos.
85. Obtener las claves (nombres de campos) de user:cronos.
86. Determinar si existe el campo cuil en user:cronos.
87. Obtener todos los valores (sin los nombres de campos) de user:cronos.
88. Obtener la longitud del valor del campo mail de user:cronos.

Sección 9 - Geospatial

89. Agregar en un conjunto denominado cities las siguientes localidades con sus
coordenadas (longitud, latitud):

|                        | Ciudad  | Latitud    | Longitud   |
| ---------------------- | ------- | ---------- | ---------- |
| Buenos Aires           |         | -34.61315  | -58.37723  |
| Cordoba                |         | -31.41350  | -64.18105  |
| Rosario                |         | -32.94682  | -60.63932  |
| Mendoza                |         | -32.89084  | -68.82717  |
| San Miguel de Tucuman  |         | -26.82414  | -65.22260  |
| La Plata               |         | -34.92145  | -57.95453  |
| Mar del Plata          |         | -38.00042  | -57.55620  |
| Salta                  |         | -24.78590  | -65.41166  |
| Santa Fe               |         | -31.64881  | -60.70868  |
| San Juan               |         | -31.53750  | -68.53639  |
| Resistencia            |         | -27.46056  | -58.98389  |
| Santiago del Estero    |         | -27.79511  | -64.26149  |
| Posadas                |         | -27.36708  | -55.89608  |
| San Salvador de Jujuy  |         | -24.19457  | -65.29712  |
| Bahia Blanca           |         | -38.71959  | -62.27243  |
| Parana                 |         | -31.73271  | -60.52897  |

Atencion  En Redis, los datos geoespaciales se almacenan como conjuntos ordenados (Sorted
Sets). Las coordenadas se pasan en el orden longitud, latitud (no latitud, longitud).

90. Obtener todos los miembros del conjunto cities.
91. Obtener las coordenadas almacenadas de Santa Fe.
92. Obtener la distancia en kilómetros entre Buenos Aires y Córdoba.
93. Obtener las ciudades que se encuentran en un radio de 100 km de la coordenada
(-27.37, -55.9), incluyendo la distancia de cada una.
94. Obtener las ciudades que se encuentran a menos de 700 km de Córdoba.
Al completar esta práctica el alumno debe poder operar con una instancia de Redis, conocer sus
principales tipos de datos y comandos, y comprender en qué casos resulta una alternativa
adecuada frente a otros motores de base de datos.