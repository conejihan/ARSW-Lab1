# Laboratorio 1

_Desarrollo y solución del laboratorio 1_

## Nombres:

* **Juan Carlos Alayon Molina**
* **Nicolas Medina Vargas**

## Parte I

_Salida al ejecutar con el comando start()_

![Screenshot](img1.png)

_Salida al ejecutar con el comando run()_

![Screenshot](img2.png)

Como se puede evidenciar en las imagenes anteriores al cambiar start() por run()
, se pasa de una ejecución paralela a una ejecución secuencial, ya que de la primera 
forma crea un nuevo hilo y como este se le asigna su propio escenario de ejecución, en este escenario 
llama a su metodo run() de una forma asincronica, por otro lado, de la segunda forma
lo que se hace es ejecutar el hilo de manera sincronica

## Parte III

Se obtuvieron los siguientes resultados de las validaciones que se realizaron 

_#Threads = 1_
![Screenshot](img/1hilo.png)
_#Threads = 8_
![Screenshot](img/8hilos.png)
_#Threads = 16_
![Screenshot](img/16hilos.png)
_#Threads = 50_
![Screenshot](img/50hilos.png)
_#Threads = 100_
![Screenshot](img/100hilos.png)

Con los anteriores resultados se realizo una grafica, en la cual se determino que entre mas hilos se usen, menor es el tiempo de ejecucion

_Grafica de Numero de hilos vs tiempo_
![Screenshot](img/grafica1.png)

## Parte IV

1. Según la ley de Amdahls, donde S(n) es el mejoramiento teórico del desempeño, P la fracción paralelizable del algoritmo, y n el número de hilos, a mayor n, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos? R: llegara un punto en que el tiempo de ejecucion no se puede reducir mas debido a que los componentes no son inifinitos y se acerca a su limite. Cómo se compara este desempeño cuando se usan 200? R: Aumenta la aceleracion con 500.
2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste? R: Al usar el doble, se evidencio que hubo uno disminucion considerable en el tiempo de ejecucion, pero el uso de la CPU y de la memoria aumentaron.
3. De acuerdo con lo anterior, si para este problema en lugar de 100 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 100 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 100/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta. R: Si se usa 1 hilo en cada una de las 100 maquinas hipoteticas, no se estaria aplicando de la manera mas optima, ya que no se esta aprovechando los equipos, por otro lado si se usaran c cantidad de hilos en cada uno de estos equipos, estariamos aprovechando al maximo estos, generando un mejor tiempo de ejecucion, al dividir los procesos entre el numero de cores.
