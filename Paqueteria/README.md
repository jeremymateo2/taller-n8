# Sistema de Gestión de Paquetería

Taller de Recursividad, Búsqueda y Ordenamiento – UDLA  
Autor: Macas Pachar Amelio

Aplicación de escritorio en Java (Swing + GUI Designer de IntelliJ) para registrar paquetes enviados entre ciudades del Ecuador y controlar su estado (Receptado → Enviado → Recibido).

## Funcionalidades

- Registro, listado y modificación de paquetes (sin trackings duplicados)
- Cambio de estado por número de tracking
- Cálculos recursivos: total de paquetes, peso total, peso por ciudad y cantidad por estado
- Ordenamiento burbuja (por tracking) e inserción (por peso)
- Búsqueda binaria por tracking y búsqueda por cédula + estado

## Clases

| Clase | Descripción |
|---|---|
| `Paqueteria` | Datos de un paquete |
| `Lista` | Implementa la interfaz `GestionPaquetes` usando un `ArrayList` |
| `ControladorPaqueteria` | Validaciones y la interfaz interna `GestionPaquetes` |
| `VentanaPaqueteria` (.java + .form) | Interfaz gráfica |
| `Main` | Inicia la aplicación |

## Cómo ejecutarlo

1. Clonar el repositorio y abrir la carpeta en IntelliJ IDEA.
2. En **File → Project Structure → SDK** seleccionar un JDK.
3. Ejecutar `Main.java`.
