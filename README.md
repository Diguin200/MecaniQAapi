# MecaniQA API

API REST em Spring Boot (Java 21) para a MecaniQA. Etapa 3: persistência em **MySQL** com **JDBC puro**
(sem JPA/Hibernate/ORM), Mappers de ResultSet, exceções customizadas e camada Service.

## Arquitetura

```
Controller (DTOs + try/catch)  ->  Service (regras de negócio)  ->  Repository (JDBC + SQL manual)  ->  MySQL
```

- `controller/`  recebe DTOs, chama o Service e devolve o status HTTP (tratamento manual com try/catch).
- `service/`     validações, cálculos (valor total do pedido) e comunicação com os repositórios.
- `repository/`  interface genérica `Repository<T, ID>` + implementações JDBC (`INSERT/SELECT/UPDATE/DELETE` na mão).
- `mapper/`      `*ResultSetMapper` (ResultSet -> modelo) e `*Mapper` (modelo <-> DTO).
- `exception/`   exceções customizadas (`DadosInvalidosException`, `PecaNaoEncontradaException`, ...).
- `config/`      `ConnectionFactory` (conexões via `DriverManager`).

## Como executar

1. Crie o banco e os dados iniciais: `mysql -u root -p < database/mecaniqa.sql`
2. Ajuste o acesso ao MySQL (variáveis `DB_URL`, `DB_USER`, `DB_PASSWORD` ou `application.properties`).
3. Rode: `./mvnw spring-boot:run` (porta **8081**).

## Endpoints

| Recurso | Rotas |
|---|---|
| Peças | `GET/POST /api/pecas`, `GET/PUT/DELETE /api/pecas/{codigo}` |
| Serviços | `GET/POST /api/servicos`, `GET/PUT/DELETE /api/servicos/{codigo}` |
| Ordens de serviço | `GET/POST /api/ordens-servico`, `GET/DELETE /api/ordens-servico/{id}`, `PATCH /api/ordens-servico/{id}/status` |
| Pedidos de peças | `GET/POST /api/pedidos-pecas`, `GET/DELETE /api/pedidos-pecas/{id}`, `POST /api/pedidos-pecas/{id}/itens`, `PATCH /api/pedidos-pecas/{id}/status` |

Status HTTP: `200/201/204` sucesso, `400` dados inválidos, `404` não encontrado, `409` regra de negócio, `500` erro de banco.

## Exemplo (PowerShell)

```powershell
# item com o código de uma peça existente (veja database/mecaniqa.sql)
$body = @{ codigoPeca = "11111111-1111-1111-1111-111111111111"; quantidade = 2 } | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8081/api/pedidos-pecas/1/itens" -Method Post -ContentType "application/json" -Body $body
```
