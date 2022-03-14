package facebook.Questions;
/*
Q1 Explain the difference between Lazy Loading and Eager loading and give an example.

Eager loading initializes or loads a resource as soon as the code is executed. Eager loading also involves pre-loading
related entities referenced by a resource.
while Lazy loading delays the initialization of a resource i.e. when the getInstance method is called then only the
resource is loaded.

In Eager loading there might be chances of wastage of resources as it is loaded when the code is executed but in lazy
loading the resources are created when there is need and helps in saving resources.


 */

public class Question1 {
    public static void main(String[] args) {
        LazySingleton lazySingleton = LazySingleton.getInstance();
        LazySingleton lazySingleton1 = LazySingleton.getInstance();
        System.out.println(lazySingleton.hashCode() + " , " + lazySingleton1.hashCode());

        EagerLoading eagerSingleton = EagerLoading.getInstance();
        EagerLoading eagerSingleton1 = EagerLoading.getInstance();
        System.out.println(eagerSingleton.hashCode() + " , " + eagerSingleton1.hashCode());
    }

}

class LazySingleton {
    private static LazySingleton singleton = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (singleton == null)
            singleton = new LazySingleton();
        return singleton;
    }
}

class EagerSingleton {
    private static final EagerSingleton singleton = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return singleton;
    }
}
