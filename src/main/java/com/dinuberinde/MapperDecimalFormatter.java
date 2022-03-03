package com.dinuberinde;

import java.lang.annotation.*;

/**
 * Annotation used to format a numeric column to a Java string field.
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
 *  &#064;MapperDecimalFormatter(pattern = "#.00#")
 *  &#064;MapperLabel(name = "HEIGHT")
 *  private String height;
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
public @interface MapperDecimalFormatter {

    /**
     * The pattern used to format the numeric column to a Java string field.
     * The implementation uses the {@link java.text.DecimalFormat} class to format the number.
     */
    String pattern();

    /**
     * The locale used for formatting.
     * The implementation uses the {@link java.util.Locale} class.
     */
    String locale() default "en";
}
