package fel.cvut.cz.ear.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "MEMBER")
@NamedQueries({
        @NamedQuery(name = "member.findByName", query = "SELECT m FROM Member m WHERE m.name = :username")
})

public class Member extends AbstractEntity   {
    @Basic(optional = false)
    @Column(name="name", nullable = false)
    private String name;
    @Basic(optional = false)
    @Column(name="address", nullable = false)
    private String address;
    @Basic(optional = false)
    @Column(name="phoneNumber", nullable = false)
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name="email", nullable = false)
    private String email;
    @Basic(optional = false)
    @Column(name="balance", nullable = false)
    private double balance;
    @Basic(optional = false)
    @Column(name="status", nullable = false)
    private boolean status;
    @Basic(optional = false)
    @Column(name="level", nullable = false)
    private int level;
    @Basic(optional = false)
    @Column(name="points", nullable = false)
    private int points;
    @Basic(optional = false)
    @Column(name="login", nullable = false, unique = true)
    private String login;
    @Basic(optional = false)
    @Column(name="password", nullable = false)
    private String password;
    @Basic(optional = false)
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;


    @OneToMany(mappedBy = "member")
    private List<Payment> payments;


    @OneToMany(mappedBy = "member")
    @OrderBy("place DESC ")
    private List<TournamentResult> tournamentResults;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public Member() {
        this.role = Role.MEMBER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }
    public void changePoints(int points){
        this.points += points;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public void encodePassword(PasswordEncoder encoder) {
        if (this.password != null) {
            this.password = encoder.encode(password);
        } else {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
    }
}

