# BBB HTTP Server

A minimal GUI-based local HTTP server built with Jetty, designed to serve **BigBlueButton playback files** from the current directory.  
This tool is especially useful for viewing or testing downloaded BBB recordings offline using your default browser.

---

## 📦 Features

- Auto-starts an embedded Jetty HTTP server from the current directory
- Dynamically finds an available port in the dynamic/private port range
- Automatically opens the playback (`index.html`) in the default browser
- Cross-platform GUI with Java Swing
- Minimal dependencies, simple to build and run

---

## 🛠 Requirements

- Java 8 or newer
- Maven

---

## 🚀 How to Build

Make sure you have Java and Maven installed. Then, from the project root, run:

```bash
mvn package
```
The JAR will be generated at:
```bash
target/bbb-http-server-[version]-jar-with-dependencies.jar
```

---

## ▶️ How to Run
Copy the generated JAR file into the directory containing your BBB playback files, then run:
```bash
java -jar bbb-http-server-[version]-jar-with-dependencies.jar
```
