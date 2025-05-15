package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

 import java.nio.file.*;
import java.util.*;
 import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Value("${file.a.path}")
    private String fileA;

    @Value("${file.pool.dir}")
    private String poolDir;

 
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    
    @Override
public void run(String... args) throws Exception {
    Set<String> a = loadWords(Paths.get(fileA));
    Path best = null;
    double top = -1;

    for (Path p : Files.newDirectoryStream(Paths.get(poolDir))) {
        if (!Files.isRegularFile(p)) continue;
        Set<String> b = loadWords(p);
        double s = jaccard(a, b) * 100;
        System.out.printf("%s → %.2f%%%n", p.getFileName(), s);
        if (s > top) {
            top = s;
            best = p;
        }
    }

    if (best != null) System.out.printf("Best match: %s (%.2f%%)%n", best.getFileName(), top);
}




    
private Set<String> loadWords(Path p) {
    String text = "";
    try {
        text = Files.readString(p);
    } catch (Exception ignored) {}

    return Arrays.stream(text.split("[^A-Za-z]+"))
        .filter(w -> !w.isEmpty())
        .map(String::toLowerCase)
        .collect(Collectors.toSet());
}



private double jaccard(Set<String> a, Set<String> b) {
    int same = 0;
    for (String word : a) {
        if (b.contains(word)) same++;
    }

    int total = a.size() + b.size() - same;

    if (total == 0) return 1.0;

    return (double) same / total;
}

}
