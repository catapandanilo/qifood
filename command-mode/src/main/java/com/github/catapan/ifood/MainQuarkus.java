package com.github.catapan.ifood;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@QuarkusMain
public class MainQuarkus implements QuarkusApplication {

    @Inject
    @Channel("parameters")
    Emitter<String> emitter;

    @Override
    public int run(String... args) throws Exception {
        Stream.of(args).forEach(s -> emitter.send(s));
        System.out.println("MainQuarkus.run() " + Arrays.toString(args));
        Quarkus.waitForExit();
        return 0;
    }

}

//@QuarkusMain
//public class MainQuarkus implements QuarkusApplication {
//
//    @Override
//    public int run(String... args) throws Exception {
//        System.out.println("()" + Arrays.toString(args));
//        Quarkus.waitForExit();
//        return 0;
//    }
//}

//@QuarkusMain
//public class MainQuarkus {
//
//    public static void main(String[] args) {
//        Quarkus.run(MainQuarkus2.class, args);
//    }
//
//}
//
//class MainQuarkus2 implements QuarkusApplication {
//
//    @Override
//    public int run(String... args) throws Exception {
//        System.out.println("MainQuarkus.run(2) " + Arrays.toString(args));
//        Quarkus.waitForExit();
//        return 0;
//    }
//
//}
