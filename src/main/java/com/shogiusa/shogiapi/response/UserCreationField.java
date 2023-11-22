package com.shogiusa.shogiapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a single field in a user creation form.
 * <p>
 * This class encapsulates the properties of a form field, including its name, type, options,
 * validation rules, and whether it's required or not. It's designed to be flexible to accommodate
 * different types of form fields.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationField {
    /**
     * The name of the field. This is typically used as the key or identifier for the field.
     */
    private String name;

    /**
     * The type of the field (e.g., text, email, password, select).
     */
    private String type;

    /**
     * Options for the field, applicable for types like 'select', where multiple options are presented.
     */
    private String[] options;

    /**
     * Validation rules for the field, expressed as strings. These could be regex patterns,
     * required field indicators, or other custom validation rules.
     */
    private String[] validation;

    /**
     * Indicates whether the field is required or not.
     */
    private boolean required;
}

