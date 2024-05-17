package com.github.wohaopa.GTNHModify.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.wohaopa.GTNHModify.GTNHModifyMod;
import com.github.wohaopa.GTNHModify.strategies.Strategy;

public class Handlers {

    public static List<String> handlers = Arrays.asList("Furnace", "GT");
    private static final String Suffix = "_RecipesHandler";
    private static final List<Method> methods = new ArrayList<>();

    public static void init() {
        if (!Strategy.prevInit()) return;

        GTNHModifyMod.LOG.info("Start processing the recipe");
        if (methods.isEmpty()) {
            String pkg = Handlers.class.getName()
                .replace("Handlers", "");
            for (String name : handlers) {
                String className = pkg + name + Suffix;
                try {
                    Class<?> clazz = Class.forName(className);
                    IHandler iHandler = clazz.getAnnotation(IHandler.class);
                    if (iHandler != null) {
                        Method method = clazz.getDeclaredMethod(iHandler.value());
                        methods.add(method);
                        GTNHModifyMod.LOG.info("Discovery handler: " + className);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    GTNHModifyMod.LOG.debug("An error occurred while initializing handler. Reason: " + e.getMessage());
                }
            }

            for (Method method : methods) {
                try {
                    GTNHModifyMod.LOG.info(
                        "Invoke handler: " + method.getDeclaringClass()
                            .getName());
                    method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    GTNHModifyMod.LOG.debug("An error occurred while executing handler. Reason: " + e.getMessage());
                }
            }
        }

        Strategy.postInit();
        GTNHModifyMod.LOG.info("Complete processing the recipe");
    }
}
