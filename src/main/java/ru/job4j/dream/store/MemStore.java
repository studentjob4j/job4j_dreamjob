package ru.job4j.dream.store;

/**
 * @author Shegai Evgenii
 * @since 23.06.2021
 * @version 1.0
 * Хранилище кандидатов и вакансии
 */

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {

    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CAND_ID = new AtomicInteger(3);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Candidate, List<File>> photos = new ConcurrentHashMap<>();

    private MemStore() {
        save(new Post(1,"Programmer", "Junior Java Dev", LocalDate.now()));
        save(new Post(2, "Programmer", "Middle Java Dev", LocalDate.now()));
        save(new Post(3, "Programmer", "Senior Java Dev", LocalDate.now()));
        save2(new Candidate(1, "John"));
        save2(new Candidate(2, "Mike"));
        save2(new Candidate(3, "Sam"));
    }

    public static MemStore instOf() {
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

    public void save2(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CAND_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
        photos.put(candidate, new CopyOnWriteArrayList<>());
    }

    public Post findById(int id) {
        if (!posts.containsKey(id)) {
            throw new NoSuchElementException("Post with id" + id + "not found");
        }
        return posts.get(id);
    }

    public Candidate findByCandidateId(int id) {
        if (!candidates.containsKey(id)) {
            throw new NoSuchElementException("Candidate with id" + id + "not found");
        }
        return candidates.get(id);
    }

    public void deleteCandidate(String id) {
        Candidate candidate = candidates.get(Integer.parseInt(id));
        if (photos.get(candidate).size() != 0) {
            List<File> fileList = photos.get(candidate);
            for (File file : fileList) {
                file.delete();
            }
        }
        candidates.remove(Integer.parseInt(id));
    }

    public void addCandidatePhoto(Candidate candidate, File file) {
        if (!photos.containsKey(candidate)) {
            List<File> photosList = new CopyOnWriteArrayList<>();
            photosList.add(file);
            photos.put(candidate, photosList);
        } else {
            photos.get(candidate).add(file);
        }
    }
}
