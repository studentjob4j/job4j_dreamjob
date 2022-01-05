package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import java.time.LocalDate;

/**
 * @author Shegai Evgenii
 * @since 03.01.2022
 * @version 1.0
 */

public class MainStore {

    public static void main(String[] args) {
        Store store = DbStore.instOf();
        Post one = store.savePost(new Post(0, "Java Job", "test", LocalDate.now()));
        Post two = store.savePost(new Post(0, "Java Job2", "test2", LocalDate.now()));
        System.out.println(store.findPostById(two.getId()));
        Post update = new Post(two.getId(),"JavaUpdate","test", LocalDate.now());
        store.savePost(update);
        for (Post post : store.findAllPosts()) {
            System.out.println(post);
        }
        System.out.println(store.deletePost(String.valueOf(one.getId())));
        System.out.println(store.deletePost(String.valueOf(two.getId())));
        Candidate candidate = store.saveCandidate(new Candidate(0,"Can"));
        Candidate candidate2 = store.saveCandidate(new Candidate(0,"Can2"));
        System.out.println(store.findCandidateById(candidate2.getId()));
        Candidate upd = new Candidate(candidate2.getId(), "Update");
        store.saveCandidate(upd);
        store.findAllCandidates().stream().forEach(System.out::println);
        System.out.println(store.deleteCandidate(String.valueOf(candidate.getId())));
        System.out.println(store.deletePost(String.valueOf(candidate2.getId())));
    }
}
