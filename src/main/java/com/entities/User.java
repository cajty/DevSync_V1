package com.entities;



import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" , nullable = false, unique = true)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;


    public Integer getDailyReplaceTokens() {
        return dailyReplaceTokens;
    }

    public void setDailyReplaceTokens(Integer dailyReplaceTokens) {
        this.dailyReplaceTokens = dailyReplaceTokens;
    }

    @Column(name = "daily_replace_tokens", nullable = false)
    private Integer dailyReplaceTokens;

    @Column(name = "monthly_delete_tokens", nullable = false)
    private Integer monthlyReplaceTokens;


    @Enumerated(EnumType.STRING)
    @Column(name = "role" , nullable = false)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }




    public Integer getMonthlyReplaceTokens() {
        return monthlyReplaceTokens;
    }

    public void setMonthlyReplaceTokens(Integer monthlyReplaceTokens) {
        this.monthlyReplaceTokens = monthlyReplaceTokens;
    }
}