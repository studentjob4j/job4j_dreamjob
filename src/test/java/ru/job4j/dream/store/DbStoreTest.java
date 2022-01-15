package ru.job4j.dream.store;

import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * @author Shegai Evgenii
 * @since 09.01.2022
 * @version 1.0
 */

public class DbStoreTest {

    private Store store = DbStore.instOf();

    @Test
    public void whenTestPost() {
        store.savePost(new Post("post", "desc", LocalDate.now()));
        Post res = store.findPostById(1);
        store.savePost(new Post(1, "update", "desc", LocalDate.now()));
        Post res2 = store.findPostById(1);
        store.savePost(new Post("post2", "desc2", LocalDate.now()));
        List<Post> list = (List<Post>) store.findAllPosts();
        boolean value = store.deletePost("1");
        assertThat(res.getName(), is("post"));
        assertThat(res2.getName(), is("update"));
        assertThat(list.size(), is(2));
        assertFalse(value);
    }

    @Test
    public void whenTestCandidate() {
        store.saveCandidate(new Candidate("can"));
        Candidate res = store.findCandidateById(1);
        store.saveCandidate(new Candidate(1, "update"));
        Candidate update = store.findCandidateById(1);
        store.saveCandidate(new Candidate("can2"));
        List<Candidate> list = (List<Candidate>) store.findAllCandidates();
        boolean res2 = store.deleteCandidate("1");
        assertThat(res.getName(), is("can"));
        assertThat(update.getName(), is("update"));
        assertThat(list.size(), is(2));
        assertFalse(res2);
    }

    @Test
    public void whenTestUser() {
        store.saveUser(new User("user", "email", "pass"));
        User res = store.findUserById(1);
        store.saveUser(new User(1, "update", "emailUpdate", "passUpdate"));
        User res2 = store.findUserById(1);
        store.saveUser(new User("user2", "email2", "pass2"));
        User res3 = store.findUserByEmail("email2");
        List<User> list = (List<User>) store.findAllUser();
        boolean value = store.deleteUser("1");
        assertThat(res.getName(), is("user"));
        assertThat(res2.getName(), is("update"));
        assertThat(res3.getName(), is("user2"));
        assertThat(list.size(), is(2));
        assertFalse(value);
    }
}
