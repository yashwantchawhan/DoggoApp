# DoggoApp
This is sample app to use multi module architecture. I'm consuning https://dog.ceo/api/ to fetch breed and sub breed of dogs.

This app has three module 
doggo-api-bridge -> This module contains data classes and interfaces. This module is light weight and can reuse across the app.
doggo-api -> This module contains implementaion of API
doggo-api-ui -> This module contains UI related classes and interfaces 



# the technology used in the project
1. Kotlin
2. MVVM
3. LiveData
4. RxJava
5. Dagger2
6. Navigation Components
7. Retrofit2
8. Single Activity Architecture

# Architecture used in the project
1. Followed multi module architecture. 
2. Used CommonDependencies provider pattern
3. Use MVI state pattern
