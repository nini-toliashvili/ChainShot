# ChainShot

ChainShot is a widget-based application that allows users to send photos in real-time to their desired friends.
The sent photo will appear on the recipient’s home screen widget. This app makes it possible for users to share their daily moments instantly with their friends.

What makes this app unique is the feature where the recipient cannot view the sent photo until they send one back to the sender.
This creates a more interactive and engaging experience. Additionally, sent photos are automatically deleted after 24 hours.

The app requires camera permissions and ensures that users only send photos taken at the moment. After downloading the app, users can sign in using their Google account.
Once logged in, users are redirected to the photo capture screen, where they can navigate to their profile or friends' pages as desired.
The app also supports multilingual interfaces (available in both Georgian and English).

Technologies and Features:

Authentication: Firebase Auth, with Google account login.
Database: Cloud Firestore for storing user data (userId, name, email, and timestamp of login) and handling friend requests.
Multilingual Support: Available in both Georgian and English.
Architecture: Follows SOLID principles and Clean Architecture.
Dependency Injection: Uses Hilt.
Navigation: Based on Single Activity Principle with fragment-based UI and Navigation Graph.
Data Storage: SharedPreferences for storing language settings.
