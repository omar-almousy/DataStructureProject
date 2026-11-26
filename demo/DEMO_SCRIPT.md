# Smart Study Planner — Demo Script

The demo is half the grade, so run it exactly in this order. Every success and
failure message in the program appears at least once, and nothing crashes.

- **Time:** about 10–12 minutes live.
- **Start from a fresh run**, because the first steps show the empty-list messages.
- **Type exactly what is in the "Type" column.** `(blank)` means press Enter on an empty line.
- **Speakers:** the person named in each section's heading drives the keyboard and explains.
  Roles match the README's Team Roles table. If your team has three members,
  Member 4's sections go to whoever owns `SmartStudyPlannerManager` + `SmartStudyPlannerApplication`.

---

## 0. Before the demo (any member, 1 minute)

Open PowerShell in the project folder:

```powershell
cd "C:\Users\amier\OneDrive\Desktop\DSprjct\DataStructureProject"
```

If `java -version` does not say 24, run this first (fixes the Java 8 clash):

```powershell
$env:Path = "C:\Users\amier\.jdks\openjdk-24.0.1\bin;" + $env:Path
```

Compile and start:

```powershell
javac *.java
```

```powershell
java SmartStudyPlannerApplication
```

**Say:** "This is the Smart Study Planner. It manages courses, study tasks and
study sessions with linked lists, and every input is validated."

---

## 1. Empty lists on a fresh run — Member 4 (Manager & Application)

| # | Menu | Type | Expected output | What it proves |
|---|------|------|-----------------|----------------|
| 1 | 14 | `14` | `No tasks exist, so there is no completion percentage yet.` | Percentage avoids division by zero |
| 2 | 8 | `8` | `No courses available.` | Empty course list message |
| 3 | 9 | `9` | `No study tasks available.` | Empty task list message |
| 4 | 10 | `10` | `No high-priority incomplete tasks found.` | Empty high-priority message |
| 5 | 11 | `11` | `No sessions available.` | Empty session list message |

**Say:** "Every list prints a message instead of a blank screen."

---

## 2. Courses — Member 1 (Course Management)

| # | Menu | Type (one per prompt) | Expected output | What it proves |
|---|------|------------------------|-----------------|----------------|
| 6 | 1 | `1`, `1234` | `Invalid course ID: must be exactly 5 digits (10000-99999).` | Five-digit ID rule |
| 7 | 1 | `1`, `10101`, `(blank)` | `Invalid course name: cannot be empty.` | Non-empty name |
| 8 | 1 | `1`, `10101`, `Data Structures`, `(blank)` | `Invalid instructor name: cannot be empty.` | Non-empty instructor |
| 9 | 1 | `1`, `10101`, `Data Structures`, `Dr. Sara`, `9` | `Invalid credit hours: must be a whole number between 1 and 6.` | Credit hours 1–6 |
| 10 | 1 | `1`, `10101`, `Data Structures`, `Dr. Sara`, `3` | `Course 10101 added successfully.` | **Course 1 added** |
| 11 | 1 | `1`, `20202`, `Calculus`, `Dr. Ali`, `4` | `Course 20202 added successfully.` | **Course 2 added** |
| 12 | 1 | `1`, `10101`, `Copy Course`, `Dr. X`, `3` | `Failed to add course: duplicate ID 10101 already exists.` | **Duplicate rejected by the manager** |
| 13 | 8 | `8` | Both courses listed | `Course.toString()` |

**Say:** "Each field is checked as it is typed, using one shared `Validator`
class. The duplicate check needs the stored data, so the manager does it."

---

## 3. Tasks — Member 2 (Task Management)

All task attempts use task ID `100001` until one succeeds.

| # | Menu | Type (one per prompt) | Expected output | What it proves |
|---|------|------------------------|-----------------|----------------|
| 14 | 5 | `5`, `999` | `Invalid task ID: must be exactly 6 digits (100000-999999).` | Six-digit ID rule |
| 15 | 5 | `5`, `100001`, `(blank)` | `Invalid title: cannot be empty.` | Non-empty title |
| 16 | 5 | `5`, `100001`, `Linked lists`, `Implement a singly linked list`, `55555` | `Unknown course: no course exists with ID 55555.` | **Unknown course** |
| 17 | 5 | …same…, `10101`, `31/13/2026` | `Invalid date: must be a real date in DD/MM/YYYY format (e.g. 05/03/2026).` | **Bad date** (month 13) |
| 18 | 5 | …same…, `10101`, `1/1/2026` | same date message | Bad date (wrong shape) |
| 19 | 5 | …same…, `10101`, `15/12/2026`, `5` | `Invalid priority: must be 1 (High), 2 (Medium), or 3 (Low).` | **Bad priority** |
| 20 | 5 | …same…, `15/12/2026`, `1`, `Done` | `Invalid status: must be Pending, In Progress, or Completed.` | Status whitelist |
| 21 | 5 | …same…, `1`, `Pending`, `0` | `Invalid estimated hours: must be a number greater than 0.` | Positive number |
| 22 | 5 | `5`, `100001`, `Linked lists`, `Implement a singly linked list`, `10101`, `15/12/2026`, `1`, `Pending`, `3` | `Task 100001 added successfully to course 10101.` | **Task 1 added** |
| 23 | 5 | `5`, `100002`, `Stacks quiz`, `Revise stacks and queues`, `10101`, `20/12/2026`, `1`, `In Progress`, `2` | `Task 100002 added successfully to course 10101.` | **Task 2 added** |
| 24 | 5 | `5`, `100003`, `Derivatives`, `Practice sheet 4`, `20202`, `10/12/2026`, `2`, `Pending`, `1.5` | `Task 100003 added successfully to course 20202.` | **Task 3 added** |
| 25 | 5 | `5`, `100001`, `Duplicate task`, `Should be rejected`, `10101`, `15/12/2026`, `1`, `Pending`, `1` | `Failed to add task: duplicate ID 100001 already exists.` | Duplicate task |
| 26 | 9 | `9` | All 3 tasks, each with description, course, deadline, priority, status | Full task listing |
| 27 | 14 | `14` | `Completion percentage: 0.00%` | Percentage **before** completing anything |

**Say:** "The date check uses a regex for the DD/MM/YYYY shape, then checks it is
a real calendar date, so 31/13 and 1/1/2026 are both rejected."

---

## 4. Study sessions — Member 3 (Session Management)

| # | Menu | Type (one per prompt) | Expected output | What it proves |
|---|------|------------------------|-----------------|----------------|
| 28 | 7 | `7`, `12` | `Invalid session ID: must be exactly 6 digits (100000-999999).` | Six-digit session ID |
| 29 | 7 | `7`, `500001`, `100001`, `0` | `Failure: duration must be a number greater than 0.` | **Non-positive duration** |
| 30 | 7 | `7`, `500001`, `999999`, `2` | `Failure: task not found.` | Unknown task |
| 31 | 7 | `7`, `500001`, `100001`, `1.5` | `Session 500001 logged successfully: 1.50 hour(s) on task 100001 (Linked lists).` | **Session 1 added**, names its task |
| 32 | 7 | `7`, `500002`, `100002`, `2` | `Session 500002 logged successfully: 2.00 hour(s) on task 100002 (Stacks quiz).` | **Session 2 added** |
| 33 | 7 | `7`, `500001`, `100003`, `1` | `Failure: duplicate session ID 500001.` | **Duplicate session ID** |
| 34 | 11 | `11` | Both sessions, each with its task | Session listing |

**Say:** "Hours are always shown with 2 decimals through one `OutputFormat` class."

---

## 5. Status, search and high priority — Member 2 (Task Management)

| # | Menu | Type | Expected output | What it proves |
|---|------|------|-----------------|----------------|
| 35 | 3 | `3`, `999999` | `Task not found.` | Update on a missing task |
| 36 | 3 | `3`, `100002`, `Completed` | `Status updated successfully.` | **Status → Completed** |
| 37 | 14 | `14` | `Completion percentage: 33.33%` | 1 of 3 completed, 2 decimals |
| 38 | 4 | `4`, `100001` | The full `Task[...]` line | **Search hit** |
| 39 | 4 | `4`, `123456` | `no matching task` | **Search miss** |
| 40 | 10 | `10` | Only task 100001 | High-priority **incomplete** only: 100002 is Completed, 100003 is Medium |

---

## 6. Deleting with dependency rules — Member 4 (Manager & Application)

| # | Menu | Type | Expected output | What it proves |
|---|------|------|-----------------|----------------|
| 41 | 6 | `6`, `100001` | `Cannot delete task - has sessions.` | **Blocked**: session 500001 uses it |
| 42 | 6 | `6`, `100003` | `Task deleted successfully.` | **Deleted**: it has no session |
| 43 | 6 | `6`, `100003` | `Task not found.` | Deleting it again |
| 44 | 14 | `14` | `Completion percentage: 50.00%` | Now 1 of 2 completed |
| 45 | 2 | `2`, `10101` | `Cannot delete course - has tasks.` | **Course blocked** by its tasks |
| 46 | 2 | `2`, `30303` | `Course not found.` | Missing course |
| 47 | 2 | `2`, `20202` | `Course deleted successfully.` | Its only task was deleted in step 42 |

**Say:** "There is no cascade delete. The manager refuses to delete anything
that something else still depends on."

---

## 7. Sessions for a task — Member 3 (Session Management)

| # | Menu | Type | Expected output | What it proves |
|---|------|------|-----------------|----------------|
| 48 | 13 | `13`, `100001` | `Sessions recorded for task 'Linked lists' (ID: 100001):` + session 500001 | **Hit** |
| 49 | 12 | `12`, `500002` | `Session deleted successfully.` | Delete a session |
| 50 | 12 | `12`, `500002` | `Session not found.` | Delete it again |
| 51 | 13 | `13`, `100002` | `No sessions recorded for this task.` | **Miss**: the task exists but has no sessions |
| 52 | 13 | `13`, `777777` | `Task not found.` | **Task not found**, a different message from the miss |

---

## 8. Finish — Member 1 (Course Management)

| # | Menu | Type | Expected output | What it proves |
|---|------|------|-----------------|----------------|
| 53 | 3 | `3`, `100001`, `Completed` | `Status updated successfully.` | Complete the last task |
| 54 | 14 | `14` | `Completion percentage: 100.00%` | Percentage **after** completing everything |
| 55 | 10 | `10` | `No high-priority incomplete tasks found.` | List empties as tasks complete |
| 56 | menu | `x` | `Invalid input. Please enter a number.` | **try-catch** on non-numeric input; no crash |
| 57 | menu | `99` | `Invalid choice. Please select 0-14.` | Out-of-range option |
| 58 | menu | `0` | `Goodbye!` | Clean exit |

---

## Rehearsal: run the whole demo yourself in 10 seconds

Every member should do this on their own laptop before the demo. It feeds all
the inputs above into the program automatically and checks the output against
the saved expected output.

From the project folder in PowerShell (after the Java 24 PATH step above):

```powershell
javac *.java
```

```powershell
Get-Content demo\demo_input.txt | java SmartStudyPlannerApplication | Out-File demo\my_output.txt -Encoding utf8
```

```powershell
Compare-Object (Get-Content demo\expected_output.txt) (Get-Content demo\my_output.txt)
```

**No output from `Compare-Object` means your run matches exactly.** Delete
`demo\my_output.txt` afterwards. It is only for checking, so don't commit it.

In Command Prompt instead of PowerShell:

```
java SmartStudyPlannerApplication < demo\demo_input.txt
```

### Rehearsal checklist

- [ ] Every member has run the automatic check above, and it matched.
- [ ] Every member has typed their own sections live at least once.
- [ ] Each speaker can explain their class in 30 seconds without notes (see `CHEAT_SHEET.md`).
- [ ] One full timed run-through: target under 12 minutes.
- [ ] Decide who answers questions about each class (see the cheat sheet's "Ask me about" column).

### Note on coverage

The demo shows all 45 distinct messages in the program except one:
`Unexpected error.`. That is the fallback branch of the delete switches. The
manager only ever returns the codes handled above, so it cannot be reached from
the menu. If asked, explain that it is defensive code.
