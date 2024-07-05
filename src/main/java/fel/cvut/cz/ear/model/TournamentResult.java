package fel.cvut.cz.ear.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "TOURNAMENT_RESULT")
public class TournamentResult extends AbstractEntity {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "second_member_id")
    private Member secondMember;

    @Column(name = "point", nullable = false)
    private int point;

    @Column(name = "place", nullable = false)
    private int place;

    // Private constructor
    public TournamentResult() {
    }

    public static class Builder {
        private Tournament tournament;
        private Member member;
        private Member secondMember;
        private int point;
        private int place;

        public Builder tournament(Tournament tournament) {
            this.tournament = tournament;
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder secondMember(Member secondMember) {
            this.secondMember = secondMember;
            return this;
        }

        public Builder point(int point) {
            this.point = point;
            return this;
        }

        public Builder place(int place) {
            this.place = place;
            return this;
        }

        public TournamentResult build() {
            TournamentResult tournamentResult = new TournamentResult();
            tournamentResult.tournament = this.tournament;
            tournamentResult.member = this.member;
            tournamentResult.secondMember = this.secondMember;
            tournamentResult.point = this.point;
            tournamentResult.place = this.place;
            return tournamentResult;
        }
    }

    // Getters and setters omitted for brevity

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        if (this.member != null){
            this.member.changePoints(-point);
            if (this.member.getPoints() <= 0){
                this.member.setPoints(0);
            }
        }
        member.changePoints(point);
        this.member = member;
    }

    public Member getSecondMember() {
        return secondMember;
    }

    public void setSecondMember(Member secondMember) {
        if (this.secondMember != null){
            this.secondMember.changePoints(-point);
            if (this.secondMember.getPoints() <= 0){
                this.secondMember.setPoints(0);
            }
        }
        secondMember.changePoints(point);
        this.secondMember = secondMember;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
