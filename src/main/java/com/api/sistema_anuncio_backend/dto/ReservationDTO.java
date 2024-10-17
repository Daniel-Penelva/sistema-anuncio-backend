package com.api.sistema_anuncio_backend.dto;

import java.util.Date;

import com.api.sistema_anuncio_backend.enums.ReservationStatus;
import com.api.sistema_anuncio_backend.enums.ReviewStatus;

import lombok.Data;

@Data
public class ReservationDTO {

    private Long id;
    private Date bookDate;
    private String serviceName;
    private ReservationStatus reservationStatus;
    private ReviewStatus reviewStatus;
    
    private Long userId;
    private String userName;
    private Long companyId;
    private Long adId;
    
}
