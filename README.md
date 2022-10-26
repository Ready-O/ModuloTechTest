# ModuloTech Technical Test

## DI

I use Hilt for dependency injection which allows me to inject the necessary instances into classes and test them.

## Fetch data from API

To fetch data from the JSON, I used two libraries :
Retrofit, the most popular library in Android development to communicate with REST services and Moshi, a JSON converter that converts JSON strings into Kotlin objects.

## Manage data

To persist data and to be able to manage it easily, I decided to save in a database using Room library. I also use Flow to collect data in real-time from the Room database.
For each data type in the app, I assigned a repository that will be responsible to handle fetching and sending data from the remote source (API) and the local source (database).

## ViewModel

Following the MVVM pattern, I create a ViewModel for each fragment in order to manage the logic and the data to display.

## HomePage

I fetch all the three types of products from the Database (and the API if it's the first time). 
The data is mapped to the Device type and then it is displayed within a RecyclerView in the HomePageFragment.
When clicking a device, the app navigates to the DeviceSteeringFragment with the id of the device as an argument.

## Device Steering

The ViewModel of this fragment uses the id argument to fetch the device from the repo and display the corresponding screen.
Adter editing the data, the repository updates the changes in the database. And since the UI work in reactive way with flows, the changes are displayed immediately in the home page.

## My Account

Same as the devices, the user data is in the Room database and fetched by the UI layer using flow. In the fragment, I could have saved the edits of the user in the viewModel (to survive configuration changes for example). 

## Test

I use Mockk library to generate mock instances of other classes and Junit for test execution.
I tested successfully the simple I/O functions but for multiple functions that are complex, I wrote their test but they didn't work.

## Multi-language

I added two string resources for english and french.
