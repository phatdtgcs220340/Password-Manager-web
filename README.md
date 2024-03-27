# Password Manager Project

This project is a password manager application developed using Spring Boot, Spring Data JPA, Spring Security, Docker Compose, PostgreSQL, and Thymeleaf. It aims to provide users with a secure platform for storing and managing their passwords.

## Installation Guide

Before running the project, ensure you have the following prerequisites installed:

1. **Java Development Kit (JDK) 21:** 
   - Install the JDK 21 from the official Oracle website or any other trusted source.

2. **Gradle:**
   - You can install Gradle manually or use a build tool like SDKMAN! for managing your development environment. Here's how to install it manually:
     ```bash
     # On Unix-based systems (Linux, macOS)
     wget https://services.gradle.org/distributions/gradle-x.x.x-bin.zip
     unzip gradle-x.x.x-bin.zip
     sudo mv gradle-x.x.x /opt/gradle
     export PATH=$PATH:/opt/gradle/bin
     gradle --version
     ```

3. **Docker:**
   - Docker provides a containerized environment for running applications. Install Docker from the official Docker website for your specific operating system.

## Running the Project

Follow these steps to run the project:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/password-manager.git
2. **Navigate to the Project Directory:**
   ```bash
   cd password-manager
3. **Build and Run the Project with Gradle:**
   ```bash
    ./gradlew bootRun
4. **Access the Application:**
   Once the application has started successfully, you can access it by opening a web browser and navigating to http://localhost:8080.
