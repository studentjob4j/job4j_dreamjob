package ru.job4j.dream.store;

/**
 * @author Shegai Evgenii
 * @since 23.06.2021
 * @version 1.0
 */

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post("John",1, "Junior Java Job", LocalDate.now()));
        posts.put(2, new Post("Stiven",2, "Middle Java Job", LocalDate.now()));
        posts.put(3, new Post("Max", 3, "Senior Java Job", LocalDate.now()));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
