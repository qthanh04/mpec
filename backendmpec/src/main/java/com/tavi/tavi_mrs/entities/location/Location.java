package com.tavi.tavi_mrs.entities.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "location", schema = "dbo")
public class Location {

    @Id
    private String id;

    @Column(name = "locname")
    private String locname;

    @Column(name = "loclevel")
    private String loclevel;

    @Column(name = "pid")
    private String pid;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Location location;
//
//    @ManyToMany(mappedBy = "pid")
//    private Set<Location> locationChild = new HashSet<>();
}
