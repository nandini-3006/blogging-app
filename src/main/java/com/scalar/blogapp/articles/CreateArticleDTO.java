package com.scalar.blogapp.articles;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateArticleDTO {
    @NonNull
    private String title;
    @NonNull
    private String body;
    @Nullable
    private String subtitle;
}
