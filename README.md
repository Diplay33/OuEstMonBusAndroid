OuEstMonBus-Android (Android)

OuEstMonBus is an Android application that allows users to follow public transport in real time. It provides live vehicle positions, next arrivals at stops, theoretical timetables, and the ability to manage favorite lines and stops. The app is developed in Kotlin with Jetpack Compose, ensuring a modern and native Android experience.

⸻

Features
	•	Real-time tracking of vehicles (bus, tram, shuttles)
	•	Display of next departures at stops (with countdown and destination)
	•	View theoretical timetables based on schedule data
	•	Save favorite lines and stops for quick access
	•	Supports multiple transport networks (TBM, Kicéo, STAR, TBK, FilBleu…)
	•	Implemented in Kotlin using Jetpack Compose
	•	Accessibility support (TalkBack, font scaling, sufficient contrast)

⸻

Installation & Setup

Requirements
	•	Android Studio Bumblebee / Electric Eel (or newer)
	•	JDK 11 or 17
	•	Access to the GitHub repository

Steps
	1.	Clone the repo:
git clone https://github.com/Diplay33/OuEstMonBusAndroid.git
cd OuEstMonBusAndroid
	2.	Open project in Android Studio (Open an existing project).
	3.	Wait for Gradle sync to finish, include any missing SDKs or plugins.
	4.	Define API keys if needed (e.g. in local.properties or via BuildConfig).
	5.	Run the app on emulator or device.

⸻

Unit & Instrumentation Tests

The Android project includes two kinds of tests:
	•	Unit tests (JUnit + MockK) located in src/test
	•	Typically focused on the rawGTFSToServices() logic

Run unit tests locally:
```
./gradlew testDebugUnitTest
```

The Android CI also launches the test instrumentation (ServiceDAOTests). In GitHub Actions, these are executed using:
```
./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.ouestmonbus.ServiceDAOTests
```

So tests are automatically run at each commit to catch regressions.

⸻

CI / CD

The project uses GitHub Actions split into 3 job stages:
	1.	build – compile assembleDebug
	2.	unit-tests – run the ServiceDAOTests instrumentation only
	3.	deploy – publish to internal track in Google Play using Fastlane, triggered only on tags matching *-build-*

This ensures that only tested builds get deployed.

⸻

Contribution

Contributions are welcome!
	•	Fork the repo and create a branch (feature/..., fix/...)
	•	Add unit/instrumentation tests for changes
	•	Open a descriptive pull request
	•	Use commit titles that reference task IDs (e.g. fix(service): normalize route parsing JIR-24)

⸻

License

This project is shared under the MIT License. See the LICENSE file for details.

⸻

Support

If you encounter problems or have suggestions, please open an issue on GitHub.