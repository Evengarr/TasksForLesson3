import Interfaces.AfterSuite;
import Interfaces.BeforeSuite;
import Interfaces.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class Tester {
    public static void start(Class c) throws InvocationTargetException, IllegalAccessException {
        Method beforeMethod = null;
        Method afterMethod = null;
        ArrayList<Method> testMethod = new ArrayList<>();

        Object object = null;
        try {
            object = c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethod.add(method);
            } else if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod == null) {
                    beforeMethod = method;
                } else {
                    throw new RuntimeException("Должно быть не больше одного метода с аннотацией @BeforeSuite");
                }
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) {
                    afterMethod = method;
                } else {
                    throw new RuntimeException("Должно быть не больше одного метода с аннотацией @AfterSuite");
                }
            }
        }

        if (beforeMethod != null) {
            beforeMethod.invoke(object);
        }

        testMethod.sort(Comparator.comparing(o -> o.getAnnotation(Test.class).priority()));
        for (Method method : testMethod) {
            method.invoke(object);
        }

        if (afterMethod != null) {
            afterMethod.invoke(object);
        }
    }
}
