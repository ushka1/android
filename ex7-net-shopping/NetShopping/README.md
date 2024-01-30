# Net Shopping

## Requirements met

- 3.0 pobieranie produktów z aplikacji serwerowej
  - Aplikacja pobiera produkty z Firebase Firestore.
- 3.5 pobieranie dodatkowo kategorii
  - Aplikacja pobiera kategorie z Firebase Firestore.
- 4.0 wyświetlanie produktów oraz ich kategorii na dwóch osobnych listach
  - W `MainActivity` znajdują się dwa RecyclerView, jeden z produktami, drugi z kategoriami.
- 4.5 zapis danych lokalnie
  - Firebase Firestore standardowo zapisuje dane lokalnie, więc dodatkowa konfiguracja nie była potrzebna ([źródło](https://firebase.google.com/docs/firestore/manage-data/enable-offline#:~:text=For%20Android%20and%20Apple%20platforms%2C%20offline%20persistence%20is%20enabled%20by%20default.%20To%20disable%20persistence%2C%20set%20the%20PersistenceEnabled%20option%20to%20false.)).
- 5.0 dodawanie produktów z poziomu aplikacji mobilnej
  - W `MainActivity` znajduje się przycisk przekierowujący do `AddProductActivity` z poziomu, którego można dodawać produkty za pomocą formularza.
