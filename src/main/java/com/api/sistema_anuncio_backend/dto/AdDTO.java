package com.api.sistema_anuncio_backend.dto;

import lombok.Data;

@Data
public class AdDTO {

    private Long id;
    private String serviceName;
    private String description;
    private Double price;
    private byte[] img;
    private Long userId;
    private String companyName;

}
