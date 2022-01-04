package ru.job4j.dream.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Shegai Evgenii
 * @since 02.01.2022
 * @version 1.0
 */

public class PropertiesUtil {

    public static Properties properties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(PropertiesUtil.class.getClassLoader()
                                .getResourceAsStream("pathname.properties"))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }
}
