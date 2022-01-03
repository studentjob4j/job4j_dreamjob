package ru.job4j.dream.store;

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
        store.save(new Post(0, "Java Job", "test", LocalDate.now()));
        System.out.println(store.findById(0));
        Post update = new Post(1,"JavaUpdate","test", LocalDate.now());
        store.save(update);
        for (Post post : store.findAllPosts()) {
            System.out.println(post);
        }
        store.findAllCandidates();
    }
}
