package com.scalar.blogapp.articles;

import com.scalar.blogapp.users.UserEntity;
import com.scalar.blogapp.users.UserRepository;
import com.scalar.blogapp.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articlesRepository;

    @Autowired
    private UserRepository usersRepository;

    public ArticleService(ArticleRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public ArticleEntity getArticleBySlug(String slug) {
        return articlesRepository.findBySlug(slug)
                .orElseThrow(() -> new ArticleNotFoundException(slug));
    }

    public ArticleEntity createArticle(CreateArticleDTO a, Long authorId)
            throws UserService.UserNotFoundException {

        var author = usersRepository.findById(authorId)
                .orElseThrow(() -> new UserService.UserNotFoundException("User with id " + authorId + " not found"));

        return articlesRepository.save(
                ArticleEntity.builder()
                        .title(a.getTitle())
                        .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                        .body(a.getBody())
                        .author(author)
                        .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a) {
        var article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (a.getTitle() != null) {
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }

        if (a.getBody() != null) {
            article.setBody(a.getBody());
        }

        if (a.getSubtitle() != null) {
            article.setSubtitle(a.getSubtitle());
        }

        return articlesRepository.save(article);
    }

    // --- Exceptions ---
    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + " not found");
        }

        public ArticleNotFoundException(Long id) {
            super("Article with id: " + id + " not found");
        }
    }
}
