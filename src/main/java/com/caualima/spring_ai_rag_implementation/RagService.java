package com.caualima.spring_ai_rag_implementation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    @Value("classpath:/prompts/rag-template.st")
    private Resource promptTemplate;

    @Autowired
    public RagService(VectorStore vectorStore, ChatClient.Builder chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient.build();
    }

    public String generateContent(String message) {
        String information = retriveContent(message);

        SystemPromptTemplate template = new SystemPromptTemplate(promptTemplate);
        Prompt prompt = new Prompt(List.of(
                template.createMessage(Map.of("information", information.isEmpty()? "Sem contexto" : information )),
                new UserMessage(message)
        ));

        return chatClient.prompt(prompt).call().content();
    }

    public String retriveContent(String message) {
        List<Document> similarContents = vectorStore
                .similaritySearch(SearchRequest
                        .builder()
                        .query(message)
                        .topK(4)
                        .build()
        );

        return similarContents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }

    public void addNewContent(String content) {
        Document document = new Document(content);
        vectorStore.add(List.of(document));
    }
}
