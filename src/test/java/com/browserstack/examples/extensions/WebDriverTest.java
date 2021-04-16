package com.browserstack.examples.extensions;

import static org.apiguardian.api.API.Status.STABLE;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apiguardian.api.API;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = STABLE)
@TestTemplate
@ExtendWith(WebDriverTestExtension.class)
public @interface WebDriverTest {
}
