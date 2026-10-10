# Clean Code API

Startowe API Spring Boot, Java 21 i Maven. Pakiety organizowane według funkcji (`greeting`), wspólna obsługa błędów w `common`, konfiguracja w `config`.

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

Strona http://localhost:8080/ zawiera jedno pole tekstowe i przycisk „Wyślij”.
Formularz wysyła `POST /messages` jako `application/x-www-form-urlencoded` z polem `message`.
Endpoint odbiera `MessageRequestDto`, nie przetwarza ani nie zapisuje wiadomości i przekierowuje na formularz.

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
Encja `Message` i `MessageRepository` są przygotowane; formularz nadal nie zapisuje wiadomości.

Opcjonalne zmienne: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DDL_AUTO`.
Testy używają osobnej bazy w pamięci z `create-drop`.

Podgląd w IntelliJ i aplikacja korzystają z `AUTO_SERVER=TRUE`, aby równocześnie otwierać bazę H2. Sterownik H2 w IDE powinien mieć tę samą wersję co aplikacja.
