package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.MayTheCourseBeWithYouAPI;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.StatisticsDashboard;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseAlreadyEnrolledException;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseNotEnlistedException;

import java.util.HashMap;
import java.util.Map;

public class MayTheCourseBeWithYou  implements MayTheCourseBeWithYouAPI {
    private Map<String, CourseEnrollment> courses;
    private Map<String, Student> students;

    public MayTheCourseBeWithYou() {
        this.courses = new HashMap<String, CourseEnrollment>();
        this.students = new HashMap<String, Student>();
    }

    @Override
    public void enlistCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        if (courses.containsKey(course.code())) {
            throw new CourseAlreadyEnrolledException("Course is already enrolled");
        }

        CourseEnrollment courseEnrollment = new CourseEnrollment(course);
        courses.put(course.code(), courseEnrollment);
    }

    @Override
    public Course delistCourse(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course Code cannot be null or blank");
        }

        if (!courses.containsKey(courseCode)) {
            throw new CourseNotEnlistedException("Course is not enlisted");
        }

        CourseEnrollment delistedCourse = courses.get(courseCode);
        courses.remove(courseCode);

        return delistedCourse.course();
    }

    @Override
    public boolean enroll(Student student, String courseCode) {
        if (student == null || courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Params cannot be null or blank");
        }

        if (!courses.containsKey(courseCode)) {
            throw new CourseNotEnlistedException("Course is not enlisted");
        }

        CourseEnrollment courseEnrollment = courses.get(courseCode);

        students.put(student.facultyNumber(), student);

        return courseEnrollment.enroll(student.facultyNumber());
    }

    @Override
    public boolean drop(Student student, String courseCode) {
        if (student == null || courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Params cannot be null or blank");
        }

        if (!courses.containsKey(courseCode)) {
            throw new CourseNotEnlistedException("Course is not enlisted");
        }

        CourseEnrollment courseEnrollment = courses.get(courseCode);

        return courseEnrollment.drop(student.facultyNumber());
    }

    @Override
    public StatisticsDashboard getStatisticsDashboard() {
        return new StatisticsDashboardImpl(courses, students);
    }
}
