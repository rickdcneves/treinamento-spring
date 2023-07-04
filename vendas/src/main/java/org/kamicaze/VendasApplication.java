package org.kamicaze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class VendasApplication {
    @Autowired
    @Qualifier("nome")
    private String nomeAPP;

    @Value("${application.name}")
    private String nomeAPPConfigfile;
    @GetMapping("/ola")
    public String ola(){
        return nomeAPP+"\n"+nomeAPPConfigfile;
    }
    public static void main(String args[]){
        SpringApplication.run(VendasApplication.class,args);
    }
}
