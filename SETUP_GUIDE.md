# Quick Setup Guide

## 🚀 Get Started in 5 Minutes

### Step 1: Clone the Repository
```bash
git clone <repository-url>
cd LincRide
```

### Step 2: Get Google Maps API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable **Maps SDK for Android**:
   - Navigate to "APIs & Services" → "Library"
   - Search for "Maps SDK for Android"
   - Click "Enable"
4. Create an API Key:
   - Go to "APIs & Services" → "Credentials"
   - Click "Create Credentials" → "API Key"
   - Copy the generated API key

### Step 3: Configure API Key

Create a `local.properties` file in the project root (or use the template):

```bash
cp local.properties.template local.properties
```

Edit `local.properties` and add your API key:
```properties
MAPS_API_KEY=AIzaSy...your_actual_key_here
```

⚠️ **Important**: Never commit `local.properties` to version control!

### Step 4: Open in Android Studio

1. Launch Android Studio
2. Select "Open" and choose the `LincRide` folder
3. Wait for Gradle sync to complete (may take a few minutes on first run)
4. If prompted, install any missing SDK components

### Step 5: Run the App

**Option A: Using Android Studio**
1. Select a device or emulator (API 24+)
2. Click the green "Run" button (▶️) or press `Shift + F10`

**Option B: Command Line**
```bash
# Build and install
./gradlew installDebug

# Or run directly on connected device
./gradlew installDebug && adb shell am start -n com.lincride/.presentation.MainActivity
```

## 📱 System Requirements

- **Android Studio**: Hedgehog (2023.1.1) or newer
- **Java**: JDK 17
- **Android SDK**: API 34 (Android 14)
- **Minimum Device**: API 24 (Android 7.0)
- **Gradle**: 8.2.0 (auto-managed)
- **Internet**: Required for Google Maps

## ✅ Verify Installation

After launching, you should see:
1. A Google Map centered on San Francisco
2. "Stop new requests" button at the top
3. "1 Active campaign" green banner
4. Bottom navigation with "Offer Ride" button

Click **"Offer Ride"** to start the simulation!

## 🐛 Common Issues & Solutions

### Issue: "API key not found"
**Solution**: Make sure `local.properties` exists with `MAPS_API_KEY=your_key`

### Issue: Map shows gray screen
**Solutions**:
1. Check if Maps SDK for Android is enabled in Google Cloud Console
2. Verify API key is correct and has no restrictions blocking your app
3. Check internet connection

### Issue: Gradle sync failed
**Solutions**:
1. File → Invalidate Caches → Invalidate and Restart
2. Check internet connection
3. Update Android Studio to latest version
4. Delete `.gradle` folder and sync again

### Issue: Build fails with "Java version" error
**Solution**: File → Project Structure → SDK Location → Set JDK to version 17

### Issue: Emulator is slow
**Solutions**:
1. Enable hardware acceleration (HAXM for Intel, Hypervisor for M1 Mac)
2. Allocate more RAM to emulator (Edit AVD → Advanced → RAM)
3. Use a physical device for better performance

## 🎮 Using the App

### Automatic Flow (Recommended)
1. Launch app → See map
2. Click "Offer Ride" → Bottom sheet appears
3. **Wait and observe** the automatic progression through all 5 events
4. Car animates to pickup → Pickup confirmation → Journey to destination → Trip complete

### Manual Testing
- Reset anytime with "New Trip" button
- Each event triggers automatically with realistic timing
- No manual intervention needed after clicking "Offer Ride"

## 📞 Need Help?

- Check the main [README.md](README.md) for detailed documentation
- Review code comments for implementation details
- Open an issue if you encounter problems

## 🎯 What to Test

✅ Map loads correctly with San Francisco center  
✅ Click "Offer Ride" triggers bottom sheet animation  
✅ Car marker moves smoothly along route  
✅ Progress bars update during journey  
✅ Pickup confirmation appears automatically  
✅ Trip completion overlay shows earnings  
✅ "New Trip" resets to initial state  

**Happy Testing! 🚀**
