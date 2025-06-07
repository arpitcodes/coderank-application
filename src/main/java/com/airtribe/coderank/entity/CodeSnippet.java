package com.airtribe.coderank.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name="snippets")
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long snippetId;
    @Column(unique = true, nullable = false)
    private String snippetFilePath;

    @Column(nullable = false)
    private String languageName;

    private String executionId;
    private String outputFilePath;

    @UpdateTimestamp
    @Column(name="executed_at")
    private Date executedAt;
    @ManyToOne
    private User user;

    public CodeSnippet(Long snippetId, String snippetFilePath, String executionId, String outputFilePath, String languageName, Date executedAt, User user) {
        this.snippetId = snippetId;
        this.snippetFilePath = snippetFilePath;
        this.languageName = languageName;
        this.outputFilePath = outputFilePath;
        this.executionId = executionId;
        this.executedAt = executedAt;
        this.user = user;
    }

    public CodeSnippet() {
    }


    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public Long getSnippetId() {
        return snippetId;
    }

    public void setSnippetId(Long snippetId) {
        this.snippetId = snippetId;
    }



    public String getSnippetFilePath() {
        return snippetFilePath;
    }

    public void setSnippetFilePath(String snippetFilePath) {
        this.snippetFilePath = snippetFilePath;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Date getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Date executedAt) {
        this.executedAt = executedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
