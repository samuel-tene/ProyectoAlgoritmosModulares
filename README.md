# Taller de Algoritmos Modulares

## Hecho por 

- Samuel Tenelanda Moreno 

## Casos desarrollados

### 1. Cafetería universitaria (inventario)
- Hasta 20 productos con código, nombre, precio, cantidad y categoría (Bebidas, Panadería, Snacks).
- Opciones: registrar producto, realizar venta, actualizar inventario (ver y reponer stock), mostrar agotados y total vendido del día.
- El código del producto se asigna automáticamente.
- Usa programación orientada a objetos (clase `Producto`).

### 2. Control académico (notas)
- Hasta 25 estudiantes con código, nombre, tres notas y porcentaje de asistencia (calculado con las clases del semestre y las clases asistidas).
- Opciones: registrar estudiante, ver definitivas y estados, promedio general del grupo, mejor y peor promedio.
- Definitiva: promedio simple de las 3 notas. Estado: Reprueba si la asistencia es menor a 70%; si no, Aprobado (3.0 o más), Habilita (de 2.0 a 2.99) o Reprueba (menos de 2.0).
- Programación modular con vectores y una matriz.

### 3. Control de producción (fábrica)
- 10 operarios y 5 días de producción (matriz de unidades enteras).
- Opciones: registrar operario, ingresar producción diaria, producción semanal por operario, mejor operario, promedio general y días con baja producción.
- Un día tiene baja producción si su promedio es menor al 80% del promedio general. Un operario sin producción ingresada cuenta como 0.
- Programación modular con vectores y una matriz.


## Validación de datos

Todas las lecturas pasan por `utils/Validaciones.java`, que repite la solicitud hasta recibir un dato válido, así el programa no se cae si se escribe texto en vez de un número. También se validan cupos máximos, códigos repetidos, rangos de notas (0.0 a 5.0) y valores no negativos.

## Cómo ejecutar

1. Instalar el JDK de Java.
2. Abrir la carpeta del proyecto en VS Code con el Extension Pack for Java.
3. Abrir `src/Main.java` y ejecutar con el botón Run.
