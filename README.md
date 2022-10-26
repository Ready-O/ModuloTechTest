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

## Test

I use Mockk library to generate mock instances of other classes and Junit for test execution.
