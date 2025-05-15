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
    List<Path> best = new ArrayList<>();
    double top = -1;

    for (Path p : Files.newDirectoryStream(Paths.get(poolDir))) {
        if (!Files.isRegularFile(p)) continue;
        Set<String> b = loadWords(p);
        double s = jaccard(a, b) * 100;
        System.out.printf("%s → %.2f%%%n", p.getFileName(), s);

        if (s > top) {
            top = s;
            best.clear();
            best.add(p);
        } else if (s == top) {
            best.add(p);
        }
    }

    if (!best.isEmpty()) {
        System.out.printf("Best match (%.2f%%):%n", top);
        for (Path p : best) System.out.println("- " + p.getFileName());
    }
}





    
private Set<String> loadWords(Path p) {
    try {
        return Files.lines(p)
            .flatMap(line -> Arrays.stream(line.split("[^A-Za-z]+")))
            .filter(w -> !w.isEmpty())
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    } catch (Exception e) {
        return Set.of();
    }
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
