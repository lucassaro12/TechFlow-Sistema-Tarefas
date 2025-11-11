package br.com.fecaf.controller;

import br.com.fecaf.model.Moto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/motos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MotoController {

    // A lista que vai guardar as motos em memória
    private List<Moto> motos = new ArrayList<>();

    @PostConstruct
    public void carregarJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();


            InputStream inputStream = getClass().getResourceAsStream("/data/motos.json");

            // Checagem para ver se o arquivo foi encontrado
            if (inputStream == null) {
                System.err.println("❌ Erro: Não foi possível encontrar o arquivo /data/motos.json");
                return;
            }

            // Lê o JSON e coloca na lista "motos"
            motos = objectMapper.readValue(inputStream, new TypeReference<List<Moto>>() {});
            System.out.println("✅ JSON de motos carregado: " + motos.size() + " motos.");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar o JSON de motos: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Moto> listarMotos() {
        return motos;
    }

    @GetMapping("/{id}")
    public Moto buscarPorId(@PathVariable Long id) {

        for (Moto moto : motos) {
            if (moto.getId().equals(id)) {
                return moto; // Retorna a moto se o ID for igual
            }
        }

        return null; // Retorna nulo se não encontrar
    }
}