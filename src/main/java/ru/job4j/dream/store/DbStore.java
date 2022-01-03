package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @author Shegai Evgenii
 * @since 03.01.2022
 * @version 1.0
 */

public class DbStore implements Store {

    private static final DbStore instance = new DbStore();

    private final BasicDataSource pool = new BasicDataSource();

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
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
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

    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

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
            e.printStackTrace();
        }
        return posts;
    }

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
            e.printStackTrace();
        }
        return result;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(id, name) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, candidate.getId());
            ps.setString(2, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(id, name,  des, data) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            Timestamp time = Timestamp.valueOf(post.getCreate().atStartOfDay());
            ps.setInt(1, post.getId());
            ps.setString(2, post.getName());
            ps.setString(3, post.getDesc());
            ps.setTimestamp(4, time);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post posts) {
        String sql = "UPDATE post SET name = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, posts.getName());
            ps.setInt(2, 0);
            int n = ps.executeUpdate();
            System.out.println("Количество обновлённых строк: " + n);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
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
            e.printStackTrace();
        }
        return post;
    }
}
