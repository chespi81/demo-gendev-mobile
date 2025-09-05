- The project follows a modular VIP (View - Interactor - Presenter) architecture, Clean, and utilizes SOLID, DRY, and KISS principles. The modules are designed to be independent and reusable, with a clear separation between presentation, domain, and data layers.
- Consider only the following components to implement the solution
- Do not consider the files of the presentation layer
- Do not consider configure Retrofit
---

## 1. Domain Layer

- Contains the business logic and is independent of frameworks.
- Source: <module_name>/src/main/java/cl/demo/app/<module_name>/domain

### Entity

- Represents the core data structure used in the application. Entities should be simple data classes that hold the data relevant to the domain. These classes are typically used to transfer data between layers.

### Repository (interface)

- Defines the methods to access data. Repositories act as an abstraction layer between the data sources and the rest of the application, providing a consistent API for data operations. This allows the application to switch data sources without affecting the rest of the code.
- The data type of the response for each method is represented by an entity
- The Repository needs to be added to the dependency injection managed by Dagger

### UseCase

- Encapsulates specific business logic operations. Each use case should perform a single task and should be independent of the UI and data layers. Use cases are executed by the presentation layer and interact with the repository to fetch or manipulate data. This ensures that the business logic is centralized and can be easily tested.

---

## 2. Data Layer

- Responsible for accessing data sources (API, Local DB, etc.).
- Source: <module_name>/src/main/java/cl/demo/app/<module_name>/data

### API

- Provides the interface to interact with remote data sources. The API should define the endpoints and data models expected from the remote server. Use annotations like `@GET`, `@POST`, etc., to specify the HTTP methods and parameters.
- The Api needs to be added to the dependency injection managed by Dagger

### Model

- Represents the data structure received from the API. Models should be mapped to the domain entities to ensure that the application uses a consistent data format. Use @SerializedName annotations to map JSON keys to Kotlin properties, ensuring that the data can be correctly deserialized from the API response.

### DataSource

- Defines the methods to access data sources. This interface should abstract the data access logic, allowing different implementations (e.g., local database, remote API) to be used interchangeably.

### DataSourceRemote

- Provides the concrete implementation of the `DataSource` interface. This could involve accessing a local database, a remote API, or any other data source.
- The DataSourceRemote needs to be added to the dependency injection managed by Dagger

### RepositoryImpl

- Contains the implementation of the repository interface. It should call the methods defined in the `DataSource` interface. 

---

## 3. Dependency Injection
- All aspects related to dependency injection are configured. 
- Source: <module_name>/src/main/java/cl/demo/app/<module_name>/di
- Within this directory, you will find subdirectories such as:

#### scope
- Scopes in Dagger are annotations used to indicate the lifetime of an instance of a dependency.
- For example, `FeatureScope` defines that dependencies marked with this annotation will have a lifecycle associated with the `FeatureComponent` component.
```
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope
```

#### module
- The module directory includes classes annotated with @Module, which are used by Dagger to know how to build and inject dependencies.
- Each module can provide different types of dependencies
- Example for Repository and DataSourceRemote: FeatureModule provides instances of Repository and DataSourceRemote for functionality related to Feature
```
@Module
abstract class FeatureModule {

    @Reusable
    @Binds
    abstract fun provideFeatureRepository(repository: FeaturenRepositoryData): FeatureRepository

    @Reusable
    @Binds
    abstract fun provideFeatureDataSourceRemote(repository: FeaturenDataSourceRemote): FeaturenDataSourceRemote
}
```
- Example for Api: ApiFeatureModule use the annotation @Module to indicate that Dagger should provide a subcomponent for each api.
```
@Module
abstract class ApiFeatureModule {
    @Reusable
    @Provides
    fun provideFeatureApi(msExpConnector: MSExpConnector): FeatureApi {
        return msExpConnector.getConnector(FeatureApi::class.java)
    }
}
```
---