public class ClassExample {
    //global variable
    public static int number = 10;
    int dynamicNumber = 10;

    public ClassExample() {}

    public void functionExample() {
        System.out.println(dynamicNumber); //10
        int dynamicNumber = 0; // local variable
        System.out.println(dynamicNumber);// 0

    }

    public void functionExample2() {
        System.out.println(dynamicNumber); // 10
    }
}
