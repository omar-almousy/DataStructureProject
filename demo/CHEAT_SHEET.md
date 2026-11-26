# Smart Study Planner — Class Responsibilities Cheat Sheet

| Class | Responsibility (one line) | Key methods | Owner | Ask me about |
|-------|---------------------------|-------------|-------|--------------|
| `Course` | One course: 5-digit ID, name, instructor, credit hours | constructor (guards with `Validator`), getters/setters, `equals` (by ID), `toString` | Member 1 | Course fields, equality by ID |
| `StudyTask` | One task: 6-digit ID, title, description, course, deadline, priority 1–3, hours, status | constructor, `setStatus`, `isCompleted`, `isHighPriority`, `compareTo` (priority, then deadline), `toString` | Member 2 | Priority/deadline ordering, status rules |
| `StudySession` | One study session logged to a task, with a duration in hours | logged-session constructor, `getTasks`, `getDurationHours`, `toString` | Member 3 | How a session links to its task |
| `SmartStudyPlannerManager` | Stores everything in three `KWLinkedList`s; enforces data rules | `addCourse`, `addTask`, `logStudySession`, `deleteCourse`, `deleteStudyTask`, `removeSession`, `searchTaskByID`, `updateTaskStatus`, `getSessionsForTask`, `calculateCompletionPercentage`, the `display...` methods | Member 4 | Duplicates, existence checks, delete blocking, return codes |
| `SmartStudyPlannerApplication` | The menu: reads input, validates it, calls the manager, prints results | `main` loop, `handleChoice` (switch), `readValidInt/Double/Line`, one `handle...` per option | Member 4 | Menu loop, try-catch on input |
| `Validator` | Every field rule and its error message, in one place | `isValidCourseID`, `isValidTaskID`, `isValidSessionID`, `isValidDate`, `isValidPriority`, `isValidStatus`, `isPositive`, `isNonEmpty`, `isValidCreditHours` | Member 2 | Date regex + calendar check |
| `OutputFormat` | How numbers are printed: always 2 decimals | `percentage(...)`, `number(...)` | Member 3 | Why percentages never divide by zero |
| `KWLinkedList` | Course doubly linked list (head, tail, size, inner `Node` and `KWListIter`) used for all storage | `add`, `addFirst`, `addLast`, `get`, `remove(index)`, `size`, `isEmpty`, `indexOf`, `listIterator` | Member 4 | Why linked list; how remove works via the iterator |
| `KWArrayList` | Course array list (array that doubles when full) used for a session's tasks and `TaskManager` | `add`, `get`, `set`, `remove`, `indexOf`, `contains`, `size`, `isEmpty` | Member 3 | `reallocate()` doubling capacity |
| `Task`, `TaskManager` | Part B versions of the task model and its manager (not used by the menu) | `addTask`, `calculateCompletionPercentage` | Member 2 | Part B requirements |

## Where each check lives (one rule, one place)

1. **`Validator`** defines every rule and message once.
2. **Application** checks everything the user types, using `Validator`, and prints the message.
3. **Manager** checks only rules that need stored data: duplicate IDs, whether the course/task exists, and delete dependencies. It reports them with return values and never prints validation messages.
4. **Model constructors** reuse `Validator` as a last-line guard (throw `IllegalArgumentException`); the menu never triggers this.

## Rules to quote from memory

| Field | Rule |
|-------|------|
| Course ID | exactly 5 digits (10000–99999) |
| Task / Session ID | exactly 6 digits (100000–999999) |
| Credit hours | 1–6 |
| Deadline | `DD/MM/YYYY` (regex `^\d{2}/\d{2}/\d{4}$`) **and** a real date: rejects 31/13/2026, 1/1/2026, 31/04, 29/02 in non-leap years |
| Priority | 1 = High, 2 = Medium, 3 = Low |
| Status | exactly `Pending`, `In Progress` or `Completed` |
| Hours / duration | greater than 0 |
| Names / titles | not empty |

## Manager return codes

| Method | 0 | 1 | 2 |
|--------|---|---|---|
| `deleteCourse` | deleted | not found | blocked: has tasks |
| `deleteStudyTask` | deleted | not found | blocked: has sessions |
| `logStudySession` | logged | duplicate session ID | task not found |

`calculateCompletionPercentage()` returns **-1** when there are no tasks, so it never divides by zero. The menu then prints "No tasks exist…".

## Coding rules we followed (expect questions)

- No Java collection classes: we use the course's `KWLinkedList` and `KWArrayList` instead of `java.util.LinkedList` / `ArrayList`.
- Indexed `for` loops only. No enhanced for-each.
- All instance fields are `private`.
- Menu dispatch uses a `switch`.
- Every number the user types is parsed inside `try`/`catch`, so typing a letter never crashes the program.
- Every method has a Javadoc comment.
