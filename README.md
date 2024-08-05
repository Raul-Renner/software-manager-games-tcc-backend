
# Ferramenta para gerenciamento direcionada para desenvolvimento de jogos - BACKEND
Essa ferramenta de gerenciamento de projetos é direcionada para desenvolvimento de jogos, que visa solucionar e adaptar a multidisciplinaridade encontrada na área por dos diferentes setores existentes, como, por exemplo, os setores de design e software. O grande diferencial dessa ferramenta para outras ferramentas existentes é que essa ferramenta desenvolvida, permite a criação de novas colunas(etapas), caso seja necessária para cada projeto, além de possuir o bloqueio de atividades, que ocorrem quando uma atividade necessita que outra seja desenvolvida totalmente, para posteriormente estar disponível para também ser desenvolvida. 


## Instalação

docker build -t minha-api-spring .

docker run --name dbgames -e POSTGRES_USER=usuario -e POSTGRES_PASSWORD=senha -e POSTGRES_DB=nome_do_banco -p 5432:5432 -d postgres

docker run -d --name minha-api-spring  -p 9090:8081 minha-api-spring

## Ferramentas e técnicas
  * Framework Angular 16
  * Java 17
  * Framework Spring boot 3.2.3
  * PostgresSQL
  * SOLID

## Requisitos

  1. [RF001] Registrar Organização
  O sistema permite que as equipes/organizações possam se registrar no sistema e
  utilizar a ferramenta de gerenciamento.

  2. [RF002] Realizar Login
  O sistema permite que os usuários registrados no sistema de uma organização possam
  realizar login no sistema.

  3. [RF003] Atualizar Dados da Organização
  O sistema permite que o usuário com perfil de ADMINISTRADOR de uma organização
  registrada possa atualizar os dados da sua organização.

  4. [RF004] Criar Projeto
  O sistema permite que o usuário com perfil de ADMINISTRADOR de uma organização
  registrada, possa registrar os projetos que serão gerenciados no sistema.

  5. [RF005] Remover Projeto
  O sistema permite que o usuário com perfil de ADMINISTRADOR de uma organização
  registrada possa remover projetos do sistema.
  
  6. [RF006] Atualizar Dados do Projeto
  O sistema permite que o usuário com perfil de ADMINISTRADOR de uma organização
  registrada possa atualizar o nome, a descrição e remover/adicionar colaboradores do
  sistema.

  7. [RF007] Visualizar Projetos
  Os usuários com perfil: ADMINISTRADOR e GERENTE, de uma organização
  registrada, podem visualizar todos os projetos de sua organização que estão registrados no
  sistema.

  8. [RF008] Visualizar Meus Projetos
  O sistema permite que o usuário pode alterar o setor de desenvolvimento da atividade
  no board e os cards disponíveis em específico.

  9. [RF009] Filtrar Projetos
  O sistema permite que o usuário pode alterar o setor de desenvolvimento da atividade
  no board e os cards disponíveis em específico.
  Os usuários com perfil: ADMINISTRADOR e GERENTE de uma organização
  registrada podem filtrar os projetos, através dos seguintes classificações: Em Progresso e
  Concluída, status esse que se dá conforme o andamento de cada projeto.

  10. [RF010] Visualizar Detalhes do Projeto
  Os usuários com perfil: ADMINISTRADOR, GERENTE e DESENVOLVEDOR
  podem visualizar as informações permitidas de cada projeto.

  11. [RF011] Cadastrar Colaborador
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  possam registrar novos colaboradores da sua organização no sistema.
  
  12. [RF012] Remover Colaborador
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  possam remover colaboradores da sua organização do sistema.

  13. [RF013] Remover Colaborador do Projeto
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  possam remover colaboradores de projeto(s) que fazem parte.

  14. [RF014] Alterar Cargo e Projetos do Colaborador
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  possam alterar o cargo, removê-lo e adicioná-lo em novos projetos.

  15. [RF015] Visualizar Colaboradores
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  possam visualizar todos os colaboradores que fazem parte da organização.

  16. [RF016] Buscar Colaboradores Por: Projeto
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  podem visualizar colaboradores por projeto.

  17. [RF017] Visualizar Dados Colaborador
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE
  possam visualizar as informações permitidas de cada usuário.

  18. [RF018] Visualizar Projetos Colaborador
  O sistema permite que os usuários com perfil: ADMINISTRADOR e GERENTE,
  ao visualizar as informações permitidas de cada usuário, também possam visualizar os projetos
  em que o mesmo está agregado.
  O sistema permite também que os usuários com o perfil: DESENVOLVEDOR,
  ADMINISTRADOR e GERENTE possam visualizar os projetos que os mesmos participam.
  
  19. [RF019] Visualizar Board
  O sistema permite que todos os usuários registrados no sistema tenham acesso ao
  board de gerenciamento de cada projeto registrado no sistema.

  20. [RF020] Criar Coluna no Board
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam criar colunas no board de cada projeto.

  21. [RF021] Editar Coluna do Board
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam editar colunas que foram registradas no board do projeto.

  22. [RF022] Remover Coluna do Board
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam remover colunas que foram registradas no board do projeto. Ao remover a coluna, as
  atividades que lá estavam serão direcionadas para a coluna de ToDo.

  23. [RF023] Criar Atividade
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam criar novas atividades para o projeto que serão demonstradas no board do mesmo
  projeto.

  24. [RF024] Remover Atividade
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam remover atividades do sistema de um projeto específico.

  25. [RF025] Editar Atividade
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam editar atividades registradas no sistema de um projeto específico.
 
  26. [RF026] Alterar Setor da Atividade
  O sistema permite que todos os perfis de usuário possam alterar o setor (coluna) de
  uma atividade registrada no sistema.

  27. [RF027] Inscrever-se na Atividade
  O sistema permite que todos os perfis de usuário possam se designar nas atividades
  que estão disponíveis no board de um projeto registrado.

  28. [RF028] Visualizar Detalhes da Atividade
  O sistema permite que todos os perfis de usuário possam visualizar as informações
  permitidas das atividades que estão disponíveis no board de um projeto registrado.

  29. [RF029] Visualizar Atividades Depedentes
  O sistema permite que todos os perfis de usuário possam visualizar a lista de dependências
  de uma atividade que está disponível no cards de um projeto registrado.

  30. [RF030] Remover Atividade Dependente
  O sistema permite que usuários com perfil: ADMINISTRADOR e GERENTE
  possam remover atividades da lista de dependências de uma atividade dependente registrada no
  sistema de um projeto específico.


 ## Algumas telas da ferramenta
[Imagem 1](https://github.com/Raul-Renner/software-manager-games-tcc-backend/blob/main/src/main/resources/screens-backend/image-backend-1.png)

[Imagem 2](https://github.com/Raul-Renner/software-manager-games-tcc-backend/blob/main/src/main/resources/screens-backend/image-backend-2.png)

[Imagem 3](https://github.com/Raul-Renner/software-manager-games-tcc-backend/blob/main/src/main/resources/screens-backend/image-backend-3.png)


[Imagem 4](https://github.com/Raul-Renner/software-manager-games-tcc-backend/blob/main/src/main/resources/screens-backend/image-backend-4.png)
