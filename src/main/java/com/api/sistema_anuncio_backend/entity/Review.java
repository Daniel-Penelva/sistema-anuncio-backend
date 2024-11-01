package com.api.sistema_anuncio_backend.entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.api.sistema_anuncio_backend.dto.ReviewDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reviewDate;                                      // data em que a avaliação foi feita.
    private String review;                                        // texto da avaliação.
    private Long rating;                                          // avaliação numérica (ex.: nota).

    // Muitas avaliações (review) pode está associada a um único usuário (user)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // Muitas avaliações (review) pode está associada a um único anúncio (ad)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ad_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ad ad;

    public ReviewDTO getDto() {
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(id);
        reviewDTO.setReview(review);
        reviewDTO.setRating(rating);
        reviewDTO.setReviewDate(reviewDate);
        reviewDTO.setUserId(user.getId());
        reviewDTO.setClientName(user.getName());
        reviewDTO.setAdId(ad.getId());
        reviewDTO.setServiceName(ad.getServiceName());

        return reviewDTO;
    }

}

/**
 * OBSERVAÇÃO:
 * RELACIONAMENTO COM USER (usuário)
 *  Indica um relacionamento "muitos-para-um", onde várias avaliações (Review) podem estar associadas a um único usuário (User). Isso significa 
 *  que um usuário pode fazer várias avaliações, mas cada avaliação pertence a um único usuário.
 *  
 *  Sobre o FetchType.LAZY define que o carregamento do objeto User será "preguiçoso". Isso significa que o User associado só será carregado 
 *  quando explicitamente necessário, economizando memória e otimizando o desempenho, especialmente quando as informações do usuário não são 
 *  imediatamente relevantes.
 *  
 *  O @JoinColumn(name = "user_id") define a coluna user_id na tabela Review, que armazena o ID do User associado. Esse é o campo de ligação 
 *  entre as tabelas.
 * 
 *  O OnDelete(action = OnDeleteAction.CASCADE) garante que, ao deletar o User associado, todas as avaliações feitas por ele também serão 
 *  excluídas, preservando a integridade referencial e evitando avaliações órfãs no banco de dados.
 * 
 * RELACIONAMENTO COM AD (anúncio)
 *  Indica também um relacionamento "muitos-para-um" entre Review e Ad. Isso significa que um anúncio (Ad) pode ter várias avaliações (Review), 
 *  mas cada avaliação está relacionada a um único anúncio. Esse tipo de relacionamento é comum para modelos de avaliação.
 *  Possuem as mesmas características ditas acima, porém entre a entidade Ad e Review.
 * 
 * VISÃO GERAL DO RELACIONAMENTO
 *  Esses relacionamentos fazem da entidade Review uma entidade dependente de User e Ad, onde a remoção de um User ou Ad acarreta na remoção das 
 *  avaliações correspondentes.
*/
