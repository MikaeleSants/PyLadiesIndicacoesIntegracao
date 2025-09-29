

## ✅ Casos de Teste — API Profissionais

### 🔹 1. Cadastro de Profissional (`POST /profissionais`)

| ID   | Descrição | Entrada | Resultado Esperado |
|------|-----------|---------|---------------------|
| CT01 | Cadastro válido | JSON com todos os campos obrigatórios | `201 Created` com corpo contendo o profissional salvo |
| CT02 | Cadastro com campos extras | JSON com campos personalizados | `201 Created` e campos extras armazenados |

---

### 🔹 2. Listar Todos os Profissionais (`GET /profissionais`)

| ID   | Descrição | Entrada | Resultado Esperado |
|------|-----------|---------|---------------------|
| CT03 | Listagem padrão | Nenhuma | `200 OK` com lista de profissionais |

---

### 🔹 3. Buscar por Área (`GET /profissionais/area/{area}`)

| ID   | Descrição | Entrada | Resultado Esperado |
|------|-----------|---------|---------------------|
| CT04 | Área existente | `Desenvolvimento` | `200 OK` com lista de profissionais da área |

---

### 🔹 4. Buscar por ID (`GET /profissionais/{id}`)

| ID   | Descrição | Entrada | Resultado Esperado |
|------|-----------|---------|---------------------|
| CT05 | ID válido | ID existente | `200 OK` com dados do profissional |

---

### 🔹 5. Listar Áreas (`GET /profissionais/areas`)

| ID  | Descrição | Entrada | Resultado Esperado |
|-----|-----------|---------|---------------------|
| CT6 | Áreas distintas | Nenhuma | `200 OK` com lista de áreas únicas |

---

### 🔹 6. Editar Profissional (`PUT /profissionais/{id}`)

| ID  | Descrição | Entrada | Resultado Esperado |
|-----|-----------|---------|---------------------|
| CT7 | Edição completa | JSON com novos dados | `200 OK` com profissional atualizado |
| CT8 | Edição parcial | JSON com apenas um campo alterado | `200 OK` com dados mesclados |

---

### 🔹 7. Deletar Profissional (`DELETE /profissionais/{id}`)

| ID  | Descrição | Entrada | Resultado Esperado |
|-----|-----------|---------|---------------------|
| CT9 | Exclusão válida | ID existente | `204 No Content` |

---

### 🔹 8. Performance (RNF)

| ID   | Descrição | Entrada | Resultado Esperado |
|------|-----------|---------|---------------------|
| CT10 | Tempo de resposta | Qualquer requisição | Tempo < 500ms |

