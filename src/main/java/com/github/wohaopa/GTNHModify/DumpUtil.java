package com.github.wohaopa.GTNHModify;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.launchwrapper.Launch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.wohaopa.GTNHModify.dumpers.Dumper;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DumpUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
        .create();
    private static final File dumpDir = new File(Launch.minecraftHome, "GTNHModifyDump");
    private static final Log log = LogFactory.getLog(DumpUtil.class);
    private static DumpThread dumpThread;
    static {
        if (!dumpDir.isDirectory()) {
            dumpDir.delete();
        }
        if (!dumpDir.exists()) {
            dumpDir.mkdir();
        }

    }

    public static void writeFile(String filename, Object object) {
        try {
            Files.asCharSink(new File(dumpDir, filename), StandardCharsets.UTF_8)
                .write(gson.toJson(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void submitTask(Dumper task) {
        if (DumpThread.running) return;
        if (dumpThread == null) dumpThread = new DumpThread();

        dumpThread.addTask(task);
    }

    public static void completeSubmitTask() {
        dumpThread.start();
    }

    private static class DumpThread extends Thread {

        static boolean running = false;
        private final List<Runnable> tasks = new LinkedList<>();

        public DumpThread() {
            setName("DumpThread");
        }

        public void addTask(Runnable task) {
            synchronized (tasks) {
                tasks.add(task);
            }
        }

        @Override
        public void run() {
            if (running) return;
            running = true;
            if (!tasks.isEmpty()) {
                for (Runnable task : tasks) task.run();
            }
            running = false;
        }

    }
}
