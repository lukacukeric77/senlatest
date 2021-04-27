package hr.lukacukeric.test_senla.service;

import hr.lukacukeric.test_senla.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultStorageService implements StorageService {

    private Set<Book> books = new LinkedHashSet<>();

    @Override
    public void store(Book book) {
        books.add(book);
    }

    @Override
    public void loadResource(MultipartFile file)
        throws ParserConfigurationException, IOException, SAXException {
        if (!file.isEmpty()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file.getInputStream());

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (var i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String isbn = element.getAttributes().getNamedItem("isbn").getNodeValue();
                    String title = element.getElementsByTagName("Title").item(0).getChildNodes()
                        .item(0).getNodeValue();
                    String author = element.getElementsByTagName("Author").item(0).getChildNodes()
                        .item(0).getNodeValue();
                    books.add(new Book(isbn, title, author));
                }
            }
        }
    }

    @Override
    public Set<Book> getBookList() {
        return Collections.unmodifiableSet(books);
    }

    @Override
    public void remove(String isbn) {
        this.books = books.stream().filter(filteredIsbn ->
            !filteredIsbn.getIsbn().equals(isbn)).collect(Collectors.toSet());
    }


    @Override
    public Book getBookByIsbn(String isbn) {
        return books.stream().filter(book -> book.getIsbn().equals(isbn)).findAny().orElse(
            null); // I could "dare" to other here null because this is in EDIT and ISBN is therefore ALWAYS set
    }

    @Override
    public Set<Book> findFromSearch(String word) {
        return books.stream()
            .filter(book -> book.getIsbn().toLowerCase().contains(word.toLowerCase())
                || book.getAuthor().toLowerCase().contains(word.toLowerCase()) || book.getTitle()
                .toLowerCase().contains(word.toLowerCase()))
            .collect(Collectors.toSet());
    }

    @Override
    public Boolean searchForPossibleCopyOfISBN(String isbn) {
        return books.stream().anyMatch(book -> book.getIsbn().contains(isbn));
    }


    @Override
    public Set<Book> sortByISBN() {
        return books.stream().sorted(Book.sortByIsbn())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<Book> sortByTitle() {
        return books.stream().sorted(Book.sortByTitle())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<Book> sortByAuthor() {
        return books.stream().sorted(Book.sortByAuthor())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
