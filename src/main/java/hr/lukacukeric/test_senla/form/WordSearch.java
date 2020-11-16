package hr.lukacukeric.test_senla.form;

import javax.validation.constraints.NotEmpty;

public class WordSearch {

    @NotEmpty(message = "field cannot be empty")
    private String searchedWord;

    public WordSearch(String searchedWord) {
        this.searchedWord = searchedWord;
    }

    public String getSearchedWord() {
        return searchedWord;
    }
}
