package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Shegai Evgenii
 * @since 03.01.2022
 * @version 1.0
 */

public class DbStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();
    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class.getName());

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        DbStore.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INSTANCE = new DbStore();
    }

    public static Store instOf() {
        return Lazy.INSTANCE;
    }

    @Override
    public Post savePost(Post post) {
        Post result = new Post();
        if (post.getId() == 0) {
            result = createPost(post);
        } else {
            updatePost(post);
        }
        return result;
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name,  des, data) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            Timestamp time = Timestamp.valueOf(post.getCreate().atStartOfDay());
            ps.setString(1, post.getName());
            ps.setString(2, post.getDesc());
            ps.setTimestamp(3, time);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }
        return post;
    }

    private void updatePost(Post post) {
        String sql = "UPDATE post SET name = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            int n = ps.executeUpdate();
            System.out.println("Количество обновлённых строк: " + n);
        } catch (SQLException e) {
           LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    LocalDate date = LocalDate.from(it.getTimestamp("data").toLocalDateTime());
                    posts.add(new Post(it.getInt("id"), it.getString("name"),
                             it.getString("des"), date));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Post findPostById(int id) {
        Post post = new Post();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("select * from post where id = ?;")) {
            ps.setInt(1, id);
            ResultSet it = ps.executeQuery();
            if (it.next()) {
                LocalDate date = LocalDate.from(it.getTimestamp("data").toLocalDateTime());
                post.setId(it.getInt("id"));
                post.setName(it.getString("name"));
                post.setDesc(it.getString("des"));
                post.setCreate(date);
            }

        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }
        return post;
    }

    @Override
    public boolean deletePost(String id) {
        boolean result = true;
        try ( Connection cn = pool.getConnection()) {
           PreparedStatement ps = cn.prepareStatement("DELETE FROM post WHERE id = ?;");
           ps.setInt(1, Integer.parseInt(id));
           result =  ps.execute();
        } catch (SQLException e) {
           LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        Candidate result = new Candidate();
        if (candidate.getId() == 0) {
            result = createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
        return result;
    }

    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("UPDATE candidate SET name = ? WHERE id = ?;");
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            int n = ps.executeUpdate();
            System.out.println("Количество обновлённых строк: " + n);
        } catch (SQLException e) {
          LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        ArrayList<Candidate> result = new ArrayList<>();
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate");
            try(ResultSet set = ps.executeQuery()) {
                while (set.next()) {
                    result.add(new Candidate(set.getInt(1), set.getString(2)));
                }
            }
        } catch (SQLException e) {
           LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = new Candidate();
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps =  cn.prepareStatement("select * from candidate where id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
          LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    @Override
    public boolean deleteCandidate(String id) {
        boolean result = true;
        try ( Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM candidate WHERE id = ?;");
            ps.setInt(1, Integer.parseInt(id));
            result =  ps.execute();
        } catch (SQLException e) {
           LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public User saveUser(User user) {
       User result = new User();
        if (user.getId() == 0) {
            result = createUser(user);
        } else {
            updateUser(user);
        }
        return result;
    }

    private User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    private void updateUser(User user) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?;");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            int n = ps.executeUpdate();
            System.out.println("Количество обновлённых строк: " + n);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public User findUserById(int id) {
       User user = new User();
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps =  cn.prepareStatement("select * from users where id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean deleteUser(String id) {
        boolean result = true;
        try ( Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM users WHERE id = ?;");
            ps.setInt(1, Integer.parseInt(id));
            result =  ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps =  cn.prepareStatement("select * from users where email = ?;");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public Collection<User> findAllUser() {
        ArrayList<User> result = new ArrayList<>();
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM users");
            try(ResultSet set = ps.executeQuery()) {
                while (set.next()) {
                    result.add(new User(set.getInt(1), set.getString(2), set.getString(3),
                            set.getString(4)));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void cleanTable() {
        try(Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM post");
            PreparedStatement ps2 =  cn.prepareStatement("ALTER SEQUENCE post_id_seq RESTART WITH 1");
            PreparedStatement ps3 = cn.prepareStatement("DELETE FROM candidate");
            PreparedStatement ps4 = cn.prepareStatement("ALTER SEQUENCE candidate_id_seq RESTART WITH 1");
            PreparedStatement ps5 =   cn.prepareStatement("DELETE FROM users");
            PreparedStatement ps6 =  cn.prepareStatement("ALTER SEQUENCE users_id_seq RESTART WITH 1");
            ps.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
            ps4.executeUpdate();
            ps5.executeUpdate();
            ps6.executeUpdate();
        } catch (SQLException e) {
        LOG.error(e.getMessage(), e);
        }
    }
}
