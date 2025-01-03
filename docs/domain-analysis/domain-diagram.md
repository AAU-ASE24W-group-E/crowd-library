# Domain Analysis Diagram

### Import notes:
- book, author entity is _derived_ from ISBN API response
    - https://openlibrary.org/dev/docs/api/search
    - Example Request: https://openlibrary.org/search.json?isbn=9781611748864
    - Example Response [example.json](./example_response_isbn_api.json)


### ToDo
- [ ] Add missing classes
    - [x] Book
    - [x] Author
    - [x] User
    - [x] OwnBook
    - [x] Location
    - [ ] Lending
    - [ ] Wishlist
    - [ ] 
- [ ] Add Connections



## Diagram

```mermaid
classDiagram
    Book --> BookFormat
    Book o-- Author

    OwnBook --> BookStatus 

    class Book {
        UUID id
        String title
        String isbn
        BookFormat format
        String language
        String publisher
        Int publishingYear
        String coverId
        Author[] authors
    }

    class BookFormat {
        <<enumeration>>
        HARDCOVER
        PAPERBACK
        EBOOK
        AUDIOBOOK
    }

    note for BookFormat "Any Electronic, PDF, EPUB, etc is mapped to EBOOK"
    note for BookFormat "Any Audio, CD, MP3, etc is mapped to AUDIOBOOK"

    class Author {
        UUID id
        String key
        String name
    }

    class User {
        UUID id
        String username
        String email
        Location location
        ...
    }

    class OwnBook {
        UUID id
        User owner
        Book book
        Bool lendable
        Bool giftable
        Bool exchangeable
        BookStatus status 
    }

    class BookStatus {
        <<enumeration>>
        lent
        exchanged
        gifted
        inLibrary
    }

    class Location {
        UUID id
        Float lon
        Float lat
    }

```
---

### Explainations
- book coverId -> can used to get cover image -> example https://covers.openlibrary.org/b/olid/OL51694024M-L.jpg
- Location -> WGS84 https://epsg.io/4326
- Author.key -> unique alternative identified, case-insensitive, generated from author's name by reducing all forenames to initials and full surname.
