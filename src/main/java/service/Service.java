package service;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Log
public class Service {

    public void startApplication(String pathToExeFile) {

        try {
            Runtime.getRuntime().exec("cmd /c start " + pathToExeFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // If for starting windows service is needed admin rights then start IDE as administrator
    public void startWindowsService(String serviceName) {

        String[] command = {"cmd.exe", "/c", "sc", "start", serviceName};
        try {
            Runtime.getRuntime().exec(command);

            if(getWindowsServiceStatus(serviceName) == 1) {
                log.info(serviceName + " service is started");
            } else {
                log.info(serviceName + " service is not started");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // If for starting windows service is needed admin rights then start IDE as administrator
    public void stopWindowsService(String serviceName) {

        String[] command = {"cmd.exe", "/c", "sc", "stop", serviceName};
        try {
            Runtime.getRuntime().exec(command);

            if(getWindowsServiceStatus(serviceName) == 0) {
                log.info(serviceName + " service is stoped");
            } else {
                log.info(serviceName + " service is not stoped");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getWindowsServiceStatus(String serviceName) {

        String[] command = {"cmd.exe", "/c", "sc", "query", serviceName, "|", "find", "/C", "\"RUNNING\""};
        try {
            Process process = Runtime.getRuntime().exec(command);
            int status = Integer.parseInt(new String(process.getInputStream().readAllBytes()).trim());
            log.info("Status of the " + serviceName + " service is " + status);
            return status;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Return true if process or service are started
    public boolean getWindowsProcess(String processName) {
        return ProcessHandle.allProcesses()
                .anyMatch(info -> info.info().command().filter(str -> str.contains(processName)).isPresent());
    }

    // Running maven commands
    public void runMavenCommand(String command) {

        String[] commands = {"cmd.exe", "/c", "mvn " + command};

        try {
            AtomicReference<Process> atomicReference = new AtomicReference<>();
            atomicReference.set(Runtime.getRuntime().exec(commands));

            Stream.generate(() -> {
                try {
                    return atomicReference.get().waitFor(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException("!!!! Error !!!!");
                }
            })
                    .limit(10)
                    .takeWhile(a -> a)
                    .findFirst()
                    .orElseThrow();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
