## 🎄 Advent of Code Solution 🎄

Welcome to **Feli's Advent of Code Solution**! 

---

### 🌟 Features

- Solve Advent of Code puzzles for specific years and days.
- Festive user interface with a decorated Christmas tree and emojis.
- Toggle between **Test Mode** and **Real Input Mode**.
- Supports multiple years.
---

### 🌟 Coming soon

- Developer tools for fetching and managing puzzle input.

---

### 🚀 Run in IntelliJ

This project was built and tested in **IntelliJ**. (Emojis currently only work there... 😢)

#### 1. Clone the Repository

```bash
git clone https://github.com/baurf/AdventOfCode.git
```

#### 2. Open the project in IntelliJ
#### 3. Build

- **Using Gradle Wrapper** (no need to install Gradle manually):
  ```bash
  ./gradlew build
  ```

#### 4. Run the Application

- Run the application using the Gradle `run` task:
  ```bash
  ./gradlew run
  ```
---

### 🚀 Run in Git Bash or Windows PowerShell

Emojis still don't work, just ignore the ?? in the output.

#### 1. Clone the Repository and navigate into it

```bash
git clone https://github.com/baurf/AdventOfCode.git
cd AdventOfCode
```

#### 2. Build

- **Using Gradle Wrapper** (no need to install Gradle manually):
  ```bash
  ./gradlew build
  ```

#### 3. Run the Application

- Run the application using the Gradle `run` task:
  ```bash
  ./gradlew run
  ```
---

### 🎄 How to Use

Once the application is running, follow the on-screen prompts to interact with it.

#### Main Menu Options

1. **Solve Daily Puzzle**:
    - Enter the day of the puzzle you want to solve (e.g., `01` for Day 1).
    - The application will show solutions for **Part 1** and **Part 2**.

2. **Change Year**:
    - Switch to a different year (e.g., `2024`, `2023`) if puzzles for that year are available.

3. **Toggle Test Mode**:
    - Enables or disables **Test Mode**, where smaller test inputs are used instead of the full puzzle inputs.

4. **Developer Tools**:
    - Access developer features, like fetching new puzzle inputs (feature in progress).

5. **Quit**:
    - Exit the application.

---

### 🛠 Project Structure

The project is organized as follows:

```plaintext
src/
├── main/
│   ├── java/aoc/
│   │   ├── Main.java           # Main application logic
│   │   ├── common/             # Common utilities and helper classes
│   │   └── year202X/           # Solutions for specific years
│   └── resources/input/        # Input files for puzzles
├── test/                       # Unit tests
```

- **Input Files**:
    - Real inputs are stored under `src/main/resources/input/yearYYYY/real/`.
    - Test inputs are stored under `src/main/resources/input/yearYYYY/test/`.

---

### 🛠️ Troubleshooting

#### Common Issues:

1. **`java : The term 'java' is not recognized`**:
    - Ensure Java 21 is installed and added to your system `PATH`.

2. **Missing Input Files**:
    - If the input file for a specific day is missing, the application will notify you. Add the file manually under:
      ```
      src/main/resources/input/yearYYYY/real/DayXX.txt
      ```

3. **Gradle Issues**:
    - If Gradle is not installed, use the included Gradle Wrapper (`./gradlew`).

---

### ⚠️ Run at Your Own Risk! ⚠️
This project was crafted with good intentions 🎁, but no guarantees! It might:

- Confuse you with non-working emojis (or question marks).
- Not work on every system (Windows PowerShell and IntelliJ are your best friends).
- Crash and burn if you try solving puzzles that don't exist yet.