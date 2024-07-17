# Karate Club 

Tento projekt používá Java Spring Boot pro vývoj backendové části. Níže jsou uvedeny hlavní rysy a technologie, které náš projekt zahrnuje:

## Hlavní rysy

- **Java Spring Boot**: Používáme Spring Boot pro rychlý a efektivní vývoj backendové části.
- **Docker**: Projekt je kontejnerizován pomocí Dockeru, což usnadňuje nasazení a správu závislostí.
- **REST technologie**: Naše API je navrženo v souladu s REST principy.
- **Architektura MVC**: Projekt je strukturován podle architektury Model-View-Controller (MVC).

## Nepoužíváme

- **Elasticsearch**
- **Cache**
- **Princip messaging**
- **Interceptors**

## Stav frontendu

Frontendová část projektu momentálně není k dispozici.

## Design patterny

V projektu jsou implementovány následující design patterny:

- **DAO (Data Access Object)**
- **DTO (Data Transfer Object)**: Používáme v třídě `Member` pro přenos dat.
- **Builder**: Implementován v třídě `TournamentResult` pro vytváření instancí s více volitelnými parametry.
- **Strategy**: Používáme v třídě `NotificationStrategy` pro definování různých notifikačních strategií.

## Jak začít

1. Klonujte tento repozitář do svého lokálního prostředí.
2. Ujistěte se, že máte nainstalovaný Docker.
3. Zapustěte projekt podle instrukcí pro spuštění projektu s pomocí Dockeru.

## Kontakt

Pro více informací nebo dotazy, prosím kontaktujte Alinu Voropaevu nebo Annu Luzan na emailu [voropali@fel.cvut.cz].

Doufáme, že vám náš projekt bude užitečný!
