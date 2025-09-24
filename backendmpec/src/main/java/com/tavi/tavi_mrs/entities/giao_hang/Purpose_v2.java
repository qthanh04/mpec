package com.tavi.tavi_mrs.entities.giao_hang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purpose")
public class Purpose_v2 {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner_V2 partner;

    @Column(name = "type")
    private Integer type;

    @Column(name = "uri")
    private String uri;

    @Column(name = "json_result")
    private String json_result;

    @Column(name = "name")
    private String name;

    @Column(name = "json_send")
    private String jsonSend;

    @Column(name = "note")
    private String note;

    @Column(name = "param")
    private String param;
}
