# Use a Debian-based image as the base
FROM debian:latest

# Copy the JDK DEB package to the image
COPY jdk-21_linux-x64_bin.deb .

# Install the JDK package
RUN dpkg -i ./jdk-21_linux-x64_bin.deb

# Set the environment variable JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk-amd64/

# Expose the default HTTP port
EXPOSE 8080

# Define the command to run when the container starts
CMD ["java", "-version"]