
public class Course {
    // Private instance variables
    private int courseID;
    private String courseName;
    private String instructorName;
    private int creditHours;
    public Course(int courseID, String courseName, String instructorName, int creditHours) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.creditHours = creditHours;
    }

    
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Returns the course name.
     */
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public String getInstructorName() {
        return instructorName;
    }

    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Course)) {
            return false;
        }
        Course other = (Course) o;
        return this.courseID == other.courseID;
    }

    @Override
    public String toString() {
        return "Course ID: " + courseID
                + ", Course Name: " + courseName
                + ", Instructor: " + instructorName
                + ", Credit Hours: " + creditHours;
    }
}