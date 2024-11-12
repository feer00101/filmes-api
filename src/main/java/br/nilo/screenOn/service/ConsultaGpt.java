package br.nilo.screenOn.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaGpt {

    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService("sua key aqui");
        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt("traduzaa para o portugues o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
