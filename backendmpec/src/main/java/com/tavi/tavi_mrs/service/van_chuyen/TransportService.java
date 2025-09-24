package com.tavi.tavi_mrs.service.van_chuyen;

import com.tavi.tavi_mrs.entities.van_chuyen.Fee;
import com.tavi.tavi_mrs.entities.van_chuyen.OrderFee;
import com.tavi.tavi_mrs.entities.van_chuyen.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface  TransportService {

    Optional<Transport> save(Transport transport);

    List<Fee> getFee(HttpServletRequest request, OrderFee order) throws Exception;

    Page<Transport> findAllVanChuyen(Pageable pageable);

    Page<Transport> searchVanChuyen(String text,Pageable pageable);
}
