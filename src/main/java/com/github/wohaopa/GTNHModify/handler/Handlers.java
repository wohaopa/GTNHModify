package com.github.wohaopa.GTNHModify.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class Handlers {

    public static List<String> handlers = Arrays.asList("Furnace", "GT");
    private static final String Suffix = "_RecipesHandler";

    public static void init() {
        String pkg = Handlers.class.getName()
            .replace("Handlers", "");
        for (String name : handlers) {
            String className = pkg + name + Suffix;
            try {
                Class<?> clazz = Class.forName(className);
                IHandler iHandler = clazz.getAnnotation(IHandler.class);
                if (iHandler != null) {
                    clazz.getDeclaredMethod(iHandler.value())
                        .invoke(null);
                }
            } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException
                | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
