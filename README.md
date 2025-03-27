# Maze Solver Application

## **Overview**
This is a Java-based graphical application that allows users to solve mazes and save their solutions. The program finds the shortest path in a maze and displays the solution visually. Users can load mazes, manage start and end positions, and save results in text, binary, or PNG formats.

## **Features**
- Load and visualize mazes from text or binary files
- Solve mazes using Dijkstra’s algorithm to find the shortest path
- Display the solution graphically
- Save results as text, binary, or PNG images
- Interactive GUI with controls to set start and end points

## **Technologies Used**
- **Java (Swing)** – For GUI development
- **Graph Algorithms (Dijkstra's Algorithm)** – For finding the shortest path
- **File Handling** – Supports text, binary, and PNG formats
- **Object-Oriented Programming (OOP)** – Modular and maintainable code structure

## **Project Structure**
```
/code
│── /bin
│── /dane (sample input/output data)
│── /src (source code)
│── Makefile
```

## **How to Run**
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/maze-solver.git
   ```
2. Navigate to the project directory:
   ```bash
   cd maze-solver
   ```
3. Compile the program:
   ```bash
   make
   ```
4. Run the application:
   ```bash
   java -jar MazeSolver.jar
   ```

## **Usage**
1. Load a maze file (`.txt` or `.bin`).
2. Set the start (`P`) and end (`K`) positions.
3. Click "Find Path" to compute the shortest route.
4. Save the result in `.txt`, `.bin`, or `.png`.

## **Example Maze Format**
### **Text Format**
```
XXXXXX
XP  KX
X  XX
XXXXXX
```
### **Binary Format**
Refer to `opis_pliku_binarnego.pdf` for the binary format specification.

## **Contributors**
- **Andrii Rybachok**
- **Oleksandr Chaienko**
