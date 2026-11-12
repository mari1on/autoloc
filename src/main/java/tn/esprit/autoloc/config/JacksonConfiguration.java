package tn.esprit.autoloc.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;

@Configuration
public class JacksonConfiguration {

    //sm8
    @Bean
    public JsonMapperBuilderCustomizer agenceSerializationCustomizer() {
        return builder -> builder.addMixIn(Agence.class, AgenceMixin.class);
    }

    private abstract static class AgenceMixin {
        @JsonIgnore(false)
        List<Employe> employes;
    }
}
