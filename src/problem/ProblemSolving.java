package problem;

public class ProblemSolving {

    public static void main(String[] args) {

       /* System.out.println(factorial(5));


        int number = 1243;
        boolean isPrime = isPrime(number);
        if (isPrime) {
            System.out.println(number + " is a prime number.");
        } else {
            System.out.println(number + " is not a prime number.");
        }*/


        int[] arr = {64, 25, 12, 22, 11};
        bubbleSort(arr);
        System.out.println();
        System.out.println("Sorted array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }


    public static void bubbleSort(int[] arr) {
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int iValue = arr[i];
                    arr[i] = arr[j];
                    arr[j] = iValue;
                }
                System.out.print(arr[j] + " | ");
            }
        }

    }


    static int factorial(int value) {
        if (value == 0)
            return 1;

        return value * factorial(value - 1);
    }

    static boolean isPrimeX(int n) {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        System.out.println(Math.sqrt(n));
        for (int i = 2; i <= Math.sqrt(n); i++) {
            System.out.println(i);
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
