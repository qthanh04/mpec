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
@Table(name = "transport")
public class Transport_V2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner_V2 partner;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "fee")
    private Long fee;

    @Column(name = "pick_time")
    private String pickTime;

    @Column(name = "deliver_time")
    private String deliverTime;

    @Column(name = "status")
    private Integer status;

}
