![JCryptoPayAPI](/header.png)

# JCryptoPayAPI [![CryptoPay API Docs](https://img.shields.io/badge/CryptoPay%20API-Docs-blue)](https://help.crypt.bot/crypto-pay-api) [![JCryptoPayAPI JitPack](https://jitpack.io/v/Jone501/JCryptoPayAPI.svg)](https://jitpack.io/#Jone501/JCryptoPayAPI)
Библиотека, позволяющая работать с [CryptoPay API](https://help.crypt.bot/crypto-pay-api) прямо в вашем Java-приложении. Поддерживает как синхронную, так и асинхронную работу с API.

## Подключение зависимости

### Maven
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
    <groupId>com.github.Jone501</groupId>
    <artifactId>JCryptoPayAPI</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.Jone501:JCryptoPayAPI:1.0.0'
}
```


## Начало работы
Для работы с JCryptoPayAPI, необходимо создать клиент. Клиент выбирается в зависимости от выбранной вами сети:

#### Основная сеть
```java
CryptoApp client = new CryptoApp("TOKEN");
```

#### Тестовая сеть
```java
CryptoApp client = new CryptoAppTest("TOKEN");
```

Также при создании клиента можно задать конфигурацию polling'a, о которой будет сказано далее. Делается это следующим образом:
```java
CryptoApp client = new CryptoApp("TOKEN", new PollingConfiguration()
        .maxTrackerLifetime(Duration.ofHours(1))
        .period(Duration.ofSeconds(5)));
```

Чтобы убедиться в том, что токен указан верно, достаточно вызвать метод `getMe` следующим образом:

#### Синхронный вызов
```java
AppInfo appInfo = client.getMe();
System.out.println(appInfo.name());
```
#### Асинхронный вызов
```java
client.getMeAsync(ResponseHandler.create(
        appInfo -> System.out.println(appInfo.name()),
        error -> System.out.println(error.name())
));
```

В случае синхронного вызова, метод возвращает информацию о приложении или, в случае ошибки, бросает исключение `CryptoPayApiException`, в котором содержится информация об ошибке.

В случае асинхронного вызова, в метод передаётся объект, который обрабатывает результат этого метода. Его можно создать, используя статический метод `ResponseHandler.create` и передав в него обработчики результата и ошибки.

Все асинхронные методы работают по такому же принципу, относительно их синхронных версий, поэтому, ради сокращения текста, далее будут показаны только синхронные методы. Просто помните, что у каждого метода ниже есть асинхронный собрат

## Работа с валютой
В JCryptoPayAPI и фиатные валюты, и криптовалюты обобщены в один класс `Currency`, Что делает работу с API в разы удобнее.

Для работы с валютами есть удобный класс `Currencies`, но перед тем, как его использовать, необходимо загрузить в него актуальную информацию о валютах. Делается это следующим образом:

```java
client.loadCurrencies();
```
Мы рекомендуем вызывать данный метод сразу после создания клиента, чтобы в последствии не было ошибок с использованием класса `Currencies`.

Для получения валют, в `Currencies` есть множество методов:

```java
CurrencyList all();           // Возвращает список всех валют
CurrencyList stablecoins();   // Возвращает список всех стейблкоинов
CurrencyList blockchain();    // Возвращает список всех блокчейн-криптовалют
CurrencyList crypto();        // Возвращает список всех криптовалют
CurrencyList fiat();          // Возвращает список всех фиатных валют
Currency byCode(String code); // Возвращает валюту по её буквенному коду
```

`CurrencyList` - это удобный класс, который позволяет фильтровать и работать сразу с целым массивом валют.

Также есть методы `BTC`, `USD` и т.п., которые являются сокращениями метода `byCode` и возвращают валюту, с одноимённым буквенным кодом. Все эти методы позволяют комфортно работать с валютами, что будет видно далее.

## Polling
Polling - это один из способов получения обновлений от API, который работает благодаря периодической отправке запросов к API. Ранее вы видели, что при создании клиента можно настроить конфигурацию polling'a, сейчас об этом будет рассказано подробнее.

Каждый клиент содержит `PollingManager`, который и отправляет периодические запросы на сервер API. Чтобы этот менеджер начал получать уведомления для определённого объекта, необходимо создать трекер для этого объекта. В JCryptoPayAPI уже есть два готовых трекера: `CheckPollingTracker` и `InvoicePollingTracker` для чеков и счетов соответственно. О том, как их использовать, будет рассказано ниже.

По умолчанию все трекеры не имеют ограничения на время жизни, однако его можно задать в `PollingConfiguration`, используя метод `maxTrackerLifetime`. Также, используя метод `period`, можно задать период, с которым будут отправляться запросы. По умолчанию период равен трём секундам.

Также, при помощи метода `PollingConfiguration.fromJsonFile` можно загружать конфигурации polling'а из JSON файлов следующего формата:
```json
{
  "max_tracker_lifetime": 60000,
  "period": 1500
}
```
_Время указывается в **миллисекундах**_

## Счета
_Здесь и далее все методы, их параметры и возвращаемые ими объекты реализованы и названы в соответствии с [официальной документацией CryptoPay API](https://help.crypt.bot/crypto-pay-api)_

Для работы со счетами есть следующие методы:
```java
Invoice createInvoice(CreateInvoiceRequest.Builder builder); // Создаёт счёт
Boolean deleteInvoice(long invoiceId);                       // Удаляет счёт с данным ID
Invoice getInvoice(long invoiceId);                          // Получает счёт с данным ID
Invoice[] getInvoices(GetInvoicesRequest.Builder builder);   // Получает массив счетов, соответствующих данным условиям
```
Все необходимые билдеры создаются вызовом статического метода `builder()` у необходимого класса.

Пример создания счёта:
```java
Invoice invoice = client.createInvoice(CreateInvoiceRequest
        .builder(Currencies.BTC(), 5.01)
        .acceptedAssets(Currencies.BTC(), Currencies.USDT())
        .allowComments(true));
```

Чтобы отслеживать счёт, используя систему polling'а, необходимо создать трекер следующим образом:
```java
InvoicePollingTracker invoiceTracker = client.pollingManager().trackInvoice(invoice);
```

Чтобы добавить обработчики различных событий, необходимо воспользоваться следующими методами:
```java
invoicePaid(Consumer<Invoice> handler);    // Задаёт обработчик оплаты счёта
invoiceDeleted(Runnable handler);          // Задаёт обработчик удаления счёта
invoiceExpired(Consumer<Invoice> handler); // Задаёт обработчик просрочивания счёта
onError(Consumer<Error> handler);          // Задаёт обработчик ошибки API
trackEnds(Runnable handler);               // Задаёт обработчик остановки трекера
```

## Чеки
Для работы с чеками есть следующие методы:
```java
Check createCheck(CreateCheckRequest.Builder builder); // Создаёт чек
Boolean deleteCheck(long checkId);                     // Удаляет чек с данным ID
Check getCheck(long checkId);                          // Получает чек с данным ID
Check[] getChecks(GetChecksRequest.Builder builder);   // Получает массив чеков, соответствующих данным условиям
```

Пример создания чека:
```java
Check check = client.createCheck(CreateCheckRequest
        .builder(Currencies.USDT(), 1.337)
        .pinToUsername("jone501"));
```

Чтобы отслеживать чек, используя систему polling'а, необходимо создать трекер следующим образом:
```java
CheckPollingTracker checkTracker = client.pollingManager().trackCheck(check);
```

Чтобы добавить обработчики различных событий, необходимо воспользоваться следующими методами:
```java
checkActivated(Consumer<Invoice> handler); // Задаёт обработчик активации чека
checkDeleted(Runnable handler);            // Задаёт обработчик удаления чека
onError(Consumer<Error> handler);          // Задаёт обработчик ошибки API
trackEnds(Runnable handler);               // Задаёт обработчик остановки трекера
```

## Переводы
Для работы с переводами есть следующие методы:
```java
Transfer transfer(TransferRequest.Builder builder);           // Инициирует перевод
Transfer[] getTransfers(GetTransfersRequest.Builder builder); // Получает массив переводов, соответствующих данным условиям
```

Пример перевода:
```java
Transfer transfer = client.transfer(TransferRequest
        .builder(1671756264, Currencies.USDT(), 99.9, "SPEND_ID")
        .comment("Now we're even"));
```

## Балансы, валюты и обменные курсы
Для получения балансов вашего приложения есть метод `getBalance`
```java
Balance[] getBalance(); // Получает массив балансов вашего приложения
```

Как правило, информацию о валютах можно получить через класс `Currencies`, но никто не запрещает использовать для этого метод `getCurrencies`
```java
CurrencyList getCurrencies(); // Возвращает актуальный список всех валют
```

Получить актуальную информацию об обменных курсах можно при помощи метода `getExchangeRates`
```java
ExchangeRateList getExchangeRates(); // Возвращает актуальный список обменных курсов
```
`ExchangeRateList` - это удобный класс, который позволяет фильтровать и работать сразу с целым массивом обменных курсов.

Также можно получить статистику вашего приложения, используя метод `getStats`
```java
AppStats getStats(GetStatsRequest.Builder builder); // Возвращает статистику вашего приложения
```

## Webhook
Webhook - это технология, позволяющая получать уведомления об оплате счёта прямо на ваш сервер. Чтобы ею воспользоваться, необходимо:
1) Открыть [CryptoBot](https://t.me/CryptoBot) (или [CryptoTestnetBot](https://t.me/CryptoTestnetBot) для тестовой сети). Перейдите в раздел Crypto Pay → Мои приложения, выберите своё приложение, затем перейдите в раздел Вебхуки, нажмите "Включить вебхуки" и укажите URL, на который будут отправляться уведомления.
2) Перейдите в код вашего сервера и добавьте обработку Webhook следующим образом:
```java
String requestJson = "WEBHOOK_REQUEST_BODY";
        
Webhook webhook = Webhook.fromJson(requestJson);
System.out.println("Webhook ID: " + webhook.updateId());
System.out.println("Invoice paid at " + webhook.payload().paidAt());
```
3) Для проверки целостности и достоверности полученной информации можно воспользоваться методом `Webhook.checkSignature`
```java
String requestJson = "WEBHOOK_REQUEST_BODY";
String signature = "SIGNATURE_FROM_HEADER";

boolean isValid = Webhook.checkSignature(signature, client.token(), requestJson);
```
_[Принцип проверки подлинности Webhook](https://help.crypt.bot/crypto-pay-api#verifying-webhook-updates)_
___
Есть вопросы или предложения по поводу библиотеки? Пиши __[сюда](https://t.me/jone501)__ 👈
