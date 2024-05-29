package com.example.demo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Ange giltig epost address")
    @Email(message = "Ange giltig epost address")
    private String email;

    @NotBlank(message = "")
    @Size(min = 4, max = 8, message = "lösenord måste vara mellan 4-8 bokstäver")
    private String password;

    private String profession;

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getProfession () {
        return profession;
    }

    public void setProfession (String profession) {
        this.profession = profession;
    }


}
