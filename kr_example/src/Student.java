public class Student implements Comparable<Student>{
    private String name;
    private int age;
    private double grade;

    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public Student() {

    }

    public static double convert_grade(Student other) {
        return 100 * other.grade;
    }

    @Override
    public int compareTo(Student other) {
       return name.compareTo(other.name);
    }
    @Override
    public String toString() {
        return "Student{name=" + name + " age=" + age + " grade=" + grade + "}";
    }
}
