<div align="center">

# 🔄 UninaSwap

**Piattaforma desktop per la compravendita, lo scambio e il dono di oggetti tra utenti**


[

> Progetto universitario sviluppato per il corso di **Programmazione a Oggetti & Basi Dati** — Gruppo OOBD46  
> Università degli Studi di Napoli Federico II

</div>

***

## 📖 Descrizione

**UninaSwap** è un'applicazione desktop sviluppata in Java che consente agli utenti registrati di pubblicare annunci per vendere, scambiare o donare oggetti. Il sistema gestisce l'intero ciclo di vita di un annuncio: dalla creazione all'offerta, fino alla consegna finale, con una GUI intuitiva realizzata con Java Swing.

Il progetto adotta il pattern architetturale **Entity–DAO–Controller–GUI**, garantendo una netta separazione tra logica di dominio, accesso ai dati, business logic e interfaccia utente.

***

## ✨ Funzionalità principali

- 🔐 **Autenticazione** — Registrazione e login utente con sessione persistente
- 📢 **Gestione Annunci** — Creazione, modifica ed eliminazione di annunci in tre modalità:
  - `VENDITA` — con prezzo fisso e offerta minima
  - `SCAMBIO` — specificando gli oggetti desiderati in cambio
  - `REGALO` — cessione gratuita dell'oggetto
- 💬 **Gestione Offerte** — Invio, accettazione e rifiuto di offerte su annunci attivi
- 📦 **Gestione Consegne** — Tracciamento delle consegne associate alle offerte accettate
- 🗂️ **Categorie** — Organizzazione degli annunci per categoria merceologica
- 📊 **Statistiche** — Dashboard con statistiche aggregate sugli annunci e sulle transazioni
- 👤 **Profilo Account** — Visualizzazione e gestione del proprio account utente

***

## 🏗️ Architettura del progetto

Il progetto segue rigorosamente il pattern a **4 livelli**:

```
src/
├── entity/          # Classi di dominio (POJO)
│   └── enums/       # Enumerazioni (TipoAnnuncio, StatoOfferta, TipoConsegna)
├── DAO/             # Data Access Object — query SQL verso PostgreSQL
│   ├── AnnuncioDAO.java
│   ├── CategoriaDAO.java
│   ├── ConsegnaDAO.java
│   ├── OffertaDAO.java
│   └── UtenteDAO.java
├── Controller/      # Business logic e validazione
│   ├── AnnuncioController.java
│   ├── CategoriaController.java
│   ├── ConsegnaController.java
│   ├── OffertaController.java
│   └── UtenteController.java
├── GUI/             # Interfaccia grafica (Java Swing)
│   ├── MainFrame.java
│   ├── LoginFrame.java
│   ├── RegisterFrame.java
│   ├── MenuFrame.java
│   ├── AnnuncioFrame.java
│   ├── OffertaFrame.java
│   ├── ConsegnaFrame.java
│   ├── CategoriaFrame.java
│   ├── StatisticheFrame.java
│   └── AccountFrame.java
└── util/
    └── DBConnection.java   # Singleton per la connessione JDBC
```

***

## 🛠️ Tecnologie utilizzate

| Tecnologia | Versione | Utilizzo |
|------------|----------|----------|
| Java | 17+ | Linguaggio principale |
| Java Swing | — | Interfaccia grafica desktop |
| PostgreSQL | 15 | Database relazionale |
| JDBC | — | Connessione e query al DB |
| Eclipse IDE | — | Ambiente di sviluppo |

***

## ⚙️ Prerequisiti

Prima di avviare l'applicazione assicurati di avere installato:

- **Java JDK 17** o superiore
- **PostgreSQL 15** in esecuzione sulla porta `5433`
- **Eclipse IDE** (o altro IDE compatibile con progetti Java standard)
- Driver JDBC per PostgreSQL (`postgresql-xx.jar`) nel classpath

***

## 🚀 Configurazione e avvio

### 1. Clona la repository

```bash
git clone https://github.com/francesc05Cervera/OOBD46-UninaSwap.git
cd OOBD46-UninaSwap
```

### 2. Configura il database

Crea un database PostgreSQL chiamato `unina_swap`:

```sql
CREATE DATABASE unina_swap;
```

Poi esegui gli script SQL per creare le tabelle (se presenti nella cartella `sql/` o forniti separatamente dal gruppo).

### 3. Aggiorna le credenziali DB

Modifica il file `src/util/DBConnection.java` con le tue credenziali:

```java
static final String URL      = "jdbc:postgresql://localhost:5433/unina_swap";
static final String USER     = "postgres";
static final String PASSWORD = "la_tua_password";
```

> ⚠️ **Attenzione:** Non committare mai credenziali reali nel repository. Valuta l'uso di variabili d'ambiente o un file `.env` per la configurazione.

### 4. Avvia l'applicazione

Importa il progetto in Eclipse come **Java Project esistente**, aggiungi il driver JDBC al build path ed esegui `GUI/MainFrame.java` come applicazione Java.

***

## 🗃️ Modello dei dati

Le principali entità del sistema sono:

| Entità | Descrizione |
|--------|-------------|
| `Utente` | Utente registrato alla piattaforma |
| `Annuncio` | Annuncio pubblicato (vendita, scambio o regalo) |
| `Categoria` | Categoria merceologica dell'annuncio |
| `Offerta` | Proposta inviata da un utente su un annuncio |
| `Consegna` | Consegna associata a un'offerta accettata |

### Tipi di annuncio (`TipoAnnuncio`)

```
VENDITA  →  con prezzo di vendita e prezzo offerta minima
SCAMBIO  →  con lista di oggetti desiderati in cambio
REGALO   →  cessione gratuita, senza condizioni economiche
```

### Stati di un'offerta (`StatoOfferta`)

```
IN_ATTESA  →  offerta inviata, in attesa di risposta
ACCETTATA  →  offerta accettata dal venditore
RIFIUTATA  →  offerta declinata
```

***

## 👥 Autori

Progetto sviluppato dal **Gruppo OOBD46** per il corso di Programmazione a Oggetti & Basi Dati —  
Università degli Studi di Napoli Federico II (UNINA).

***

## 📄 Licenza

Questo progetto è stato realizzato a scopo accademico. Tutti i diritti sono riservati agli autori del gruppo OOBD46.
