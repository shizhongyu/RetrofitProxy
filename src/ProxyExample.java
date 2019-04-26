
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyExample {
    public static void main(String[] args) {
        // Setup... this is where the magic happens...
        ApiInterface service = (ApiInterface) Proxy.newProxyInstance(
                ApiInterface.class.getClassLoader(),
                new Class[]{ ApiInterface.class },
                (proxy, method, args1) -> {
                    // we control what happens when the method is called (invoked), we get all the necessary information
                    System.out.println("invoking method "+method.getName()+" with arguments "+ Arrays.toString(args1));
                    // this next part is just to show how you can manipulate return value
                    if (args1.length > 0 && args1[0] instanceof String) {
                        return ((String) args1[0]).toUpperCase();
                    }
                    return null;
                }
        );

        // CLIENT CODE we call both methods in the ApiInterface
        System.out.println("returned: " + service.getSomeStringFromServer("Hello"));
        service.doSomeOtherThing(4.5d);
    }

    interface ApiInterface {
        String getSomeStringFromServer(String parameter);
        void doSomeOtherThing(double otherParam);
    }
}