package fel.cvut.cz.ear.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "Club.findByNameOrdered",
                query = "SELECT c FROM Club c WHERE c.name = :clubName ORDER BY c.address"
        )
})
@Table(name = "CLUB")
public class Club extends AbstractEntity{
    @Basic(optional = false)
    @Column(name="name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name="address", nullable = false)
    private String address;

    @JsonIgnore
    @OrderBy("name ASC ")
    @OneToMany(mappedBy = "club")
    private List<Member> members;

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

    public List<Member> getMembers() {
        return members;
    }
}
