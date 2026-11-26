# Smart Study Planner

## Project Description
A Java-based Smart Study Planner that manages courses, study tasks, and study sessions.
Uses ArrayList, HashMap, and PriorityQueue data structures for efficient organization.

## How to Run

1. Open a terminal/command prompt in the project folder:
   ```
   cd C:\Users\amier\OneDrive\Desktop\DSprjct\DataStructureProject
   ```

2. Compile all Java files:
   ```
   javac *.java
   ```

3. Run the application:
   ```
   java SmartStudyPlannerApplication
   ```

## Validation Design

Each kind of check has exactly one home, and every option follows the same split:

| Layer | What it checks | How it reports |
|-------|----------------|----------------|
| `Validator` | Defines every field rule once: five-digit course ID, six-digit task/session ID, `DD/MM/YYYY` real date (regex `^\d{2}/\d{2}/\d{4}$` + calendar check), priority 1–3, status whitelist (`Pending`, `In Progress`, `Completed`), positive number, non-empty text, credit hours 1–6. | Returns true/false; holds the user-facing message for each rule. |
| `SmartStudyPlannerApplication` (app layer) | Checks **everything the user types**, field by field, through the shared `readValid...` helpers using `Validator` rules. | Prints the `Validator` message and returns to the menu. |
| `SmartStudyPlannerManager` (manager layer) | Only rules that need stored data: duplicate IDs, whether a referenced course/task exists, delete dependencies. | Return values (boolean / status codes); never prints validation messages. |
| Model constructors (`Course`, `StudyTask`, `StudySession`) | Re-use `Validator` rules as a last-line guard. | Throw `IllegalArgumentException`; never reached through the menu. |

Number output goes through `OutputFormat`: percentages always show 2 decimals (`66.67%`), and hours/durations show 2 decimals with no currency symbol or thousands separator (`1.50`).

## Submission Instructions
- Submit only the `.java` source files.
- Do **not** submit `.class` files or any `.zip` archive.
- Files must be uncompressed.

## Team Roles

| Member | Role | Responsibility |
|--------|------|----------------|
| [Member 1 Name] | Course Management | Course.java — course data model |
| [Member 2 Name] | Task Management | StudyTask.java — task data model with priority ordering |
| [Member 3 Name] | Session Management | StudySession.java — session data model and scheduling |
| [Member 4 Name] | Manager & Application | SmartStudyPlannerManager.java + SmartStudyPlannerApplication.java — business logic and main entry point |

## Files
- `Course.java` — Course entity
- `StudyTask.java` — Study task entity (Comparable)
- `StudySession.java` — Study session entity
- `SmartStudyPlannerManager.java` — Manager using ArrayList, HashMap, PriorityQueue
- `SmartStudyPlannerApplication.java` — Main application entry point
- `Validator.java` — Every field rule and its error message, in one place
- `OutputFormat.java` — Number and percentage display (2 decimals), in one place
- `README.md` — This file