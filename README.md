# Projeto
É utilizado tecnologias de ponta, incluindo Spring Boot 3, Spring Security 6, Angular 16, Ng Zorro UI e um banco de dados MySQL. Vai ser explorado os principais recursos deste sistema de reserva de serviços:

## Tecnologias:
- **Angular**: Estrutura de front-end para construção de aplicativos web dinâmicos.
- **Spring Boot**: Framework para construção de aplicativos corporativos baseados em Java.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional para armazenamento de dados.
- **Ng Zorro UI**: Biblioteca de componentes para aplicativos Angular.

## 🏢 Recursos da Empresa:
- **Criar conta**: Registre-se como usuário da empresa.
- **Login**: Acesse a conta com credenciais.
- **Publicar anúncios**: Anuncie serviços ou produtos.
- **Atualizar e excluir anúncios**: Modificar ou remover anúncios postados.
- **Ver Reservas**: Veja a lista de reservas feitas pelos clientes.
- **Aprovar ou rejeitar reserva**: Aceite ou recuse solicitações de reserva.

## 👤 Recursos do Cliente:
- **Criar conta**: Registre-se como usuário cliente.
- **Login**: Acesse a conta com credenciais.
- **Serviços de pesquisa**: Encontre serviços ou produtos.
- **Ver detalhes e comentários do anúncio**: Verifique informações detalhadas e comentários dos anúncios.
- **Serviços de reserva**: Faça reservas de serviços ou produtos.
- **Ver Reservas**: Veja a lista de reservas feitas pelo cliente.
- **Serviços de avaliação**: Forneça feedback ou avaliações sobre os serviços.

---

# ▶️ Executando o projeto localmente

Este projeto originalmente foi configurado para execução em ambiente de produção com deploy online.
Para executá-lo localmente é necessário realizar algumas alterações no **backend** e garantir que o **frontend utilize o ambiente de desenvolvimento**.

---

# ⚙️ Backend (Spring Boot)

## 1️⃣ Ativar perfil de desenvolvimento

No arquivo:

```
src/main/resources/application.properties
```

alterar:

```
spring.profiles.active=${APP_PROFILE:prod}
```

para:

```
spring.profiles.active=dev
```

Isso fará com que o projeto utilize o arquivo:

```
application-dev.properties
```

que contém as configurações do banco de dados local.

---

## 2️⃣ Configurar CORS para permitir acesso do frontend local

No arquivo:

```
GlobalCorsConfig.java
```

alterar:

```java
corsConfiguration.setAllowedOrigins(List.of("https://sistemadeanuncio.netlify.app"));
```

para:

```java
corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
```

Isso permite que o frontend executado localmente consiga acessar a API.

---

## 3️⃣ Configurar banco de dados MySQL

Certifique-se de possuir um banco MySQL rodando localmente com as seguintes configurações:

```
Database: sistema_anuncio_backendBD
Username: root
Password: root
Port: 3306
```

Essas configurações estão definidas no arquivo:

```
application-dev.properties
```

```
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_anuncio_backendBD
spring.datasource.username=root
spring.datasource.password=root
```

---

## 4️⃣ Executar o backend

Na pasta do backend execute:

```
mvn spring-boot:run
```

ou execute a classe principal da aplicação pela sua IDE.

A API ficará disponível em:

```
http://localhost:8080
```

---

# 💻 Frontend (Angular)

O frontend já está configurado para utilizar diferentes ambientes.

### Ambiente de desenvolvimento

Arquivo:

```
src/environments/environment.ts
```

```
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/'
};
```

### Ambiente de produção

Arquivo:

```
src/environments/environment.prod.ts
```

```
export const environment = {
  production: true,
  apiUrl: 'https://sistema-anuncio-backend-production.up.railway.app/'
};
```

Quando executado localmente com:

```
ng serve
```

o Angular automaticamente utiliza o arquivo **environment.ts**, apontando para a API local.

---

## 1️⃣ Instalar dependências

Na pasta do frontend execute:

```
npm install
```

---

## 2️⃣ Executar o projeto

```
ng serve
```

---

## 3️⃣ Acessar aplicação

Frontend:

```
http://localhost:4200
```

Backend:

```
http://localhost:8080
```

Após iniciar ambos os serviços, o sistema estará funcionando completamente em ambiente local.

---

# ⚠️ Observação

A aplicação foi originalmente configurada para execução em ambiente de produção com deploy online.
As alterações descritas acima permitem executar o sistema totalmente em ambiente local para fins de desenvolvimento e testes.

---
## Autor: Daniel Penelva de Andrade
