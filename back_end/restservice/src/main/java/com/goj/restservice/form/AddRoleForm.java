package com.goj.restservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(value = 0, message = "role should greater than or equal to 0")
    @Max(value = 1, message = "role should less than or equal to 1")
    Short role;
}