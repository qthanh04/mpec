package com.tavi.tavi_mrs.entities.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Column(name = "nguoi_tao_id")
    private Integer nguoiTaoId;

    @Column(name = "thoi_gian_tao")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date thoiGianTao;

    @Column(name = "nguoi_sua_id")
    private Integer nguoiSuaId;

    @Column(name = "thoi_gian_sua")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date thoiGianSua;
}
