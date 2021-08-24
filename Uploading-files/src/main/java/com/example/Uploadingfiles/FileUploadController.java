package com.example.Uploadingfiles;

import com.example.Uploadingfiles.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

/*
A anotação @Controller serve para Direcionar o Spring MVC.
O Spring pega classes com essa anotação e busca por rotas.
Os Metodos São anotados(marcados) com @GetMapping ou @PostMapping
vinculando o caminho e a ação Http Especifica. (mais detalhes sobre os métodos)

 */
@Controller
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService){
        this.storageService = storageService;
    }

    /*
    Faz uma busca na lista atual de arquivos que foram carregados do StorageService
    e carrega em um modelo do thymeleaf
    E através do MvcUriComponentsBulder ele calcula um link para o objeto(recurso)
     */
    @GetMapping("/")
    public String listUploadedFiles(Model model)throws IOException {
        model.addAllAttributes("files"
                ,storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class
                                        , path.getFileName().toString()
                                                .build()
                                                .toUri
                                                .toString()
                                                .collect(Collectors.toList()))));
        return "uploadForm";
    }

    /*
    Carrega um recurso especifico (se existir) e o envia
     ao navegador para download usando um
     cabeçalho de resposta Content-Disposition
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
       Resource file = storageService.loadAsResource(filename);

       return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION
               , "attachment; filename=\""
                       + file.getFilename() + "\"" ).body(file);
    }

    /*
        Manipula uma mensagem com varias partes em relação a um arquivo
        e envia ao StorageService para ser salva
     */
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file")MultipartFile file
            , RedirectAttributes redirectAttributes){
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message"
                , "You successfully uploaded "
                        + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

     @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handStorageFileNotFound(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
     }
}
