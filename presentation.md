- The project follows a modular VIP (View - Interactor - Presenter) architecture, Clean, and utilizes SOLID, DRY, and KISS principles. The modules are designed to be independent and reusable, with a clear separation between presentation, domain, and data layers.
- Consider including the presentation layer and dependency injection
- Do not consider the files of the domain or data layer
---

## 1. Presentation Layer

- Contains the view and presentation logic (using MVP).
- Source: <module_name>/src/main/java/cl/demo/app/<module_name>/presentation

### Contract

- Defines the communication interface between the view and the presenter. The contract should specify the methods that the view must implement and the methods that the presenter must provide. This ensures a clear separation of concerns and makes the code easier to maintain.

### Presenter

- Handles the presentation logic and acts as a bridge between the view and the domain layer. The presenter should execute use cases and update the view based on the results. This ensures that the UI logic is separated from the business logic.
- The Presenter should include the following methods:
    - `initView()`: Initializes the view and sets up any necessary UI components.
    - `unbindView()`: Unbinds the view from the presenter to prevent memory leaks and ensure proper cleanup.
    ```
    override fun unbindView() {
        super.unbindView()
    }
    ```
- The Presenter needs to be added to the dependency injection managed by Dagger

### Activity (View)

- Represents a screen in the application. Activities should implement the view interface defined in the contract and delegate the business logic to the presenter. Initialize the presenter in the `onCreate` method and ensure that the view and presenter are properly connected. This activity should be added to the AndroidManifest.xml file.
- The Activity needs to be added to the dependency injection managed by Dagger

### Fragment (View)

- Similar to activities, fragments represent a portion of a screen. Fragments should also implement the view interface and delegate the business logic to the presenter. Initialize the presenter in the `onViewCreated` method and ensure that the view and presenter are properly connected.
- The Fragment needs to be added to the dependency injection managed by Dagger

### Navigator

- Manages navigation between different screens in the application. The navigator should handle the creation of intents and the transition between activities or fragments. This helps to keep the navigation logic separate from the view and presenter.
- The Navigator needs to be added to the dependency injection managed by Dagger

---

## 2. Dependency Injection
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
- Each module can provide different types of dependencies, such as presenters, navigators, etc.
- Example for Presenter and Navigator: FeatureModule provides instances of presenters and navigators for functionality related to Feature
```
@Module
abstract class FeatureModule {
    @FeatureScope
    @Binds
    abstract fun provideFeaturePresenter(presenter: FeaturePresenter): FeatureContract.Presenter

    @FeatureScope
    @Binds
    abstract fun provideFeatureNavigator(navigator: FeatureNavigator): FeatureContract.Navigator
}
```
- Example for Activity and Fragment: FeaturePresentationModule use the annotation @ContributesAndroidInjector to indicate that Dagger should provide a subcomponent for each activity or fragment declared in this module. Each subcomponent has its own scope and associated modules.
```
@Module
abstract class FeaturePresentationModule {
    @FeatureScope
    @ContributesAndroidInjector(modules = [FeatureModule::class])
    abstract fun contributeFeatureActivity(): FeatureActivity

    @FeatureScope
    @ContributesAndroidInjector(modules = [FeatureModule::class])
    abstract fun contributeFeatureFragment(): FeatureFragment
}
```
---