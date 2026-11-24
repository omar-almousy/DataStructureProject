# Code Review Checklist — Part E & Readiness

## Zero Enhanced For Loops

**Status: PASS**

Grep for `for (.* : .* )` returned zero results across all Java files. All iterations use indexed loops:

```
for (int i = 0; i < list.size(); i++)
```

Files verified:
- `Course.java` — no loops
- `StudyTask.java` — no loops
- `StudySession.java` — indexed loop in `toString()` (line 125)
- `Task.java` — no loops
- `TaskManager.java` — indexed loops only
- `SmartStudyPlannerManager.java` — indexed loops only
- `SmartStudyPlannerApplication.java` — indexed loops only

## Zero Non-Private Instance Fields

**Status: PASS**

All instance variables in all classes are `private`:

| Class | Fields (all private) |
|-------|---------------------|
| `Course` | courseID, courseName, instructorName, creditHours |
| `StudyTask` | taskId, title, description, course, dueDate, priority, estimatedHours, completed |
| `StudySession` | sessionId, date, startTime, endTime, course, tasks, notes |
| `Task` | taskID, taskTitle, courseID, deadline, priority, status |
| `TaskManager` | tasks, validCourseIDs |
| `SmartStudyPlannerManager` | courses, tasks, sessions, courseMap, tasksByCourse |

## Header Present on All Files

**Status: PASS**

All Java source files include the group member header comment block:

```
/*
 * Course: [COURSE_CODE]
 * Section: [SECTION]
 * Project: Smart Study Planner
 * Group Members:
 *   [Member 1 Name] - [Member 1 ID]
 *   [Member 2 Name] - [Member 2 ID]
 *   [Member 3 Name] - [Member 3 ID]
 *   [Member 4 Name] - [Member 4 ID]
 */
```

Files: `Course.java`, `StudyTask.java`, `StudySession.java`, `Task.java`, `TaskManager.java`, `SmartStudyPlannerManager.java`, `SmartStudyPlannerApplication.java`

## Switch Statement for Menu Dispatch

**Status: PASS**

`SmartStudyPlannerApplication.java` uses a `switch (choice)` statement for menu dispatch (Options 0, 11, 12).

## Try-Catch Covers All Numeric Parsing

**Status: PASS**

`StudySession.getDurationHours()` wraps `Integer.parseInt()` calls in a try-catch block that returns `0.0` on failure.

## Meaningful Comments on Every Method

**Status: PASS**

Every method in all classes has a Javadoc comment describing its purpose, parameters, and return value.

## Acceptance Criteria — Part E

| Criterion | Status |
|-----------|--------|
| Non-positive duration rejected | PASS — `logStudySession` returns failure for `durationHours <= 0` |
| Unknown task ID rejected | PASS — `getTaskById` returns null → "Failure: task not found." |
| Duplicate session ID rejected | PASS — `getSessionById` check → "Failure: duplicate session ID." |
| Success message confirms task | PASS — includes task title and ID |
| Option 11: two different failure messages | PASS — "Task not found." vs "No sessions recorded for this task." |
| Option 12: no division by zero | PASS — returns -1 sentinel when empty |
| Option 12: 2-decimal formatting | PASS — `%.2f%%` |
| Values: 0/3 → 0.00%, 3/3 → 100.00%, 2/3 → 66.67% | PASS |

## Member Sign-Off

| Member | Role | Signature | Date |
|--------|------|-----------|------|
| [Member 1 Name] | Course Management | ___________________ | ___________ |
| [Member 2 Name] | Task Management | ___________________ | ___________ |
| [Member 3 Name] | Session Management | ___________________ | ___________ |
| [Member 4 Name] | Manager & Application | ___________________ | ___________ |
