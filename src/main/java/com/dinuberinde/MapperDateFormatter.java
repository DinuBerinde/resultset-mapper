package com.dinuberinde;

import java.lang.annotation.*;

/**
 * Annotation used to format a date column to a Java string field.
 * The default date pattern is <strong>dd/MM/yyyy</strong>.
 * <br>
 *<p>Example:</p>
 *<pre class="code">
 *import java.util.Date;
 *
 *public class User {
 *
 *  &#064;MapperLabel(name = "ID")
 *  private Long id;
 *
 *  &#064;MapperDateFormatter()
 *  &#064;MapperLabel(name = "BIRTHDATE")
 *  private String birthDate;
 *
 *  // getters and setters or Lombok
 * }
 *</pre>
 *
 * <p>Usage:</p>
 * <pre class="code">
 *  ResultSetMapper.toObject(resultSet, User.class);
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapperDateFormatter {

    /**
     * The date format pattern used to map a date column to a Java string field.
     */
    String pattern() default "dd/MM/yyyy";
}
