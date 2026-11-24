
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
    /**
     * Constructs a course with the given details.
     *
     * @param courseID     the course ID
     * @param courseName   the course name
     * @param instructorName the instructor name
     * @param creditHours  the credit hours
     */
    public Course(int courseID, String courseName, String instructorName, int creditHours) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.creditHours = creditHours;
    }

    
    /**
     * Returns the course ID.
     *
     * @return the course ID
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * Sets the course ID.
     *
     * @param courseID the course ID to set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Returns the course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name.
     *
     * @param courseName the course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    /**
     * Returns the instructor name.
     *
     * @return the instructor name
     */
    public String getInstructorName() {
        return instructorName;
    }

    /**
     * Sets the instructor name.
     *
     * @param instructorName the instructor name to set
     */
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    /**
     * Returns the credit hours.
     *
     * @return the credit hours
     */
    public int getCreditHours() {
        return creditHours;
    }

    /**
     * Sets the credit hours.
     *
     * @param creditHours the credit hours to set
     */
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    /**
     * Compares this course to another object for equality based on course ID.
     *
     * @param o the reference object to compare with
     * @return true if the other object is a Course with the same course ID
     */
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

    /**
     * Returns a string representation of this course.
     *
     * @return a string containing course ID, name, instructor, and credit hours
     */
    @Override
    public String toString() {
        return "Course ID: " + courseID
                + ", Course Name: " + courseName
                + ", Instructor: " + instructorName
                + ", Credit Hours: " + creditHours;
    }
}