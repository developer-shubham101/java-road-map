package inheritance;


class A {


    void methodA() {
        System.out.println("This is method A");
    }
}

class B extends A {
    void methodA() {
        System.out.println("This is method B");
    }

    void methodB() {
        System.out.println("This is method B");
    }
}

public class InheritanceExample {
    public static void main(String[] args) {
        A a = new B();
        ((B) a).methodB();
    }
}
