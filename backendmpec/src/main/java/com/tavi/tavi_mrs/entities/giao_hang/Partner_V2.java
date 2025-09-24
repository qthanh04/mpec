package com.tavi.tavi_mrs.entities.giao_hang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partner")
public class Partner_V2 {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "json")
    private String json;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private Integer type;

    @Column(name = "token_name")
    private String tokenName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "company_id")
    private Integer company_id;

}
