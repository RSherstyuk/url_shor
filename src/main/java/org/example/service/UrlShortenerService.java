package org.example.service;

import org.example.model.ShortUrl;
import org.example.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    private ShortUrlRepository repository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public String shortenUrl(String originalUrl) {
        Optional<ShortUrl> existing = repository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return baseUrl + "/" + existing.get().getShortCode();
        }

        String shortCode;
        do {
            shortCode = generateRandomCode();
        } while (repository.existsByShortCode(shortCode));

        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode);
        repository.save(shortUrl);

        return baseUrl + "/" + shortCode;
    }

    // Получение оригинального URL по короткому коду
    public Optional<String> getOriginalUrl(String shortCode) {
        Optional<ShortUrl> shortUrl = repository.findByShortCode(shortCode);
        if (shortUrl.isPresent()) {
            // Увеличиваем счетчик кликов
            ShortUrl url = shortUrl.get();
            url.setClickCount(url.getClickCount() + 1);
            repository.save(url);

            return Optional.of(url.getOriginalUrl());
        }
        return Optional.empty();
    }

    // Генерация случайного кода
    private String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    // Получение статистики
    public Optional<ShortUrl> getUrlStats(String shortCode) {
        return repository.findByShortCode(shortCode);
    }
}
