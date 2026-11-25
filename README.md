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
   javac *.javaa
   ```

3. Run the application:
   ```
   java SmartStudyPlannerApplication1
   ```

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
- `README.md` — This file