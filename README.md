Plotter for parametric and polynomial curve graphs using Java2D and Swing.

# Dependencies

This project uses the FlatLaf Look and Feel library to give the Java Swing application a modern appearance with dark mode.

## Obtaining FlatLaf JAR

### Option 1: Downloading from Maven Repository

1. Go to the [Maven Repository page for FlatLaf](https://mvnrepository.com/artifact/com.formdev/flatlaf).
2. Select the version you want to use (it's recommended to choose the latest version).
3. On the version's page, click the `jar` link to download the `flatlaf-x.x.jar` file, where `x.x` is the version number.

### Option 2: Using the Provided JAR in the Repository

The `flatlaf-x.x.jar` is also included in this repository for convenience. You can find it in the `dependencies` folder.

## Importing FlatLaf JAR into Your Project

### In Eclipse:

1. Right-click your project in the Project Explorer.
2. Go to "Build Path" > "Add External Archives...".
3. Navigate to where you downloaded the JAR file or find it in the `dependencies` folder of this repository, select it, and click "Open".

### In IntelliJ IDEA:

1. Right-click your project in the Project Explorer.
2. Go to "Open Module Settings".
3. In the "Modules" section, go to the "Dependencies" tab.
4. Click the "+" button, choose "JARs or directories...", navigate to where you downloaded the JAR file or find it in the `dependencies` folder of this repository, select it, and click "OK".

### In NetBeans:

1. Right-click your project in the Projects window.
2. Choose "Properties".
3. In the "Categories" pane, choose "Libraries".
4. Under the "Compile" tab, click "Add JAR/Folder", navigate to where you downloaded the JAR file or find it in the `dependencies` folder of this repository, select it, and click "Open".

After adding the FlatLaf JAR to your project's classpath, you can start using it in your code to set the look and feel of your Swing application.
