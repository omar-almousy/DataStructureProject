public class StudySession {

    private String subject;
    private String task;
    private int priority;
    private String deadline;
    private String status;
    
    public StudySession(String subject, String task, int priority,
                        String deadline, String status) {
        this.subject = subject;
        this.task = task;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public String getTask() {
        return task;
    }

    public int getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String level;
        

        if (priority == 1) {
            level = "High";
        } else if (priority == 2) {
            level = "Medium";
        } else {
            level = "Low";
        }

        return "Subject: " + subject +
                "\nTask: " + task +
                "\nPriority: " + priority + " (" + level + ")" +
                "\nDeadline: " + deadline +
                "\nStatus: " + status;
    }
}
