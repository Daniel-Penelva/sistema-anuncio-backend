package com.api.sistema_anuncio_backend.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AdDTO {

    private Long id;
    private String serviceName;
    private String description;
    private Double price;
    private MultipartFile img;    // MultipartFile permite o upload de imagens pelo frontend e o processamento no backend, facilitando a recepção de arquivos binários em requisições HTTP.
    private byte[] returnedImg;
    private Long userId;
    private String companyName;

}


/** OBSERVAÇÃO: SOBRE MultipartFile
 * 
 * MultipartFile: É uma interface do Spring usada para lidar com arquivos binários (como imagens) enviados via formulários em uma requisição HTTP. 
 * Isso permite que receba uma imagem diretamente de um formulário no frontend e armazene-a no objeto AdDTO antes de fazer qualquer processamento 
 * adicional (como salvar a imagem no banco de dados ou sistema de arquivos).
 * 
 * Como funciona o fluxo:
 * 1. Recepção da Imagem: Quando o usuário envia um formulário com uma imagem, essa imagem será carregada no backend como um objeto MultipartFile 
 * no DTO.
 * 
 * 2. Processamento da Imagem: No backend, o MultipartFile pode ser convertido em um array de bytes (ou salvo diretamente em algum local do sistema 
 * de arquivos). Depois, o array de bytes pode ser armazenado no banco de dados (no caso, no campo byte[] img da entidade Ad).
 * 
 * 3. Armazenamento e Conversão:
 *  -> Quando a imagem é recebida no DTO, ela pode ser processada e convertida em um array de bytes para salvar no banco de dados.
 *  -> No método getDto(), estou usando adDTO.setReturnedImg(img) para retornar a imagem armazenada como um array de bytes na entidade Ad.
 * 
 * Integração da Imagem no Fluxo: 
 * 1. Upload da Imagem:
 *  -> No frontend, poderá fazer upload da imagem via formulário.
 *  -> No backend, o MultipartFile no AdDTO lida com a recepção da imagem.
 * 
 * 2. Conversão e Armazenamento:
 *  -> Após receber o MultipartFile, ele é convertido em bytes e armazenado na entidade Ad no campo byte[] img.
 * 
 * 3. Retorno da Imagem:
 *  -> Quando os dados de um anúncio são retornados, o método getDto() converte a imagem armazenada no banco de dados de volta para o DTO para 
 *     que o frontend possa exibi-la.
*/