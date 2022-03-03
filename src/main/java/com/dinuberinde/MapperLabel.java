package com.dinuberinde;

import java.lang.annotation.*;


/**
 * Marks a field to be mapped.
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
 *  &#064;MapperLabel(name = "NAME")
 *  private String name;
 *
 *  &#064;MapperLabel(name = "BIRTHDATE")
 *  private Date birthDate;
 *
 *  // getters and setters or Lombok
 * }
 *</pre>
 *
 * <p>Usage:</p>
 * <pre class="code">
 *  ResultSetMapper.toObject(resultSet, User.class);
 * </pre>
 *
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapperLabel {

    /**
     * The name of the database column.
     */
    String name();

    /**
     * Set to true if the column may not exist in order to prevent a {@link java.sql.SQLException}.
     */
    boolean optional() default false;

    /**
     * Set to true if the column is a date and you want to map it to a Java string field
     * using the dateFormat property.
     */
    boolean dateToString() default false;

    /**
     * The date format used to map a date column to a Java string field.
     */
    String dateFormat() default "dd/MM/yyyy";
}
