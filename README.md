Dineout Application

Following application consist of two Activities.



<div align="center">
    <img src="/screenshots/Screenshot_20181027-145623.png?raw=true" width="200px"</img>
</div>

1. HomeActivity
    The purpose of this activity is to list down 50 Food Outlets near specified fueled office.
    On clicking the list item the map view will animate and locate the restraunt.
    On Clicking View Details , you will get redirected to FoodOutletDetails Activity

2. FoodOutletDetailsActivity
    The purpose of this activity to show basic data of food outlet as well as fetch details
    from api .
    You can dislike any food outlet of your choice.
    Disliked food outlet will also show up in the list of all food outlets on HomeActivity


For gathering data, following FourSquareApis were used, these APIs have limited qouta, change keys in the string
resource file if the quota is finished.

The app is based on MVP architecture where
1. Activities represents VIEW
2. DataModel represents  MODEL
3. ActivityPresenterImpl represents PRESENTER

App Structure is as follows

app
    |-src
        |-main
            |-java
                |-com.phantasmist.dineout
                    |-AppModules
                        |-Home
                        |-FoodOutletDetails
                    |-Base
                    |-Cache
                    |-DependenyInjection
                    |-Remote
                    |-Utils
                    =DineoutApplication




The AppModule folder consist of all the app features like HomeScreen and FoodOutletDetail Screen.
The Base and Utils directory consist of some common reusable classes.
The DependencyInjection directory consist of Components and Modules for configuring Dependeny injection in the app.
The Cache directory consist of Room Database implementation and configuration.
The Remote directory consist of retrofit configs.


Used Libraries are as follows:
1. Retrofit
2. Gson
3. Kotlin
4. JavaXInject
5. Dagger 2
6. OkHTTP
7. Android Arch Components : Room,Lifecycle,ViewModels etc

The app is for Demo purpose only demonstrating the usage of above libs, kotlin in conjunction with
MVP design pattern.