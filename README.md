# Clean Code API

Spring Boot, Java 21 i Maven. Aplikacja przyjmuje wiadomości od klientów (płatności, wysyłka), tworzy z nich zgłoszenia,
planuje maile do działów (cron) i pokazuje wzorce projektowe oraz zasady czystego kodu z laboratoriów.

Strony do prezentacji:

- http://localhost:8080/ — formularz wiadomości, zadania cron, przycisk „Uruchom zadania teraz”
- http://localhost:8080/tickets — zgłoszenia i zmiana ich stanu
- http://localhost:8080/history — historia wywołań (singleton + iterator) i statystyki (strumienie, obserwator)

## Mapa wymagań

### Lab 1 — wzorce konstrukcyjne i strukturalne (6 × 3 pkt)

| Wzorzec | Gdzie | Jak pokazać |
|---|---|---|
| Singleton | `common/RequestHistory` | `/history` |
| Builder (ręczny) | statyczne klasy `Builder` w `CronEntity`, `TicketEntity`, `InquiryEntity`, `Message`, `MessageRequestDto` | kod (`CronScheduler`, `TicketFactory`, `InquiryFacade`) |
| Factory (Simple Factory) | `domain/ticket/TicketFactory` | wysłanie wiadomości, numer i tytuł na `/tickets` |
| Adapter | `infrastructure/adapter/FormatterLibraryAdapter` → `library/FormatterLibrary` | treść zadania cron |
| Decorator | `infrastructure/adapter/decorator/*`, składanie w `config/ImplementationConfig` | „Dzień dobry” i stopka w treści zadania cron |
| Facade | `domain/inquiry/InquiryFacade` | wysłanie wiadomości |

### Lab 2 — wzorce czynnościowe (6 × 3 pkt)

| Wzorzec | Gdzie | Jak pokazać |
|---|---|---|
| Strategy | `domain/message/strategy/*`, wybór w `MessageService` | wysłanie PAYMENT / SHIPPING |
| Template Method | `AbstractMessageStrategy.saveMessage()`, `AbstractCronCommand.execute()` | wysłanie wiadomości, „Uruchom zadania teraz” |
| Observer | `domain/message/observer/*` | `/history` („Observer: zapisane od startu”), konsola `[AUDYT]` |
| Command | `infrastructure/cron/command/*`, invoker `CronScheduler` | „Uruchom zadania teraz” |
| State | `domain/ticket/state/*`, kontekst `TicketEntity` | przyciski na `/tickets` |
| Iterator | `common/NewestFirstIterator`, `RequestHistory implements Iterable` | `/history` (od najnowszego) |

### Lab 3 — SOLID i czysty kod (21 pkt)

| Zadanie | Gdzie |
|---|---|
| 1. LSP (3 pkt) | `TicketNumberGenerator`/`DatedTicketNumberGenerator`, `TicketPriorityResolver`/`KeywordTicketPriorityResolver`, `EmailService`/`CountingEmailService`; podstawienie w `ImplementationConfig`, test `LiskovSubstitutionTest` |
| 2. DIP (6 pkt) | `TextFormatter` → `AbstractTextFormatter` → `FormatterLibraryAdapter`; `MessageStrategy` → `AbstractMessageStrategy` → `Payment/ShippingMessageStrategy`; `CronCommand` → `AbstractCronCommand` → `SendEmailCommand` |
| 3. ISP (3 pkt) | grube `TicketOperations`, `CronOperations`, `RequestHistoryOperations` (`@Deprecated`, do porównania) → `TicketCreator`/`TicketReader`/`TicketStateChanger`, `CronTaskQueue`/`CronTaskRunner`, `RequestHistoryRecorder`/`RequestHistoryReader` |
| 4. Nazwy (2 pkt) | `PriorityEnum` → `TicketPriority`, `SampleSingleton` → `RequestHistory`, `createInquryForPaymentDepartment` → `createInquiry`, `TicketNumberAllocator` → `TicketNumberGenerator` |
| 5. Metody ≤ 20 linii (3 pkt) | m.in. `FormatterLibrary.formatWords` podzielona na kroki |
| 9. Wyjątki zamiast kodów błędów (3 pkt) | `CronSchedulingException` (wcześniej zwracany `CronStatus`), `UnsupportedMessageTypeException`, `InvalidMessageException`, `IllegalTicketStateTransitionException` |
| 10. Magiczne liczby (1 pkt) | `EXECUTION_DELAY_MS`, `EMAIL_LINE_LENGTH`, `MAX_MESSAGE_LENGTH`, `CORS_MAX_AGE_SECONDS`… |

### Lab 4 — programowanie funkcyjne i testy (19 pkt)

| Zadanie | Gdzie |
|---|---|
| 1. 3 interfejsy funkcyjne + lambdy (6 pkt) | `MessageContentValidator` (`MessageContentValidators`, `ForbiddenWordsValidator`), `ContentTransformer` (`ContentTransformers`), `PriorityRule` (`KeywordTicketPriorityResolver`) |
| 2. Strumienie na 3 kolekcjach (3 pkt) | `dashboard/StatisticsService` (wiadomości, zadania cron, zgłoszenia, historia) |
| 5. Testy jednostkowe 5 × 4 klasy (10 pkt) | `RequestHistoryTest`, `TicketEntityTest`, `MessageServiceTest`, `FormatterLibraryAdapterTest` (dodatkowo `LiskovSubstitutionTest`, `TextFormatterDecoratorTest`) |

## Uruchomienie

Wymagana Java 21 (sprawdź `java -version`). Maven Wrapper pobierze Maven automatycznie.

```sh
./mvnw spring-boot:run
```

Jeśli Maven wybiera inną Javę na macOS z Homebrew:

```sh
JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home ./mvnw spring-boot:run
```

API: http://localhost:8080

## Formularz Thymeleaf

Strona http://localhost:8080/ zawiera wybór typu, pole tekstowe i przycisk „Wyślij”.
Formularz wysyła `POST /messages` z polami `type` i `message`. Wiadomość przechodzi walidację, trafia do strategii
swojego typu, a ta przez fasadę tworzy zgłoszenie i zadanie cron z mailem do działu. Błędy pokazywane są nad formularzem.

## Endpointy

- `GET /api/v1/greetings` — przykładowa odpowiedź JSON.
- `POST /api/v1/greetings` — JSON `{"name":"Ala"}`; imię wymagane, maksymalnie 100 znaków.
- `GET /actuator/health` — stan aplikacji.

```sh
curl http://localhost:8080/api/v1/greetings
curl -X POST http://localhost:8080/api/v1/greetings -H 'Content-Type: application/json' -d '{"name":"Ala"}'
```

Błędy zwracane jako `application/problem+json`. Błędy walidacji zawierają tablicę `errors` z polami `field` i `message`.

## Frontend

CORS domyślnie dopuszcza `http://localhost:3000` i `http://localhost:5173`.

```js
const response = await fetch('http://localhost:8080/api/v1/greetings');
const data = await response.json();
console.log(data.message);
```

Zmienne środowiskowe: `PORT` (domyślnie 8080), `CORS_ALLOWED_ORIGINS` (adresy rozdzielone przecinkami). Przykład:

```sh
CORS_ALLOWED_ORIGINS=https://frontend.example.com PORT=8081 ./mvnw spring-boot:run
```

Projekt nie zawiera jeszcze uwierzytelniania. CORS nie zastępuje autoryzacji — przed udostępnieniem prywatnych danych dodaj zabezpieczenia.

## Testy i budowanie

```sh
./mvnw test
./mvnw clean package
java -jar target/clean-code-api-0.0.1-SNAPSHOT.jar
```

## Baza danych

Spring Data JPA korzysta z Hibernate i lokalnej bazy H2 w pliku `data/clean-code.mv.db`.
Baza powstaje automatycznie przy uruchomieniu; dane pozostają po restarcie.
`spring.jpa.hibernate.ddl-auto=update` tworzy i aktualizuje tabele na podstawie encji.
`schema.sql` poszerza kolumny `content` w starszej bazie (Hibernate z `update` nie zmienia typu istniejących kolumn).

Opcjonalne zmienne: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DDL_AUTO`.
Testy używają osobnej bazy w pamięci z `create-drop`.

Podgląd w IntelliJ i aplikacja korzystają z `AUTO_SERVER=TRUE`, aby równocześnie otwierać bazę H2. Sterownik H2 w IDE powinien mieć tę samą wersję co aplikacja.
