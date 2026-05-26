# 🛠 Skill: Creación de Composables Estándar

Esta guía establece los estándares técnicos y la estructura de archivos para la creación de componentes de UI en el proyecto **Workout Tracker**.

## 1. Configuración del Sistema de Diseño
Antes de implementar la UI, es obligatorio integrar o verificar los estilos en:

*   **Colores:** [`Color.kt`](../app/src/main/java/com/eelizarraras/workout/ui/theme/Color.kt)
    *   Centralizar tonos de marca y estados.
*   **Tipografía:** [`Type.kt`](../app/src/main/java/com/eelizarraras/workout/ui/theme/Type.kt)
    *   Definir escalas de texto (Title, Body, Label).
*   **Tema:** [`Theme.kt`](../app/src/main/java/com/eelizarraras/workout/ui/theme/Theme.kt)
    *   Asegurar que el componente herede del `WorkoutTrackerTheme`.

## 2. Patrón Stateful & Stateless
Para asegurar la testabilidad y reutilización, cada componente debe dividirse en:

### Nivel 1: Stateful (Gestor de Estado)
- Maneja Resources (`stringResource`), ViewModels y navegación.
- Nombre: `NombreComponente` (ej. `WorkoutCard`).

### Nivel 2: Stateless (Puro de UI)
- Recibe datos primitivos o modelos de dominio.
- No contiene lógica de negocio ni acceso a contextos externos.
- Nombre: `Content`.
- **Obligatorio:** Recibir y aplicar un `modifier: Modifier`.

## 3. Previews y Validación
- Todo componente debe incluir una función `@Preview`.
- La preview debe estar envuelta en `WorkoutTrackerTheme`.
- Usar `showBackground = true` cuando sea necesario validar contraste.

## 4. Checklist de Calidad
- [ ] ¿Usa tokens de `MaterialTheme.colorScheme` o `Color.kt`?
- [ ] ¿Usa estilos de `MaterialTheme.typography`?
- [ ] ¿El modificador se pasa desde el Stateful al Stateless?
- [ ] ¿La Preview compila y refleja el diseño final?
