package by.prusco.ozonstatistics.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class UserCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String logon;
    @Column(nullable = false)
    private String encodedPassword;
    private String email;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities", joinColumns = {
            @JoinColumn(name = "users_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "authorities_id", referencedColumnName = "id") })
    private Set<Authority> authorities;

    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
}
