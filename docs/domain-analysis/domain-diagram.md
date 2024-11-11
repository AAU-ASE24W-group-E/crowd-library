# Domain Analysis Daigram

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
    - [x] BookOwnership
    - [x] Location
    - [ ] Lending
    - [ ] 
- [ ] Add Connections



## Diagram

```mermaid
classDiagram
    class Book {
        UUID id
        String title
        String isbn
        String format
        String language
        String publisher
        Int publishingYear
        String coverId
        Author[] authors
    }

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

    class BookOwnership {
        UUID id
        UUID userId
        UUID bookId
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
