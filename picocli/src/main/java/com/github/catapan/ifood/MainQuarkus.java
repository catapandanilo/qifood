package com.github.catapan.ifood;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = { Hello.class, Bye.class })
public class MainQuarkus {

}

@Command(name = "Hello", description = "Show user's name")
class Hello implements Runnable {

    @Option(names = { "-n", "--name" }, description = "User's full name", defaultValue = "Danilo")
    String name;

    @Override
    public void run() {
        System.out.println("Hello.run() " + name);
    }
}

@Command(name = "Bye", description = "Show Bye and user's name")
class Bye implements Runnable {

    @Option(names = { "-n", "--name" }, description = "User's full name", required = true)
    String name;

    @Override
    public void run() {
        System.out.println("Bye.run() " + name);
    }
}
