package org.example.controller;

import org.example.model.ShortUrl;
import org.example.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url, Model model) {
        try {
            String shortUrl = urlShortenerService.shortenUrl(url);
            model.addAttribute("shortUrl", shortUrl);
            model.addAttribute("originalUrl", url);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании короткой ссылки");
        }
        return "index";
    }

    // Редирект по короткой ссылке
    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginal(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(shortCode);

        if (originalUrl.isPresent()) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(originalUrl.get());
            return redirectView;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ссылка не найдена");
        }
    }

    // Статистика по ссылке
    @GetMapping("/stats/{shortCode}")
    public String getStats(@PathVariable String shortCode, Model model) {
        Optional<ShortUrl> urlStats = urlShortenerService.getUrlStats(shortCode);

        if (urlStats.isPresent()) {
            model.addAttribute("url", urlStats.get());
            return "stats";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ссылка не найдена");
        }
    }
}
