// File: src/main/java/com/scalar/blogapp/articles/ArticlesController.java
package com.scalar.blogapp.articles;

import com.scalar.blogapp.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping("")
    String getArticles() { return "get all articles"; }

    @GetMapping("/{id}")
    String getArticleById(@PathVariable("id") String id) {
        return "get article with id: " + id;
    }

    @PostMapping("")
    String createArticle(@AuthenticationPrincipal UserEntity user) {
        return "create article called by " + user.getUsername();
    }
}