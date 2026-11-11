package bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl;

import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.StatisticsDashboard;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.exception.CourseNotEnlistedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatisticsDashboardImpl implements StatisticsDashboard {
    private Map<String, CourseEnrollment> courses;
    private Map<String, Student> students;

    public StatisticsDashboardImpl(Map<String, CourseEnrollment> courses, Map<String, Student> students) {
        this.courses = courses;
        this.students = students;
    }

    @Override
    public Set<Course> getMostContestedCourses() {
        List<CourseEnrollment> courseEnrollments =
            new ArrayList<>(courses.values());

        courseEnrollments.sort(this::compareEnrollments);

        Set<Course> result = new LinkedHashSet<>();

        for (CourseEnrollment enrollment : courseEnrollments) {
            result.add(enrollment.course());
        }

        return Collections.unmodifiableSet(result);
    }

    private int compareEnrollments(CourseEnrollment o1, CourseEnrollment o2) {
        int result = Integer.compare(o2.waitlistCount(), o1.waitlistCount());

        if (result != 0) {
            return result;
        }

        result = Integer.compare(o2.seatedCount(), o1.seatedCount());

        if (result != 0) {
            return result;
        }

        return o1.course().title().compareTo(o2.course().title());
    }

    @Override
    public List<Student> getBusiestStudents() {
        Map<Student, Integer> studentCourseCount = new HashMap<Student, Integer>();

        for (Student student: students.values()) {
            int count = 0;

            for (CourseEnrollment enrollment: courses.values()) {
                if (enrollment.seatedIds().contains(student.facultyNumber())) {
                    count++;
                }
            }
            studentCourseCount.put(student, count);
        }

        List<Student> result = createResult(studentCourseCount);

        return Collections.unmodifiableList(result);
    }

    private List<Student> createResult(Map<Student, Integer> studentCourseCount) {
        List<Student> result = new ArrayList<Student>(students.values());

        result.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                int count1 = studentCourseCount.get(o1);
                int count2 = studentCourseCount.get(o2);

                int comparison = Integer.compare(count2, count1);

                if (comparison != 0) {
                    return comparison;
                }

                return o1.facultyNumber().compareTo(o2.facultyNumber());
            }
        });
        return result;
    }

    @Override
    public Map<String, Integer> countStudentsPerCourse() {
        Map<String, Integer> result = new HashMap<String, Integer>();

        for (CourseEnrollment enrollment: courses.values()) {
            result.put(enrollment.course().code(), enrollment.seatedCount());
        }

        return Collections.unmodifiableMap(result);
    }

    @Override
    public Set<Student> getEnrolledStudents(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course cannot be null or blank");
        }

        if (!courses.containsKey(courseCode)) {
            throw new CourseNotEnlistedException("Course is not enlisted");
        }

        Set<Student> result = new HashSet<Student>();
        CourseEnrollment courseEnrollment = courses.get(courseCode);

        for (String facultyNumber: courseEnrollment.seatedIds()) {
            Student student = students.get(facultyNumber);
            result.add(student);
        }

        return Collections.unmodifiableSet(result);
    }

    @Override
    public List<Student> getWaitlist(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course cannot be null or blank");
        }

        if (!courses.containsKey(courseCode)) {
            throw new CourseNotEnlistedException("Course is not enlisted");
        }

        List<Student> result = new ArrayList<Student>();
        CourseEnrollment courseEnrollment = courses.get(courseCode);

        for (String facultyNumber: courseEnrollment.waitlistIds()) {
            Student student = students.get(facultyNumber);
            result.add(student);
        }

        return Collections.unmodifiableList(result);
    }
}
