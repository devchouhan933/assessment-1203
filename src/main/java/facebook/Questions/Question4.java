package facebook.Questions;


/*
What is a singleton class, and how you can implement a Singleton class both lazy and eager loading and
explaining the pros and cons of each.

Singleton class:
A singleton class is a class that can have only one object (an instance of the class) at a time. After the first time,
if we try to instantiate the Singleton class, the new variable also points to the first instance created. So whatever
modifications we do to any variable inside the class through any instance, affects the variable of the single instance
created and is visible if we access that variable through any variable of that class type defined.

To create a singleton class, a class must implement the following properties:

* Create a private constructor of the class to restrict object creation outside the class.
* Create a public static method that allows us to create and access the object we created. Inside the method, we will
create a condition that restricts us from creating more than one object.

Eager Loading: The object creation takes place at the load time.
Lazy Loading: The object creation is done according to the requirement when the getInstance method is called.

pros and cons:
Eager Loading:
– Pro: Small chance for lag when the application is running (everything is already in memory)
– Con: The time it takes to start the application is longer, as everything needs to be loaded

Lazy Loading:
– Pro: Your application loads faster, the user gets in quicker
– Con: When moving to new sections there can be some lag when things are getting loaded into memory
 */
public class Question4 {
    public static void main(String[] args) {
        LazyLoading lazySingleton = LazyLoading.getInstance();
        LazyLoading lazySingleton1 = LazyLoading.getInstance();
        System.out.println(lazySingleton.hashCode() + " , " + lazySingleton1.hashCode());

        EagerLoading eagerSingleton = EagerLoading.getInstance();
        EagerLoading eagerSingleton1 = EagerLoading.getInstance();
        System.out.println(eagerSingleton.hashCode() + " , " + eagerSingleton1.hashCode());
    }

}

class LazyLoading {
    private static LazyLoading singleton = null;

    private LazyLoading() {
    }

    public static LazyLoading getInstance() {
        if (singleton == null)
            singleton = new LazyLoading();
        return singleton;
    }
}

class EagerLoading {
    private static final EagerLoading singleton = new EagerLoading();

    private EagerLoading() {
    }

    public static EagerLoading getInstance() {
        return singleton;
    }
}

