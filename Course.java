
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
     * Returns the course code used as the key in course maps.
     */
    public String getCourseCode() {
        return String.valueOf(courseID);
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
