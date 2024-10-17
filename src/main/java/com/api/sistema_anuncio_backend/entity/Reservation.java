package com.api.sistema_anuncio_backend.entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.api.sistema_anuncio_backend.dto.ReservationDTO;
import com.api.sistema_anuncio_backend.enums.ReservationStatus;
import com.api.sistema_anuncio_backend.enums.ReviewStatus;

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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReservationStatus reservationStatus;

    private ReviewStatus reviewStatus;

    private Date bookDate;

    // Várias reservas (reservation) podem ser feitas por um único usuário (user)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)        
    @JoinColumn(name = "user_id", nullable = false)             
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // Várias reservas (reservation) podem ser publicadas por uma única empresa (company)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User company;

    // Várias reservas (reservation) podem estar associadas a um mesmo anúncio (ad)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ad_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ad ad;

    // Método para converte um objeto Reservation em um objeto DTO (Data Transfer Object), mais especificamente um ReservationDTO
    public ReservationDTO getReservationDTO() {

        ReservationDTO dto = new ReservationDTO();
        dto.setId(id);
        dto.setServiceName(ad.getServiceName());
        dto.setBookDate(bookDate);
        dto.setReservationStatus(reservationStatus);
        dto.setReviewStatus(reviewStatus);

        dto.setAdId(ad.getId());
        dto.setCompanyId(company.getId());
        dto.setUserName(user.getName());

        return dto;
    }
}

/** OBSERVAÇÃO - RELACIONAMENTOS
 * Relacionamento com o Usuário (User):
 *  -> @ManyToOne: Define um relacionamento muitos-para-um, indicando que várias reservas podem ser feitas por um mesmo usuário. Esse 
 *     relacionamento reflete o conceito de que um usuário pode realizar várias reservas, mas cada reserva pertence a um único usuário.
 *  
 *  -> fetch = FetchType.LAZY: O modo de carregamento lazy significa que o JPA carregará os dados do usuário somente quando forem acessados 
 *     (adiando a leitura do banco de dados). Isso é útil para melhorar a performance ao lidar com dados que podem não ser necessários em todas 
 *     as operações.
 * 
 *  -> @JoinColumn(name = "user_id"): Especifica que a coluna user_id será usada na tabela Reservation para armazenar a chave estrangeira que 
 *     referencia a tabela User. Ou seja, essa coluna conecta uma reserva a um usuário específico no banco de dados.
 * 
 *  -> nullable = false: A coluna user_id não pode ser nula. Isso significa que toda reserva deve estar associada a um usuário.
 * 
 *  -> @OnDelete(action = OnDeleteAction.CASCADE): Se um usuário for deletado, todas as reservas associadas a esse usuário também serão removidas 
 *     automaticamente.
 * 
 * Relacionamento com a Empresa (User):
 *  -> @ManyToOne: Esse relacionamento é semelhante ao anterior, mas refere-se à empresa que publicou o anúncio da reserva. Ou seja, indica que 
 *     várias reservas podem ser publicadas por uma única empresa (company).
 * 
 *  -> company é um User do tipo empresa, então a chave estrangeira company_id será usada na tabela de Reservation para vincular uma reserva a 
 *     uma empresa específica.
 * 
 *  -> Novamente, o uso de @OnDelete(action = OnDeleteAction.CASCADE) garante que, se a empresa for deletada, as reservas associadas a ela também 
 *     sejam apagadas.
 * 
 * Relacionamento com o Anúncio (Ad):
 *  -> @ManyToOne: Define um relacionamento muitos-para-um com a entidade Ad, indicando que várias reservas podem estar associadas a um mesmo 
 *     anúncio, mas cada reserva está vinculada a um único anúncio.
 * 
 *  -> @JoinColumn(name = "ad_id"): Aqui, a chave estrangeira ad_id será usada para conectar a reserva ao anúncio específico que está sendo 
 *     reservado.
 * 
 *  -> nullable = false: O campo ad_id também é obrigatório; uma reserva precisa estar sempre associada a um anúncio.
 * 
 *  -> @OnDelete(action = OnDeleteAction.CASCADE): Ao deletar um anúncio, todas as reservas associadas a ele serão automaticamente removidas.
 * 
*/
