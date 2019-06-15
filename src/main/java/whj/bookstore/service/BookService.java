package whj.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whj.bookstore.dao.BookMapper;
import whj.bookstore.dao.CategoryMapper;
import whj.bookstore.model.Book;
import whj.bookstore.model.Category;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public void upLoadBook(String name, String author, String press, double price, int cid, int uid, String description) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPress(press);
        book.setPrice(price);
        book.setCid(cid);
        book.setUid(uid);
        book.setDescription(description);
        bookMapper.insertSelective(book);
    }

    public Map<Category, List<Book>> listAllBooksByCategory() {
        // 获取所有Category
        List<Category> categories = categoryMapper.selectAllCategories();
        // 使用LinkedHashMap存储，若使用HashMap则无序
        Map<Category,List<Book>> booksMap = new LinkedHashMap<>();
        for (Category category : categories) {
            List<Book> books = bookMapper.getListByCategoryId(0,5, category.getId());
            booksMap.put(category,books);
        }
        return booksMap;
    }

    public List<Book> listOneKindOfBooks(int cid) {
        return bookMapper.selectByCid(cid);
    }

    public List<Book> listMySellBooks(int uid) {
        return bookMapper.selectByUid(uid);
    }

    public Book getBookById(int id) {
        Book book = bookMapper.selectByPrimaryKey(id);
        return book;
    }

    public int lastId() {
        return bookMapper.lastId();
    }

    public List<Book> listAskBook() {
        return bookMapper.selectAllAskBooks();
    }

    public List<Book> searchBook(String bookName) {
        return bookMapper.selectSearchBooks(bookName);
    }

}
