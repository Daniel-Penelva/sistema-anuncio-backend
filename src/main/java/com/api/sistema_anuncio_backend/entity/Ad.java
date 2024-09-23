package com.api.sistema_anuncio_backend.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.api.sistema_anuncio_backend.dto.AdDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ads")
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String description;
    private Double price;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    // Muitos anuncios (Ad) podem pertencer a um único usuário (User)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    /*Este método vai facilitar a conversão da entidade Ad para AdDTO, encapsulando todos os dados relevantes do anúncio, incluindo a imagem e o nome da empresa associada.*/
    public AdDTO getDto(){
        
        AdDTO adDTO = new AdDTO();
        adDTO.setId(id);                               // Copia o ID do anúncio
        adDTO.setServiceName(serviceName);             // Copia o nome do serviço
        adDTO.setDescription(description);             // Copia a descrição do serviço
        adDTO.setPrice(price);                         // Copia o preço do serviço
        adDTO.setCompanyName(user.getName());          // Copia o nome da empresa (associada ao User)
        adDTO.setReturnedImg(img);                     // Copia a imagem (em formato de array de bytes)

        return adDTO;                                  // Retorna o DTO populado
    }
    
}
