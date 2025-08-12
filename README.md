# 🎬 BadCine - Sistema de Locadora de Filmes

## 📄 Descrição do Projeto

**BadCine** é uma aplicação de desktop para gerenciamento de uma locadora de filmes, desenvolvida como projeto final para a disciplina de Programação Orientada a Objetos. O sistema permite a interação de dois tipos de usuários: clientes e administradores, cada um com suas respectivas funcionalidades, em uma interface gráfica moderna e intuitiva construída com JavaFX.

O projeto demonstra a aplicação prática de conceitos fundamentais de POO, como Herança, Polimorfismo, Encapsulamento e o uso de Padrões de Projeto como o DAO para a persistência de dados em arquivos CSV.

---

## ✨ Funcionalidades

### Para Clientes:
- **Cadastro e Login:** Novos usuários podem se cadastrar e usuários existentes podem se autenticar.
- **Catálogo Visual:** Navegação por um catálogo de filmes que exibe os pôsteres em uma grade dinâmica.
- **Busca em Tempo Real:** Filtra os filmes do catálogo conforme o usuário digita o título.
- **Carrinho de Compras:** Adiciona e remove filmes do carrinho antes de finalizar o aluguel.
- **Finalização de Aluguel:** Confirma o aluguel, atualizando o estoque de filmes de forma persistente.
- **Logout:** Encerra a sessão e retorna à tela de login.

### Para Administradores:
- **Autenticação Segura:** Login com credenciais de administrador.
- **Painel de Controle:** Acesso a uma dashboard para gerenciamento completo do sistema.
- **CRUD de Filmes:** Funcionalidade completa para **Criar, Ler, Atualizar e Excluir** filmes do catálogo, incluindo a seleção de imagens de pôster através de um seletor de arquivos.
- **Persistência Imediata:** Todas as alterações no catálogo são salvas automaticamente no arquivo `filmes.csv`.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Interface Gráfica:** JavaFX com FXML
- **Build e Gerenciamento de Dependências:** Gradle
- **Estilização:** CSS
- **Persistência de Dados:** Arquivos de Texto (CSV)
- **IDE:** IntelliJ IDEA

---

## 📋 Pré-requisitos

Para executar este projeto, você precisará ter os seguintes softwares instalados em sua máquina:

- **JDK (Java Development Kit):** Versão 17 ou superior.
- **IntelliJ IDEA:** Versão Community ou Ultimate.

_O **JavaFX SDK não é mais necessário** ser baixado separadamente, pois o Gradle cuidará disso automaticamente._

---

## 🚀 Configuração e Execução

O projeto utiliza o **Gradle** para gerenciar as dependências e o processo de build, tornando a configuração muito mais simples.

1.  **Clone o Repositório:**
    ```bash
    git clone 
    ```
    *(Lembre-se de substituir pela URL real do seu repositório)*

2.  **Abra no IntelliJ IDEA:**
    - Abra o IntelliJ e selecione `File -> Open...`.
    - Navegue até a pasta do projeto clonado e selecione o arquivo `build.gradle`. Clique em `OK`.
    - O IntelliJ perguntará se você confia no projeto. Clique em **"Trust Project"**.
    - O IntelliJ irá automaticamente detectar que é um projeto Gradle e começará a sincronizar e baixar as dependências (incluindo o JavaFX). Aguarde este processo terminar.

3.  **Execute a Aplicação:**
    - No canto superior direito da janela do IntelliJ, você encontrará uma configuração de execução já pronta, provavelmente chamada **`Executar`** ou **`BadCine [run]`**.
    - Certifique-se de que esta configuração está selecionada.
    - Clique no botão verde de **Play (▶️)** ao lado dela.

    _O Gradle irá compilar e executar a aplicação. A tela de login do BadCine aparecerá em instantes._

    **Alternativa (se a configuração não aparecer):**
    - No lado direito da janela, abra a aba vertical **`Gradle`**.
    - Navegue por `Tasks -> application`.
    - Dê um duplo-clique na tarefa **`run`**.

---

## 🔑 Credenciais de Acesso

Você pode usar as seguintes credenciais para testar a aplicação:

- **Perfil de Administrador:**
    - **Login:** `admin`
    - **Senha:** `777`

- **Perfil de Cliente:**
    - **Login:** `mateus`
    - **Senha:** `123`

_Você também pode criar novos usuários através da tela de cadastro._

---
