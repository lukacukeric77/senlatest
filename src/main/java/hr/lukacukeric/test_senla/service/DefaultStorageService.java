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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultStorageService implements StorageService {

    private Set<Book> books = new LinkedHashSet<>();

    @Override
    public void store(Book book) {
        books.add(book);
    }

    @Override
    public void loadResource(MultipartFile file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file.getInputStream());

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (var i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String isbn = element.getAttributes().getNamedItem("isbn").getNodeValue();
                String title = element.getElementsByTagName("Title").item(0).getChildNodes().item(0).getNodeValue();
                String author = element.getElementsByTagName("Author").item(0).getChildNodes().item(0).getNodeValue();
                books.add(new Book(isbn, title, author));
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
        // there is no check for Optional presence because user chose the book from the list of books
        return books.stream().filter(book -> book.getIsbn().equals(isbn)).findAny().orElse(null);
    }
}
