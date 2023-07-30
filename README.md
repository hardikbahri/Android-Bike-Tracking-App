# Android Bike Tracking App

This Android app is designed to track a bike's movement and display real-time GPS data, speed, distance traveled, and accelerometer data. It utilizes the FusedLocationProviderClient to retrieve GPS location updates and the accelerometer sensor to detect the bike's movement.

# Features
Real-time GPS Tracking: The app displays the latitude and longitude of the bike's current location.

Speed and Distance Tracking: The app calculates and displays the bike's current speed (in m/s) and the total distance traveled (in meters).

Accelerometer Data: The app reads accelerometer data to detect the bike's movement and determine the direction of movement (forward or backward).

# Screenshots


# Getting Started
Clone the repository to your local machine using the following command:

bash
Copy code
git clone https://github.com/your-username/AndroidBikeTrackingApp.git
Open the project in Android Studio.

Connect your Android device or use an emulator to run the app.

Grant the required location permissions when prompted.

Launch the app on your device.

The app will display real-time GPS data, speed, distance, and accelerometer data.

# Permissions
The app requires the following permissions:

Fine Location: To access GPS location for tracking the bike's movement.
Internet: To access Google Maps services.
# Dependencies
The app uses the following dependencies:

Google Play Services Location API: To access the FusedLocationProviderClient for GPS updates.
Google Maps API: To display the bike's location on Google Maps.


# License
This project is licensed under the MIT License.



