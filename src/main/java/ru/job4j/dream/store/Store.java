package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

/**
 * @author Shegai Evgenii
 * @since 03.01.2022
 * @version 1.0
 */

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Post savePost(Post post);

    Candidate saveCandidate(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    boolean deletePost(String id);

    boolean deleteCandidate(String id);

    Collection<User> findAllUser();

    User saveUser(User user);

    User findUserById(int id);

    boolean deleteUser(String id);

    User findUserByEmail(String email);

    void cleanTable();

}
