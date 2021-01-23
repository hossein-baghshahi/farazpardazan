package com.farazpardazan.cardmanagementsystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author Hossein Baghshahi
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{username.empty}")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "{user.name.empty}")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "{user.family.empty}")
    @Column(nullable = false)
    private String family;

    @NotBlank(message = "{user.mobile_number.empty}")
    @Column(name = "mobile_number" ,nullable = false)
    private String mobileNumber;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
