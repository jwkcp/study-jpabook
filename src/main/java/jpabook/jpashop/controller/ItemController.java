package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    private void createDummyData() {
        Item item1 = new Book();
        item1.setName("책1");
        item1.setPrice(1000);
        item1.setStockQuantity(100);

        Item item2 = new Book();
        item2.setName("책2");
        item2.setPrice(2000);
        item2.setStockQuantity(200);

        itemService.saveItem(item1);
        itemService.saveItem(item2);
    }

    @GetMapping("/items/new")
    public String createForm(Model model) {
        // TODO: 나중에 삭제
        createDummyData();

        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        // 예제를 단순화 하기 위해서 Book으로 받고 캐스팅함
        Book item = (Book)itemService.findOne(itemId);
        BookForm form = new BookForm();

        form.setId(item.getId());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) {
        Book book = new Book();
        book.setId(form.getId());
        book.setIsbn(form.getIsbn());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());

//        itemService.updateItem(book);
        itemService.saveItem(book);

        return "redirect:/items";
    }
}
