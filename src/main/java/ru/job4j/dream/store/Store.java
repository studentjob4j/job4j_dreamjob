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
import java.util.concurrent.atomic.AtomicInteger;

public class Store {

    private static final Store INST = new Store();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CAND_ID = new AtomicInteger(3);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post("Programmer",1, "Junior Java Dev", LocalDate.now()));
        posts.put(2, new Post("Programmer",2, "Middle Java Dev", LocalDate.now()));
        posts.put(3, new Post("Programmer", 3, "Senior Java Dev", LocalDate.now()));
        candidates.put(1, new Candidate(1, "John"));
        candidates.put(2, new Candidate(2, "Mike"));
        candidates.put(3, new Candidate(3, "Sam"));
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

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CAND_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findByCandidateId(int id) {
        return candidates.get(id);
    }
}
