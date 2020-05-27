package finalize;

public class Teacher {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize.Teacher 被回收了");
    }

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        teacher = null;
        System.gc();
    }
}
