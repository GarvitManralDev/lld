public class SingletonClass {
    private static SingletonClass instance;
    private final int someField;

    private SingletonClass(){
        someField = 10;
    }

    public static synchronized SingletonClass getInstance(){
        if(instance == null){
           instance = new SingletonClass();
        }
        return instance;
    }

    public int getSomeField(){
        return someField;
    }
}
