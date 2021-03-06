package be.vdab.muziek.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "albums")
@NamedEntityGraph(name = "Album.metArtiest", attributeNodes = @NamedAttributeNode("artiest"))
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String naam;
    @Range(min = 0, max = 10)
    private int score;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artiestid")
    private Artiest artiest;
    @ElementCollection
    @CollectionTable(name = "tracks", joinColumns = @JoinColumn(name = "albumid"))
    private Set<Track> tracks;

    public Album(String naam, int score, Artiest artiest) {
        this.naam = naam;
        this.score = score;
        this.artiest = artiest;
        this.tracks = new LinkedHashSet<>();
    }

    public Album() {
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public Set<Track> getTracks() {
        return Collections.unmodifiableSet(tracks);
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime calculateTotaleTrackTime(){
        LocalTime som = LocalTime.of(0, 0, 0);

        for(Track track : this.tracks){
            som = som.plusHours(track.getTijd().getHour())
                    .plusMinutes(track.getTijd().getMinute())
                    .plusSeconds(track.getTijd().getSecond());
        }
        return som;
    }
}
