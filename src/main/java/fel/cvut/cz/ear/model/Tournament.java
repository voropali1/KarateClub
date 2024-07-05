package fel.cvut.cz.ear.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TOURNAMENT")
public class Tournament extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @JsonManagedReference
    @OneToMany(mappedBy = "tournament")
    private List<TournamentResult> tournamentResults;

    public Tournament() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<TournamentResult> getTournamentResults() {
        return tournamentResults;
    }

    public void setTournamentResults(List<TournamentResult> tournamentResults) {
        this.tournamentResults = tournamentResults;
    }
}