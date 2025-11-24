# Sistema de Chat Multicliente em Java

## Descrição

Sistema de chat em Java usando sockets TCP/IP no modelo cliente-servidor, com suporte a múltiplos clientes simultâneos através de threads.

## Estrutura do Projeto

```
ChatMulticliente/
├── src/
│   ├── servidor/
│   │   ├── ServidorChat.java      # Servidor principal (porta 12345)
│   │   └── ClienteServidor.java   # Thread que gerencia cada cliente
│   └── cliente/
│       └── ClienteChat.java        # Aplicação cliente
├── .gitignore
└── README.md
```

## Funcionalidades

✅ **Múltiplos clientes simultâneos** - Suporte para vários usuários conectados ao mesmo tempo  
✅ **Broadcast de mensagens** - Mensagens enviadas para todos os clientes conectados  
✅ **Comandos especiais**:
   - `/listar` - Lista todos os usuários conectados
   - `/sair` - Desconecta do servidor  
✅ **Notificações** - Aviso quando usuários entram ou saem do chat  
✅ **Thread-safe** - Sincronização correta para evitar problemas de concorrência  
✅ **Nomes únicos** - Geração automática de nomes para usuários anônimos  
✅ **Recepção assíncrona** - Thread separada para receber mensagens

## Como Compilar

No diretório raiz do projeto (`ChatMulticliente/`), execute:

```bash
javac -d out src/servidor/*.java src/cliente/*.java
```

Isso compilará todos os arquivos Java e colocará os `.class` no diretório `out/`.

## Como Executar

### 1. Iniciar o Servidor

Em um terminal, execute:

```bash
java -cp out servidor.ServidorChat
```

Você verá:
```
=================================
   SERVIDOR DE CHAT INICIADO
=================================
Aguardando conexões na porta 12345...
```

### 2. Iniciar Clientes

Em outros terminais (quantos você quiser), execute:

```bash
java -cp out cliente.ClienteChat
```

Cada cliente:
1. Conectará ao servidor
2. Receberá uma mensagem de boas-vindas
3. Será solicitado a digitar seu nome
4. Poderá enviar mensagens e usar comandos

## Exemplo de Uso

**Cliente 1:**
```
✓ Conectado ao servidor de chat!

=== BEM-VINDO AO CHAT ===
Digite seu nome:
João
✓ [João] entrou no chat
/listar

=== USUÁRIOS CONECTADOS ===
  🟢 João
  🟢 Maria
===========================

Olá pessoal!
[João]: Olá pessoal!
```

**Cliente 2:**
```
✓ Conectado ao servidor de chat!

=== BEM-VINDO AO CHAT ===
Digite seu nome:
Maria
✓ [Maria] entrou no chat
✓ [João] entrou no chat
[João]: Olá pessoal!
Oi João!
[Maria]: Oi João!
```

## Detalhes Técnicos

### Arquitetura

- **ServidorChat.java**: 
  - Cria um `ServerSocket` na porta 12345
  - Aceita conexões em loop infinito
  - Para cada cliente, cria uma instância de `ClienteServidor` e inicia uma thread

- **ClienteServidor.java** (implements Runnable):
  - Gerencia a comunicação com um cliente específico
  - Processa comandos (`/listar`, `/sair`)
  - Faz broadcast de mensagens para todos os clientes
  - Remove o cliente da lista ao desconectar

- **ClienteChat.java**:
  - Conecta ao servidor via `Socket`
  - Cria uma thread daemon para receber mensagens
  - Lê entrada do usuário e envia ao servidor

### Sincronização

O método `broadcast()` usa sincronização dupla:
```java
private synchronized void broadcast(String mensagem) {
    System.out.println(mensagem);
    synchronized (clientes) {
        for (ClienteServidor c : clientes) {
            if (c.saida != null) {
                c.saida.println(mensagem);
            }
        }
    }
}
```

Isso garante que:
1. Apenas uma thread por vez pode fazer broadcast
2. A lista de clientes não é modificada durante a iteração
3. Evita `ConcurrentModificationException`

## Configuração de IDEs

### IntelliJ IDEA

Para executar múltiplas instâncias do cliente:

1. Run → Edit Configurations
2. Selecione a configuração do ClienteChat
3. Modify options → Allow multiple instances
4. ✓ Marque a opção

### NetBeans

**Importante**: NetBeans não permite múltiplas instâncias pelo botão Run.

**Solução**: Use o terminal/CMD para executar múltiplos clientes:
- Cada janela de terminal = 1 cliente conectado
- Execute o comando `java -cp out cliente.ClienteChat` em cada terminal

## Por que Arquivos Aparecem Vermelhos no IDE?

### Explicação dos Arquivos Vermelhos

Quando você abre o projeto em uma IDE como IntelliJ IDEA ou Eclipse, pode ver alguns arquivos marcados em **vermelho** no explorador do projeto. Isso é **normal e esperado**. Veja por quê:

#### 1. Pasta `.idea` (IntelliJ IDEA)

- **O que é**: Diretório criado automaticamente pelo IntelliJ IDEA
- **Conteúdo**: Arquivos XML de configuração do projeto
  - `workspace.xml` - Configurações do espaço de trabalho
  - `modules.xml` - Estrutura de módulos
  - `misc.xml` - Configurações diversas
  - `.gitignore` - Arquivos a ignorar no versionamento

- **Por que ficam vermelhos**: 
  - São arquivos de metadados específicos da IDE
  - Geralmente estão no `.gitignore` (não devem ser versionados)
  - A IDE os marca em vermelho para indicar que estão ignorados pelo Git

- **Deve se preocupar?**: **NÃO!**
  - Esses arquivos são locais e pessoais
  - Cada desenvolvedor tem suas próprias configurações
  - Não afetam a compilação ou execução do projeto

#### 2. Arquivos Compilados (`out/`, `*.class`)

- **O que são**: Bytecode Java compilado
- **Por que ficam vermelhos**: Estão no `.gitignore`
- **Correto**: Arquivos compilados não devem ser versionados

#### 3. Arquivos do Sistema Operacional

- `.DS_Store` (macOS)
- `Thumbs.db` (Windows)
- Também ficam vermelhos por estarem no `.gitignore`

### Resumo

| Cor | Significado | Ação Necessária |
|-----|-------------|-----------------|
| 🔴 Vermelho | Arquivo ignorado pelo Git (`.gitignore`) | Nenhuma - é esperado |
| 🟢 Verde | Arquivo novo não commitado | Adicionar ao Git se necessário |
| 🔵 Azul | Arquivo modificado | Commitar se as mudanças forem válidas |
| ⚪ Branco/Preto | Arquivo normal versionado | Nenhuma |

**Conclusão**: Arquivos vermelhos no IDE são **normais** e indicam que estão sendo corretamente ignorados pelo controle de versão. Isso mantém o repositório limpo e focado apenas no código-fonte.

## Solução de Problemas

### "Erro ao conectar ao servidor"

- Verifique se o servidor está rodando
- Confirme que está usando a porta correta (12345)
- Verifique se não há firewall bloqueando a conexão

### "Address already in use"

- A porta 12345 já está em uso
- Feche o servidor anterior ou mude a porta

### Clientes não recebem mensagens

- Verifique a sincronização do método `broadcast()`
- Certifique-se de que a thread de recepção está rodando

## Recursos Implementados

- ✅ Múltiplos clientes simultâneos
- ✅ Broadcast para todos (inclusive remetente)
- ✅ Comandos /listar e /sair
- ✅ Notificações de entrada/saída
- ✅ Thread-safe (sincronização correta)
- ✅ Desconexão limpa com finally
- ✅ Nomes únicos automáticos
- ✅ Mensagens vazias ignoradas
- ✅ Tratamento robusto de exceções

## Autor

Projeto desenvolvido como parte da disciplina de Linguagem de Programação 3 - IFSUL TADS

## Licença

Projeto educacional - livre para uso acadêmico
