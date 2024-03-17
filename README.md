# ADA LocateCar - Vehicle Rental System

## Description (English)

ADA LocateCar is a Java application designed to manage vehicle rentals, applying the concepts learned in class. The system allows for registering, modifying, and searching for vehicles, as well as managing rentals and returns for both individual and corporate clients.

### Key Features

- Register and modify vehicles.
- Search for vehicles by name.
- Register and modify individual and corporate clients.
- Rent and return vehicles for both types of clients.

### Business Rules

- Unique vehicle registration based on license plate.
- Vehicle types considered: SMALL, MEDIUM, and SUV.
- Rentals and returns include location, date, and time.
- Fractional hours in rentals are considered as a full day.
- Rented vehicles cannot be available for others.
- Unique registration of clients using CPF (for individuals) and CNPJ (for corporations).
- Return rules include discounts for long-term rentals.

### Pricing

- SMALL: R$ 100.00 per day
- MEDIUM: R$ 150.00 per day
- SUV: R$ 200.00 per day

### Optional Features

- Pagination for listings.
- Data persistence using files.

### Project Challenges and Learnings

We faced difficulties particularly with file handling for storing and managing the rental and return process of vehicles. Implementing the logic to make a vehicle available or unavailable to clients based on its rental status was challenging.

#### Difficulties

- **File Handling:** Integrating file operations for persisting rental data was complex, especially in tracking the state of vehicles (rented or available).
- **Rental Logic:** The process of renting out and returning vehicles, ensuring that a vehicle becomes unavailable once rented and then available again upon return, required careful management of object states and file data.

#### Easiness

- **Vehicle and Client Registration:** We found the process of registering vehicles and clients (both individuals and corporations) straightforward, applying basic OOP principles.

## Descrição (Português)

ADA LocateCar é uma aplicação Java projetada para gerenciar o aluguel de veículos, aplicando os conceitos vistos em aula. O sistema permite o cadastro, alteração e busca de veículos, além do gerenciamento de aluguéis e devoluções para clientes individuais e corporativos.

### Principais Recursos

- Cadastrar e alterar veículos.
- Buscar veículos por parte do nome.
- Cadastrar e alterar clientes (pessoa física e jurídica).
- Alugar e devolver veículos para ambos os tipos de clientes.

### Regras de Negócio

- Cadastro único de veículos pela placa.
- Tipos de veículos considerados: PEQUENO, MEDIO e SUV.
- Aluguéis e devoluções contemplam local, data e horário.
- Horas fracionadas em aluguéis contam como um dia inteiro.
- Veículos alugados não podem estar disponíveis para outros.
- Cadastro único dos clientes usando CPF (para pessoas físicas) e CNPJ (para empresas).
- Regras de devolução incluem descontos para aluguéis de longa duração.

### Valores

- PEQUENO: R$ 100,00 por dia
- MEDIO: R$ 150,00 por dia
- SUV: R$ 200,00 por dia

### Recursos Opcionais

- Paginação nas listagens.
- Persistência de dados usando arquivos.

### Desafios e Aprendizados do Projeto

Enfrentamos dificuldades especialmente com o manuseio de arquivos para armazenar e gerenciar o processo de aluguel e devolução dos veículos. Implementar a lógica para disponibilizar ou não um veículo para os clientes, baseando-se em seu status de aluguel, foi desafiador.

#### Dificuldades

- **Manuseio de Arquivos:** Integrar operações de arquivo para persistir os dados de aluguel foi complexo, especialmente no rastreamento do estado dos veículos (alugados ou disponíveis).
- **Lógica de Aluguel:** O processo de alugar e devolver veículos, garantindo que um veículo se torne indisponível uma vez alugado e depois disponível novamente ao ser devolvido, exigiu um gerenciamento cuidadoso dos estados dos objetos e dos dados em arquivo.

#### Facilidades

- **Cadastro de Veículos e Clientes:** Achamos o processo de cadastro de veículos e clientes (tanto indivíduos quanto corporações) direto, aplicando princípios básicos de POO.

## Team Members (Integrantes do Grupo)

1. Yasmin Barcelos
2. Paulo Henrique
3. Daniel Martins
