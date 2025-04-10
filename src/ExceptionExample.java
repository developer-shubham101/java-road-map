public class ExceptionExample {
    public static void main(String[] args) {

        try {
            throw new Exception("exception thrown from try block");
        } catch (Exception ex) {
            System.out.println("Inner catch block handling " + ex.getMessage());
        } finally {
            System.out.println("Inner finally block");
            int x = 3 / 0;
            System.out.println("This line is never reached");
        }

        System.out.println("Outside Catch");

    }
}