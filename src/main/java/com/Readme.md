# 📐 Calculadora de Derivadas

> Una calculadora de derivadas simbólicas con interfaz gráfica moderna, capaz de parsear expresiones matemáticas, derivar simbólicamente y mostrar resultados en formato LaTeX.

![Java](https://img.shields.io/badge/Java-17-007396?style=flat&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-17-007396?style=flat&logo=javafx)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=flat&logo=apache-maven)
![LaTeX](https://img.shields.io/badge/LaTeX-JLaTeXMath-008080?style=flat)

---

## 🎯 Características

### ✅ Funcionalidades matemáticas

- **Parser recursivo descendente**: Analiza expresiones matemáticas con paréntesis anidados
- **Derivación simbólica**: Aplica reglas de derivación completas:
    - Suma y resta: `(u ± v)' = u' ± v'`
    - Producto: `(u·v)' = u'·v + u·v'`
    - Cociente: `(u/v)' = (u'·v - u·v')/v²`
    - Potencia: `(u^n)' = n·u^(n-1)·u'`
- **Simplificación automática**: Reduce expresiones algebraicas
- **Soporte para potencias**: `x^2`, `(x+2)^3`

### ✅ Interfaz gráfica

- **Entrada interactiva**: Campo de texto para escribir funciones
- **Renderizado LaTeX**: Fórmulas visualmente profesionales
- **Historial de cálculos**: Registro de todas las derivadas
- **Evaluación numérica**: Calcula f'(2) automáticamente
- **Soporte para paréntesis**: Validación y visualización

### ✅ Tecnologías

- **Java 17**: Lenguaje principal
- **JavaFX 17**: Interfaz gráfica moderna
- **JLaTeXMath**: Renderizado de fórmulas matemáticas
- **Maven**: Gestión de dependencias y construcción
- **Git**: Control de versiones

---

## 🚀 Instalación y ejecución

### Requisitos previos

- Java 17 o superior
- Maven 3.9 o superior
- Git (opcional, para clonar)

### Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/calculadora-derivadas.git
cd calculadora-derivadas