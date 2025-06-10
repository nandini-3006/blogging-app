package com.scalar.blogapp.articles;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateArticleRequest {
    @Nullable
    private String title;
    @Nullable
    private String body;
    @Nullable
    private String subtitle;
}
