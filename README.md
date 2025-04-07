# 🩸 API de Registro de Casos de Serial Killers

Este projeto é uma API RESTful desenvolvida com **Quarkus** para registrar e consultar casos envolvendo **serial killers**, suas **vítimas** e os **crimes** cometidos. A aplicação visa demonstrar conceitos como modelagem de entidades com relacionamentos complexos, validação de dados, tratamento de erros e documentação com Swagger/OpenAPI.

---

## 📌 Tecnologias Utilizadas

- Java 17+
- Quarkus (versão mais recente)
- Hibernate + Panache ORM
- Banco de Dados H2 (persistência em memória)
- Bean Validation
- Swagger UI (OpenAPI)
- Maven

---

## 🧠 Domínio de Negócio

### 🧍 Entidades Principais

#### 🔪 SerialKiller
- `id` (UUID)
- `nomeCompleto`
- `alcunha`
- `nacionalidade`
- `ativo` (boolean)
- **Relacionamento:** `OneToMany` com `Crime`

#### 💀 Crime
- `id` (UUID)
- `descricao`
- `data`
- `local`
- `tipoCrime` (ENUM: ASSASSINATO, ABUSO, TORTURA, ETC)
- `ativo` (boolean)
- **Relacionamentos:**
  - `ManyToOne` com `SerialKiller`
  - `ManyToMany` com `Vitima`

#### 🧍‍♀️ Vitima
- `id` (UUID)
- `nome`
- `idade`
- `genero`
- `sobrevivente` (boolean)
- **Relacionamento:** `ManyToMany` com `Crime`

---

## 🔁 Relacionamentos

- **One-to-Many:** SerialKiller → Crimes  
- **Many-to-Many:** Crime ↔ Vítimas  
- **Optional One-to-One (futuramente):** exemplo: Investigador Responsável, Perfil Psicológico

---

## 🎯 Funcionalidades

### 📌 SerialKiller
- Criar registro de serial killer
- Atualizar dados
- Listar todos
- Obter detalhes por ID
- **Soft Delete:** arquivar (não apagar do banco)

### 📌 Crime
- Criar novo crime
- Atualizar informações
- Listar todos
- Obter por ID
- **Soft Delete:** arquivar crime

### 📌 Vítima
- Registrar nova vítima
- Atualizar dados
- Listar vítimas
- Obter por ID
- **Delete definitivo:** atender solicitações de privacidade

---

## 🚨 Validações e Tratamento de Erros

- Todos os campos obrigatórios validados com Bean Validation
- Mensagens de erro claras e com códigos HTTP apropriados
- Tratamento global de exceções

---

## 📚 Documentação da API

Acesse o Swagger UI em:  
http://localhost:8080/q/swagger-ui/

Você encontrará descrições completas dos endpoints, exemplos de uso e respostas esperadas.

---

## 📦 Executando o Projeto

```bash
./mvnw quarkus:dev
```

O projeto será iniciado em http://localhost:8080

---

## 💌 Exemplos de Uso (via curl)

### Criar Serial Killer
```bash
curl -X POST http://localhost:8080/serial-killers \
  -H "Content-Type: application/json" \
  -d '{"nomeCompleto": "John Wayne Gacy", "alcunha": "Killer Clown", "nacionalidade": "Americana"}'
```

### Criar Crime
```bash
curl -X POST http://localhost:8080/crimes \
  -H "Content-Type: application/json" \
  -d '{"descricao": "Ataque brutal", "data": "1978-05-22", "tipoCrime": "ASSASSINATO"}'
```

---

## 👻 Observações

Este projeto é fictício e acadêmico, sem fins educativos.  
Nenhuma apologia a crimes reais. Todos os dados usados são públicos ou simulados.

---

## 🪦 Autora

**Mylena Oliveira**  
[dev.mylenaoliveira@gmail.com](mailto:dev.mylenaoliveira@gmail.com)
