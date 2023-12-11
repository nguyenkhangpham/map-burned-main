# Use an existing image with Java (as Android SDK requires Java)
FROM openjdk:8-jdk

# Install required dependencies
RUN apt-get update && apt-get install -y unzip wget

# Download and install Android SDK
ENV ANDROID_SDK_ROOT /usr/local/android-sdk
RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip -O cmdline-tools.zip && \
    unzip cmdline-tools.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools && \
    rm cmdline-tools.zip && \
    mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/latest

# Set environment variable
ENV PATH ${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

# Accept licenses
RUN yes | sdkmanager --licenses

# Install necessary Android SDK packages
RUN sdkmanager "platform-tools" "platforms;android-29"

# Copy your APK file into the Docker image
COPY app-debug.apk /app-debug.apk

# Expose a port for ADB (optional)
EXPOSE 5037

# Keep the container running
CMD tail -f /dev/null
