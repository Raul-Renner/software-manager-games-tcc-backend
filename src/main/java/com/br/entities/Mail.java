package com.br.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Mail {

    private String from;

    private String to;

    private String subject;

    private HtmlTemplate htmlTemplate;

    @Data
    public static class HtmlTemplate {
        private String template;

        private Map<String, Object> props;

        public HtmlTemplate(String template, Map<String, Object> props){
            this.template = template;
            this.props = props;
        }

    }

}
