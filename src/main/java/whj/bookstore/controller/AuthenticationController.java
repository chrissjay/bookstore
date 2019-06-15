package whj.bookstore.controller;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whj.bookstore.common.IConstants;
import whj.bookstore.common.MD5;
import whj.bookstore.model.Book;
import whj.bookstore.model.Category;
import whj.bookstore.model.JsonBean;
import whj.bookstore.model.User;
import whj.bookstore.service.BookService;
import whj.bookstore.service.CategoryService;
import whj.bookstore.service.UserRoleRService;
import whj.bookstore.service.UserService;
import whj.bookstore.utils.ParamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleRService userRoleRService;

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/register_do", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public JsonBean registerDo(HttpServletRequest request) {
        JsonBean reJson = new JsonBean();
        Map paramMap = ParamUtils.handleServletParameter(request);
        String userCode = MapUtils.getString(paramMap, "userCode");
        String userPwd = MD5.next(MapUtils.getString(paramMap, "userPwd"));
        String userName = MapUtils.getString(paramMap, "userName");
        String userAddr = MapUtils.getString(paramMap, "userAddr");
        String userPhone = MapUtils.getString(paramMap, "userPhone");
        userService.registerUser(userCode, userPwd, userName, userAddr, userPhone);
        userRoleRService.registerUserRoleR(userCode);
        reJson.setStatus(IConstants.RESULT_INT_SUCCESS);
        reJson.setMessage("注册成功");
        return reJson;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login_in", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public JsonBean loginIn(HttpServletRequest request) {
        JsonBean reJson = new JsonBean();
        Map paramMap = ParamUtils.handleServletParameter(request);
        String userCode = MapUtils.getString(paramMap, "userCode");
        String userPwd = MD5.next(MapUtils.getString(paramMap, "userPwd"));
        // shiro认证
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userCode, userPwd);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            reJson.setMessage("账户不存在");
            return reJson;
        } catch (DisabledAccountException e) {
            reJson.setMessage("账户存在问题");
            return reJson;
        } catch (AuthenticationException e) {
            reJson.setMessage("密码错误");
            return reJson;
        } catch (Exception e) {
            log.info("登陆异常", e);
            reJson.setMessage("登陆异常");
            return reJson;
        }
        reJson.setStatus(IConstants.RESULT_INT_SUCCESS);
        String res = subject.getPrincipals().toString();
        if (subject.hasRole("admin")) {
            res = res + "----------你拥有admin权限";
        }
        if (subject.hasRole("guest")) {
            res = res + "----------你拥有guest权限";
        }
        reJson.setData(res);
        reJson.setMessage("登陆成功");
        return reJson;
    }

    @RequestMapping(value = "/home")
    public String home(Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        Map<Integer, String> categoriesByMap = categoryService.listAllCategoriesByMap();
        Map<Category, List<Book>> booksByCategory = bookService.listAllBooksByCategory();
        paramMap.put("categories", categoriesByMap);
        paramMap.put("booksMap", booksByCategory);
        return "home";
    }

    @RequestMapping(value = "/myBookshelf")
    public String myBookshelf(Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        List<Book> bookList = bookService.listMySellBooks(user.getId());
        paramMap.put("askBooks", bookList);
        paramMap.put("books", bookList);
        return "myBookshelf";
    }

    @RequestMapping(value = "/upLoadSell")
    public String upLoadSell(Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        List<Category> categories = categoryService.getAllCategories();
        paramMap.put("categories", categories);
        return "upLoadSell";
    }

    @ResponseBody
    @RequestMapping(value = "/upLoadImg", method = {RequestMethod.POST})
    public JsonBean uploadFile (@RequestParam("file") MultipartFile uploadfile) {
        JsonBean reJson = new JsonBean();
        if (uploadfile.isEmpty()) {
            reJson.setMessage("空文件！");
            return reJson;
        }
        String imgName = bookService.lastId()+1 + ".jpg";
        try{
            byte[] bytes = uploadfile.getBytes();
            String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            Path path = Paths.get(rootPath + "static/statics/img/book-list/article/" + imgName);
            Files.write(path, bytes);
            String sourcePath = rootPath.replaceAll("classes/", "").replaceAll("target/", "");
            Path newPath = Paths.get(sourcePath + "src/main/resources/static/statics/img/book-list/article/" + imgName);
            Files.write(newPath, bytes);
        }
        catch (IOException e) {
            return null;
        }
        reJson.setStatus(IConstants.RESULT_INT_SUCCESS);
        reJson.setMessage("上传图片成功");
        return reJson;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadsell_do", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public JsonBean upLoadSellDo(HttpServletRequest request) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        JsonBean reJson = new JsonBean();
        Map paramMap = ParamUtils.handleServletParameter(request);
        String name = MapUtils.getString(paramMap, "name");
        String author = MapUtils.getString(paramMap, "author");
        String press = MapUtils.getString(paramMap, "press");
        double price = MapUtils.getDoubleValue(paramMap, "price");
        String description = MapUtils.getString(paramMap, "description");
        int cid = MapUtils.getIntValue(paramMap, "cid");
        int uid = user.getId();
        bookService.upLoadBook(name, author, press, price, cid, uid, description);
        reJson.setStatus(IConstants.RESULT_INT_SUCCESS);
        reJson.setMessage("上传成功");
        return reJson;
    }

    @RequestMapping(value = "/bookStore/{categoryid}/{pageid}")
    public String bookStore(@PathVariable int categoryid, @PathVariable int pageid,Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        List<Category> categoryList = categoryService.getAllCategories();
        Category category = categoryService.getCategoryById(categoryid);
        PageHelper.startPage(pageid, 20);
        List<Book> bookList = bookService.listOneKindOfBooks(categoryid);
        paramMap.put("categories", categoryList);
        paramMap.put("category", category);
        paramMap.put("books", bookList);
        paramMap.put("pageid", pageid);
        return "bookStore";
    }

    @RequestMapping(value = "/searchBooks")
    public String searchBookStore(String bookName, Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        List<Book> books = bookService.searchBook('%' + bookName + '%');
        paramMap.put("books", books);
        return "searchBookStore";
    }

    @RequestMapping(value = "/askBookStore/{pageid}")
    public String askBookStore(@PathVariable int pageid,Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        PageHelper.startPage(pageid, 20);
        List<Book> bookList = bookService.listAskBook();
        paramMap.put("books", bookList);
        paramMap.put("pageid", pageid);
        return "askBookStore";
    }

    @RequestMapping(value = "/bookDetail/{bookid}")
    public String bookDetail(@PathVariable int bookid, Map<String, Object> paramMap) {
        User user = userService.getNowUser(SecurityUtils.getSubject().getPrincipal().toString());
        paramMap.put("user", user);
        Book book = bookService.getBookById(bookid);
        paramMap.put("book", book);
        paramMap.put("seller", userService.getUser(book.getUid()));
        return "bookDetail";
    }

}
