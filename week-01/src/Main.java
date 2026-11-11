import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.MayTheCourseBeWithYouAPI;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.StatisticsDashboard;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Course;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Department;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.entity.Student;
import bg.sofia.uni.fmi.mjt.maythecoursebewithyou.impl.MayTheCourseBeWithYou;

public class Main {
    @SuppressWarnings("checkstyle:MethodLength")
    public static void main(String[] args) {
        MayTheCourseBeWithYouAPI app = new MayTheCourseBeWithYou();

        Course mjt = new Course(
            "MJT",
            "Modern Java Technologies",
            Department.COMPUTER_SCIENCE,
            2
        );

        app.enlistCourse(mjt);

        Student ivan = new Student("1", "Иван");
        Student mariya = new Student("2", "Мария");
        Student petar = new Student("3", "Петър");

        System.out.println(app.enroll(ivan, "MJT"));   // true
        System.out.println(app.enroll(mariya, "MJT")); // true
        System.out.println(app.enroll(petar, "MJT"));  // false - няма места

        StatisticsDashboard dashboard = app.getStatisticsDashboard();

        System.out.println("Записани студенти:");
        System.out.println(dashboard.getEnrolledStudents("MJT"));

        System.out.println("Списък на чакащите:");
        System.out.println(dashboard.getWaitlist("MJT"));

        app.drop(mariya, "MJT");

        System.out.println("След отписването на Мария:");

        System.out.println("Записани студенти:");
        System.out.println(dashboard.getEnrolledStudents("MJT"));

        System.out.println("Списък на чакащите:");
        System.out.println(dashboard.getWaitlist("MJT"));
    }
}