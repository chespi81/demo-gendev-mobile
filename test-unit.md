# JaCoCo Coverage Commands

Este documento contiene los comandos útiles para trabajar con cobertura de código en el proyecto DemoBank.

## Comandos Disponibles

### 1. Ejecutar Tests y Generar Reporte de Cobertura
```bash
./gradlew testDevDebugUnitTest jacocoTestReport
```

### 2. Verificar Umbrales de Cobertura
```bash
./gradlew jacocoTestCoverageVerification
```

### 3. Ejecutar Todo el Pipeline de Cobertura
```bash
./gradlew testDevDebugUnitTest jacocoTestReport jacocoTestCoverageVerification
```

### 4. Limpiar y Ejecutar Cobertura Completa
```bash
./gradlew clean testDevDebugUnitTest jacocoTestReport jacocoTestCoverageVerification
```

## Ubicaciones de Reportes

### Reporte HTML
- Ubicación: `app/build/reports/jacoco/jacocoTestReport/html/index.html`
- Abrir con: navegador web para visualización interactiva

### Reporte XML
- Ubicación: `app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml`
- Uso: integración con CI/CD y herramientas de análisis

## Configuración de Umbrales

### Umbrales Generales
- **Cobertura de línea**: 80% mínimo
- **Cobertura de branch**: 75% mínimo

### Umbrales para Componentes Críticos
- **Login, Dominio, Sesión**: 90% mínimo
- Clases incluidas:
  - `cl.tinet.demobank.ui.login.presentation.*`
  - `cl.tinet.demobank.ui.login.domain.*`
  - `cl.tinet.demobank.data.session.*`

## Exclusiones

Las siguientes clases están excluidas del reporte de cobertura:
- Clases generadas por Android (R, BuildConfig, etc.)
- Módulos de Dagger (inyección de dependencias)
- Activities y Fragments (capa UI)
- Clases de databinding
- Application class

## Comandos de Desarrollo

### Para ejecutar solo tests
```bash
./gradlew testDevDebugUnitTest
```

### Para ver todas las tareas de verificación
```bash
./gradlew tasks --group=verification
```

### Para ver todas las tareas de reporting
```bash
./gradlew tasks --group=reporting
```

## Buenas Prácticas para Tests Unitarios

### Nomenclatura GIVEN-WHEN-THEN

Para escribir tests claros y mantenibles, sigue la estructura **Given-When-Then**:

#### Ejemplo de Test con SessionManager
```kotlin
@Test
fun `GIVEN user is logged in WHEN getUserToken is called THEN should return saved token`() {
    // GIVEN - Configuración del estado inicial
    val expectedToken = "test-token-123"
    val username = "testuser"
    sessionManager.saveUserSession(expectedToken, username, "userId123")
    
    // WHEN - Acción que se está probando
    val actualToken = sessionManager.getUserToken()
    
    // THEN - Verificación del resultado esperado
    assertEquals(expectedToken, actualToken)
}
```

#### Ejemplo de Test con LoginPresenter
```kotlin
@Test
fun `GIVEN empty username WHEN login is called THEN should show validation error`() {
    // GIVEN
    val emptyUsername = ""
    val validPassword = "password123"
    val mockView = mockk<LoginContract.View>(relaxed = true)
    presenter.attachView(mockView)
    
    // WHEN
    presenter.login(emptyUsername, validPassword)
    
    // THEN
    verify { mockView.showLoginError("Usuario y contraseña son requeridos") }
}
```

### Convenciones de Nomenclatura

#### 1. Nombres de Métodos de Test
```kotlin
// ✅ CORRECTO - Describe escenario completo
fun `GIVEN valid credentials WHEN login is called THEN should save session and navigate to home`()
fun `GIVEN network error WHEN login is called THEN should show connection error message`()
fun `GIVEN user not logged in WHEN isUserLoggedIn is called THEN should return false`()

// ❌ INCORRECTO - Nombres poco descriptivos
fun testLogin()
fun checkUserSession()
fun validateInput()
```

#### 2. Estructura de Carpetas de Test
```
app/src/test/java/cl/tinet/demobank/
├── ui/
│   └── login/
│       ├── presentation/
│       │   └── LoginPresenterTest.kt
│       └── domain/
│           └── LoginUseCaseTest.kt
└── data/
    └── session/
        └── SessionManagerTest.kt
```

### Plantillas de Test

#### Test para Presenter
```kotlin
class LoginPresenterTest {
    
    @MockK lateinit var mockView: LoginContract.View
    @MockK lateinit var mockUseCase: LoginUseCase
    @MockK lateinit var mockSessionManager: SessionManager
    
    private lateinit var presenter: LoginPresenter
    
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter = LoginPresenter(mockUseCase, mockSessionManager)
        presenter.attachView(mockView)
    }
    
    @Test
    fun `GIVEN successful login response WHEN login is called THEN should save session and navigate`() {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val successResponse = LoginResponse(true, "token123", null, "user123", username)
        coEvery { mockUseCase.execute(username, password) } returns successResponse
        every { mockSessionManager.saveUserSession(any(), any(), any()) } just Runs
        every { mockView.showLoading() } just Runs
        every { mockView.hideLoading() } just Runs
        every { mockView.navigateToHome() } just Runs
        
        // WHEN
        presenter.login(username, password)
        
        // THEN
        coVerify { mockUseCase.execute(username, password) }
        verify { mockSessionManager.saveUserSession("token123", username, "user123") }
        verify { mockView.navigateToHome() }
    }
}
```

#### Test para Use Case
```kotlin
class LoginUseCaseTest {
    
    @MockK lateinit var mockRepository: LoginRepository
    
    private lateinit var useCase: LoginUseCase
    
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = LoginUseCase(mockRepository)
    }
    
    @Test
    fun `GIVEN valid credentials WHEN execute is called THEN should return success response`() {
        // GIVEN
        val username = "testuser"
        val password = "password123"
        val request = LoginRequest(username, password)
        val expectedResponse = LoginResponse(true, "token123", null)
        coEvery { mockRepository.login(request) } returns expectedResponse
        
        // WHEN
        val result = runBlocking { useCase.execute(username, password) }
        
        // THEN
        assertEquals(expectedResponse, result)
        coVerify { mockRepository.login(request) }
    }
}
```

#### Test para SessionManager (Test de Integración)
```kotlin
class SessionManagerTest {
    
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    
    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        sessionManager = SessionManager(context)
        // Limpiar cualquier dato previo
        sessionManager.clearSession()
    }
    
    @Test
    fun `GIVEN no saved session WHEN isUserLoggedIn is called THEN should return false`() {
        // GIVEN - Ya limpio por setup()
        
        // WHEN
        val isLoggedIn = sessionManager.isUserLoggedIn()
        
        // THEN
        assertFalse(isLoggedIn)
    }
    
    @Test
    fun `GIVEN saved session WHEN getUserSessionData is called THEN should return complete user data`() {
        // GIVEN
        val token = "test-token"
        val username = "15.413.217-1"
        val userId = "user123"
        sessionManager.saveUserSession(token, username, userId)
        
        // WHEN
        val sessionData = sessionManager.getUserSessionData()
        
        // THEN
        assertNotNull(sessionData)
        assertEquals(token, sessionData?.token)
        assertEquals(username, sessionData?.username)
        assertEquals(userId, sessionData?.userId)
    }
}
```

### Dependencias para Testing

Agregar al `build.gradle.kts`:
```kotlin
testImplementation("io.mockk:mockk:1.13.8")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
testImplementation("app.cash.turbine:turbine:1.0.0") // Para testing de Flows
```

### Comandos para Ejecutar Tests

```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests de una clase específica
./gradlew test --tests "LoginPresenterTest"

# Ejecutar tests con patrón específico
./gradlew test --tests "*SessionManager*"

# Ejecutar con reporte de cobertura
./gradlew testDevDebugUnitTest jacocoTestReport
```

## Integración con CI/CD

Para integrar en pipelines de CI/CD, usar:
```bash
./gradlew clean testDevDebugUnitTest jacocoTestReport jacocoTestCoverageVerification
```

Este comando:
1. Limpia artifacts previos
2. Ejecuta tests unitarios con cobertura
3. Genera reportes HTML y XML
4. Verifica que se cumplan los umbrales mínimos
5. Falla el build si no se cumple la cobertura mínima